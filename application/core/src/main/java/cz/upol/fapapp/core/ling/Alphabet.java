package cz.upol.fapapp.core.ling;

import java.util.Set;
import java.util.TreeSet;

public class Alphabet extends TreeSet<Symbol> {

	private static final long serialVersionUID = 4248164739865894692L;

	public Alphabet(Set<Symbol> symbols) {
		super();
		this.addAll(symbols);
	}

	@Override
	public String toString() {
		return "Alphabet" + super.toString() + "";
	}
}
