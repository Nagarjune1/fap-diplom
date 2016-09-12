package cz.upol.automaton.automata.tools;

import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.NFAedge;

public class NFATools
		extends
		AbstractAutomataTools<NondetermisticFuzzyAutomata, Degree, Degree, Degree, FuzzySet<State>, FuzzySet<Transition>, NFAedge> {

	public NFATools(NondetermisticFuzzyAutomata automaton) {
		super(automaton);
	}

	@Override
	public void copy(Transition transition, FuzzySet<Transition> from,
			FuzzySet<Transition> to) {
		Degree degree = from.find(transition);
		to.insert(transition, degree);
	}

	@Override
	public Degree remove(Transition transition, FuzzySet<Transition> from) {
		return from.remove(transition);
	}

	@Override
	public void add(Transition transition, Degree with, FuzzySet<Transition> to) {
		to.insert(transition, with);
	}

	@Override
	public FuzzySet<Transition> transitions() {
		return automaton.newTransitionsFuzzySet();
	}

	@Override
	public NFAedge edge(State from, State to, FuzzySet<Transition> transitions) {
		if (transitions == null) {
			return new NFAedge(from, to, transitions());
		} else {
			return new NFAedge(from, to, transitions);
		}
	}

	@Override
	public boolean isInSet(Transition transition, FuzzySet<Transition> in) {

		Degree degree = in.find(transition);
		boolean isInSet = !degree.equals(automaton.getResiduedLattice()
				.getZero());

		return isInSet;
	}

	@Override
	public boolean isInBothSets(Transition transition,
			FuzzySet<Transition> first, FuzzySet<Transition> second) {

		Degree inFirst = first.find(transition);
		Degree inSecond = second.find(transition);

		return inFirst.equals(inSecond);
	}

	@Override
	public void setInitial(State state, Degree with) {
		automaton.getInitialStates().insert(state, with);
	}

	@Override
	public void setFinite(State state, Degree with) {
		automaton.getFiniteStates().insert(state, with);
	}

	@Override
	public Degree unsetInitial(State state) {
		return automaton.getInitialStates().remove(state);
	}

	@Override
	public Degree unsetFinite(State state) {
		return automaton.getFiniteStates().remove(state);
	}

	@Override
	public void doCustomTransitionCheck(Transition transition) {
	}
}
