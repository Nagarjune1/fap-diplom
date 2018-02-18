package cz.upol.fapapp.core.ling;

import java.util.Set;

/**
 * Language is set of words. No more to say.
 * 
 * @author martin
 *
 */
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((words == null) ? 0 : words.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		if (words == null) {
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Language:" + words;
	}
}
