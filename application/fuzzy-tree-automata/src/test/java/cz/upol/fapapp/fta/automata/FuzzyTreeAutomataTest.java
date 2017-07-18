package cz.upol.fapapp.fta.automata;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.CollectionsUtils;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.fta.data.AtomicTree;
import cz.upol.fapapp.fta.data.BaseTree;
import cz.upol.fapapp.fta.data.CompositeTree;

public class FuzzyTreeAutomataTest {

	final State qlambda = new State("q_lambda");
	final State qxi = new State("q_xi");
	final State qeta = new State("q_eta");

	final Symbol nA = new Symbol("A");
	final Symbol ta = new Symbol("a");

	final Symbol sqlambda = new Symbol(qlambda.getLabel());
	final Symbol sqxi = new Symbol(qxi.getLabel());
	final Symbol sqeta = new Symbol(qeta.getLabel());

	@Test
	public void test() {

		FuzzyTreeAutomata fta = createFuzzyTreeAutomata();

		BaseTree tree = new CompositeTree(nA, //
				new AtomicTree(ta), //
				new AtomicTree(ta), //
				new CompositeTree(nA, //
						new AtomicTree(ta), //
						new AtomicTree(ta), //
						new AtomicTree(ta) //
				) //
		); //

		Degree deg = fta.accept(tree);

		assertEquals(new Degree(0.7), deg);

	}

	private FuzzyTreeAutomata createFuzzyTreeAutomata() {
		// states
		Set<State> states = CollectionsUtils.toSet(qlambda, qxi, qeta);

		// nonterminals
		Alphabet nonterminals = CollectionsUtils.toAlphabet(nA);

		// terminals
		Alphabet terminals = CollectionsUtils.toAlphabet(ta);

		// transition function
		Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction = new HashMap<>();

		Couple<Word, FuzzyState> ta0 = tuple(new Word(), 0.8, 0.7, 1.0);
		BinaryRelation<Word, FuzzyState> transa = CollectionsUtils.toBinary(ta0);
		transitionFunction.put(ta, transa);

		Couple<Word, FuzzyState> tA1 = tuple(new Word(sqlambda), 1, 0, 0);
		Couple<Word, FuzzyState> tA2 = tuple(new Word(sqxi), 0, 1, 0);
		Couple<Word, FuzzyState> tA3 = tuple(new Word(sqeta), 0, 0, 1);
		Couple<Word, FuzzyState> tA4 = tuple(new Word(sqlambda, sqlambda), 0.2, 0.8, 0);
		Couple<Word, FuzzyState> tA5 = tuple(new Word(sqlambda, sqxi, sqeta), 0, 0, 1.0);

		BinaryRelation<Word, FuzzyState> transA = CollectionsUtils.toBinary(tA1, tA2, tA3, tA4, tA5);
		transitionFunction.put(nA, transA);

		// final states
		Set<State> finalStates = CollectionsUtils.toSet(qeta);

		// automata
		FuzzyTreeAutomata fta = new FuzzyTreeAutomata(states, nonterminals, terminals, transitionFunction, finalStates);
		return fta;
	}

	private Couple<Word, FuzzyState> tuple(Word input, double degreeOfLambda, double degreeOfXi, double degreeOfEta) {

		Map<State, Degree> map = new HashMap<>();
		map.put(qlambda, new Degree(degreeOfLambda));
		map.put(qxi, new Degree(degreeOfXi));
		map.put(qeta, new Degree(degreeOfEta));

		FuzzyState to = new FuzzyState(map);

		return new Couple<>(input, to);
	}

}
