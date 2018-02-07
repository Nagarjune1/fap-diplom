package cz.upol.fapapp.fa.typos;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;

public class TyposCorrecterTest {

	@BeforeClass
	public static void setUp() {
		 Logger.get().setVerbose(false);
	}

	@Test
	public void testCorrectString() {

		Degree typoRatio = new Degree(0.5);
		KeyboardMap keymap = new DefaultKeymap();
		List<Word> dictionary = Arrays.asList( //
				new Word(new Symbol("f"), new Symbol("o"), new Symbol("o")), //
				new Word(new Symbol("b"), new Symbol("a"), new Symbol("r")), //
				new Word(new Symbol("b"), new Symbol("a"), new Symbol("z"))); //

		TyposCorrecter correcter = new TyposCorrecter(dictionary, keymap, typoRatio);

		// no typo
		assertEquals("foo", correcter.correct("foo"));
		assertEquals("bar", correcter.correct("bar"));
		assertEquals("baz", correcter.correct("baz"));

		// one simple typo (replacement)
		assertEquals("foo", correcter.correct("foi"));
		assertEquals("bar", correcter.correct("bat"));

		// one simple typo (insertion)
		assertEquals("foo", correcter.correct("fooi"));
		assertEquals("baz", correcter.correct("bvaz"));

		// one simple typo (removal)
		//TODO implement and test removal of symbol
		
		// multiples
		assertEquals("foo", correcter.correct("fopp"));
		assertEquals("bar", correcter.correct("vbqart"));
		assertEquals("baz", correcter.correct("basu"));
		
		

	}

}
