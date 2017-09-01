package cz.upol.fapapp.cfa.misc;

import static org.junit.Assert.*;

import org.junit.Test;

public class TwoDimArrTIMTest {

	private final TwoDimArrTIMComposer<Integer> composer = new TwoDimArrTIMComposer<>("some array", String::valueOf,
			"numbers");
	private final TwoDimArrTIMParser<Integer> parser = new TwoDimArrTIMParser<>("some array", Integer::parseInt,
			"numbers");

	@Test
	public void testCompose() {

		TwoDimArray<Integer> array = createArrayA();
		String expected = createGeneratedFileA();

		String actual = composer.compose(array);
		assertEquals(actual, expected);
	}

	@Test
	public void testParse() {

		String input = createSomeFileA();
		TwoDimArray<Integer> expected = createArrayA();

		TwoDimArray<Integer> actual = parser.parse(input);
		assertEquals(actual, expected);

	}

	@Test
	public void testComposeAndParse() {

		TwoDimArray<Integer> array = createArrayA();

		String composed = composer.compose(array);
		TwoDimArray<Integer> parsed = parser.parse(composed);

		assertEquals(array, parsed);
	}

	@Test
	public void testParseAndCompose() {

		String input = createGeneratedFileA();

		TwoDimArray<Integer> parsed = parser.parse(input);
		String composed = composer.compose(parsed);

		assertEquals(input, composed);

	}

	/*************************************************************************/
	@Test(expected = IllegalArgumentException.class)
	public void testParseWithMissingRows() {
		String input = createSomeFileWithMissingRows();
		parser.parse(input);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseWithMissingCols() {
		String input = createSomeFileWithMissingCols();
		parser.parse(input);
	}

	@Test()
	public void testParseWithExtraRows() {
		String input = createSomeFileWithExtraRows();
		parser.parse(input);
	}

	@Test()
	public void testParseWithExtraCols() {
		String input = createSomeFileWithExtraCols();
		parser.parse(input);
	}

	/*************************************************************************/

	private String createSomeFileA() {
		String input = "" //
				+ "type:\n" //
				+ "	some array\n" //
				+ "size:\n" //
				+ "	0, 3 \n" //
				+ "numbers:\n" //
				+ "	1	2	3\n" //
				+ "	40	50	60\n" //
				+ "	97	98	99\n" //
				+ "\n"; //
		return input;
	}

	private String createGeneratedFileA() {
		String expected = "" //
				+ "type:\n" //
				+ "	some array\n" //
				+ "\n" //
				+ "size:\n" //
				+ "	0	3\n" //
				+ "\n" //
				+ "numbers:\n" //
				+ "	1	2	3\n" //
				+ "	40	50	60\n" //
				+ "	97	98	99\n" //
				+ "\n"; //
		return expected;
	}

	private TwoDimArray<Integer> createArrayA() {
		TwoDimArray<Integer> arr = new TwoDimArray<>(0, 3);

		arr.set(0, 0, 1);
		arr.set(0, 1, 2);
		arr.set(0, 2, 3);

		arr.set(1, 0, 40);
		arr.set(1, 1, 50);
		arr.set(1, 2, 60);

		arr.set(2, 0, 97);
		arr.set(2, 1, 98);
		arr.set(2, 2, 99);

		return arr;
	}

	/*************************************************************************/

	private String createSomeFileWithMissingRows() {
		return "type:\n" + "	some array\n" + "size:\n" + "	0, 3 \n" + "numbers:\n" //
				+ "	1	2	3\n" //
				+ "\n"; //
	}

	private String createSomeFileWithMissingCols() {
		return "type:\n" + "	some array\n" + "size:\n" + "	0, 3 \n" + "numbers:\n" //
				+ "	1\n" //
				+ "	40\n" //
				+ "	97\n" //
				+ "\n"; //
	}

	private String createSomeFileWithExtraRows() {
		return "type:\n" + "	some array\n" + "size:\n" + "	0, 3 \n" + "numbers:\n" //
				+ "	1	2	3\n" //
				+ "	40	50	60\n" //
				+ "	97	98	99\n" //
				+ "	101	102	103\n" //
				+ "	201	202	203\n" //
				+ "\n"; //
	}

	private String createSomeFileWithExtraCols() {
		return "type:\n" + "	some array\n" + "size:\n" + "	0, 3 \n" + "numbers:\n" //
				+ "	1	2	3 101 201\n" //
				+ "	40	50	60 102 202\n" //
				+ "	97	98	99 103 203\n" //
				+ "\n"; //
	}

}
