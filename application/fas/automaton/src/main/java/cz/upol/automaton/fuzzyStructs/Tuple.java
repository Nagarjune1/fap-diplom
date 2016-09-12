package cz.upol.automaton.fuzzyStructs;

public interface Tuple {
	public Object getIthItem(int i) throws IllegalArgumentException;

	public int hashCode();

	public boolean equals(Object obj);
}
