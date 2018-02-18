package cz.upol.fapapp.core.fuzzy.sets;

import java.util.Map;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;

/**
 * Ternary fuzzy relation, extension of {@link BaseFuzzyRelation}.
 * 
 * @author martin
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */
public class FuzzyTernaryRelation<T1, T2, T3> extends BaseFuzzyRelation<Triple<T1, T2, T3>> {

	public FuzzyTernaryRelation(Map<Triple<T1, T2, T3>, Degree> tuples) {
		super(tuples);
	}

	/**
	 * Returns degree of given triple.
	 * 
	 * @param first
	 * @param second
	 * @param third
	 * @return
	 */
	public Degree get(T1 first, T2 second, T3 third) {
		Triple<T1, T2, T3> triple = new Triple<>(first, second, third);
		return get(triple);
	}

	/**
	 * Returns fuzzy set containing elements of given couple, i.e. map satysfying:
	 * result(x) = get(first, second, x)
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public Map<Triple<T1, T2, T3>, Degree> get(T1 first, T2 second) {
		return tuples.keySet().stream() //
				.filter((k) -> k.getFirst().equals(first) && k.getSecond().equals(second)) //
				.collect(Collectors.toMap( //
						(k) -> k, //
						(k) -> get(k)));
	}

	/**
	 * Returns fuzzy set (map) containing elements matching given element, i.e.
	 * map satisfying: result(x, y) = get(first, x ,y)
	 * 
	 * @param first
	 * @return
	 */
	public Map<Triple<T1, T2, T3>, Degree> get(T1 first) {
		return tuples.keySet().stream() //
				.filter((k) -> k.getFirst().equals(first)) //
				.collect(Collectors.toMap( //
						(k) -> k, //
						(k) -> get(k)));
	}

	@Override
	public Degree get(Triple<T1, T2, T3> triple) {
		return super.get(triple);
	}

	@Override
	public String toString() {
		return "FuzzyTernaryRelation: " + tuples;
	}

}
