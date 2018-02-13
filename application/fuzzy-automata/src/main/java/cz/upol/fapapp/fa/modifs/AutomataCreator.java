package cz.upol.fapapp.fa.modifs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.CollectionsUtils;
import cz.upol.fapapp.core.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.fapapp.fa.automata.FuzAutWithEpsilonMoves;
import cz.upol.fapapp.fa.automata.FuzzyAutomata;

public class AutomataCreator {

	/**
	 * Constructs following automata:
	 * 
	 * <pre>
	 *   
	 *    /--------> a/0.5 ----------->\
	 *    |                            V
	 * ( q_0 / 1.0 / 0.4 )   ( q_1 / 0.1 / 0.8)
	 *  |            A A               |
	 *  \--> a/0.2 --/ \--- b/1.0 <----/
	 * </pre>
	 * 
	 * @return
	 */
	public static FuzzyAutomata createAutomata1() {
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

		return new FuzzyAutomata(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	public static FuzzyAutomata createAutomata2() {
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
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolA, stateQ1), new Degree(0.8));
		transitions.put(new Triple<State, Symbol, State>(stateQ0, Symbol.EMPTY, stateQ1), new Degree(0.9));
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolA, stateQ0), new Degree(0.9));
		transitions.put(new Triple<State, Symbol, State>(stateQ1, symbolB, stateQ0), Degree.ONE);
		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = new FuzzyTernaryRelation<>(transitions);

		Map<State, Degree> initials = new HashMap<>();
		initials.put(stateQ0, Degree.ONE);
		initials.put(stateQ1, new Degree(0.7));
		FuzzySet<State> initialStates = new FuzzySet<>(initials);

		Map<State, Degree> finals = new HashMap<>();
		finals.put(stateQ0, new Degree(0.6));
		finals.put(stateQ1, new Degree(0.9));
		FuzzySet<State> finalStates = new FuzzySet<>(finals);

		return new FuzzyAutomata(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	public static FuzzyAutomata createAutomata3() {
		final State stateQ0 = new State("q_0");

		final Symbol symbolU = new Symbol("u");
		final Symbol symbolUL = new Symbol("ul");
		final Symbol symbolUR = new Symbol("ur");
		final Symbol symbolL = new Symbol("l");
		final Symbol symbolR = new Symbol("r");

		Set<State> states = new TreeSet<>();
		states.add(stateQ0);

		Set<Symbol> symbols = new TreeSet<>();
		symbols.add(symbolU);
		symbols.add(symbolUL);
		symbols.add(symbolUR);
		symbols.add(symbolR);
		symbols.add(symbolL);
		Alphabet alphabet = new Alphabet(symbols);

		Map<Triple<State, Symbol, State>, Degree> transitions = new HashMap<>();
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolU, stateQ0), Degree.ONE);
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolUL, stateQ0), new Degree(0.9));
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolUR, stateQ0), new Degree(0.8));
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolUR, stateQ0), new Degree(0.7));
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolR, stateQ0), new Degree(0.6));
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolL, stateQ0), new Degree(0.5));
		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = new FuzzyTernaryRelation<>(transitions);

		FuzzySet<State> initialStates = CollectionsUtils.singletonFuzzySet(states, stateQ0);
		FuzzySet<State> finalStates = CollectionsUtils.singletonFuzzySet(states, stateQ0);

		return new FuzzyAutomata(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	
	public static FuzAutWithEpsilonMoves createAutomataWET(int precision) {
		final State stateQ0 = new State("q_0");
		final State stateQ1 = new State("q_1");
		final State stateQ2 = new State("q_2");

		final Symbol symbolA = new Symbol("x");
		final Symbol symbolB = new Symbol("y");

		Set<State> states = new TreeSet<>();
		states.add(stateQ0);
		states.add(stateQ1);
		states.add(stateQ2);

		Set<Symbol> symbols = new TreeSet<>();
		symbols.add(symbolA);
		symbols.add(symbolB);
		Alphabet alphabet = new Alphabet(symbols);

		Map<Triple<State, Symbol, State>, Degree> transitions = new HashMap<>();
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolA, stateQ1), new Degree(0.5));
		transitions.put(new Triple<State, Symbol, State>(stateQ0, symbolA, stateQ0), new Degree(0.2));
		transitions.put(new Triple<State, Symbol, State>(stateQ1, symbolB, stateQ0), Degree.ONE);
		transitions.put(new Triple<State, Symbol, State>(stateQ0, Symbol.EMPTY, stateQ1), new Degree(0.7));
		transitions.put(new Triple<State, Symbol, State>(stateQ1, Symbol.EMPTY, stateQ1), new Degree(0.8));
		transitions.put(new Triple<State, Symbol, State>(stateQ1, Symbol.EMPTY, stateQ2), new Degree(0.3));
		transitions.put(new Triple<State, Symbol, State>(stateQ2, Symbol.EMPTY, stateQ0), new Degree(0.6));
		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = new FuzzyTernaryRelation<>(transitions);

		Map<State, Degree> initials = new HashMap<>();
		initials.put(stateQ0, Degree.ONE);
		initials.put(stateQ1, new Degree(0.1));
		FuzzySet<State> initialStates = new FuzzySet<>(initials);

		Map<State, Degree> finals = new HashMap<>();
		finals.put(stateQ0, new Degree(0.4));
		finals.put(stateQ1, new Degree(0.8));
		FuzzySet<State> finalStates = new FuzzySet<>(finals);

		return new FuzAutWithEpsilonMoves(alphabet, states, transitionFunction, initialStates, finalStates, precision);
	}

	
	
	///////////////////////////////////////////////////////////////////////////
	
	
	public static FuzzyAutomata automataOfWord(Word word) {
		Alphabet alphabet = CollectionsUtils.inferAlphabetOfWord(word);
		FuzzyAutomata automata = automataOfWord(alphabet, word);
		return automata;
	}

	public static FuzzyAutomata automataOfWord(Alphabet alphabet, Word word) {
		Logger.get().moreinfo("Creating automata of " + word);
		
		StatesCreator creator = new StatesCreator();

		Map<Triple<State, Symbol, State>, Degree> transitions = new HashMap<>();
		Set<State> states = new HashSet<>(word.getLength() + 1);

		State initState = creator.next();
		states.add(initState);
		State previousState = initState;

		for (int i = 0; i < word.getLength(); i++) {
			State from = previousState;
			State to = creator.next();
			Symbol over = word.at(i);

			states.add(to);
			previousState = to;
			
			Triple<State, Symbol, State> triple = new Triple<State, Symbol, State>(from, over, to);
			transitions.put(triple, Degree.ONE);
			
		}

		State finState = previousState;
		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = new FuzzyTernaryRelation<>(transitions);

		FuzzySet<State> initialStates = CollectionsUtils.singletonFuzzySet(states, initState);
		FuzzySet<State> finalStates = CollectionsUtils.singletonFuzzySet(states, finState);

		return new FuzzyAutomata(alphabet, states, transitionFunction, initialStates, finalStates);
	}

}
