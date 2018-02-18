package cz.upol.fapapp.fa.modifs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.fapapp.fa.automata.BaseFuzzyAutomaton;
import cz.upol.fapapp.fa.automata.FuzAutWithEpsilonMoves;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

/**
 * Structure similar to {@link BaseFuzzyAutomaton}, but more technical and with
 * encapusulation allowing simple and consistend modifications.
 * 
 * @author martin
 *
 */
public class MutableAutomatonStructure {
	private final Set<Symbol> alphabet;
	private final Set<State> states;
	private final Map<Triple<State, Symbol, State>, Degree> transitionFunction;
	private final Map<State, Degree> initialStates;
	private final Map<State, Degree> finalStates;

	private boolean hasEpsilonRules;

	public MutableAutomatonStructure(Set<Symbol> alphabet, Set<State> states,
			Map<Triple<State, Symbol, State>, Degree> transitionFunction, Map<State, Degree> initialStates,
			Map<State, Degree> finalStates) {
		super();
		this.alphabet = alphabet;
		this.states = states;
		this.transitionFunction = transitionFunction;
		this.initialStates = initialStates;
		this.finalStates = finalStates;
	}

	public MutableAutomatonStructure(Alphabet alphabet, Set<State> states,
			FuzzyTernaryRelation<State, Symbol, State> transitionFunction, FuzzySet<State> initialStates,
			FuzzySet<State> finalStates) {
		this(alphabet, states, transitionFunction.getTuplesWithDegree(), initialStates.toMap(), finalStates.toMap());
	}

	public MutableAutomatonStructure(BaseFuzzyAutomaton automaton) {
		this(automaton.getAlphabet(), automaton.getStates(), automaton.getTransitionFunction(),
				automaton.getInitialStates(), automaton.getFinalStates());
	}

	///////////////////////////////////////////////////////////////////////////

	public Alphabet toAlphabet() {
		return new Alphabet(alphabet);
	}

	public Set<Symbol> getAlphabet() {
		return alphabet;
	}

	public Map<Triple<State, Symbol, State>, Degree> getTransitionFunction() {
		return transitionFunction;
	}

	public Map<State, Degree> getInitialStates() {
		return initialStates;
	}

	public Map<State, Degree> getFinalStates() {
		return finalStates;
	}

	public Set<State> getStates() {
		return states;
	}

	public FuzzyTernaryRelation<State, Symbol, State> toTransitionFunction() {
		return new FuzzyTernaryRelation<>(transitionFunction);
	}

	public FuzzySet<State> toInitialStates() {
		return new FuzzySet<>(initialStates);
	}

	public FuzzySet<State> toFinalStates() {
		return new FuzzySet<>(finalStates);
	}

	public FuzzyAutomaton toAutomaton(Integer precisionOrNull) {
		Alphabet alphabet = toAlphabet();
		Set<State> states = this.states;
		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = toTransitionFunction();
		FuzzySet<State> initialStates = toInitialStates();
		FuzzySet<State> finalStates = toFinalStates();

		if (!hasEpsilonRules) {
			return new FuzzyAutomaton(alphabet, states, transitionFunction, initialStates, finalStates);
		} else {
			if (precisionOrNull == null) {
				throw new NullPointerException("Precision required for epsilon-FA");
			}
			return new FuzAutWithEpsilonMoves(alphabet, states, transitionFunction, initialStates, finalStates,
					precisionOrNull);
		}
	}

	/////////////////////////////////////////////////////////////////////////

	public void addState(State state, Degree initialIn, Degree finalIn) {
		addStateInternal(state, initialIn, finalIn);
	}

	public void removeState(State state) {
		removeStateInternal(state, true, true, true);
	}

	public void replaceState(State oldState, State newState) {
		replaceStateInternal(oldState, newState, true);
	}

	/////////////////////////////////////////////////////////////////////////

	public void addTransition(Triple<State, Symbol, State> transition, Degree degree) {
		addTransitionInternal(transition, degree);
	}

	public void addTransition(State from, Symbol over, State to, Degree degree) {
		Triple<State, Symbol, State> transition = new Triple<State, Symbol, State>(from, over, to);
		addTransitionInternal(transition, degree);
	}

	public void removeTransition(Triple<State, Symbol, State> transition) {
		removeTransitionInternal(transition);
	}

	public void removeTransition(State from, Symbol over, State to) {
		Triple<State, Symbol, State> transition = new Triple<State, Symbol, State>(from, over, to);
		removeTransitionInternal(transition);
	}

	public void replaceTransition(Triple<State, Symbol, State> oldTransition,
			Triple<State, Symbol, State> newTransition) {
		replaceTransitionInternal(oldTransition, newTransition);
	}

