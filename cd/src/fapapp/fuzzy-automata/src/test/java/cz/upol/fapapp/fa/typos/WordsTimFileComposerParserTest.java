package cz.upol.fapapp.fa.typos;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;

public class WordsTimFileComposerParserTest {

	@Test
	public void testParse() {
		final String input = "" //
				+ "type:\n" //
				+ "	words for typos correct\n" //
				+ "words:\n" //
				+ "	foo\n" //
				+ "	Lorem Ipsum\n"; //

		WordsTimFileParser parser = new WordsTimFileParser();
		List<Word> actual = parser.parse(input);

		List<Word> expected = Arrays.asList( //
				new Word(new Symbol("f"), new Symbol("o"), new Symbol("o")), //
				new Word(new Symbol("l"), new Symbol("o"), new Symbol("r"), new Symbol("e"), new Symbol("m")), //
				new Word(new Symbol("i"), new Symbol("p"), new Symbol("s"), new Symbol("u"), new Symbol("m"))); //

		System.out.println(actual);

		assertEquals(expected, actual);
	}

	@Test
	public void testCompose() {
		List<Word> input = Arrays.asList( //
				new Word(new Symbol("f"), new Symbol("o"), new Symbol("o")), //
				new Word(new Symbol("l"), new Symbol("o"), new Symbol("r"), new Symbol("e"), new Symbol("m")), //
				new Word(new Symbol("i"), new Symbol("p"), new Symbol("s"), new Symbol("u"), new Symbol("m"))); //

		
		
		WordsTimFileComposer composer = new WordsTimFileComposer();
		String actual = composer.compose(input);

		final String expected = "" //
				+ "type:\n" //
				+ "	words for typos correct\n" //
				+ "\n" //
				+ "words:\n" //
				+ "	foo\n" //
				+ "	lorem\n"
				+ "	ipsum\n" //
				+ "\n"; //

		
		System.out.println(actual);

		assertEquals(expected, actual);
	}

}
