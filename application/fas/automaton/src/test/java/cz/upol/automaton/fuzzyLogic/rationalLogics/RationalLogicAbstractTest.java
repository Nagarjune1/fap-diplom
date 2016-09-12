package cz.upol.automaton.fuzzyLogic.rationalLogics;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;
import cz.upol.automaton.fuzzyLogic.rationalLogics.RationalFuzzyLogic;

public abstract class RationalLogicAbstractTest {
	private static final double EPSILON = 0.001;

	/**
	 * @uml.property  name="logic"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final RationalFuzzyLogic logic;

	/**
	 * @uml.property  name="num00"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
			num00 = RationalFuzzyLogic.ZERO;

	/**
	 * @uml.property  name="num01"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
 num01 = new Rational0to1Number(0.1);

	/**
	 * @uml.property  name="num05"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
 num05 = new Rational0to1Number(0.5);

	/**
	 * @uml.property  name="num08"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
 num08 = new Rational0to1Number(0.8);

	/**
	 * @uml.property  name="num10"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Rational0to1Number //
 num10 = RationalFuzzyLogic.ONE;

	public RationalLogicAbstractTest(RationalFuzzyLogic logic) {
		this.logic = logic;
	}

	@Test
	public void testSupremum() {
		assertEquals(0.1, logic.supremum(num00, num01).getValue(), EPSILON);
		assertEquals(0.8, logic.supremum(num01, num08).getValue(), EPSILON);
		assertEquals(1.0, logic.supremum(num10, num08).getValue(), EPSILON);
		assertEquals(0.5, logic.supremum(num05, num05).getValue(), EPSILON);
	}

	@Test
	public void testImfimum() {
		assertEquals(0.0, logic.imfimum(num00, num01).getValue(), EPSILON);
		assertEquals(0.1, logic.imfimum(num01, num08).getValue(), EPSILON);
		assertEquals(0.8, logic.imfimum(num10, num08).getValue(), EPSILON);
		assertEquals(0.5, logic.imfimum(num05, num05).getValue(), EPSILON);
	}

	@Test
	public void testResiduum() {
		assertEquals(1.0, logic.residuum(num00, num00).getValue(), EPSILON);
		assertEquals(1.0, logic.residuum(num00, num10).getValue(), EPSILON);
		assertEquals(0.0, logic.residuum(num10, num00).getValue(), EPSILON);
		assertEquals(1.0, logic.residuum(num10, num10).getValue(), EPSILON);
	}

	@Test
	public void testTimes() {
		assertEquals(0.0, logic.times(num00, num00).getValue(), EPSILON);
		assertEquals(0.0, logic.times(num00, num10).getValue(), EPSILON);
		assertEquals(0.0, logic.times(num10, num00).getValue(), EPSILON);
		assertEquals(1.0, logic.times(num10, num10).getValue(), EPSILON);
	}

}
