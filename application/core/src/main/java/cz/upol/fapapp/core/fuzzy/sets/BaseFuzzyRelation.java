package cz.upol.fapapp.core.fuzzy.sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.sets.BaseRelation.Tuple;

/**
 * General description of (base) fuzzy relation. Represents mapping T -> [0,1] (i.e. @{link Degree}).  
 * @author martin
 *
 * @param <T>
 */
public class BaseFuzzyRelation<T extends Tuple> {

	protected final Map<T, Degree> tuples;

	public BaseFuzzyRelation(Map<T, Degree> tuples) {
		super();
		this.tuples = tuples;
	}

/////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Get degree of acceptance of given tuple.
	 * @param tuple
	 * @return
	 */
	public Degree get(T tuple) {
		return this.tuples.get(tuple); // TODO zero if not in set?
	}

	/**
	 * Returns universe set of the fuzzy relation (all tuples currently in use).
	 * @return
	 */
	public Set<T> domain() {
		return tuples.keySet();
	}
	
	/**
	 * Returns itself as map.
	 * @return
	 */
	public Map<T, Degree> getTuplesWithDegree() {
		return new HashMap<>(tuples);
	}

	/////////////////////////////////////////////////////////////////////////////
	
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
