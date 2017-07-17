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
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.fta.automata.BaseFuzzyTreeAutomata.FTAmuTuple;

public class FTAInputFileTest {

	@Test
	public void testJustToNotToFailOnVariousCombinations() {
		String input = createInputFileA();

		FTAInputFileParser parser = new FTAInputFileParser();
		FuzzyTreeAutomata fta = parser.parse(input);

		System.out.println(fta);
	}

	@Test
	public void testComposeAndParseBack() {
		FTAInputFileParser parser = new FTAInputFileParser();
		FTAInputFileComposer composer = new FTAInputFileComposer();

		FuzzyTreeAutomata expectedAutomata = createAutomataB();
		
		String output = composer.compose(expectedAutomata);
		FuzzyTreeAutomata actualAutomata = parser.parse(output);

		System.out.println("testComposeAndParseBack: \n" + output);
		
		assertEquals(expectedAutomata.toString(), actualAutomata.toString());
		assertEquals(expectedAutomata, actualAutomata);
	}

	@Test
	public void testBi() {
		FTAInputFileParser parser = new FTAInputFileParser();
		FTAInputFileComposer composer = new FTAInputFileComposer();

		FuzzyTreeAutomata expectedAutomata = createAutomataB();
		String expectedInput = createInputFileB();

		FuzzyTreeAutomata actualAutomata = parser.parse(expectedInput);
		String actualInput = composer.compose(expectedAutomata);

		assertEquals(expectedAutomata, actualAutomata);

	}

	public static String createInputFileA() {
		return "" //
				+ "type:\n" //
				+ "	fuzzy TREE automata\n" //
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
				+ "	fuzzy tree automata\n" //
				+ "states:\n" //
				+ "	q_1, q_2\n" //
				+ "terminals:\n" //
				+ "	a, b\n" //
				+ "nonterminals:\n" //
				+ "	X, Y\n" //
				+ "transition function:\n" //
				+ "	a:	epsilon	-> q_0/1\n" //
				+ "	b:	epsilon -> q_1/1\n" //
				+ "	X	q_0 q_1	-> q_0/0.5\n" //
				+ "	Y	q_1 q_0	-> q_1/0.5\n" //
				+ "final states:\n" //
				+ "	q_1\n"; //
	}

	public static FuzzyTreeAutomata createAutomataB() {
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

		FTAmuTuple ta0 = new FTAmuTuple(Word.EMPTY, new FuzzyState(CollectionsUtils.singletonFuzzySet(q1)));
		BinaryRelation<Word, FuzzyState> transa = CollectionsUtils.toBinary(ta0);
		transitionFunction.put(ta, transa);

		FTAmuTuple tb0 = new FTAmuTuple(Word.EMPTY, new FuzzyState(CollectionsUtils.singletonFuzzySet(q2)));
		BinaryRelation<Word, FuzzyState> transb = CollectionsUtils.toBinary(tb0);
		transitionFunction.put(tb, transb);

		Word wX = new Word(sq1, sq2);
		FTAmuTuple tX0 = new FTAmuTuple(wX, new FuzzyState(CollectionsUtils.singletonFuzzySet(q2, new Degree(0.5))));
		BinaryRelation<Word, FuzzyState> transX = CollectionsUtils.toBinary(tX0);
		transitionFunction.put(nX, transX);

		Word wY = new Word(sq2, sq1);
		FTAmuTuple tY0 = new FTAmuTuple(wY, new FuzzyState(CollectionsUtils.singletonFuzzySet(q1, new Degree(0.5))));
		BinaryRelation<Word, FuzzyState> transY = CollectionsUtils.toBinary(tY0);
		transitionFunction.put(nY, transY);

		// final states
		Set<State> finalStates = CollectionsUtils.toSet(q2);

		// automata
		return new FuzzyTreeAutomata(states, nonterminals, terminals, transitionFunction, finalStates);
	}

}
