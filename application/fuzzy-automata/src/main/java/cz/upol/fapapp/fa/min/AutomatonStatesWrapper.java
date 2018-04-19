package cz.upol.fapapp.fa.min;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

public class AutomatonStatesWrapper implements AutomatonDescWrapper<State> {

	private final FuzzyAutomaton automaton;

	public AutomatonStatesWrapper(FuzzyAutomaton automaton) {
		this.automaton = automaton;
	}

	//////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Degree initalIn(State state) {
		return automaton.getInitialStates().degreeOf(state);
	}

	@Override
	public Degree finalIn(State state) {
		return automaton.getFinalStates().degreeOf(state);
	}

	@Override
	public Degree transition(State from, Symbol over, State to) {
		Degree degree = automaton.getTransitionFunction().get(from, over, to);
		
		if (degree != null) {
			return degree;
		} else {
			return Degree.ZERO;
		}
	}

}
