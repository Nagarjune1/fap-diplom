package cz.upol.automaton.fuzzyLogic;

public interface ComparableTo<T> extends Comparable<T> {
	public boolean isLowerOrEqual(T other);
}
