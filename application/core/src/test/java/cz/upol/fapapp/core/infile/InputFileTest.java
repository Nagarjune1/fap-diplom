package cz.upol.fapapp.core.infile;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InputFileTest {

	public InputFileTest() {
	}

	////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testParse() {
		String input1 = createTestFileA1();
		String input2 = createTestFileA2();
		InputFileData expectedData = createTestDataA();

		InputFileParser parser = new InputFileParser();
		InputFileData actualData1 = parser.parse(input1);
		InputFileData actualData2 = parser.parse(input2);

		assertEquals(actualData1.toString(), expectedData.toString());
		assertEquals(actualData2.toString(), expectedData.toString());

		assertEquals(actualData1, expectedData);
		assertEquals(actualData2, expectedData);
	}

	@Test
	public void testCompose() {
		InputFileData data = createTestDataA();
		String expectedFile = createTestFileA2();

		InputFileComposer composer = new InputFileComposer();
		String actualFile = composer.compose(data);

		assertEquals(actualFile, expectedFile);
	}

	@Test
	public void testParseAndCompose() {
		String expectedFile = createTestFileA2();

		InputFileParser parser = new InputFileParser();
		InputFileData data = parser.parse(expectedFile);

		InputFileComposer composer = new InputFileComposer();
		String actualFile = composer.compose(data);

		assertEquals(actualFile, expectedFile);
	}

	@Test
	public void testComposeAndParse() {
		InputFileData expectedData = createTestDataA();

		InputFileComposer composer = new InputFileComposer();
		String file = composer.compose(expectedData);

		InputFileParser parser = new InputFileParser();
		InputFileData actualData = parser.parse(file);

		assertEquals(actualData, expectedData);
	}

	////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testProcessPaddedLine() {
		LineItems items1 = new LineItems("foo", "bar", "baz");
		assertEquals(items1, InputFileParser.processPaddedLine("	foo, bar, baz"));

		LineItems items2 = new LineItems("42", "foo", "bar", "baz");
		assertEquals(items2, InputFileParser.processPaddedLine("	42: foo		bar		baz"));

		LineItems items3 = new LineItems("foo", "bar", "baz");
		assertEquals(items3, InputFileParser.processPaddedLine("	foo,bar,baz"));
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

	////////////////////////////////////////////////////////////////////////////////

	private InputFileData createTestDataA() {
		InputFileData expectedData = new InputFileData();
		expectedData.start("foo");
		expectedData.add("foo", new LineItems("Lorem", "Ipsum", "..."));
		expectedData.add("foo", new LineItems("...", "Dolor", "Sit", "Amet"));
		expectedData.start("bar");
		expectedData.add("bar", new LineItems("42"));
		return expectedData;
	}

	private String createTestFileA1() {
		return "" //
				+ "foo:\n" //
				+ "\tLorem Ipsum ...\n" //
				+ "\t... Dolor Sit Amet\n" //
				+ "\n" //
				+ "# something else\n" //
				+ "bar:\n" //
				+ "\t42\n";
	}

	private String createTestFileA2() {
		return "" //
				+ "foo:\n" //
				+ "\tLorem\tIpsum\t...\n" //
				+ "\t...\tDolor\tSit\tAmet\n" //
				+ "\n" //
				+ "bar:\n" //
				+ "\t42\n" + "\n";
	}

}
