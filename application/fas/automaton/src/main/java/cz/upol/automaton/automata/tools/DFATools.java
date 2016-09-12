package cz.upol.automaton.automata.tools;

import java.util.Set;
import java.util.TreeSet;

import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.DFAedge;

public class DFATools
		extends
		AbstractAutomataTools<DeterministicFuzzyAutomata, Boolean, Boolean, Degree, Set<State>, Set<Transition>, DFAedge> {

	public DFATools(DeterministicFuzzyAutomata automaton) {
		super(automaton);
	}

	@Override
	public void copy(Transition transition, Set<Transition> from,
			Set<Transition> to) {
		to.add(transition);
	}

	@Override
	public Boolean remove(Transition transition, Set<Transition> from) {
		from.remove(transition);
		return true;
	}

	@Override
	public void add(Transition transition, Boolean with, Set<Transition> to) {
		to.add(transition);
	}

	@Override
	public Set<Transition> transitions() {
		return new TreeSet<Transition>();
	}

	@Override
	public DFAedge edge(State from, State to, Set<Transition> transitions) {
		return new DFAedge(from, to, transitions);
	}

	@Override
	public boolean isInSet(Transition transition, Set<Transition> in) {
		return in.contains(transition);
	}

	@Override
	public boolean isInBothSets(Transition transition, Set<Transition> first,
			Set<Transition> second) {
		return first.contains(transition) && second.contains(transition);
	}

	@Override
	public void setInitial(State state, Boolean with) {
		automaton.setInitialState(state);
	}

	@Override
	public void setFinite(State state, Degree with) {
		automaton.setFiniteState(state, with);

	}

	@Override
	public Boolean unsetInitial(State state) {
		automaton.setInitialState(null);
		tryToSetInitialToSome();
		return true;
	}

	@Override
	public Degree unsetFinite(State state) {
		return automaton.getFiniteStates().remove(state);
	}

	public void checkAndSetInitialState(State unsetInitial) {
		if (automaton.getInitialState().equals(unsetInitial)) {
			automaton.setInitialState(null);
			tryToSetInitialToSome();
		}

	}

	public void tryToSetInitialToSome() {
		if (automaton.getStatesCount() > 0) {
			State some = automaton.iterateOverStates().iterator().next();
			automaton.setInitialState(some);
		}
	}

	// TODO otestovat?
	@Override
	public void doCustomTransitionCheck(Transition transition) {

		boolean contains = containsSymbolOnAnotherEdge(transition);

		if (contains) {
			throw new IllegalArgumentException("Cannot go over "
					+ transition.getOver().getValue()
					+ ", symbol is used on another transition");
		}
	}

	public boolean containsSymbolOnAnotherEdge(Transition transition) {

		State from = transition.getFrom();
		Set<DFAedge> edges = automaton.getOutgoingEdges(from);

		for (DFAedge edge : edges) {
			for (Transition t : edge.getTransitions()) {
				if (t.equals(transition)) {
					continue;
				}

				if (t.getOver().equals(transition.getOver())) {
					return true;
				}
			}
		}

		return false;
	}

}
