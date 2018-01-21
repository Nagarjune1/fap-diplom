package cz.upol.fapapp.core.sets;

import java.util.Set;

import cz.upol.fapapp.core.sets.BaseRelation.Tuple;

public class BaseRelation<T extends Tuple> {

	protected final Set<T> tuples;

	public BaseRelation(Set<T> tuples) {
		super();
		this.tuples = tuples;
	}

	public Set<T> getTuples() {
		return tuples;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tuples == null) ? 0 : tuples.hashCode());
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
		BaseRelation<?> other = (BaseRelation<?>) obj;
		if (tuples == null) {
			if (other.tuples != null)
				return false;
		} else if (!tuples.equals(other.tuples))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Relation:" + tuples + "";
	}

	public static abstract class Tuple {

	}

}
