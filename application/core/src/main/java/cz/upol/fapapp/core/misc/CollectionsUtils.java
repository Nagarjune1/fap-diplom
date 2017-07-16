
package cz.upol.fapapp.core.misc;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet.FuzzyTuple;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;

public class CollectionsUtils {

	@SafeVarargs
	public static <E> Set<E> toSet(E... elements) {
		return Arrays.stream(elements).collect(Collectors.toSet());
	}

	@SafeVarargs
	public static <DT, TT> BinaryRelation<DT, TT> toBinary(Couple<DT, TT>... elements) {
		return new BinaryRelation<>(Arrays.stream(elements).collect(Collectors.toSet()));
	}

	public static Alphabet toAlphabet(Symbol... symbols) {
		return new Alphabet(Arrays.stream(symbols).collect(Collectors.toSet()));
	}

	@SuppressWarnings("unchecked")
	public static <E> Set<Couple<E, Degree>> cast(Set<FuzzyTuple<E>> tuples) {
		Set<FuzzyTuple<E>> fuzzys = tuples;
		Object object = fuzzys;

		Set<Couple<E, Degree>> couples = (Set<Couple<E, Degree>>) object;

		return couples;
	}

	public static <E> void checkDisjoint(Set<E> first, Set<E> second) {
		first.forEach((e) -> {
			if (second.contains(e)) {
				throw new IllegalArgumentException(
						"Not disjoint, both contains " + e + " (" + first + " and " + second + ")");
			}
		});
	}

	public static <E> void checkSubset(Set<E> subset, Set<E> superset) {
		subset.forEach((e) -> {
			if (!superset.contains(e)) {
				throw new IllegalArgumentException(
						"Not subset, subset does't contain " + e + " (" + subset + " and " + superset + ")");
			}
		});
	}
}
