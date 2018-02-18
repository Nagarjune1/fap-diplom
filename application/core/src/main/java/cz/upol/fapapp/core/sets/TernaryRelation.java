package cz.upol.fapapp.core.sets;

import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.sets.BaseRelation.Tuple;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;

/**
 * Ternary relation, set of triples.
 * 
 * @author martin
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */
public class TernaryRelation<T1, T2, T3> extends BaseRelation<Triple<T1, T2, T3>> {

	public TernaryRelation(Set<Triple<T1, T2, T3>> tuples) {
		super(tuples);
	}

	public TernaryRelation(Map<T1, Map<T2, T3>> map) {
		super(mapToTriples(map));
	}

	/**
	 * Converts itself to map of maps, i.e. T1 -> (T2 -> T3).
	 * 
	 * @return
	 */
	public Map<T1, Map<T2, T3>> toMap() {
		return triplesToMap(tuples);
	}

	/**
	 * Returns element for given two. Throws exception if not found.
	 * 
	 * @param first
	 * @param second
	 * @see #getOrNot(Object, Object)
	 * @return
	 */
	public T3 get(T1 first, T2 second) {
		return tuples.stream() //
				.filter((t) -> t.getFirst().equals(first) && t.getSecond().equals(second)) //
				.map((t) -> t.getThird()) //
				.findAny().orElseThrow(() -> {
					return new IllegalStateException("Not found (" + first + ", " + second + ") in " + this);
				});
	}

	/**
	 * Returns element for given two. Returns null if not found.
	 * 
	 * @param first
	 * @param second
	 * @see #get(Object, Object).
	 * @return
	 */
	public T3 getOrNot(T1 first, T2 second) {
		return tuples.stream() //
				.filter((t) -> t.getFirst().equals(first) && t.getSecond().equals(second)) //
				.map((t) -> t.getThird()) //
				.findAny().orElse(null);
	}

	/**
	 * Converts given map to set of triples.
	 * 
	 * @param map
	 * @return
	 */
	private static <T1, T2, T3> Set<Triple<T1, T2, T3>> mapToTriples(Map<T1, Map<T2, T3>> map) {
		throw new UnsupportedOperationException("mapToTriples");
		// return map.entrySet().stream() //
		// .map((e) -> new Triple<>(e.getKey(), (T2) null, (T3) null))
		// .collect(Collectors.toSet());
		// TODO FIXME IMPLEMENTME
	}

	/**
	 * Converts given set of triples to map.
	 * 
	 * @param tuples
	 * @return
	 */
	private static <T1, T2, T3> Map<T1, Map<T2, T3>> triplesToMap(Set<Triple<T1, T2, T3>> tuples) {
		throw new UnsupportedOperationException("triplesToMap");
		// return tuples.stream() //
		// .collect(Collectors.toMap( //
		// (c) -> c.getFirst(), //
		// (c) -> null));
		// TODO FIXME IMPLEMENTME
	}

	
////////////////////////////////////////////////////////////////////////////
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Ternary:" + tuples + "";
	}
	
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Triple, simple tuple containing exactly three values, named first, second and third.
	 * @author martin
	 *
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 */
	public static class Triple<T1, T2, T3> extends Tuple {
		private final T1 first;
		private final T2 second;
		private final T3 third;

		public Triple(T1 first, T2 second, T3 third) {
			super();
			this.first = first;
			this.second = second;
			this.third = third;
		}

		public T1 getFirst() {
			return first;
		}

		public T2 getSecond() {
			return second;
		}

		public T3 getThird() {
			return third;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			result = prime * result + ((second == null) ? 0 : second.hashCode());
			result = prime * result + ((third == null) ? 0 : third.hashCode());
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
			Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
			if (first == null) {
				if (other.first != null)
					return false;
			} else if (!first.equals(other.first))
				return false;
			if (second == null) {
				if (other.second != null)
					return false;
			} else if (!second.equals(other.second))
				return false;
			if (third == null) {
				if (other.third != null)
					return false;
			} else if (!third.equals(other.third))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "(" + first + ", " + second + ", " + third + ")";
		}
	}
}
