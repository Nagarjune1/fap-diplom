package cz.upol.fapapp.core.ling;

import java.util.Set;

import org.junit.Test;

import cz.upol.fapapp.core.misc.CollectionsUtils;

public class TestLing {

	private static final Symbol S1 = new Symbol("a");
	private static final Symbol S2 = new Symbol("b");
	private static final Symbol S3 = new Symbol("c");

	@Test
	public void testAlphabet() {
		Set<Symbol> symbols = CollectionsUtils.toSet(S1, S2, S3);

		Alphabet a1 = new Alphabet(symbols);

		System.out.println(a1);
	}


	@Test
	public void testWord() {
		Word w1 = new Word(S1, S2, S1, S3);
		
		System.out.println(w1);
	}

	
}
