package cz.upol.fapapp.core.fuzzy;

import java.util.Map;

import cz.upol.fapapp.core.sets.BaseFuzzyRelation;
import cz.upol.fapapp.core.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
/**
 * @see FuzzyTernaryRelation
 * @author martin
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */
@Deprecated
public class TernaryFuzzyRelation<T1, T2, T3> extends BaseFuzzyRelation<Triple<T1, T2, T3>> {

	public TernaryFuzzyRelation(Map<Triple<T1, T2, T3>, Degree> tuples) {
		super(tuples);
	}

	@Override
	public String toString() {
		return "TernaryFuzzyRelation: " + tuples;
	}

}
