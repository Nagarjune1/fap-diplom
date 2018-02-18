package cz.upol.fapapp.fta.automata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;

public class FTATIMFileTest {

	@Test
	public void testJustToNotToFailOnVariousCombinations() {
		String input = createInputFileA();

		FTATIMParser parser = new FTATIMParser();
		FuzzyTreeAutomaton fta = parser.parse(input);

		System.out.println(fta);
	}

	@Test
	public void testSomeIncorrectInputs() {

		try {
			FTATIMParser parser = new FTATIMParser();
			parser.parse(createInputX());
			fail("event not a FTA");
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			FTATIMParser parser = new FTATIMParser();
			parser.parse(createInputY());
			fail("not a state in transition function");
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			FTATIMParser parser = new FTATIMParser();
			parser.parse(createInputZ());
			fail("not a word over states");
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	@Test
	public void testComposeAndParseBack() {
		FTATIMParser parser = new FTATIMParser();
		FTATIMComposer composer = new FTATIMComposer();

		FuzzyTreeAutomaton expectedAutomaton = createAutomatonB();

		String output = composer.compose(expectedAutomaton);
		FuzzyTreeAutomaton actualAutomaton = parser.parse(output);

		assertEquals(expectedAutomaton, actualAutomaton);

	}

	@Test
	public void testBi() {
		FTATIMParser parser = new FTATIMParser();
		FTATIMComposer composer = new FTATIMComposer();

		FuzzyTreeAutomaton expectedAutomaton = createAutomatonB();
		String file = composer.compose(expectedAutomaton);

		FuzzyTreeAutomaton actualAutomaton = parser.parse(file);

		assertEquals(expectedAutomaton, actualAutomaton);

	}

	/////////////////////////////////////////////////////////////////////////

	public static String createInputFileA() {
		return "" //
				+ "type:\n" //
				+ "	fuzzy TREE automaton\n" //
				+ "states:\n" //
				+ "	q_0, q_1, q_2\n" //
				+ "terminals:\n" //
				+ "	a, b, c\n" //
				+ "nonterminals:\n" //
				+ "	A1, A2\n" //
				+ "	B1  B2\n" //
				+ "mu:\n" //
				+ "	a:	epsilon	->	q_0/1 q_1/0.9 q_2/0.8\n" //
				+ "	b:	epsilon	->	q_0/1\n" //
				+ "	A1	q_0		->	q_0/1, q_1/0.5, q_2/0.1\n" //
				+ "	A2:	q_0 q_1	->	q_0/0.5\n" //
				+ "	B1:	q_2 q_2	->	q_2/1\n" //
				+ "\n" //
				+ "F:\n" //
				+ "	q_0, q_1\n"; //
	}

	public static String createInputFileB() {
		return "" //
				+ "type:\n" //
				+ "	fuzzy tree automaton\n" //
				+ "states:\n" //
				+ "	q_1, q_2\n" //
				+ "terminals:\n" //
				+ "	a, b\n" //
				+ "nonterminals:\n" //
				+ "	X, Y\n" //
				+ "transition function:\n" //
				+ "	a:	epsilon	-> q_1/1\n" //
				+ "	b:	epsilon -> q_2/1\n" //
				+ "	X	q_1 q_2	-> q_1/0.5\n" //
				+ "	Y	q_2 q_1	-> q_2/0.5\n" //
				+ "final states:\n" //
				+ "	q_1\n"; //
	}

	private String createInputX() {
		return "" //
				+ "type:\n" //
				+ "	not the FUZZY TREE AUTOMATA\n" //
				+ "whatever:\n" //
				+ "	something_1 something_2\n"; //
	}

	private String createInputY() {
		return "" //
				+ "type:\n" //
				+ "	fuzzy tree automaton\n" //
				+ "states:\n" //
				+ "	q_1\n" //
				+ "terminals:\n" //
				+ "	a\n" //
				+ "nonterminals:\n" //
				+ "	X\n" //
				+ "transition function:\n" //
				+ "	a:	epsilon	-> q_XXX/1\n" //
				+ "	X	q_1 q_2	-> q_YYY/0.5\n" //
				+ "final states:\n" //
				+ "	q_ZZZ\n"; //
	}

	private String createInputZ() {
		return "" //
				+ "type:\n" //
				+ "	fuzzy tree automaton\n" //
				+ "states:\n" //
				+ "	q_1\n" //
				+ "terminals:\n" //
				+ "	a\n" //
				+ "nonterminals:\n" //
				+ "	X\n" //
				+ "transition function:\n" //
				+ "	a:	q_1	q_1 -> q_1/1\n" //
				+ "	X	q_XXX q_YYY	-> q_1/0.5\n" //
				+ "final states:\n" //
				+ "	q_1\n"; //
	}

	public static FuzzyTreeAutomaton createAutomatonB() {
		final State q1 = new State("q_1");
		final State q2 = new State("q_2");

		final Symbol ta = new Symbol("a");
		final Symbol tb = new Symbol("b");
		final Symbol nX = new Symbol("X");
		final Symbol nY = new Symbol("Y");

		final Symbol sq1 = new Symbol(q1.getLabel());
		final Symbol sq2 = new Symbol(q2.getLabel());

		// states
		Set<State> states = CollectionsUtils.toSet(q1, q2);

		// nonterminals
		Alphabet nonterminals = CollectionsUtils.toAlphabet(nX, nY);

		// terminals
		Alphabet terminals = CollectionsUtils.toAlphabet(ta, tb);

		// transition function
		Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction = new HashMap<>();

		Couple<Word, FuzzyState> ta0 = new Couple<>(Word.EMPTY, new FuzzyState(CollectionsUtils.singletonFuzzySet(q1)));
		BinaryRelation<Word, FuzzyState> transa = CollectionsUtils.toBinary(ta0);
		transitionFunction.put(ta, transa);

		Couple<Word, FuzzyState> tb0 = new Couple<>(Word.EMPTY, new FuzzyState(CollectionsUtils.singletonFuzzySet(q2)));
		BinaryRelation<Word, FuzzyState> transb = CollectionsUtils.toBinary(tb0);
		transitionFunction.put(tb, transb);

		Word wX = new Word(sq1, sq2);
		Couple<Word, FuzzyState> tX0 = new Couple<>(wX,
				new FuzzyState(CollectionsUtils.singletonFuzzySet(q2, new Degree(0.5))));
		BinaryRelation<Word, FuzzyState> transX = CollectionsUtils.toBinary(tX0);
		transitionFunction.put(nX, transX);

		Word wY = new Word(sq2, sq1);
		Couple<Word, FuzzyState> tY0 = new Couple<>(wY,
				new FuzzyState(CollectionsUtils.singletonFuzzySet(q1, new Degree(0.5))));
		BinaryRelation<Word, FuzzyState> transY = CollectionsUtils.toBinary(tY0);
		transitionFunction.put(nY, transY);

		// final states
		Set<State> finalStates = CollectionsUtils.toSet(q2);

		// automaton
		return new FuzzyTreeAutomaton(states, nonterminals, terminals, transitionFunction, finalStates);
	}

}
