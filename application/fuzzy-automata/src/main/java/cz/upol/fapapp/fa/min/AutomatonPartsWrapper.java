package cz.upol.fapapp.fa.min;

import java.util.Set;
import java.util.function.Function;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.CollectionsUtils.BinaryFunction;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

public class AutomatonPartsWrapper implements AutomatonDescWrapper<Set<State>> {

	private final FuzzyAutomaton automaton;

	public AutomatonPartsWrapper(FuzzyAutomaton automaton) {
		this.automaton = automaton;
	}

	//////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Degree initalIn(Set<State> part) {
		return average(part, //
				(s) -> automaton.getInitialStates().degreeOf(s));
	}

	@Override
	public Degree finalIn(Set<State> part) {
		return average(part, //
				(s) -> automaton.getFinalStates().degreeOf(s));
	}

	@Override
	public Degree transition(Set<State> from, Symbol over, Set<State> to) {
		return average(from, to, //
				(sf, st) -> automaton.getTransitionFunction().get(sf, over, st));
	}

	//////////////////////////////////////////////////////////////////////////////////////

	private Degree average(Set<State> part, Function<State, Degree> mapper) {
		double sum = 0.0;

		for (State state : part) {
			Degree degree = mapper.apply(state);
			if (degree != null) {
				sum += degree.getValue();
			}
		}

		double val = sum / part.size();
		return new Degree(val);
	}

	private Degree average(Set<State> froms, Set<State> tos, BinaryFunction<State, State, Degree> mapper) {
		double sum = 0.0;

		for (State from : froms) {
			for (State to : tos) {
				Degree degree = mapper.run(from, to);
				if (degree != null) {
					sum += degree.getValue();
				}
			}
		}

		double val = sum / (froms.size() * tos.size());
		return new Degree(val);
	}

}
