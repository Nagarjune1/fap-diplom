package cz.upol.fapapp.fa.automata;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.sets.CollectionsUtils;
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
		FuzzyAutomata automata1 = AutomataCreator.automataOfWord(alphabet, word1);
		automata1.print(System.out);
		assertEquals(Degree.ONE, automata1.degreeOfWord(word1));
		
		
		Word word2 = new Word(symA, symA, symB, symC, symA, symD, symC);
		FuzzyAutomata automata2 = AutomataCreator.automataOfWord(alphabet, word2);
		automata2.print(System.out);
		assertEquals(Degree.ONE, automata2.degreeOfWord(word2));
		
		assertEquals(Degree.ZERO, automata1.degreeOfWord(word2));
		assertEquals(Degree.ZERO, automata2.degreeOfWord(word1));
	}

}
