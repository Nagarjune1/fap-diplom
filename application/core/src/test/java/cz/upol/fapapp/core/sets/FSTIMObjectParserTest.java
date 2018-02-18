package cz.upol.fapapp.core.sets;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;

public class FSTIMObjectParserTest {

	@Test
	public void test() {

		FSTIMObjectParser<String> parser = new FSTIMObjectParser<>("some fuzzy set", (s) -> s);

		final String input = "" //
				+ "type:\n" //
				+ "	some fuzzy set\n" //
				+ "universe:\n" //
				+ "	A B C D\n" //
				+ "mapping:\n" //
				+ "	A 0.1\n" //
				+ "	B 1.0\n" //
				+ "	  0.4\n" //
				+ "	D 0.6\n" //
				+ "\n"; //

		FuzzySet<String> output = parser.parse(input);

		assertEquals(new Degree(0.1), output.degreeOf("A"));
		assertEquals(new Degree(1.0), output.degreeOf("B"));
		assertEquals(new Degree(0.4), output.degreeOf("C"));
		assertEquals(new Degree(0.6), output.degreeOf("D"));
	}

}
