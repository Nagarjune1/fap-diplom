package cz.upol.fapapp.core.fuzzy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.core.sets.CollectionsUtils;

public class FuzzySet<E> {

	private final Map<E, Degree> map;

	public FuzzySet(Set<FuzzyTuple<E>> tuples) {
		super();
		this.map = CollectionsUtils.convert(tuples);
	}

	public FuzzySet(Map<E, Degree> map) {
		super();
		this.map = map;
	}

	public Map<E, Degree> toMap() {
		return new HashMap<>(map);
	}

	public Set<E> domain() {
		return map.keySet();
	}

	public Degree degreeOf(E element) {
		return map.entrySet().stream() //
				.filter((t) -> t.getKey().equals(element)) //
				.map((t) -> t.getValue()) //
				.findAny().orElse(Degree.ZERO);
	}

	public boolean isSubsetOf(FuzzySet<E> superset, boolean allowEquality) {
		return superset.domain().stream() //
				.map((e) -> Degree.isSmallerThan(this.degreeOf(e), superset.degreeOf(e), allowEquality)) //
				.allMatch((b) -> b); //
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuzzySet<?> other = (FuzzySet<?>) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FuzzySet" + map + "";
	}

	public static class FuzzyTuple<E> extends Couple<E, Degree> {

		public FuzzyTuple(E domain, Degree target) {
			super(domain, target);
		}
	}

	public static <E> FuzzySet<E> intersect(FuzzySet<E> first, FuzzySet<E> second) {
		if (!first.domain().equals(second.domain())) {
			Logger.get().warning(
					"Fuzzy sets " + first + " and " + second + " has different domain during the intersection");
		}

		return new FuzzySet<>(first.domain().stream() //
				.filter((e) -> second.domain().contains(e))
				.collect(Collectors.toMap((e) -> (e), (e) -> (Degree.infimum(first.degreeOf(e), second.degreeOf(e))))));
	}
	
	public static <E> FuzzySet<E> union(FuzzySet<E> first, FuzzySet<E> second) {
		if (!first.domain().equals(second.domain())) {
			Logger.get().warning(
					"Fuzzy sets " + first + " and " + second + " has different domain during the union");
		}

		return new FuzzySet<>(first.domain().stream() //
				.filter((e) -> second.domain().contains(e))
				.collect(Collectors.toMap((e) -> (e), (e) -> (Degree.supremum(first.degreeOf(e), second.degreeOf(e))))));
	}

	public static <E> Degree maxDegree(FuzzySet<E> set) {
		// TODO rewrite to lambdas?
		Degree result = Degree.ZERO;

		for (E item : set.domain()) {
			Degree degree = set.degreeOf(item);

			result = Degree.supremum(result, degree);
		}

		return result;

	}

}
