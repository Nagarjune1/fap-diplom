package cz.upol.fapapp.fa.automata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;

public class TestAutomataCreator {
	

	/**
	 * Constructs following automaton:
	 * 
	 * <pre>
	 *   
	 *    /--------> a/0.5 ----------->\
	 *    |                            V
	 * ( q_0 / 1.0 / 0.4 )   ( q_0 / 0.1 / 0.8)
	 *  |            A A               |
	 *  \--> a/0.2 --/ \--- b/1.0 <----/
	 * </pre>
	 * 
	 * @return
	 */
	public static FuzzyAutomaton createAutomaton1() {
		final State stateQ0 = new State("q_0");
		final State stateQ1 = new State("q_1");

		final Symbol symbolA = new Symbol("a");
		final Symbol symbolB = new Symbol("b");

		Set<State> states = new TreeSet<>();
		states.add(stateQ0);
		states.add(stateQ1);

		Set<Symbol> symbols = new TreeSet<>();
		symbols.add(symbolA);
		symbols.add(symbolB);
		Alphabet alphabet = new Alphabet(symbols);

		Map<Triple<State, Symbol, State>, Degree> transitions = new HashMap<>();
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolA, stateQ1), new Degree(0.5));
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolA, stateQ0), new Degree(0.2));
		transitions.put(new Triple<State, Symbol, State>(stateQ1, symbolB, stateQ0), Degree.ONE);
		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = new FuzzyTernaryRelation<>(transitions);

		Map<State, Degree> initials = new HashMap<>();
		initials.put(stateQ0, Degree.ONE);
		initials.put(stateQ1, new Degree(0.1));
		FuzzySet<State> initialStates = new FuzzySet<>(initials);

		Map<State, Degree> finals = new HashMap<>();
		finals.put(stateQ0, new Degree(0.4));
		finals.put(stateQ1, new Degree(0.8));
		FuzzySet<State> finalStates = new FuzzySet<>(finals);

		return new FuzzyAutomaton(alphabet, states, transitionFunction, initialStates, finalStates);
	}

}
