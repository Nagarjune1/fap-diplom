package cz.upol.fapapp.core.fuzzy;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.misc.CollectionsUtils;

public class TestFuzzy {

	@Test
	public void testDegrees() {
		Degree d05 = new Degree(0.5);
		Degree d01 = new Degree(0.1);
		Degree d08 = new Degree(0.8);
		Degree d10 = Degree.ONE;
		Degree d00 = Degree.ZERO;

		// isLessOrEqual
		assertTrue(d05.isLessOrEqual(d08));
		assertFalse(d10.isLessOrEqual(d00));
		assertTrue(d00.isLessOrEqual(d10));

		// infimum
		assertEquals(d01, Degree.infimum(d05, d01));
		assertEquals(d05, Degree.supremum(d05, d01));

		// isSmallerThan
		assertTrue(Degree.isSmallerThan(d00, d10, false));
		assertTrue(Degree.isSmallerThan(d00, d10, true));

		assertFalse(Degree.isSmallerThan(d05, d05, false));
		assertTrue(Degree.isSmallerThan(d05, d05, true));

		assertTrue(Degree.isSmallerThan(d00, d00, false));
		assertTrue(Degree.isSmallerThan(d00, d00, true));

	}

	@Test
	public void testFuzzySets() {
		Map<String, Degree> map1 = new HashMap<>();

		map1.put("foo", Degree.ZERO);
		map1.put("bar", Degree.ONE);
		map1.put("baz", new Degree(0.4));
		map1.put("aux", new Degree(0.1));

		FuzzySet<String> s1 = new FuzzySet<>(map1);

		System.out.println(s1);

		assertEquals(Degree.ZERO, s1.degreeOf("foo"));
		assertEquals(new Degree(0.1), s1.degreeOf("aux"));

		try {
			s1.degreeOf("xxx");
			// fail("should fail"); //TODO fail if elem is not from universe?
		} catch (IllegalStateException e) {
			// ok
		}

		FuzzySet<String> s2 = CollectionsUtils.singletonFuzzySet("baz", new Degree(0.3));

		assertFalse(s1.isSubsetOf(s2, false));
		assertTrue(s2.isSubsetOf(s1, false));

		assertFalse(s1.isSubsetOf(s1, false));
		assertTrue(s1.isSubsetOf(s1, true));

	}

}
