package cz.upol.fapapp.core.sets;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.core.sets.BaseRelation.Tuple;

/**
 * Binary relation, i.e. set of tuples.
 * 
 * @author martin
 *
 * @param <DT>
 * @param <TT>
 */
public class BinaryRelation<DT, TT> extends BaseRelation<Couple<DT, TT>> {

	public BinaryRelation(Set<Couple<DT, TT>> tuples) {
		super(tuples);
	}

	public BinaryRelation(Map<DT, TT> map) {
		super(mapToCouples(map));
	}

	/**
	 * Converts itself to map DT -> TT.
	 * 
	 * @return
	 */
	public Map<DT, TT> toMap() {
		return couplesToMap(tuples);
	}

	/**
	 * Returns target of given domain object. Throws exception if not found.
	 * 
	 * @param domain
	 * @return
	 */
	public TT get(DT domain) throws IllegalArgumentException {
		return tuples.stream() //
				.filter((t) -> t.getDomain().equals(domain)) //
				.map((t) -> t.getTarget()) //
				.findAny().orElseThrow(() -> {
					return new IllegalStateException("Not found " + domain + " in " + this);
				});
	}

	///////////////////////////////////////////////////////////////////////////

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
		return "Binary:" + tuples + "";
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * converts given map to set of tuples.
	 * 
	 * @param map
	 * @return
	 */
	private static <DT, TT> Set<Couple<DT, TT>> mapToCouples(Map<DT, TT> map) {
		return map.entrySet().stream() //
				.map((e) -> new Couple<>(e.getKey(), e.getValue())) //
				.collect(Collectors.toSet());
	}

	/**
	 * Converts given set of tuples to map.
	 * 
	 * @param couples
	 * @return
	 */
	private static <DT, TT> Map<DT, TT> couplesToMap(Set<Couple<DT, TT>> couples) {
		return couples.stream() //
				.collect(Collectors.toMap( //
						(c) -> c.getDomain(), //
						(c) -> c.getTarget()));
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Extension of tuple containing exactly two items, called domain and
	 * target.
	 * 
	 * @author martin
	 *
	 * @param <DT>
	 * @param <TT>
	 */
	public static class Couple<DT, TT> extends Tuple {
		private final DT domain;
		private final TT target;

		public Couple(DT domain, TT target) {
			super();
			this.domain = domain;
			this.target = target;
		}

		public DT getDomain() {
			return domain;
		}

		public TT getTarget() {
			return target;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((domain == null) ? 0 : domain.hashCode());
			result = prime * result + ((target == null) ? 0 : target.hashCode());
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
			Couple<?, ?> other = (Couple<?, ?>) obj;
			if (domain == null) {
				if (other.domain != null)
					return false;
			} else if (!domain.equals(other.domain))
				return false;
			if (target == null) {
				if (other.target != null)
					return false;
			} else if (!target.equals(other.target))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "(" + domain + ", " + target + ")";
		}

	}

}
