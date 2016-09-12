package cz.upol.automaton.ling.alphabets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cz.upol.automaton.ling.Symbol;

public class CharactersAlphabetTest {

	@Test
	public void testCalculateSize() {

		assertEquals(26, CharactersAlphabet.calculateSize(true, false, false));
		assertEquals(26, CharactersAlphabet.calculateSize(false, true, false));
		assertEquals(10, CharactersAlphabet.calculateSize(false, false, true));

		assertEquals(52, CharactersAlphabet.calculateSize(true, true, false));
		assertEquals(62, CharactersAlphabet.calculateSize(true, true, true));

		assertEquals(3, CharactersAlphabet.calculateSize(false, false, false,
				'.', '+', '-'));
		assertEquals(67, CharactersAlphabet.calculateSize(true, true, true,
				',', '.', '!', '?', '-'));
	}

	@Test
	public void testInitialize() {
		CharactersAlphabet uppers = new CharactersAlphabet(false, true, false);

		assertTrue(uppers.contains(new Symbol('A')));
		assertTrue(uppers.contains(new Symbol('Z')));

		assertFalse(uppers.contains(new Symbol('a')));
		assertFalse(uppers.contains(new Symbol('z')));

		CharactersAlphabet algebraic = new CharactersAlphabet(true, false,
				true, '+', '-', '*', '/');

		assertTrue(algebraic.contains(new Symbol('a')));
		assertTrue(algebraic.contains(new Symbol('z')));
		assertTrue(algebraic.contains(new Symbol('0')));
		assertTrue(algebraic.contains(new Symbol('9')));
		assertTrue(algebraic.contains(new Symbol('+')));
		assertTrue(algebraic.contains(new Symbol('/')));

		assertFalse(algebraic.contains(new Symbol('A')));
		assertFalse(algebraic.contains(new Symbol('Z')));
		assertFalse(algebraic.contains(new Symbol('@')));
		assertFalse(algebraic.contains(new Symbol('_')));

	}

}
