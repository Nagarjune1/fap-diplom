package cz.upol.fapapp.core.ling;

import java.util.List;

/**
 * {@link Word} extended to have assigned alphabet with itself.
 * 
 * @author martin
 *
 */
public class WordWithAlphabet extends Word {
	
	private final Alphabet alphabet;

	public WordWithAlphabet(Alphabet alphabet, Word word) {
		super(word.getSymbols());

		this.alphabet = alphabet;
	}

	public WordWithAlphabet(Alphabet alphabet, List<Symbol> symbols) {
		super(symbols);

		this.alphabet = alphabet;
	}

	public WordWithAlphabet(Alphabet alphabet, Symbol... symbols) {
		super(symbols);

		this.alphabet = alphabet;
	}

	public Alphabet getAlphabet() {
		return alphabet;
	}

}
