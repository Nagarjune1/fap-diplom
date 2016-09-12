package cz.upol.automaton.automata.impls;

import java.util.Set;

import cz.upol.automaton.automata.abstracts.AbstractDetermFuzzyAutomaton;
import cz.upol.automaton.automata.abstracts.SimpleAutomaton;
import cz.upol.automaton.automata.definition.DetermFuzzyAutomataDefinition;
import cz.upol.automaton.automata.tools.DFATools;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.DFAedge;

public class DeterministicFuzzyAutomata extends DetermFuzzyAutomataDefinition
		implements AbstractDetermFuzzyAutomaton,
		SimpleAutomaton<Set<Transition>, DFAedge> {

	private final DFATools tools = new DFATools(this);

	public DeterministicFuzzyAutomata(ResiduedLattice residuedLattice,
			Alphabet alphabet) {
		super(residuedLattice, alphabet);
		tools.doInitialCheck();
	}

	public DeterministicFuzzyAutomata(ResiduedLattice residuedLattice,
			Set<State> states, Alphabet alphabet, State initialState,
			FuzzySet<State> finiteStates, Set<Transition> delta) {

		super(residuedLattice, states, alphabet, initialState, finiteStates,
				delta);
		tools.doInitialCheck();
	}

	public DeterministicFuzzyAutomata(DetermFuzzyAutomataDefinition other) {
		super(other);
		tools.doInitialCheck();
	}

	@Override
	public boolean addState(State state) {
		boolean added = getStates().add(state);
		tools.tryToSetInitialToSome();
		return !added;
	}

	@Override
	public void removeState(State state) {
		getStates().remove(state);

		tools.fixAfterStateDeleted(state);
	}

	@Override
	public void updateState(State currentState, State newState) {
		getStates().remove(currentState);
		getStates().add(newState);

		tools.fixAfterStateUpdated(currentState, newState);
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
	public void addTransition(Transition transition) {
		tools.checkTransition(transition);
		getDelta().add(transition);
	}

	@Override
	public void updateTransitionDegree(Transition transition, Degree degree) {
		tools.checkTransition(transition);
		getDelta().add(transition);
	}

	@Override
	public void removeTransition(Transition transition) {
		getDelta().remove(transition);
	}

	
	@Override
	public boolean hasIncidentingState(Transition transition) {
		return tools.containsSymbolOnAnotherEdge(transition);
	}
	@Override
	public void updateTransition(Transition currentTransition,
			Transition newTransition) {

		getDelta().remove(currentTransition);
		tools.checkTransition(newTransition);
		getDelta().add(newTransition);
	}

	@Override
	public Iterable<Transition> iterateOverTransitions() {
		return getDelta();
	}

	@Override
	public Set<Transition> transitionsFrom(State from) {
		return tools.transitionsFrom(from);
	}

	@Override
	public Set<Transition> transitionsFromAndOver(State from, Symbol symbol) {
		return tools.transitionsFromAndOver(from, symbol);
	}

	@Override
	public Set<Transition> transitionsFromTo(State from, State to) {
		return tools.transitionsFromTo(from, to);
	}

	@Override
	public DFAedge getEdge(State from, State to) {
		Set<Transition> transitions = transitionsFromTo(from, to);
		return new DFAedge(from, to, transitions);
	}

	@Override
	public Set<DFAedge> getOutgoingEdges(State from) {
		Set<Transition> transitions = transitionsFrom(from);
		return tools.transitionsToEdges(transitions);
	}

	@Override
	public Set<DFAedge> getEdges() {
		return tools.transitionsToEdges(getDelta());
	}

	@Override
	public void addEdge(DFAedge edge) {
		tools.addEdge(edge);
	}

	@Override
	public void removeEdge(DFAedge edge) {
		tools.removeEdge(edge);
	}

	@Override
	public void updateEdge(DFAedge edge) {
		tools.updateEdge(edge);
	}

	@Override
	public Set<Transition> getTransitionsWithState(State state) {
		return tools.getTransitionsWithState(state);
	}

	@Override
	public boolean isInitial(State state) {
		return getInitialState() != null && getInitialState().equals(state);
	}

	@Override
	public void setFiniteState(State state, Degree degree) {
		getFiniteStates().insert(state, degree);
	}

	@Override
	public Degree degreeOfFiniteState(State state) {
		return getFiniteStates().find(state);
	}

}
