package cz.upol.automaton.fuzzyLogic;

public interface Orderable<T extends Orderable<T>> extends Comparable<T> {
	public boolean isLowerOrEqual(T other);
}
