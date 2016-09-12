package cz.upol.automaton.fuzzyLogic.rationalLogics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;

public class Rational0to1NumberTest {
	private static final double EPSILON = 0.001;

	/**
	 * @uml.property  name="num05"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
			num05 = new Rational0to1Number(0.5); //

	/**
	 * @uml.property  name="num04"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
 num04 = new Rational0to1Number(0.4);

	/**
	 * @uml.property  name="num02"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
 num02 = new Rational0to1Number(0.2);

	/**
	 * @uml.property  name="num03"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
 num03 = new Rational0to1Number(0.3);

	@Test
	public void testRational0to1NumberDouble() {
		try {
			new Rational0to1Number(-0.1);
			fail("Should fail");
		} catch (IllegalArgumentException e) {
		}

		try {
			new Rational0to1Number(1.1);
			fail("Should fail");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testMax() {
		assertEquals(0.3, Rational0to1Number.max(num03, num02).getValue(),
				EPSILON);
		assertEquals(0.3, Rational0to1Number.max(num03, num03).getValue(),
				EPSILON);
		assertEquals(0.5, Rational0to1Number.max(num05, num04).getValue(),
				EPSILON);
	}

	@Test
	public void testMin() {
		assertEquals(0.2, Rational0to1Number.min(num03, num02).getValue(),
				EPSILON);
		assertEquals(0.3, Rational0to1Number.min(num03, num03).getValue(),
				EPSILON);
		assertEquals(0.4, Rational0to1Number.min(num05, num04).getValue(),
				EPSILON);
	}

	@Test
	public void testCompareTo() {
		assertEquals(1, num05.compareTo(num02));
		assertEquals(-1, num04.compareTo(num05));
		assertEquals(0, num02.compareTo(num02));
	}

}
