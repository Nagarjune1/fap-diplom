package cz.upol.fapapp.fa.modifs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.CollectionsUtils;
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
			Map<State, Degree> finalStates, boolean hasEpsilonRules) {
		super();
		this.alphabet = alphabet;
		this.states = states;
		this.transitionFunction = transitionFunction;
		this.initialStates = initialStates;
		this.finalStates = finalStates;
		this.hasEpsilonRules = hasEpsilonRules;
	}

	public MutableAutomatonStructure(Alphabet alphabet, Set<State> states,
			FuzzyTernaryRelation<State, Symbol, State> transitionFunction, FuzzySet<State> initialStates,
			FuzzySet<State> finalStates, boolean hasEpsilonRules) {
		this(alphabet, states, transitionFunction.getTuplesWithDegree(), initialStates.toMap(), finalStates.toMap(),
				hasEpsilonRules);
	}

	public MutableAutomatonStructure(BaseFuzzyAutomaton automaton) {
		this(automaton.getAlphabet(), automaton.getStates(), automaton.getTransitionFunction(),
				automaton.getInitialStates(), automaton.getFinalStates(),
				(automaton instanceof FuzAutWithEpsilonMoves));
	}

	public MutableAutomatonStructure() {
		this.alphabet = new HashSet<>();
		this.states = new HashSet<>();
		this.transitionFunction = new HashMap<>();
		this.initialStates = new HashMap<>();
		this.finalStates = new HashMap<>();
		this.hasEpsilonRules = false;
	}

	///////////////////////////////////////////////////////////////////////////

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

	public Alphabet toAlphabet() {
		return new Alphabet(alphabet);
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

	/**
	 * Outputs to the automaton. Warning, the automaton might be both plain
	 * {@link FuzzyAutomaton} or {@link FuzAutWithEpsilonMoves}.
	 * 
	 * @param precisionOrNull
	 * @return
	 */
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

	/**
	 * Adds given state with given initial and final degrees.
	 * 
	 * @param state
	 * @param initialIn
	 * @param finalIn
	 */
	public void addState(State state, Degree initialIn, Degree finalIn) {
		addStateInternal(state, initialIn, finalIn);
	}

	/**
	 * Removes given state.
	 * 
	 * @param state
	 */
	public void removeState(State state) {
		removeStateInternal(state, true, true, true);
	}

	/**
	 * Replaces given state with another one.
	 * 
	 * @param oldState
	 * @param newState
	 */
	public void replaceState(State oldState, State newState) {
		replaceStateInternal(oldState, newState, true);
	}

	/////////////////////////////////////////////////////////////////////////

	/**
	 * Adds transition.
	 * 
	 * @param transition
	 * @param degree
	 */
	public void addTransition(Triple<State, Symbol, State> transition, Degree degree) {
		addTransitionInternal(transition, degree);
	}

	/**
	 * Adds transition.
	 * 
	 * @param from
	 * @param over
	 * @param to
	 * @param degree
	 */
	public void addTransition(State from, Symbol over, State to, Degree degree) {
		Triple<State, Symbol, State> transition = new Triple<State, Symbol, State>(from, over, to);
		addTransitionInternal(transition, degree);
	}

	/**
	 * Removes given transition.
	 * 
	 * @param transition
	 */
	public void removeTransition(Triple<State, Symbol, State> transition) {
		removeTransitionInternal(transition);
	}

	/**
	 * Removes given transition.
	 * 
	 * @param from
	 * @param over
	 * @param to
	 */
	public void removeTransition(State from, Symbol over, State to) {
		Triple<State, Symbol, State> transition = new Triple<State, Symbol, State>(from, over, to);
		removeTransitionInternal(transition);
	}

	/**
	 * Replaces transition with another one.
	 * 
	 * @param oldTransition
	 * @param newTransition
	 */
	public void replaceTransition(Triple<State, Symbol, State> oldTransition,
			Triple<State, Symbol, State> newTransition) {
		replaceTransitionInternal(oldTransition, newTransition);
	}

	/**
	 * Replaces transition with another one.
	 * 
	 * @param oldTransition
	 * @param newFrom
	 * @param newOver
	 * @param newTo
	 */
	public void replaceTransition(Triple<State, Symbol, State> oldTransition, State newFrom, Symbol newOver,
			State newTo) {

		Triple<State, Symbol, State> newTransition = new Triple<State, Symbol, State>(newFrom, newOver, newTo);
		replaceTransitionInternal(oldTransition, newTransition);
	}

	/**
	 * Replaces transition with another one.
	 * 
	 * @param oldFrom
	 * @param oldOver
	 * @param oldTo
	 * @param newFrom
	 * @param newOver
	 * @param newTo
	 */
	public void replaceTransition(State oldFrom, Symbol oldOver, State oldTo, State newFrom, Symbol newOver,
			State newTo) {

		Triple<State, Symbol, State> oldTransition = new Triple<State, Symbol, State>(oldFrom, oldOver, oldTo);
		Triple<State, Symbol, State> newTransition = new Triple<State, Symbol, State>(newFrom, newOver, newTo);

		replaceTransitionInternal(oldTransition, newTransition);
	}

	// add various combinations here ...
	/////////////////////////////////////////////////////////////////////////
	/**
	 * Adds given state with given inital and final degree. That means to add
	 * state into states and put its initial and final degree into corresponding
	 * maps.
	 * 
	 * @param state
	 * @param initialIn
	 * @param finalIn
	 */
	private void addStateInternal(State state, Degree initialIn, Degree finalIn) {
		states.add(state);
		putWithHigherDegree(initialStates, state, initialIn);
		putWithHigherDegree(finalStates, state, finalIn);
	}

	/**
	 * Removes given state. Flags indicates what else might be removed with the
	 * state.
	 * 
	 * @param state
	 * @param withInitials
	 * @param withFinals
	 * @param withTransitions
	 */
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

	/**
	 * Replaces state with another one. If flag specifies, performs also in all
	 * the transitions.
	 * 
	 * @param oldState
	 * @param newState
	 * @param withTransitions
	 */
	private void replaceStateInternal(State oldState, State newState, boolean withTransitions) {
		Degree initialIn = initialStates.get(oldState);
		Degree finalIn = finalStates.get(oldState);

		removeStateInternal(oldState, true, true, false);
		addStateInternal(newState, initialIn, finalIn);

		if (withTransitions) {
			replaceStateInTransitions(oldState, newState);
		}
	}

	/**
	 * Adds transition. That means to add the transition (if already contained,
	 * with the higher degree) and chech to set {@link #hasEpsilonRules} flag.
	 * 
	 * @param transition
	 * @param degree
	 */
	private void addTransitionInternal(Triple<State, Symbol, State> transition, Degree degree) {
		putWithHigherDegree(transitionFunction, transition, degree);
		checkAndSetEpsilons(transition);
	}

	/**
	 * Removes transition with no aditional work.
	 * 
	 * @param transition
	 */
	private void removeTransitionInternal(Triple<State, Symbol, State> transition) {
		transitionFunction.remove(transition);

	}

	/**
	 * Replaces transition. That means to remove the old and add the new withe
	 * same degree.
	 * 
	 * @param oldTransition
	 * @param newTransition
	 */
	private void replaceTransitionInternal(Triple<State, Symbol, State> oldTransition,
			Triple<State, Symbol, State> newTransition) {

		Degree degree = transitionFunction.remove(oldTransition);
		putWithHigherDegree(transitionFunction, newTransition, degree);
		checkAndSetEpsilons(newTransition);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * If given transition is epsilon rule, sets {@link #hasEpsilonRules} to
	 * true.
	 * 
	 * @param transition
	 */
	private void checkAndSetEpsilons(Triple<State, Symbol, State> transition) {
		Symbol over = transition.getSecond();

		if (Symbol.EMPTY.equals(over)) {
			hasEpsilonRules |= true;
		}
	}

	/**
	 * Removes all transitions interrested in given state (the state is their
	 * from or to state).
	 * 
	 * @param state
	 */
	private void removeStateInTransitions(State state) {

		doWithTransitionFunction((transition, from, over, to, degree) -> {

			if (from.equals(state) || to.equals(state)) {
				transitionFunction.remove(transition);
			}
		});
	}

	/**
	 * Replaces given state in all transitions where is given state interrested
	 * in.
	 * 
	 * @param oldState
	 * @param newState
	 */
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

	/**
	 * To given map puts element only and only if the degree is higher than
	 * current one. In fact, computes t-conorm of current and new degree and
	 * adds with this degree.
	 * 
	 * @param map
	 * @param element
	 * @param newDegree
	 */
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

	/**
	 * For-each function for transition function.
	 * @param mapper
	 */
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

	//////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Transition function for-each functional interface.
	 * @author martin
	 *
	 */
	@FunctionalInterface
	public static interface TransitionFunctionMapper {

		public void perform(Triple<State, Symbol, State> transition, State from, Symbol over, State to, Degree degree);

	}

	/**
	 * Merges theese two structures into (new) one.
	 * @param old
	 * @param newly
	 * @return
	 */
	public static MutableAutomatonStructure merge(MutableAutomatonStructure old, MutableAutomatonStructure newly) {
		Set<Symbol> alphabet = CollectionsUtils.join(old.getAlphabet(), newly.getAlphabet());
		Set<State> states = CollectionsUtils.join(old.getStates(), newly.getStates());

		Map<Triple<State, Symbol, State>, Degree> transitionFunction = //
				CollectionsUtils.joinFuzzy(old.getTransitionFunction(), newly.getTransitionFunction());

		Map<State, Degree> initialStates = CollectionsUtils.joinFuzzy(old.getInitialStates(), newly.getInitialStates());
		Map<State, Degree> finalStates = CollectionsUtils.joinFuzzy(old.getFinalStates(), newly.getFinalStates());
		boolean hasEpsilonRules = old.hasEpsilonRules || newly.hasEpsilonRules;

		return new MutableAutomatonStructure(alphabet, states, transitionFunction, initialStates, finalStates,
				hasEpsilonRules);
	}

}
