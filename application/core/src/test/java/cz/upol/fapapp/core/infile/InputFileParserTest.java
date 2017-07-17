package cz.upol.fapapp.core.infile;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputFileParserTest {

	@Test
	public void testParse() {
		String input = "" //
				+ "foo:\n" //
				+ "\tLorem Ipsum ...\n" //
				+ "\t... Dolor Sit Amet\n" //
				+ "\n" //
				+ "# something else\n" //
				+ "bar:\n" //
				+ "\t42\n";

		InputFileData expectedData = new InputFileData();
		expectedData.start("foo");
		expectedData.add("foo", new LineItems("Lorem", "Ipsum", "..."));
		expectedData.add("foo", new LineItems("...", "Dolor", "Sit", "Amet"));
		expectedData.start("bar");
		expectedData.add("bar", new LineItems("42"));

		InputFileParser parser = new InputFileParser();
		InputFileData actualData = parser.parse(input);

		assertEquals(actualData.toString(), expectedData.toString());
		assertEquals(actualData, expectedData);
	}

	@Test
	public void testProcessPaddedLine() {
		LineItems items1 = new LineItems("foo", "bar", "baz");
		assertEquals(items1, InputFileParser.processPaddedLine("foo, bar, baz"));

		LineItems items2 = new LineItems("42", "foo", "bar", "baz");
		assertEquals(items2, InputFileParser.processPaddedLine("42: foo		bar		baz"));

		LineItems items3 = new LineItems("foo", "bar", "baz");
		assertEquals(items3, InputFileParser.processPaddedLine("foo,bar,baz"));
	}

	@Test
	public void testProcessGroupOpeningLine() {
		assertEquals("foo", InputFileParser.processGroupOpeningLine("foo"));
		assertEquals("foo bar", InputFileParser.processGroupOpeningLine("foo bar"));
		assertEquals("foo", InputFileParser.processGroupOpeningLine("foo:"));
	}

	@Test
	public void testRemoveFromBegin() {
		assertEquals("barbaz", InputFileParser.removeFromBegin("foobarbaz", "foo"));
	}

	@Test
	public void testRemoveOptionalFromEnd() {
		assertEquals("foobar", InputFileParser.removeOptionalFromEnd("foobarbaz", "baz"));
		assertEquals("foobar", InputFileParser.removeOptionalFromEnd("foobar", "baz"));
	}

}
