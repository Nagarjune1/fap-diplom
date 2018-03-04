package cz.upol.fapap.core;

import java.util.Set;

import java.util.stream.Collectors;

import cz.upol.fapap.core.QuadriaryRelation.Quadrituple;
import cz.upol.fapapp.core.sets.BaseRelation;
import cz.upol.fapapp.core.sets.BaseRelation.Tuple;

public class QuadriaryRelation<T1, T2, T3, T4> extends
		BaseRelation<Quadrituple<T1, T2, T3, T4>> {

	public QuadriaryRelation(Set<Quadrituple<T1, T2, T3, T4>> couples) {
		super(couples);
	}

	// TODO

	public Set<Quadrituple<T1, T2, T3, T4>> get(T1 first, T2 second) {

		return tuples
				.stream()
				//
				.filter((t) -> t.getFirst().equals(first)
						&& t.getSecond().equals(second)) //
				.collect(Collectors.toSet()); //
	}

	public static class Quadrituple<T1, T2, T3, T4> extends Tuple {
		private final T1 first;
		private final T2 second;
		private final T3 third;
		private final T4 fourth;

		public Quadrituple(T1 first, T2 second, T3 third, T4 fourth) {
			super();
			this.first = first;
			this.second = second;
			this.third = third;
			this.fourth = fourth;
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

		public T4 getFourth() {
			return fourth;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			result = prime * result
					+ ((fourth == null) ? 0 : fourth.hashCode());
			result = prime * result
					+ ((second == null) ? 0 : second.hashCode());
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
			Quadrituple<?, ?, ?, ?> other = (Quadrituple<?, ?, ?, ?>) obj;
			if (first == null) {
				if (other.first != null)
					return false;
			} else if (!first.equals(other.first))
				return false;
			if (fourth == null) {
				if (other.fourth != null)
					return false;
			} else if (!fourth.equals(other.fourth))
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
			return "(" + first + ", " + second + ", " + third + ", " + fourth
					+ ")";
		}

	}

}
