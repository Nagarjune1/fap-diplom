package cz.upol.automaton.ling.alphabets;

import java.util.Spliterator;

import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.sets.Externisator;
import cz.upol.automaton.sets.SetOfAll;

public abstract class HugeAlphabet extends SetOfAll<Symbol> implements Alphabet {

	public HugeAlphabet() {
		super(Symbol.class);
	}

	@Override
	public boolean shouldBeInSet(String stringReprezentation) {
		Externisator<Symbol> externisator = getSymbolsExternisator();
		Symbol symbol = externisator.parseKnown(stringReprezentation);

		if (symbol == null) {
			return false;
		} else {
			return contains(symbol);
		}
	}

	@Override
	public String externalize() {
		return getDescription();
	}

	/**
	 * Neimplementováno, vyvolá výjimku. TODO FIXME co s tím?
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Spliterator<Symbol> spliterator() {
		throw new UnsupportedOperationException("HugeSet#spliterator");
	}

}
