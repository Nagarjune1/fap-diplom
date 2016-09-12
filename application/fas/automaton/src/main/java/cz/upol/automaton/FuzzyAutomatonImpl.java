package cz.upol.automaton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.upol.automaton.automata.definition.NondetFuzzyAutomatonDefinition;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyStructs.FuzzyRelation;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.NFAedge;

@Deprecated
public class FuzzyAutomatonImpl extends NondetFuzzyAutomatonDefinition implements
		FuzzyAutomatonAbstractImpl {

	public FuzzyAutomatonImpl(FuzzyLogic logic, Alphabet alphabet) {
		super(logic, alphabet);
	}

	public FuzzyAutomatonImpl(FuzzyLogic logic, Set<State> states,
			Alphabet alphabet, FuzzySet<State> initialStates,
			FuzzySet<State> finiteStates, FuzzyRelation<Transition> delta) {
		super(logic, states, alphabet, initialStates, finiteStates, delta);
	}

	public FuzzyAutomatonImpl(NondetFuzzyAutomatonDefinition other) {
		super(other);
	}

	@Override
	public boolean addState(State state) {
		return !getStates().add(state);
	}

	@Override
	public void removeState(State state) {
		getStates().remove(state);

		fixAfterStateDeleted(state);
	}

	public void fixAfterStateDeleted(State state) {
		Set<Transition> transitions = getTransitionsWithState(state);
		for (Transition t : transitions) {
			getDelta().remove(t);
		}
		getInitialStates().remove(state);
		getFiniteStates().remove(state);
	}

	@Override
	public void updateState(State currentState, State newState) {
		getStates().remove(currentState);
		getStates().add(newState);

		fixAfterStateUpdated(currentState, newState);
	}

	public void fixAfterStateUpdated(State currentState, State newState) {
		Set<Transition> transitions = getTransitionsWithState(currentState);

		for (Transition currentTrans : transitions) {
			State newFrom = currentTrans.getFrom(), newTo = currentTrans
					.getTo();

			if (currentTrans.getFrom().equals(currentState)) {
				newFrom = newState;
			}
			if (currentTrans.getTo().equals(currentState)) {
				newTo = newState;
			}
			Symbol over = currentTrans.getOver();
			Degree degree = getDelta().remove(currentTrans);

			Transition newTrans = new Transition(newFrom, over, newTo);
			getDelta().insert(newTrans, degree);
		}

		Degree initialDegree = getInitialStates().remove(currentState);
		getInitialStates().insert(newState, initialDegree);

		Degree finiteDegree = getFiniteStates().remove(currentState);
		getFiniteStates().insert(newState, finiteDegree);
	}

	@Override
	public Iterable<State> iterateOverStates() {
		return getStates();
	}

	@Override
	public int getStatesCount() {
		return getStates().size();
	}

	@Override
	public void addTransition(Transition transition, Degree degree) {
		checkTransition(transition);
		getDelta().insert(transition, degree);
	}

	@Override
	public void updateTransitionDegree(Transition transition, Degree degree) {
		checkTransition(transition);
		getDelta().insert(transition, degree);
	}

	@Override
	public void removeTransition(Transition transition) {
		getDelta().remove(transition);
	}

	@Override
	public void updateTransition(Transition currentTransition,
			Transition newTransition) {

		Degree degree = getDelta().remove(currentTransition);
		checkTransition(newTransition);
		getDelta().insert(newTransition, degree);
	}

	@Override
	public Iterable<Transition> iterateOverTransitions() {
		return getDelta();
	}

	@Override
	public FuzzySet<Transition> transitionsFrom(State from) {
		FuzzySet<Transition> transitions = newTransitionsFuzzySet();

		for (Transition transition : getDelta()) {
			if (transition.isFrom(from)) {
				Degree degree = getDelta().find(transition);
				transitions.insert(transition, degree);
			}
		}

		return transitions;
	}

	@Override
	public FuzzySet<Transition> transitionsFromAndOver(State from, Symbol symbol) {
		FuzzySet<Transition> transitions = newTransitionsFuzzySet();

		for (Transition transition : getDelta()) {
			if (transition.isFrom(from) && transition.isOver(symbol)) {
				Degree degree = getDelta().find(transition);
				transitions.insert(transition, degree);
			}
		}

		return transitions;
	}

	@Override
	public FuzzySet<Transition> transitionsFromTo(State from, State to) {
		FuzzySet<Transition> transitions = newTransitionsFuzzySet();

		for (Transition transition : getDelta()) {
			if (transition.isFrom(from) && transition.isTo(to)) {
				Degree degree = getDelta().find(transition);
				transitions.insert(transition, degree);
			}
		}

		return transitions;
	}

	@Override
	public NFAedge getEdge(State from, State to) {
		FuzzySet<Transition> transitions = transitionsFromTo(from, to);
		return new NFAedge(from, to, transitions);
	}

	@Override
	public Set<NFAedge> getOutgoingEdges(State from) {
		FuzzySet<Transition> transitions = transitionsFrom(from);
		return transitionsToEdges(transitions);
	}

	@Override
	public Set<NFAedge> getEdges() {
		return transitionsToEdges(getDelta());
	}

	@Override
	public void addEdge(NFAedge edge) {
		for (Transition transition : edge.getTransitions()) {
			Degree degree = edge.getTransitions().find(transition);
			checkTransition(transition);
			getDelta().insert(transition, degree);
		}
	}

	@Override
	public void removeEdge(NFAedge edge) {
		for (Transition transition : edge.getTransitions()) {
			getDelta().remove(transition);
		}
	}

	@Override
	public void updateEdge(NFAedge edge) {
		FuzzySet<Transition> currentOnEdge = transitionsFromTo(edge.getFrom(),
				edge.getTo());

		for (Transition transition : currentOnEdge) {
			Degree degreeInEdge = edge.getTransitions().find(transition);

			if (degreeInEdge.equals(getResiduedLattice().getZero())) {
				getDelta().remove(transition);
			}
		}

		for (Transition transition : edge.getTransitions()) {
			Degree degreeInAutomat = this.getDelta().find(transition);
			Degree degreeInEdge = edge.getTransitions().find(transition);

			if (!degreeInAutomat.equals(degreeInEdge)) {
				checkTransition(transition);
				getDelta().insert(transition, degreeInEdge);
			}
		}

	}

	private Set<NFAedge> transitionsToEdges(FuzzySet<Transition> transitions) {
		Map<NFAedge, NFAedge> edges = new HashMap<>();

		for (Transition transition : transitions) {
			State from = transition.getFrom();
			State to = transition.getTo();
			Degree degree = getDelta().find(transition);
/*
 * XXX FIXME
			NFAedge keyEdge = new NFAedge(from, to, this);
			NFAedge currentEdge = edges.get(keyEdge);
			if (currentEdge == null) {
				currentEdge = keyEdge;
				edges.put(keyEdge, currentEdge);
			}

			currentEdge.getTransitions().insert(transition, degree);
*/
			
		}

		return new HashSet<>(edges.values());
	}

	@Override
	public Set<Transition> getTransitionsWithState(State state) {
		Set<Transition> transitions = new HashSet<>();

		for (Transition transition : getDelta()) {
			if (transition.isFrom(state) || transition.isTo(state)) {
				transitions.add(transition);
			}
		}

		return transitions;
	}

	@Override
	public Degree degreeOfTransition(Transition transition) {
		return getDelta().find(transition);
	}

	@Override
	public void setInitialState(State state, Degree degree) {
		getInitialStates().insert(state, degree);

	}

	@Override
	public Degree degreeOfInitialState(State state) {
		return getInitialStates().find(state);
	}

	@Override
	public void setFiniteState(State state, Degree degree) {
		getFiniteStates().insert(state, degree);
	}

	@Override
	public Degree degreeOfFiniteState(State state) {
		return getFiniteStates().find(state);
	}

	/**
	 * Vytvoří a vrátí novou fuzzy množinu s přechody.
	 * 
	 * @return
	 */
	public FuzzySet<Transition> newTransitionsFuzzySet() {
		return new FuzzySet<Transition>(getResiduedLattice(), getDelta().getUniverse());
	}

	private void checkTransition(Transition transition) {
		State from = transition.getFrom();
		State to = transition.getTo();
		Symbol over = transition.getOver();

		if (!getStates().contains(from)) {
			throw new IllegalArgumentException("From (" + from.getLabel()
					+ ") is not a state of the automaton.");
		}

		if (!getStates().contains(to)) {
			throw new IllegalArgumentException("To (" + to.getLabel()
					+ ") is not a state of the automaton.");
		}

		if (!getAlphabet().contains(over)) {
			throw new IllegalArgumentException("Symbol (" + over.getValue()
					+ ") is not symbol of the automaton's alphabet.");
		}
	}

}