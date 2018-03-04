package cz.upol.fapap.pa.automata;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.probability.ProbabilisticEnvironment;

public class ProbablisticAutomatonTest {

	private static final double EPSILON = 0.0001;

	@Test
	public void test() {
		ProbablisticAutomaton automaton = AutomataCreator.createSome();
		
		
		Word word1 = new Word(new Symbol("a"), new Symbol("b"));
		Word word2 = new Word(new Symbol("b"), new Symbol("a"));
		
		assertEquals(0.9, automaton.probabilityOf(word1).getValue(), EPSILON);
		assertEquals(0.3, automaton.probabilityOf(word2).getValue(), EPSILON);
				
		assertTrue(automaton.accepts(word1, ProbabilisticEnvironment.get()));
		assertFalse(automaton.accepts(word2, ProbabilisticEnvironment.get()));
		
	}

}
