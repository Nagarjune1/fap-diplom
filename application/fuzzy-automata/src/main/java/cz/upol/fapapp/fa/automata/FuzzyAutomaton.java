package cz.upol.fapapp.fa.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.fapapp.fa.min.AutomataPartitioner;
import cz.upol.fapapp.fa.min.AutomatonPartsWrapper;
import cz.upol.fapapp.fa.min.RightPartitioner;
import cz.upol.fapapp.fa.modifs.StatesCreator;

/**
 * An implementation of fuzzy automaton.
 * 
 * @author martin
 *
 */
public class FuzzyAutomaton extends BaseFuzzyAutomaton {

	public FuzzyAutomaton(Alphabet alphabet, Set<State> states,
			FuzzyTernaryRelation<State, Symbol, State> transitionFunction, FuzzySet<State> initialStates,
			FuzzySet<State> finalStates) {
		super(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public FuzzyState computeWord(Word word) {
		return computeSymbolBySymbol(word);
	}

	protected FuzzyState computeSymbolBySymbol(Word word) {
		FuzzyState currentFuzzyState = new FuzzyState(initialStates);

		for (Symbol over : word.getSymbols()) {
			Logger.get().moreinfo("Automaton is in " + currentFuzzyState + " with input " + over);
			currentFuzzyState = stepOver(currentFuzzyState, over);

		}

		Logger.get().moreinfo("Automaton ended in " + currentFuzzyState);
		FuzzySet<State> resultSet = FuzzySet.intersect(currentFuzzyState, finalStates);
		return new FuzzyState(resultSet);
	}

	protected FuzzyState stepOver(FuzzyState fromFuzzyState, Symbol over) {
		Map<State, Degree> toFuzzyStateMap = CollectionsUtils.createMap(states, Degree.ZERO);

		for (State from : states) {
			Map<Triple<State, Symbol, State>, Degree> transitions = transitionFunction.get(from, over);
			Degree fromDegree = fromFuzzyState.degreeOf(from);

			for (Triple<State, Symbol, State> transition : transitions.keySet()) {
				State to = transition.getThird();

				Degree transitionDegree = transitions.get(transition);

				Degree toDegreeOld = toFuzzyStateMap.get(to);
				Degree toDegreeNew = TNorms.getTnorm().tnorm(fromDegree, transitionDegree);
				Degree toDegree = TNorms.getTnorm().tconorm(toDegreeOld, toDegreeNew);

				toFuzzyStateMap.put(to, toDegree);
			}

		}

		FuzzyState toFuzzyState = new FuzzyState(toFuzzyStateMap);
		return toFuzzyState;
	}

	@Override
	public Degree degreeOfWord(Word word) {
		FuzzyState ended = computeWord(word);
		return ended.maxDegree();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public BaseFuzzyAutomaton determinise() {
		StatesCreator creator = new StatesCreator();
		FuzzyState initState = new FuzzyState(initialStates);

		Map<FuzzyState, State> readyStates = new HashMap<>();
		State initialState = creator.next();
		readyStates.put(initState, initialState);
		Logger.get().moreinfo("Starting determinisation from " + initState + " aka " + initialState);

		Queue<FuzzyState> waitingStates = new LinkedList<>();
		waitingStates.add(initState);

		Set<Triple<State, Symbol, State>> transitions = new HashSet<>();

		while (!waitingStates.isEmpty()) {
			FuzzyState currentState = waitingStates.poll();
			State fromState = readyStates.get(currentState);

			for (Symbol over : alphabet) {
				FuzzyState newState = stepOver(currentState, over);
				State toState = readyStates.get(newState);
				if (toState == null) {
					toState = creator.next();
					readyStates.put(newState, toState);
					waitingStates.add(newState);
					Logger.get().moreinfo("Created new state for " + newState + " aka " + toState);
				}
				Triple<State, Symbol, State> triple = new Triple<>(fromState, over, toState);
				transitions.add(triple);
			}
		}

		Set<State> newStates = new HashSet<>(readyStates.values());
		Alphabet newAlphabet = new Alphabet(alphabet);
		FuzzyTernaryRelation<State, Symbol, State> newTransitionFunction = //
				new FuzzyTernaryRelation<>(CollectionsUtils.createMap(transitions, Degree.ONE));
		FuzzySet<State> newInitialStates = //
				CollectionsUtils.singletonFuzzySet(initialState);
		FuzzySet<State> newFinalStates = //
				createFinalStates(readyStates);

		return new FuzzyAutomaton(newAlphabet, newStates, newTransitionFunction, newInitialStates, newFinalStates);
	}

	protected FuzzySet<State> createFinalStates(Map<FuzzyState, State> readyStates) {
		Map<State, Degree> map = new HashMap<>();

		for (FuzzyState fuzzyState : readyStates.keySet()) {
			State state = readyStates.get(fuzzyState);

			FuzzySet<State> finalized = FuzzySet.intersect(fuzzyState, finalStates);

			Degree degree = finalized.maxDegree();
			map.put(state, degree);
		}

		return new FuzzySet<>(map);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public BaseFuzzyAutomaton minimise() {
		return minimise(Degree.ZERO);
	}

	@Override
	public BaseFuzzyAutomaton minimise(Degree delta) {
		Logger.get().moreinfo("Minimising automaton with delta " + delta);
		FuzzyAutomaton prepared = prepare();

		Set<Set<State>> partitions = computePartitionsClosure(prepared, delta);

		FuzzyAutomaton minimised = computeAutomaton(partitions);

		return minimised;
	}

	private FuzzyAutomaton prepare() {
		// TODO remove redundant states
		return this;
	}

	private Set<Set<State>> computePartitionsClosure(FuzzyAutomaton prepared, Degree delta) {
		AutomataPartitioner partitioner = new RightPartitioner(this, delta);
		return partitioner.compute().getPartitions();
	}

	private FuzzyAutomaton computeAutomaton(Set<Set<State>> partitions) {
		Logger.get().moreinfo("Creating minimalised automaton by " + partitions);
		
		AutomatonPartsWrapper wrapper = new AutomatonPartsWrapper(this);
		Map<Set<State>, State> partsToStates = generateStates(partitions);

		Map<State, Degree> initials = new HashMap<>(partitions.size());
		Map<State, Degree> finals = new HashMap<>(partitions.size());

		Map<Triple<State, Symbol, State>, Degree> transitions = new HashMap<>(
				partitions.size() * partitions.size() * alphabet.size());

		for (Set<State> partition : partsToStates.keySet()) {
			State state = partsToStates.get(partition);

			Degree degreeOfInitial = wrapper.initalIn(partition);
			Degree degreeOfFinal = wrapper.finalIn(partition);

			initials.put(state, degreeOfInitial);
			finals.put(state, degreeOfFinal);
		}

		for (Set<State> fromPart : partsToStates.keySet()) {
			State fromState = partsToStates.get(fromPart);
			for (Set<State> toPart : partsToStates.keySet()) {
				State toState = partsToStates.get(toPart);
				for (Symbol over : alphabet) {
					Degree degree = wrapper.transition(fromPart, over, toPart);
					Triple<State, Symbol, State> triple = new Triple<State, Symbol, State>(fromState, over, toState);
					transitions.put(triple, degree);
				}
			}
		}

		Set<State> newStates = new HashSet<>(partsToStates.values());
		Alphabet newAlphabet = new Alphabet(alphabet);
		FuzzyTernaryRelation<State, Symbol, State> newTransitionFunction = new FuzzyTernaryRelation<>(transitions);
		FuzzySet<State> newInitialStates = new FuzzySet<>(initials);
		FuzzySet<State> newFinalStates = new FuzzySet<>(finals);

		return new FuzzyAutomaton(newAlphabet, newStates, newTransitionFunction, newInitialStates, newFinalStates);
	}

	private Map<Set<State>, State> generateStates(Set<Set<State>> partitions) {
		StatesCreator creator = new StatesCreator();

		Map<Set<State>, State> result = new HashMap<>(partitions.size());
		for (Set<State> partition : partitions) {
			String label = generateStateLabel(partition);
			State state = creator.next(label);

			result.put(partition, state);
		}

		return result;
	}

	private String generateStateLabel(Set<State> partition) {
		return partition.stream() //
				.map((s) -> s.getLabel()).collect(Collectors.joining("_"));
	}

	///////////////////////////////////////////////////////////////////////////

}
