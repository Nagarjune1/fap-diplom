package cz.upol.fapapp.fa.typos;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.core.ling.Symbol;

public class DefaultKeymapTest {

	private final DefaultKeymap keymap = new DefaultKeymap();

	@Test
	public void testIsNeighbor() {

		// some randoms
		assertTrue(keymap.isNeighbor(new Symbol("m"), new Symbol("n")));
		assertTrue(keymap.isNeighbor(new Symbol("h"), new Symbol("g")));
		assertTrue(keymap.isNeighbor(new Symbol("d"), new Symbol("r")));

		// awsd, s to all of others
		assertTrue(keymap.isNeighbor(new Symbol("s"), new Symbol("a")));
		assertTrue(keymap.isNeighbor(new Symbol("s"), new Symbol("w")));
		assertTrue(keymap.isNeighbor(new Symbol("s"), new Symbol("d")));

		// symetry
		assertTrue(keymap.isNeighbor(new Symbol("a"), new Symbol("s")));
		assertTrue(keymap.isNeighbor(new Symbol("s"), new Symbol("a")));

		assertTrue(keymap.isNeighbor(new Symbol("x"), new Symbol("y")));
		assertTrue(keymap.isNeighbor(new Symbol("y"), new Symbol("x")));

		// false
		assertFalse(keymap.isNeighbor(new Symbol("a"), new Symbol("l")));
		assertFalse(keymap.isNeighbor(new Symbol("y"), new Symbol("p")));

	}

	@Test
	public void testToFuzzyRelation() {
		Degree similartyDegree = new Degree(0.5);
		FuzzyBinaryRelation<Symbol, Symbol> relation = keymap.toFuzzyRelation(similartyDegree);

		assertEquals(Degree.ZERO, relation.get(new Symbol("a"), new Symbol("l")));
		assertEquals(similartyDegree, relation.get(new Symbol("j"), new Symbol("k")));
		assertEquals(Degree.ONE, relation.get(new Symbol("f"), new Symbol("f")));
	}

}
