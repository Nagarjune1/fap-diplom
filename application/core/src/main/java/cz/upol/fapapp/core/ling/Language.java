package cz.upol.fapapp.core.ling;

import java.util.Set;

public class Language {
	private final Set<Word> words;

	public Language(Set<Word> words) {
		super();
		this.words = words;
	}

	public Set<Word> getWords() {
		return words;
	}

	@Override
	public String toString() {
		return "Language:" + words;
	}
}
