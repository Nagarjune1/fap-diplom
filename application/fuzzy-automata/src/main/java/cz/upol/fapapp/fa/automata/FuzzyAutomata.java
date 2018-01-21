package cz.upol.fapapp.fa.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.CollectionsUtils;
import cz.upol.fapapp.core.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;

public class FuzzyAutomata extends BaseFuzzyAutomata {

	public FuzzyAutomata(Alphabet alphabet, Set<State> states,
			FuzzyTernaryRelation<State, Symbol, State> transitionFunction, FuzzySet<State> initialStates,
			FuzzySet<State> finalStates) {
		super(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public FuzzyState computeWord(Word word) {
		return computeSymbolBySymbol(word);
	}

	private FuzzyState computeSymbolBySymbol(Word word) {
		FuzzyState currentFuzzyState = new FuzzyState(initialStates);

		for (Symbol over : word.getSymbols()) {
			Logger.get().moreinfo("Automata is in " + currentFuzzyState + " with input " + over);
			currentFuzzyState = stepOver(currentFuzzyState, over);

		}

		Logger.get().moreinfo("Automata ended in " + currentFuzzyState);
		FuzzySet<State> resultSet = FuzzySet.intersect(currentFuzzyState, finalStates);
		return new FuzzyState(resultSet);
	}

	private FuzzyState stepOver(FuzzyState fromFuzzyState, Symbol over) {
		Map<State, Degree> toFuzzyStateMap = CollectionsUtils.createMap(states, Degree.ZERO);

		for (State from : states) {
			Map<Triple<State, Symbol, State>, Degree> transitions = transitionFunction.get(from, over);
			Degree fromDegree = fromFuzzyState.degreeOf(from);

			for (Triple<State, Symbol, State> transition : transitions.keySet()) {
				State to = transition.getThird();

				Degree transitionDegree = transitions.get(transition);

				Degree toDegreeOld = toFuzzyStateMap.get(to);
				Degree toDegreeNew = Degree.infimum(fromDegree, transitionDegree);
				Degree toDegree = Degree.supremum(toDegreeOld, toDegreeNew);

				toFuzzyStateMap.put(to, toDegree);
			}

		}

		FuzzyState toFuzzyState = new FuzzyState(toFuzzyStateMap);
		return toFuzzyState;
	}

	@Override
	public Degree degreeOfWord(Word word) {
		FuzzyState ended = computeWord(word);
		return FuzzySet.maxDegree(ended);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public BaseFuzzyAutomata determinise() {
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

		return new FuzzyAutomata(newAlphabet, newStates, newTransitionFunction, newInitialStates, newFinalStates);
	}

	private FuzzySet<State> createFinalStates(Map<FuzzyState, State> readyStates) {
		Map<State, Degree> map = new HashMap<>();

		for (FuzzyState fuzzyState : readyStates.keySet()) {
			State state = readyStates.get(fuzzyState);

			FuzzySet<State> finalized = FuzzySet.intersect(fuzzyState, finalStates);

			Degree degree = FuzzySet.maxDegree(finalized);
			map.put(state, degree);
		}

		return new FuzzySet<>(map);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public BaseFuzzyAutomata minimise() {
		// TODO Auto-generated method stub
		return null;
	}

	///////////////////////////////////////////////////////////////////////////

}
