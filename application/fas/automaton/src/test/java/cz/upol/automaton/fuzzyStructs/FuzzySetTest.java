package cz.upol.automaton.fuzzyStructs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;

public class FuzzySetTest {
	/**
	 * @uml.property  name="set1"
	 * @uml.associationEnd  
	 */
	private FuzzySet<String> set1;
	/**
	 * @uml.property  name="set2"
	 * @uml.associationEnd  
	 */
	private FuzzySet<String> set2;
	/**
	 * @uml.property  name="logic"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final LukasiewiczLogic logic = new LukasiewiczLogic();
	/**
	 * @uml.property  name="universe"
	 */
	private final Set<String> universe = initializeUniverse();

	@Before
	public void before() {
		set1 = new FuzzySet<String>(logic, universe);
		set2 = new FuzzySet<String>(logic, universe);

		initializeSet1();
		initializeSet2();
	}

	public Set<String> initializeUniverse() {
		Set<String> universe = new HashSet<>();

		universe.add("Jan");
		universe.add("Petr");
		universe.add("Jiří");
		universe.add("Hubert");
		universe.add("Rostislav");

		universe.add("Aneta");
		universe.add("Tereza");
		universe.add("Květoslava");
		universe.add("Jiřina");
		universe.add("Naděžda");

		return universe;
	}

	private void initializeSet1() {
		set1.insert("Jan");
		set1.insert("Jiří", new Rational0to1Number(0.5));
		set1.insert("Petr", logic.getOne());
		set1.insert("Hubert", logic.getOne());
		set1.insert("Rostislav", new Rational0to1Number(0.1));
		set1.insert("Jaromír", logic.getZero());

		set1.insert("Tereza", new Rational0to1Number(0.2));
	}

	private void initializeSet2() {
		set2.insert("Jan", new Rational0to1Number(0.2));
		set2.insert("Jiří", new Rational0to1Number(0.7));
		set2.insert("Hubert", logic.getOne());
		set2.insert("Rostislav", logic.getOne());

		set2.insert("Tereza", new Rational0to1Number(0.2));
		set2.insert("Jiřina", new Rational0to1Number(0.4));
	}

	@Test
	public void testFind() {
		assertFalse(set1.find("Jan").equals(logic.getZero()));
		assertTrue(set1.find("Aneta").equals(logic.getZero()));
		assertFalse(set1.find("Petr").equals(logic.getZero()));
		assertTrue(set1.find("Petr").equals(logic.getOne()));
		assertTrue(set1.find("Jaromír").equals(logic.getZero()));

		// TODO test find
	}

	@Test
	public void testAdd() {
		assertTrue(set1.find("Květoslava").equals(logic.getZero()));
		set1.insert("Květoslava");
		assertFalse(set1.find("Květoslava").equals(logic.getZero()));

		set1.insert("Jiřina");
		assertTrue(set1.find("Jiřina").equals(logic.getOne()));

		set1.insert("Naděžda", new Rational0to1Number(0.6));
		assertFalse(set1.find("Naděžda").equals(logic.getZero()));
		assertFalse(set1.find("Naděžda").equals(logic.getOne()));

		set1.insert("Naděžda", new Rational0to1Number(0.8));
		assertFalse(set1.find("Naděžda").equals(logic.getZero()));
		assertFalse(set1.find("Naděžda").equals(logic.getOne()));
	}

	@Test
	public void testRemove() {

		set1.remove("Petr");
		assertTrue(set1.find("Petr").equals(logic.getZero()));

		set1.remove("Tereza");
		assertTrue(set1.find("Tereza").equals(logic.getZero()));

		set1.remove("Hubert");
		assertTrue(set1.find("Hubert").equals(logic.getZero()));

		set1.remove("Jaromír");
		assertTrue(set1.find("Jaromír").equals(logic.getZero()));

	}

	@Test
	public void testJoin() {
		initializeSet2();

		FuzzySet<String> join1 = set1.join(set2);
		FuzzySet<String> join2 = set2.join(set1);

		assertEquals(join1, join2);

		assertTrue(join1.find("Jan").equals(logic.getOne()));

		assertTrue(join1.find("Hubert").equals(logic.getOne()));

		assertTrue(join1.find("Rostislav").equals(logic.getOne()));

		assertFalse(join1.find("Tereza").equals(logic.getZero()));
		assertFalse(join1.find("Tereza").equals(logic.getOne()));

	}

	@Test
	public void testIntersect() {
		initializeSet2();

		FuzzySet<String> intersect1 = set1.intersect(set2);
		FuzzySet<String> intersect2 = set2.intersect(set1);

		assertEquals(intersect1, intersect2);

		assertFalse(intersect1.find("Jan").equals(logic.getZero()));
		assertFalse(intersect1.find("Jan").equals(logic.getOne()));

		assertTrue(intersect1.find("Hubert").equals(logic.getOne()));

		assertFalse(intersect1.find("Rostislav").equals(logic.getZero()));
		assertFalse(intersect1.find("Rostislav").equals(logic.getOne()));

		assertFalse(intersect1.find("Tereza").equals(logic.getZero()));
		assertFalse(intersect1.find("Tereza").equals(logic.getOne()));
	}

}
