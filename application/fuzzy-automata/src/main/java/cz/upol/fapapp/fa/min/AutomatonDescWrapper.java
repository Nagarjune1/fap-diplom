package cz.upol.fapapp.fa.min;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Symbol;

public interface AutomatonDescWrapper<I> {

	public Degree initalIn(I input);
	public Degree finalIn(I input);
	public Degree transition(I from, Symbol over, I to);
	
}
