package cz.upol.fapapp.core.fuzzy;

import java.util.Map;

import cz.upol.fapapp.core.sets.BaseFuzzyRelation;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
@Deprecated
public class BinaryFuzzyRelation<DT, TT> extends BaseFuzzyRelation<Couple<DT, TT>> {

	public BinaryFuzzyRelation(Map<Couple<DT, TT>, Degree> tuples) {
		super(tuples);
	}

	
	
	
	@Override
	public String toString() {
		return "BinaryFuzzyRelation: " + tuples;
	}

	

}
