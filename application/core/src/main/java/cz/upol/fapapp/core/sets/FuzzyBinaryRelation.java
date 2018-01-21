package cz.upol.fapapp.core.sets;

import java.util.Map;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;

public class FuzzyBinaryRelation<TD, TT> extends BaseFuzzyRelation<Couple<TD, TT>> {

	public FuzzyBinaryRelation(Map<Couple<TD, TT>, Degree> tuples) {
		super(tuples);
	}

	public Degree get(TD domain, TT target) {
		Couple<TD, TT> couple = new Couple<>(domain, target);
		return get(couple);
	}

	@Override
	public String toString() {
		return "FuzzyBinaryRelation: " + tuples;
	}

}