	public void replaceTransition(Triple<State, Symbol, State> oldTransition, State newFrom, Symbol newOver,
			State newTo) {

		Triple<State, Symbol, State> newTransition = new Triple<State, Symbol, State>(newFrom, newOver, newTo);
		replaceTransitionInternal(oldTransition, newTransition);
	}

	public void replaceTransition(State oldFrom, Symbol oldOver, State oldTo, State newFrom, Symbol newOver,
			State newTo) {

		Triple<State, Symbol, State> oldTransition = new Triple<State, Symbol, State>(oldFrom, oldOver, oldTo);
		Triple<State, Symbol, State> newTransition = new Triple<State, Symbol, State>(newFrom, newOver, newTo);

		replaceTransitionInternal(oldTransition, newTransition);
	}

	// add various combinations here ...
	/////////////////////////////////////////////////////////////////////////

	private void addStateInternal(State state, Degree initialIn, Degree finalIn) {
		states.add(state);
		putWithHigherDegree(initialStates, state, initialIn);
		putWithHigherDegree(finalStates, state, finalIn);
	}

	private void removeStateInternal(State state, boolean withInitials, boolean withFinals, boolean withTransitions) {
		states.remove(state);

		if (withInitials) {
			initialStates.remove(state);
		}
		if (withFinals) {
			finalStates.remove(state);
		}
		if (withTransitions) {
			removeStateInTransitions(state);
		}
	}

	private void replaceStateInternal(State oldState, State newState, boolean withTransitions) {
		Degree initialIn = initialStates.get(oldState);
		Degree finalIn = finalStates.get(oldState);

		removeStateInternal(oldState, true, true, false);
		addStateInternal(newState, initialIn, finalIn);

		if (withTransitions) {
			replaceStateInTransitions(oldState, newState);
		}
	}

	private void addTransitionInternal(Triple<State, Symbol, State> transition, Degree degree) {
		putWithHigherDegree(transitionFunction, transition, degree);
		checkAndSetEpsilons(transition);
	}

	private void removeTransitionInternal(Triple<State, Symbol, State> transition) {
		transitionFunction.remove(transition);

	}

	private void replaceTransitionInternal(Triple<State, Symbol, State> oldTransition,
			Triple<State, Symbol, State> newTransition) {

		Degree degree = transitionFunction.remove(oldTransition);
		putWithHigherDegree(transitionFunction, newTransition, degree);
		checkAndSetEpsilons(newTransition);
	}

	///////////////////////////////////////////////////////////////////////////

	private void checkAndSetEpsilons(Triple<State, Symbol, State> transition) {
		Symbol over = transition.getSecond();

		if (Symbol.EMPTY.equals(over)) {
			hasEpsilonRules |= true;
		}
	}

	private void removeStateInTransitions(State state) {

		doWithTransitionFunction((transition, from, over, to, degree) -> {

			if (from.equals(state) || to.equals(state)) {
				transitionFunction.remove(transition);
			}
		});
	}

	private void replaceStateInTransitions(State oldState, State newState) {

		doWithTransitionFunction((transition, from, over, to, degree) -> {

			boolean changed = false;

			if (from.equals(oldState)) {
				from = newState;
				changed |= true;
			}

			if (to.equals(oldState)) {
				to = newState;
				changed |= true;
			}

			if (changed) {
				Triple<State, Symbol, State> newTransition = new Triple<>(from, over, to);
				replaceTransitionInternal(transition, newTransition);
			}
		});
	}

	///////////////////////////////////////////////////////////////////////////

	private <E> void putWithHigherDegree(Map<E, Degree> map, E element, Degree newDegree) {
		Degree oldDegree = map.get(element);

		Degree degree;
		if (oldDegree == null) {
			degree = newDegree;
		} else {
			degree = TNorms.getTnorm().tconorm(oldDegree, newDegree);
		}

		map.put(element, degree);
	}

	public void doWithTransitionFunction(TransitionFunctionMapper mapper) {
		Map<Triple<State, Symbol, State>, Degree> clone = new HashMap<>(transitionFunction);

		for (Triple<State, Symbol, State> transition : clone.keySet()) {
			State from = transition.getFirst();
			State to = transition.getThird();
			Symbol over = transition.getSecond();
			Degree degree = clone.get(transition);

			mapper.perform(transition, from, over, to, degree);
		}
	}

	@FunctionalInterface
	public static interface TransitionFunctionMapper {

		public void perform(Triple<State, Symbol, State> transition, State from, Symbol over, State to, Degree degree);

	}

}
