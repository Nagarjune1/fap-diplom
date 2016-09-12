package cz.upol.automaton.ling;

import cz.upol.automaton.sets.Externalisable;
import cz.upol.automaton.sets.Externisator;

public class Symbol implements Comparable<Symbol>, Externalisable<Symbol> {

	public static final Externisator<Symbol> EXTERNISATOR = new SymbolsExternisator();
	/**
	 * @uml.property name="value"
	 */
	private final String value;

	public Symbol(String value) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException(value + " is not valid symbol");
		}

		this.value = value;
	}

	public Symbol(char c) {
		this.value = Character.toString(c);
	}

	/**
	 * @return
	 * @uml.property name="value"
	 */
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Symbol other = (Symbol) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Symbol [" + value + "]";
	}

	@Override
	public int compareTo(Symbol o) {
		if (o.equals(EmptyStringSymbol.INSTANCE)) {
			return -1;
		} else {
			return this.getValue().compareTo(o.getValue());
		}
	}

	@Override
	public Externisator<Symbol> getExternisator() {
		return EXTERNISATOR;
	}

	public static class SymbolsExternisator extends Externisator<Symbol> {

		@Override
		public Symbol parseKnown(String string) {
			if (string == null || string.isEmpty()) {
				return null;
			} else {
				return new Symbol(string);
			}

		}

		@Override
		public String externalizeKnown(Symbol externasiable) {
			if (externasiable == null) {
				return null;
			} else {
				return externasiable.getValue();
			}
		}

	}

}
