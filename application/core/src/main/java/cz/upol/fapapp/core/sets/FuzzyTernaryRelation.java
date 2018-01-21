package cz.upol.fapapp.core.sets;

import java.util.Map;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;

public class FuzzyTernaryRelation<T1, T2, T3> extends BaseFuzzyRelation<Triple<T1, T2, T3>> {

	public FuzzyTernaryRelation(Map<Triple<T1, T2, T3>, Degree> tuples) {
		super(tuples);
	}

	public Degree get(T1 first, T2 second, T3 third) {
		Triple<T1, T2, T3> triple = new Triple<>(first, second, third);
		return get(triple);
	}

	public Map<Triple<T1, T2, T3>, Degree> get(T1 first, T2 second) {
		return tuples.keySet().stream() //
				.filter((k) -> k.getFirst().equals(first) && k.getSecond().equals(second)) //
				.collect(Collectors.toMap( //
						(k) -> k, //
						(k) -> get(k)));
	}

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
