package cz.upol.fapapp.core.ling;

import java.util.Set;

public class Alphabet {
	private final Set<Symbol> symbols;

	public Alphabet(Set<Symbol> symbols) {
		super();
		this.symbols = symbols;
	}

	public Set<Symbol> getSymbols() {
		return symbols;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbols == null) ? 0 : symbols.hashCode());
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
		Alphabet other = (Alphabet) obj;
		if (symbols == null) {
			if (other.symbols != null)
				return false;
		} else if (!symbols.equals(other.symbols))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Alphabet" + symbols + "";
	}
}
