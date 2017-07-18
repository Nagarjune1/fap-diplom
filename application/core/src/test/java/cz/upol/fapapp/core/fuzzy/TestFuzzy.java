package cz.upol.fapapp.core.fuzzy;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestFuzzy {

	@Test
	public void testDegrees() {
		Degree d05 = new Degree(0.5);
		Degree d01 = new Degree(0.1);
		Degree d08 = new Degree(0.8);
		Degree d10 = Degree.ONE;
		Degree d00 = Degree.ZERO;

		assertTrue(d05.isLessOrEqual(d08));
		assertFalse(d10.isLessOrEqual(d00));
		assertTrue(d00.isLessOrEqual(d10));

		assertEquals(d01, Degree.infimum(d05, d01));
		assertEquals(d05, Degree.supremum(d05, d01));
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

		assertEquals(Degree.ZERO, s1.get("foo"));
		assertEquals(new Degree(0.1), s1.get("aux"));

		try {
			s1.get("xxx");
			fail("should fail");
		} catch (IllegalStateException e) {
			// ok
		}
	}

}
