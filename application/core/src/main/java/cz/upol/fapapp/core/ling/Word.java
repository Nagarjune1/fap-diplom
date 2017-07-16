package cz.upol.fapapp.core.ling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Word {
	public static final Word EMPTY = new Word();

	private final List<Symbol> symbols;

	public Word(List<Symbol> symbols) {
		super();
		this.symbols = symbols;
	}

	public Word(Symbol... symbols) {
		super();
		this.symbols = new ArrayList<>(Arrays.asList(symbols));
	}

	public List<Symbol> getSymbols() {
		return symbols;
	}

	public int getLength() {
		return symbols.size();
	}

	public Symbol at(int index) {
		return symbols.get(index);
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
		Word other = (Word) obj;
		if (symbols == null) {
			if (other.symbols != null)
				return false;
		} else if (!symbols.equals(other.symbols))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Word:" + symbols + "";
	}

}
