package cz.upol.fapap.pa.automata;

import java.util.HashSet;
import java.util.Set;

import cz.upol.fapap.core.QuadriaryRelation;
import cz.upol.fapap.core.QuadriaryRelation.Quadrituple;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.probability.Probability;

public class AutomataCreator {

	public static ProbablisticAutomaton createSome() {
		final State stateQ0 = new State("q_0");
		final State stateQ1 = new State("q_1");
		final State stateQ2 = new State("q_2");

		Set<State> states = CollectionsUtils.toSet(stateQ0, stateQ1, stateQ2);

		final Symbol symbolA = new Symbol("a");
		final Symbol symbolB = new Symbol("b");
		Alphabet alphabet = CollectionsUtils.createAlphabet(//
				symbolA.getValue().charAt(0), //
				(char) (symbolB.getValue().charAt(0) + 1));

		Set<Quadrituple<State, Symbol, State, Probability>> tuples = new HashSet<>();

		Probability prob10 = new Probability(1.0);
		Probability prob09 = new Probability(0.9);
		Probability prob08 = new Probability(0.8);
		Probability prob07 = new Probability(0.7);
		Probability prob03 = new Probability(0.3);

		tuples.add(new Quadrituple<State, Symbol, State, Probability>(stateQ0, symbolA, stateQ1, prob10));
		tuples.add(new Quadrituple<State, Symbol, State, Probability>(stateQ1, symbolB, stateQ2, prob09));
		tuples.add(new Quadrituple<State, Symbol, State, Probability>(stateQ2, symbolB, stateQ0, prob08));
		tuples.add(new Quadrituple<State, Symbol, State, Probability>(stateQ0, symbolB, stateQ1, prob07));
		tuples.add(new Quadrituple<State, Symbol, State, Probability>(stateQ1, symbolB, stateQ0, prob03));

		QuadriaryRelation<State, Symbol, State, Probability> transitionFunction = new QuadriaryRelation<State, Symbol, State, Probability>(
				tuples);

		Set<State> initialStates = CollectionsUtils.toSet(stateQ0, stateQ1);
		Set<State> finalStates = CollectionsUtils.toSet(stateQ1, stateQ2);
		return new ProbablisticAutomaton(states, alphabet, transitionFunction, initialStates, finalStates);
	}

}
