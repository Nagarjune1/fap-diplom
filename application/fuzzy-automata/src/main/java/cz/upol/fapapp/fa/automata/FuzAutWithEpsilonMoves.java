package cz.upol.fapapp.fa.automata;

import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.FuzzyTernaryRelation;

public class FuzAutWithEpsilonMoves extends FuzzyAutomata {

	private final int precision;

	public FuzAutWithEpsilonMoves(Alphabet alphabet, Set<State> states,
			FuzzyTernaryRelation<State, Symbol, State> transitionFunction, FuzzySet<State> initialStates,
			FuzzySet<State> finalStates, int precision) {
		super(alphabet, states, transitionFunction, initialStates, finalStates);

		this.precision = precision;
	}

	@Override
	protected FuzzyState stepOver(FuzzyState fromFuzzyState, Symbol over) {

		FuzzyState toConvience = computeRegularStepTo(fromFuzzyState, over);
		FuzzyState toEpsilons = computeEpsilonClosure(fromFuzzyState);

		FuzzyState toFuzzyState = new FuzzyState(FuzzySet.union(toConvience, toEpsilons));

		return toFuzzyState;
	}

	private FuzzyState computeRegularStepTo(FuzzyState fromFuzzyState, Symbol over) {
		return super.stepOver(fromFuzzyState, over);
	}

	protected FuzzyState computeEpsilonClosure(FuzzyState convienced) {
		FuzzyState currentState = convienced;
		FuzzySet<State> nextState = null;

		for (int i = 0; i < precision; i++) {
			nextState = computeRegularStepTo(currentState, Symbol.EMPTY);

			currentState = new FuzzyState(FuzzySet.union(currentState, nextState));

			// System.out.println(currentState);
			if (currentState.equals(nextState)) {
				break;
			}
		}

		FuzzyState closed = currentState;
		return closed;
	}

	// do {
	// previousState = currentState;
	// nextState = computeRegularStepTo(currentState, Symbol.EMPTY);
	//
	// currentState = new FuzzyState(FuzzySet.union(currentState, nextState));
	//
	// // TODO does not still work perfecty, but what to do with infinite loop
	// ...
	// } while (previousState.isSubsetOf(currentState, false));

}
