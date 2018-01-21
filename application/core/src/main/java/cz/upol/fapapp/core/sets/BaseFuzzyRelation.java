package cz.upol.fapapp.core.sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.sets.BaseRelation.Tuple;

public class BaseFuzzyRelation<T extends Tuple> {

	protected final Map<T, Degree> tuples;

	public BaseFuzzyRelation(Map<T, Degree> tuples) {
		super();
		this.tuples = tuples;
	}

	public Degree get(T tuple) {
		return this.tuples.get(tuple); // TODO zero if not in set?
	}

	public Set<T> domain() {
		return tuples.keySet();
	}
	
	public Map<T, Degree> getTuplesWithDegree() {
		return new HashMap<>(tuples);
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
		BaseFuzzyRelation<?> other = (BaseFuzzyRelation<?>) obj;
		if (tuples == null) {
			if (other.tuples != null)
				return false;
		} else if (!tuples.equals(other.tuples))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseFuzzyRelation:" + tuples + "";
	}

}
