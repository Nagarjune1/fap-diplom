package cz.upol.fapapp.core.sets;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.core.sets.BaseRelation.Tuple;

public class BinaryRelation<DT, TT> extends BaseRelation<Couple<DT, TT>> {

	public BinaryRelation(Set<Couple<DT, TT>> tuples) {
		super(tuples);
	}

	public BinaryRelation(Map<DT, TT> map) {
		super(mapToCouples(map));
	}

	public TT get(DT domain) {
		return tuples.stream() //
				.filter((t) -> t.getDomain().equals(domain)) //
				.map((t) -> t.getTarget()) //
				.findAny().orElseThrow(() -> {
					return new IllegalStateException("Not found " + domain + " in " + this);
				});
	}

	public Map<DT, TT> toMap() {
		return couplesToMap(tuples);
	}

	private static <DT, TT> Set<Couple<DT, TT>> mapToCouples(Map<DT, TT> map) {
		return map.entrySet().stream() //
				.map((e) -> new Couple<>(e.getKey(), e.getValue())) //
				.collect(Collectors.toSet());
	}

	private static <DT, TT> Map<DT, TT> couplesToMap(Set<Couple<DT, TT>> couples) {
		return couples.stream() //
				.collect(Collectors.toMap( //
						(c) -> c.getDomain(), //
						(c) -> c.getTarget()));
	}

	@Override
	public String toString() {
		return "Binary:" + tuples + "";
	}

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
			Couple<?,?> other = (Couple<?,?>) obj;
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
