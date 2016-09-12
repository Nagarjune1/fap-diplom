package cz.upol.automaton.ling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Word {
	/**
	 * @uml.property  name="symbols"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="cz.upol.automaton.ling.Symbol"
	 */
	private final List<Symbol> symbols;

	public Word() {
		this.symbols = new ArrayList<Symbol>();
	}

	public Word(Symbol... symbols) {
		this.symbols = Arrays.<Symbol> asList(symbols);
	}

	protected Word(List<Symbol> symbols) {
		this.symbols = symbols;
	}

	public int length() {
		return symbols.size();
	}

	public Symbol symbolAt(int index) {
		if (index < 0 || index >= symbols.size()) {
			throw new IllegalArgumentException("Index " + index
					+ " is out of range of word with length " + symbols.size());
		} else {
			return symbols.get(index);
		}
	}

	public Word popFirst() {
		List<Symbol> newSymbols = symbols.subList(1, symbols.size());
		return new Word(newSymbols);
	}

	public Word concatenate(Word other) {
		int newLength = this.length() + other.length();
		List<Symbol> newSymbols = new ArrayList<Symbol>(newLength);

		newSymbols.addAll(this.symbols);
		newSymbols.addAll(other.symbols);

		return new Word(newSymbols);
	}

}
