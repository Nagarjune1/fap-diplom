package cz.upol.fapapp.core.ling;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import cz.upol.fapapp.core.sets.CollectionsUtils;

public class TestLing {

	private static final Symbol S1 = new Symbol("a");
	private static final Symbol S2 = new Symbol("b");
	private static final Symbol S3 = new Symbol("c");
	private static final Symbol S9 = new Symbol("X");

	public TestLing() {
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testAlphabet() {
		Alphabet a1 = createAlphabetA();

		System.out.println(a1);

		assertTrue(a1.contains(S1));
		assertFalse(a1.contains(S9));
	}

	@Test
	public void testWord() {
		Word w1 = createWordA();

		System.out.println(w1);

		assertEquals(S1, w1.at(0));
		assertEquals(S2, w1.at(1));
		assertNotEquals(S9, w1.at(0));

		Alphabet a1 = createAlphabetA();
		try {
			CollectionsUtils.checkWord(w1, a1);
		} catch (IllegalArgumentException e) {
			fail(e.toString());
		}
	}

	///////////////////////////////////////////////////////////////////////////

	private Alphabet createAlphabetA() {
		Set<Symbol> symbols = CollectionsUtils.toSet(S1, S2, S3);

		Alphabet a1 = new Alphabet(symbols);
		return a1;
	}

	private Word createWordA() {
		Word w1 = new Word(S1, S2, S1, S3);
		return w1;
	}

}
