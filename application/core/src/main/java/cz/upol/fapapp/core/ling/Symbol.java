package cz.upol.fapapp.core.ling;

public class Symbol implements Comparable<Symbol> {
	public static final Symbol EMPTY = new Symbol("epsilon");	//TODO epsilon

	private final String value;

	public Symbol(String value) {
		super();
		this.value = value;
	}

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
		return "Sym(" + value + ")";
	}

	@Override
	public int compareTo(Symbol o) {
		return this.value.compareTo(o.value);
	}
}
