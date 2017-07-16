package cz.upol.fapapp.core.fuzzy;

import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.sets.BinaryRelation;

public class FuzzySet<E> extends BinaryRelation<E, Degree> {

	public FuzzySet(Set<FuzzyTuple<E>> tuples) {
		super(cast(tuples));
	}

	public FuzzySet(Map<E, Degree> map) {
		super(map);
	}

	private static <E> Set<Couple<E, Degree>> cast(Set<FuzzyTuple<E>> tuples) {
		Set<FuzzyTuple<E>> fuzzys = tuples;
		Object object = fuzzys;

		@SuppressWarnings("unchecked")
		Set<Couple<E, Degree>> couples = (Set<Couple<E, Degree>>) object;

		return couples;
	}
	
	@Override
	public String toString() {
		return "FuzzySet" + tuples + "";
	}

	public static class FuzzyTuple<E> extends Couple<E, Degree> {

		public FuzzyTuple(E domain, Degree target) {
			super(domain, target);
		}
	}

}
