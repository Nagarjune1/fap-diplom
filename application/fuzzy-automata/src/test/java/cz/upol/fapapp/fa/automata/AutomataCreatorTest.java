package cz.upol.fapapp.fa.automata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Language;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.fa.modifs.AutomataCreator;

public class AutomataCreatorTest {

	@Test
	public void test() {
		final Symbol symA = new Symbol("a");
		final Symbol symB = new Symbol("b");
		final Symbol symC = new Symbol("c");
		final Symbol symD = new Symbol("d");
		
		Alphabet alphabet = CollectionsUtils.toAlphabet(symA, symB, symC, symD);
		
		
		Word word1 = new Word(symA, symA, symB);
		FuzzyAutomaton automaton1 = AutomataCreator.automatonOfWord(alphabet, word1);
		//automaton1.print(System.out);
		assertEquals(Degree.ONE, automaton1.degreeOfWord(word1));
		
		
		Word word2 = new Word(symA, symA, symB, symC, symA, symD, symC);
		FuzzyAutomaton automaton2 = AutomataCreator.automatonOfWord(alphabet, word2);
		//automaton2.print(System.out);
		assertEquals(Degree.ONE, automaton2.degreeOfWord(word2));
		
		assertEquals(Degree.ZERO, automaton1.degreeOfWord(word2));
		assertEquals(Degree.ZERO, automaton2.degreeOfWord(word1));
		
		
		Word word3 = new Word(symA, symB, symD, symA);
		Language words = new Language(word1, word2, word3);
		FuzzyAutomaton automaton3 = AutomataCreator.automatonOfLanguage(alphabet, words);
		automaton3.print(System.out);
		assertEquals(Degree.ONE, automaton3.degreeOfWord(word1));
		assertEquals(Degree.ONE, automaton3.degreeOfWord(word2));
		assertEquals(Degree.ONE, automaton3.degreeOfWord(word3));
		
		
	}
	
	

}
