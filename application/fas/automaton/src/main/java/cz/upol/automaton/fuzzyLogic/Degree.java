package cz.upol.automaton.fuzzyLogic;

import cz.upol.automaton.sets.Externalisable;

public interface Degree  extends ComparableTo<Degree >,
		Externalisable<Degree > {
	
	public String toPrint();
}
