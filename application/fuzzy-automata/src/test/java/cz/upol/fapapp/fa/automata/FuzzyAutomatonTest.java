package cz.upol.fapapp.fa.automata;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.tnorm.GodelTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.misc.TestUtils;

public class FuzzyAutomatonTest {

	@BeforeClass
	public static void init() {
		Logger.get().setVerbose(true);
		TNorms.setTnorm(new GodelTNorm());
	}

	@Test
	public void testPrint() {
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();

		automaton.print(System.out);
	}

	@Test
	public void testComputeWord() {
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();
		
		//
		Word word3 = new Word(new Symbol("a"));
		FuzzyState excepted3 = TestUtils.createFuzzyState(//
				new State("q_0"), new Degree(0.2), new State("q_1"), new Degree(0.5));

		FuzzyState actual3 = automaton.computeWord(word3);
		assertEquals(excepted3, actual3);
		
		//
		Word word1 = new Word(new Symbol("a"), new Symbol("a"));
		FuzzyState excepted1 = TestUtils.createFuzzyState(//
				new State("q_0"), new Degree(0.2), new State("q_1"), new Degree(0.2));

		FuzzyState actual1 = automaton.computeWord(word1);
		assertEquals(excepted1, actual1);
		
		//
		Word word2 = new Word(new Symbol("a"), new Symbol("b"));
		FuzzyState excepted2 = TestUtils.createFuzzyState(//
				new State("q_0"), new Degree(0.4), new State("q_1"), Degree.ZERO);

		FuzzyState actual2 = automaton.computeWord(word2);
		assertEquals(excepted2, actual2);

		//
		Word word4 = new Word(new Symbol("b"), new Symbol("b")); // = 0.0 in all states
		FuzzyState excepted4 = TestUtils.createFuzzyState(//
				new State("q_0"), Degree.ZERO, new State("q_1"), Degree.ZERO);

		FuzzyState actual4 = automaton.computeWord(word4);
		assertEquals(excepted4, actual4);
	}

	@Test
	public void testDeterminise() {
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();

		FuzzyAutomaton detAut = (FuzzyAutomaton) automaton.determinise();

		detAut.print(System.out);

		Word word1 = new Word(new Symbol("a"), new Symbol("a"));
		Word word2 = new Word(new Symbol("a"), new Symbol("b"));
		Word word3 = new Word(new Symbol("a"), new Symbol("a"), new Symbol("a"));
		Word word4 = new Word(new Symbol("b"), new Symbol("b")); // = 0.0

		assertEquals(automaton.degreeOfWord(word1), detAut.degreeOfWord(word1));
		assertEquals(automaton.degreeOfWord(word2), detAut.degreeOfWord(word2));
		assertEquals(automaton.degreeOfWord(word3), detAut.degreeOfWord(word3));
		assertEquals(automaton.degreeOfWord(word4), detAut.degreeOfWord(word4));
	}


}
