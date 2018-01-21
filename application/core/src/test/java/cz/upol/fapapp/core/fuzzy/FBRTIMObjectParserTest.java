package cz.upol.fapapp.core.fuzzy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.core.sets.FuzzyBinaryRelation;

public class FBRTIMObjectParserTest {

	@Test
	public void test() {

		FBRTIMObjectParser<String, Integer> parser = new FBRTIMObjectParser<>("some relation", (s) -> s,
				(s) -> Integer.parseInt(s));

		final String input = "" //
				+ "type:\n" //
				+ "	some relation\n" //
				+ "domain:\n" //
				+ "	A B C\n" //
				+ "target:\n" //
				+ "	1 2\n" //
				+ "relation:\n" //
				+ "	0.5	0.1\n" //
				+ "	0.0	1.0\n" //
				+ "	0.4	0.6\n" //
				+ "\n"; //

		FuzzyBinaryRelation<String, Integer> output = parser.parse(input);

		assertEquals(new Degree(0.5), output.get("A", 1));
		assertEquals(new Degree(0.1), output.get("A", 2));

		assertEquals(new Degree(0.0), output.get("B", 1));
		assertEquals(new Degree(1.0), output.get("B", 2));

		assertEquals(new Degree(0.4), output.get("C", 1));
		assertEquals(new Degree(0.6), output.get("C", 2));
	}

}
