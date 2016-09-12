package cz.upol.automaton.ling.alphabets;

import cz.upol.automaton.sets.Externalisable;

public interface SetOfExternisables<E extends Externalisable<E>> {

	public abstract boolean shouldBeInSet(String stringReprezentation);

}