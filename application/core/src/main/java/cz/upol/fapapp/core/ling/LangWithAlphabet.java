package cz.upol.fapapp.core.ling;

import java.util.Set;

public class LangWithAlphabet extends Language {

	private final Alphabet alphabet;

	public LangWithAlphabet(Alphabet alphabet, Set<Word> words) {
		super(words);

		this.alphabet = alphabet;
	}

	public LangWithAlphabet(Alphabet alphabet, Word... words) {
		super(words);

		this.alphabet = alphabet;
	}

	public Alphabet getAlphabet() {
		return alphabet;
	}
}
