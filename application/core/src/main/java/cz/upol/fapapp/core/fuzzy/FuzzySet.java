package cz.upol.fapapp.core.fuzzy;

import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.CollectionsUtils;

public class FuzzySet<E> extends BinaryRelation<E, Degree> {

	public FuzzySet(Set<FuzzyTuple<E>> tuples) {
		super(CollectionsUtils.cast(tuples));
	}

	public FuzzySet(Map<E, Degree> map) {
		super(map);
	}

	public Degree degreeOf(E element) {
		return tuples.stream() //
				.filter((t) -> t.getDomain().equals(element)) //
				.map((t) -> t.getTarget()) //
				.findAny().orElse(Degree.ZERO);
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
