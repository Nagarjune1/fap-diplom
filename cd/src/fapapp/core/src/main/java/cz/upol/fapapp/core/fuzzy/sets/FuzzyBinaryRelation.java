package cz.upol.fapapp.core.fuzzy.sets;

import java.util.Map;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;

/**
 * Binary fuzzy relation, extension of {@link BaseFuzzyRelation}.
 * 
 * @author martin
 *
 * @param <TD>
 * @param <TT>
 */
public class FuzzyBinaryRelation<TD, TT> extends BaseFuzzyRelation<Couple<TD, TT>> {

	public FuzzyBinaryRelation(Map<Couple<TD, TT>, Degree> tuples) {
		super(tuples);
	}

	/**
	 * Returns degree for given domain and target tuple.
	 * 
	 * @param domain
	 * @param target
	 * @return
	 */
	public Degree get(TD domain, TT target) {
		Couple<TD, TT> couple = new Couple<>(domain, target);
		return get(couple);
	}

	@Override
	public String toString() {
		return "FuzzyBinaryRelation: " + tuples;
	}

}
