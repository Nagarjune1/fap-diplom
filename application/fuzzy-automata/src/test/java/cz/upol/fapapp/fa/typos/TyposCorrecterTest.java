package cz.upol.fapapp.fa.typos;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.tnorm.ProductTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;

public class TyposCorrecterTest {

	private static final Degree REPLACES_DEGREE = new Degree(0.7);
	private static final Degree REMOVES_DEGREE = new Degree(0.3);
	private static final Degree INSERTS_ONES_DEGREE = new Degree(0.4);
	private static final Degree INSERTS_MORE_DEGREE = new Degree(0.2);
	
	@BeforeClass
	public static void setUp() {
		 Logger.get().setVerbose(false);
		 TNorms.setTnorm(new ProductTNorm());
	}

	@Test
	public void testCorrectString() {

		KeyboardMap keymap = new DefaultKeymap();
		List<Word> dictionary = Arrays.asList( //
				new Word(new Symbol("f"), new Symbol("o"), new Symbol("o")), //
				new Word(new Symbol("b"), new Symbol("a"), new Symbol("r")), //
				new Word(new Symbol("b"), new Symbol("a"), new Symbol("z"))); //

		TyposCorrecter correcter = new TyposCorrecter(dictionary, keymap, //
				REPLACES_DEGREE, REMOVES_DEGREE, INSERTS_ONES_DEGREE, INSERTS_MORE_DEGREE);

		// no typo
		assertEquals("foo", correcter.correct("foo"));
		assertEquals("bar", correcter.correct("bar"));
		assertEquals("baz", correcter.correct("baz"));

		// one simple typo (replacement)
		assertEquals("FOO", correcter.correct("foi"));
		assertEquals("BAR", correcter.correct("bat"));

		// one simple typo (insertion)
		assertEquals("FOO", correcter.correct("fooi"));
		assertEquals("BAZ", correcter.correct("bvaz"));

		// one simple typo (removal)
		assertEquals("FOO", correcter.correct("fo"));
		assertEquals("BAZ", correcter.correct("bz"));
		
		// multiples
		assertEquals("FOO", correcter.correct("fopp"));
		assertEquals("BAR", correcter.correct("vbqart"));
		assertEquals("BAZ", correcter.correct("basu"));
		
		

	}

}
