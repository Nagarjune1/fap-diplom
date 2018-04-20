package cz.upol.fapapp.fa.automata;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.tnorm.GodelTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Language;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.misc.TestUtils;
import cz.upol.fapapp.fa.modifs.AutomataCreator;

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
		Word word4 = new Word(new Symbol("b"), new Symbol("b")); // = 0.0 in all
																	// states
		FuzzyState excepted4 = TestUtils.createFuzzyState(//
				new State("q_0"), Degree.ZERO, new State("q_1"), Degree.ZERO);

		FuzzyState actual4 = automaton.computeWord(word4);
		assertEquals(excepted4, actual4);
	}

	@Test
	public void testDeterminise() {
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();

		FuzzyAutomaton detAut = (FuzzyAutomaton) automaton.determinise();

		// detAut.print(System.out);

		Word word1 = new Word(new Symbol("a"), new Symbol("a"));
		Word word2 = new Word(new Symbol("a"), new Symbol("b"));
		Word word3 = new Word(new Symbol("a"), new Symbol("a"), new Symbol("a"));
		Word word4 = new Word(new Symbol("b"), new Symbol("b")); // = 0.0

		assertEquals(automaton.degreeOfWord(word1), detAut.degreeOfWord(word1));
		assertEquals(automaton.degreeOfWord(word2), detAut.degreeOfWord(word2));
		assertEquals(automaton.degreeOfWord(word3), detAut.degreeOfWord(word3));
		assertEquals(automaton.degreeOfWord(word4), detAut.degreeOfWord(word4));
	}

	@Test
	public void testDeteminiseAutOfFiniteLang() {
		Word word1 = new Word(new Symbol("a"), new Symbol("b"));
		Word word2 = new Word(new Symbol("a"), new Symbol("c"));
		
		Language language = new Language(word1, word2);
		FuzzyAutomaton automaton = AutomataCreator.automatonOfLanguage(language);

		
		FuzzyAutomaton determinised = (FuzzyAutomaton) automaton.determinise();
		determinised.print(System.out);
		
		assertEquals(5, determinised.getStates().size());
		assertEquals(Degree.ONE, determinised.degreeOfWord(word1));
		assertEquals(Degree.ONE, determinised.degreeOfWord(word2));
	}
	
	@Test
	public void testMinimalise() {
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();

		Word wordAB = new Word(new Symbol("a"), new Symbol("b"));
		Word wordAA = new Word(new Symbol("a"), new Symbol("a"));
		Word wordBB = new Word(new Symbol("b"), new Symbol("b"));

		
		FuzzyAutomaton minAut00 = (FuzzyAutomaton) automaton.minimise(Degree.ZERO);
		assertEquals(2, minAut00.getStates().size());
		//minAut1.print(System.out);

		// these automata should behave totaly equally !!!
		assertEquals(automaton.degreeOfWord(wordAB), minAut00.degreeOfWord(wordAB));
		assertEquals(automaton.degreeOfWord(wordAA), minAut00.degreeOfWord(wordAA));
		assertEquals(automaton.degreeOfWord(wordBB), minAut00.degreeOfWord(wordBB));

		FuzzyAutomaton minAut10 = (FuzzyAutomaton) automaton.minimise(Degree.ONE);
		assertEquals(2, minAut10.getStates().size());
		//minAut2.print(System.out);
		
		// this should behave totaly differently
		assertEquals(new Degree(0.4), minAut10.degreeOfWord(wordAB));
		assertEquals(new Degree(0.2), minAut10.degreeOfWord(wordAA));
		assertEquals(new Degree(0.0), minAut10.degreeOfWord(wordBB));

		FuzzyAutomaton minAut09 = (FuzzyAutomaton) automaton.minimise(new Degree(0.9));
		//minAut3.print(System.out);
		// same as previous, no other change 
		assertEquals(minAut10.degreeOfWord(wordAB), minAut09.degreeOfWord(wordAB));
		assertEquals(minAut10.degreeOfWord(wordAA), minAut09.degreeOfWord(wordAA));
		assertEquals(minAut10.degreeOfWord(wordBB), minAut09.degreeOfWord(wordBB));

		FuzzyAutomaton minAut03 = (FuzzyAutomaton) automaton.minimise(new Degree(0.3));
		//minAut3.print(System.out);
		// similar to previous, no other change 
		assertEquals(minAut00.degreeOfWord(wordAB), minAut03.degreeOfWord(wordAB));
		assertEquals(minAut00.degreeOfWord(wordAA), minAut03.degreeOfWord(wordAA));
		assertEquals(minAut00.degreeOfWord(wordBB), minAut03.degreeOfWord(wordBB));

	}

}
