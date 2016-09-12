package cz.upol.automaton.automata.impls;

import java.util.Set;

import cz.upol.automaton.automata.abstracts.AbstractNondetFuzzyAutomaton;
import cz.upol.automaton.automata.abstracts.SimpleAutomaton;
import cz.upol.automaton.automata.definition.NondetFuzzyAutomatonDefinition;
import cz.upol.automaton.automata.tools.NFATools;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.fuzzyStructs.FuzzyRelation;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.NFAedge;

public class NondetermisticFuzzyAutomata extends NondetFuzzyAutomatonDefinition
		implements AbstractNondetFuzzyAutomaton,
		SimpleAutomaton<FuzzySet<Transition>, NFAedge> {

	private final NFATools tools = new NFATools(this);

	public NondetermisticFuzzyAutomata(ResiduedLattice residuedLattice,
			Alphabet alphabet) {
		super(residuedLattice, alphabet);
		tools.doInitialCheck();
	}

	public NondetermisticFuzzyAutomata(ResiduedLattice residuedLattice,
			Set<State> states, Alphabet alphabet,
			FuzzySet<State> initialStates, FuzzySet<State> finiteStates,
			FuzzyRelation<Transition> delta) {

		super(residuedLattice, states, alphabet, initialStates, finiteStates,
				delta);
		tools.doInitialCheck();
	}

	public NondetermisticFuzzyAutomata(NondetFuzzyAutomatonDefinition other) {
		super(other);
		tools.doInitialCheck();
	}

	@Override
	public boolean addState(State state) {
		return !getStates().add(state);
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
	public void addTransition(Transition transition, Degree degree) {
		tools.checkTransition(transition);
		getDelta().insert(transition, degree);
	}

	@Override
	public void updateTransitionDegree(Transition transition, Degree degree) {
		tools.checkTransition(transition);
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
		tools.checkTransition(newTransition);
		getDelta().insert(newTransition, degree);
	}

	@Override
	public Iterable<Transition> iterateOverTransitions() {
		return getDelta();
	}

	@Override
	public FuzzySet<Transition> transitionsFrom(State from) {
		return tools.transitionsFrom(from);
	}

	@Override
	public FuzzySet<Transition> transitionsFromAndOver(State from, Symbol symbol) {
		return tools.transitionsFromAndOver(from, symbol);
	}

	@Override
	public FuzzySet<Transition> transitionsFromTo(State from, State to) {
		return tools.transitionsFromTo(from, to);
	}

	@Override
	public NFAedge getEdge(State from, State to) {
		FuzzySet<Transition> transitions = transitionsFromTo(from, to);
		return new NFAedge(from, to, transitions);
	}

	@Override
	public Set<NFAedge> getOutgoingEdges(State from) {
		FuzzySet<Transition> transitions = transitionsFrom(from);
		return tools.transitionsToEdges(transitions);
	}

	@Override
	public Set<NFAedge> getEdges() {
		return tools.transitionsToEdges(getDelta());
	}

	@Override
	public void addEdge(NFAedge edge) {
		tools.addEdge(edge);
	}

	@Override
	public void removeEdge(NFAedge edge) {
		tools.removeEdge(edge);
	}

	@Override
	public void updateEdge(NFAedge edge) {
		tools.updateEdge(edge);
	}

	@Override
	public FuzzySet<Transition> getTransitionsWithState(State state) {
		return tools.getTransitionsWithState(state);
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

	public FuzzySet<Transition> newTransitionsFuzzySet() {
		return new FuzzySet<Transition>(getResiduedLattice(), getDelta()
				.getUniverse());
	}

}
