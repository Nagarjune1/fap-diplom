package cz.upol.automaton.ling.alphabets;

import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.sets.Externisator;

public class AllStringsAlphabet extends HugeAlphabet {

	public AllStringsAlphabet() {
	}

	@Override
	public Externisator<Symbol> getSymbolsExternisator() {
		return Symbol.EXTERNISATOR;
	}

	@Override
	public String getDescription() {
		return "Alphabet of all symbols";
	}
}
