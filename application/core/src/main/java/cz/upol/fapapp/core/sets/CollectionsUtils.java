
package cz.upol.fapapp.core.sets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.fuzzy.FuzzySet.FuzzyTuple;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
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

	/////////////////////////////////////////////////////////////////

	public static <E> void checkNotNull(String key, E object) {
		if (object == null) {
			throw new IllegalArgumentException("Missing " + key);
		}
	}

	public static <E> void checkInSet(E element, Set<E> set) {
		if (!set.contains(element)) {
			throw new IllegalArgumentException("Not in set, set " + set + " does not contain " + element);
		}
	}

	@SafeVarargs
	public static <E> void checkInSetsJoin(E element, Set<E>... sets) {
		for (Set<E> set : sets) {
			if (set.contains(element)) {
				return;
			}
		}

		throw new IllegalArgumentException("Not in sets, sets " + Arrays.asList(sets) + " does not contain " + element);
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

	public static void checkFuzzyState(FuzzyState fuzzyState, Set<State> states) {
		fuzzyState.listStates().forEach((s) -> {
			if (!states.contains(s)) {
				throw new IllegalArgumentException(
						"Not subset, subset does't contain " + s + " (" + fuzzyState + " and " + states + ")");
			}
		});
	}

	public static void checkWord(Word word, Alphabet alphabet) {
		word.getSymbols().forEach((s) -> {
			if (!alphabet.contains(s)) {
				throw new IllegalArgumentException(
						"Word " + word + " is not a word of alphabet " + alphabet + " because of " + s);
			}
		});
	}

	public static void checkIsEmptyWord(Word word) {
		if (word.getLength() > 0) {
			throw new IllegalArgumentException("Word " + word + " must be empty");
		}
	}

	public static void checkIsNotEmptyWord(Word word) {
		if (word.getLength() == 0) {
			throw new IllegalArgumentException("Word " + word + " must not be empty");
		}
	}

	/////////////////////////////////////////////////////////////////

	public static <E> FuzzySet<E> singletonFuzzySet(E element) {
		return singletonFuzzySet(element, Degree.ONE);
	}

	public static <E> FuzzySet<E> singletonFuzzySet(E element, Degree degree) {
		Map<E, Degree> map = new HashMap<>();
		map.put(element, degree);

		return new FuzzySet<>(map);
	}

	public static <E> FuzzySet<E> singletonFuzzySet(Set<E> universe, E singleOne) {
		return new FuzzySet<>(universe.stream() //
				.collect(Collectors.toMap( //
						(e) -> e, //
						(e) -> singleOne.equals(e) ? Degree.ONE : Degree.ZERO)));
	}

	/////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public static <E> Set<Couple<E, Degree>> cast(Set<FuzzyTuple<E>> tuples) {
		Set<FuzzyTuple<E>> fuzzys = tuples;
		Object object = fuzzys;

		Set<Couple<E, Degree>> couples = (Set<Couple<E, Degree>>) object;

		return couples;
	}

	public static <E> Map<E, Degree> convert(Set<FuzzyTuple<E>> tuples) {
		return tuples.stream() //
				.collect(Collectors.toMap( //
						(t) -> t.getDomain(), //
						(t) -> t.getTarget()));

	}

	/////////////////////////////////////////////////////////////////

	public static <K, V> Map<K, V> createMap(Set<K> keys, V value) {
		return keys.stream() //
				.collect(Collectors.toMap((e) -> e, //
						(e) -> value));
	}

	public static Alphabet inferAlphabetOfWord(Word word) {
		// TODO move to Alpabet or Word class?
		return new Alphabet(word.getSymbols().stream().collect(Collectors.toSet()));
	}

}
