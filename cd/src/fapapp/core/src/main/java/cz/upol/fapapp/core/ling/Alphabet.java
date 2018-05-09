package cz.upol.fapapp.core.ling;

import java.util.Set;
import java.util.TreeSet;

/**
 * General term in languages theory, The alphabet holds symbols (instances of
 * {@link Symbol}). The alphabet is alphabetically-ordersed set.
 * 
 * @author martin
 *
 */
public class Alphabet extends TreeSet<Symbol> {

	private static final long serialVersionUID = 4248164739865894692L;

	public Alphabet(Set<Symbol> symbols) {
		super();
		this.addAll(symbols);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public String toString() {
		return "Alphabet" + super.toString() + "";
	}
}
