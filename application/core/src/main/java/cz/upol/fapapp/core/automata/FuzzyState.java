package cz.upol.fapapp.core.automata;

import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;

public class FuzzyState extends FuzzySet<State> {

	public FuzzyState(Set<FuzzyTuple<State>> tuples) {
		super(tuples);
	}

	public FuzzyState(Map<State, Degree> map) {
		super(map);
	}

	public FuzzyState(FuzzySet<State> fuzzySet) {
		super(fuzzySet.toMap());
	}

	public Set<State> listStates() {
		return domain();
	}

	@Override
	public String toString() {
		return "FuzzyState: " + toMap();
	}

	
	
	

	
}
