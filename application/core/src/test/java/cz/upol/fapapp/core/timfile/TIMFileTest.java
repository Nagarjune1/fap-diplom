package cz.upol.fapapp.core.timfile;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileComposer;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMFileParser;

public class TIMFileTest {

	public TIMFileTest() {
	}

	////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testParse() {
		String input1 = createTestFileA1();
		String input2 = createTestFileA2();
		TIMFileData expectedData = createTestDataA();

		TIMFileParser parser = new TIMFileParser();
		TIMFileData actualData1 = parser.parse(input1);
		TIMFileData actualData2 = parser.parse(input2);

		assertEquals(actualData1.toString(), expectedData.toString());
		assertEquals(actualData2.toString(), expectedData.toString());

		assertEquals(actualData1, expectedData);
		assertEquals(actualData2, expectedData);
	}

	@Test
	public void testCompose() {
		TIMFileData data = createTestDataA();
		String expectedFile = createTestFileA2();

		TIMFileComposer composer = new TIMFileComposer();
		String actualFile = composer.compose(data);

		assertEquals(actualFile, expectedFile);
	}

	@Test
	public void testParseAndCompose() {
		String expectedFile = createTestFileA2();

		TIMFileParser parser = new TIMFileParser();
		TIMFileData data = parser.parse(expectedFile);

		TIMFileComposer composer = new TIMFileComposer();
		String actualFile = composer.compose(data);

		assertEquals(actualFile, expectedFile);
	}

	@Test
	public void testComposeAndParse() {
		TIMFileData expectedData = createTestDataA();

		TIMFileComposer composer = new TIMFileComposer();
		String file = composer.compose(expectedData);

		TIMFileParser parser = new TIMFileParser();
		TIMFileData actualData = parser.parse(file);

		assertEquals(actualData, expectedData);
	}

	////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testProcessPaddedLine() {
		LineElements items1 = new LineElements("foo", "bar", "baz");
		assertEquals(items1, TIMFileParser.processItemElementsLine("	foo, bar, baz"));

		LineElements items2 = new LineElements("42", "foo", "bar", "baz");
		assertEquals(items2, TIMFileParser.processItemElementsLine("	42: foo		bar		baz"));

		LineElements items3 = new LineElements("foo", "bar", "baz");
		assertEquals(items3, TIMFileParser.processItemElementsLine("	foo,bar,baz"));
	}

	@Test
	public void testProcessGroupOpeningLine() {
		assertEquals("foo", TIMFileParser.processItemOpeningLine("foo"));
		assertEquals("foo bar", TIMFileParser.processItemOpeningLine("foo bar"));
		assertEquals("foo", TIMFileParser.processItemOpeningLine("foo:"));
	}

	@Test
	public void testRemoveFromBegin() {
		assertEquals("barbaz", TIMFileParser.removeFromBegin("foobarbaz", "foo"));
	}

	@Test
	public void testRemoveOptionalFromEnd() {
		assertEquals("foobar", TIMFileParser.removeOptionalFromEnd("foobarbaz", "baz"));
		assertEquals("foobar", TIMFileParser.removeOptionalFromEnd("foobar", "baz"));
	}

	////////////////////////////////////////////////////////////////////////////////

	private TIMFileData createTestDataA() {
		TIMFileData expectedData = new TIMFileData();
		expectedData.add("foo", new LineElements("Lorem", "Ipsum", "..."));
		expectedData.add("foo", new LineElements("...", "Dolor", "Sit", "Amet"));
		expectedData.add("bar", new LineElements("42"));
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
