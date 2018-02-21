package cz.upol.fapapp.core.fuzzy.sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;

/**
 * Base term in fuzzy set theory. Represents fuzzy set, i.e. mapping from E to
 * {@link Degree}.
 * 
 * @author martin
 *
 * @param <E>
 */
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

	//////////////////////////////////////////////////////////////////////////

	/**
	 * Returns this instance as map.
	 * 
	 * @return
	 */
	public Map<E, Degree> toMap() {
		return new HashMap<>(map);
	}

	/**
	 * Returns universe set of this fuzzy set.
	 * 
	 * @return
	 */
	public Set<E> domain() {
		return map.keySet();
	}

	/**
	 * Returns degree of which given element to this set belongs. If given does
	 * even not belong to universe, returns {@link Degree#ZERO}.
	 * 
	 * @param element
	 * @return
	 */
	public Degree degreeOf(E element) {
		return map.entrySet().stream() //
				.filter((t) -> t.getKey().equals(element)) //
				.map((t) -> t.getValue()) //
				.findAny().orElse(Degree.ZERO);
	}

	/**
	 * Returns true, if this is superset of given set, i.e. superset is superset
	 * of this set.
	 * 
	 * @param superset
	 * @param allowEquality
	 * @return
	 */
	public boolean isSubsetOf(FuzzySet<E> superset, boolean allowEquality) {
		return superset.domain().stream() //
				.map((e) -> Degree.isSmallerThan(this.degreeOf(e), superset.degreeOf(e), allowEquality)) //
				.allMatch((b) -> b); //
	}

	/**
	 * Returns element for which this set has the higher degree.
	 * 
	 * @return
	 */
	// TODO testme
	public E withMaxDegree() {
		Set<E> bestEs = new HashSet<>();
		Degree bestDegree  = Degree.ZERO;
		
		for (E elem: map.keySet()) {
			Degree degree = map.get(elem);
			
			if (bestDegree.compareTo(degree) < 0) {
				bestDegree = degree;
				bestEs.clear();
				bestEs.add(elem);
			}
			if (bestDegree.compareTo(degree) == 0) {
				bestEs.add(elem);
			}
			if (bestDegree.compareTo(degree) > 0) {
				continue;
			}
		}
		
		if (Degree.ZERO.equals(bestDegree)) {
			Logger.get().warning("Computed result is in degree 0.0");
		}
		
		if (bestEs.size() == 0) {
			throw new IllegalStateException("No such element");
		}
		if (bestEs.size() == 1) {
			return bestEs.iterator().next();
		}
		if (bestEs.size() >= 2) {
			Logger.get().warning("Found " + bestEs.size() + " elements with max degree in " + this);
			return bestEs.iterator().next();
		}
		
		return null;
	}

	/**
	 * Returns the biggest degree this fuzzy set has.
	 * 
	 * @param set
	 * @return
	 */
	public Degree maxDegree() {
		// TODO rewrite to lambdas
		Degree result = Degree.ZERO;

		for (E item : this.domain()) {
			Degree degree = this.degreeOf(item);

			result = Degree.supremum(result, degree);
		}

		return result;

	}

	/**
	 * Creates new fuzzy set containging the intersection of the given fuzzy
	 * sets.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static <E> FuzzySet<E> intersect(FuzzySet<E> first, FuzzySet<E> second) {
		if (!first.domain().equals(second.domain())) {
			Logger.get().warning(
					"Fuzzy sets " + first + " and " + second + " has different domain during the intersection");
		}

		return new FuzzySet<>(first.domain().stream() //
				.filter((e) -> second.domain().contains(e))
				.collect(Collectors.toMap((e) -> (e), (e) -> (Degree.infimum(first.degreeOf(e), second.degreeOf(e))))));
	}

	/**
	 * Creates new fuzzy set containging the union of the given fuzzy sets.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static <E> FuzzySet<E> union(FuzzySet<E> first, FuzzySet<E> second) {
		if (!first.domain().equals(second.domain())) {
			Logger.get().warning("Fuzzy sets " + first + " and " + second + " has different domain during the union");
		}

		return new FuzzySet<>(first.domain().stream() //
				.filter((e) -> second.domain().contains(e)).collect(
						Collectors.toMap((e) -> (e), (e) -> (Degree.supremum(first.degreeOf(e), second.degreeOf(e))))));
	}

	////////////////////////////////////////////////////////////////////////////

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

	//////////////////////////////////////////////////////////////////////////

	/**
	 * Base type for storing "fuzzy" data in fuzzy set.
	 * 
	 * @author martin
	 *
	 * @param <E>
	 */
	public static class FuzzyTuple<E> extends Couple<E, Degree> {

		public FuzzyTuple(E domain, Degree target) {
			super(domain, target);
		}
	}

}
