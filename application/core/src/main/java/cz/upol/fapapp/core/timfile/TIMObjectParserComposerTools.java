package cz.upol.fapapp.core.timfile;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet.FuzzyTuple;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.lingvar.LingVarValue;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.probability.Probability;

/**
 * Various utilities for {@link TIMObjectParser}'s and {@link TIMObjectComposer}'s implementations.
 * 
 * @author martin
 *
 */
//TODO javadoc
//TODO rename to somehing shorter
public class TIMObjectParserComposerTools {

	/////////////////////////////////////////////////////////////////////////

	public static void lenthAtLeast(LineElements line, int count) throws IllegalArgumentException {
		if (line.getElements().size() < count) {
			throw new IllegalArgumentException("Line " + line + " must have at least " + count + " elements");
		}
	}

	public static void is(LineElements line, int index, String value) throws IllegalArgumentException {
		lenthAtLeast(line, index); // just for sure

		if (!value.equals(line.getIth(index))) {
			throw new IllegalArgumentException("Line " + line + " must contain " + value + " at " + index);
		}
	}

	/////////////////////////////////////////////////////////////////////////

	public static int parseInt(String intStr) {
		try {
			return Integer.parseInt(intStr);
		} catch (Exception e) {
			throw new IllegalArgumentException("Not a (int) number: " + intStr);
		}
	}

	public static double parseDouble(String doubleStr) {
		try {
			return Double.parseDouble(doubleStr);
		} catch (Exception e) {
			throw new IllegalArgumentException("Not a (double) number: " + doubleStr);
		}
	}

	/////////////////////////////////////////////////////////////////////////

	public static Symbol parseEmptyableSymbol(String symbolStr) {
		if (Symbol.EMPTY.getValue().equals(symbolStr)) {
			return Symbol.EMPTY;
		} else {
			return parseSymbol(symbolStr);
		}
	}

	public static Symbol parseSymbol(String symbolStr) {
		return new Symbol(symbolStr);
	}

	public static Degree parseDegree(String degreeStr) {
			double degreeDouble = parseDouble(degreeStr);
			return new Degree(degreeDouble);
	}


	public static Probability parseProbability(String probStr) {
		double probDouble = parseDouble(probStr);
		return new Probability(probDouble);
	}

	
	public static LingVarValue parseLingVarValue(String valStr) {
		double valDouble = parseDouble(valStr);
		return new LingVarValue(valDouble);
	}
	
	public static State parseState(String stateStr) {
		return new State(stateStr);
	}

	private static <E> FuzzyTuple<E> parseFuzzyTuple(String token, Function<String, E> mapper) {
		String parts[] = token.split("/");

		if (parts.length < 2) {
			throw new IllegalArgumentException("Missing degree in " + token);
		}
		if (parts.length > 2) {
			throw new IllegalArgumentException("Unnescessary / in " + token);
		}

		String objStr = parts[0];
		String degreeStr = parts[1];

		E obj = mapper.apply(objStr);
		Degree degree = parseDegree(degreeStr);

		return new FuzzyTuple<>(obj, degree);
	}

	/////////////////////////////////////////////////////////////////////////

	public static boolean has(TIMFileData data, String... itemNames) {
		for (String itemName : itemNames) {
			if (data.hasItem(itemName)) {
				return true;
			}
		}

		return false;
	}
	
	public static String findItemName(TIMFileData data, String... itemNames) {
		for (String itemName : itemNames) {
			if (data.hasItem(itemName)) {
				return itemName;
			}
		}

		throw new IllegalArgumentException("Missing item (one of): " + Arrays.toString(itemNames));
	}

	public static List<LineElements> findElements(TIMFileData data, String... itemNames) {
		String itemName = findItemName(data, itemNames);
		return data.getElementsOf(itemName);
	}

	public static LineElements findElementsMerged(TIMFileData data, String... itemNames) {
		String itemName = findItemName(data, itemNames);
		return data.getElementsMerged(itemName);
	}

	public static String findSingleItem(TIMFileData data, String... itemNames) {
		String itemName = findItemName(data, itemNames);
		List<LineElements> lines = data.getElementsOf(itemName);

		// TODO if lines is empty ...
		if (lines.size() != 1 || lines.get(0).count() != 1) {
			Logger.get().warning("Required exactly one element of item " + itemName + ", ignoring rest");
		}

		return lines.get(0).getIth(0);
	}

	/////////////////////////////////////////////////////////////////////////

	public static Alphabet parseAlphabet(LineElements elements) {
		return new Alphabet(new TreeSet<>(//
				elements.getElements().stream() //
						.map((s) -> parseSymbol(s)) //
						.collect(Collectors.toSet())));
	}

	public static Set<State> parseStates(LineElements elements) {
		return new TreeSet<>(elements.getElements().stream() //
				.map((s) -> parseState(s)) //
				.collect(Collectors.toSet()));
	}

	public static FuzzyState parseFuzzyState(LineElements elements) {
		return new FuzzyState(elements.getElements().stream() //
				.map((i) -> parseFuzzyTuple(i, (s) -> parseState(s))) //
				.collect(Collectors.toSet())); //
	}

	public static Word parseWord(LineElements elements) {
		return new Word(elements.getElements().stream() //
				.map((s) -> parseSymbol(s)) //
				.collect(Collectors.toList()));
	}

	/////////////////////////////////////////////////////////////////////////

	public static LineElements intsToLine(Integer... ints) {
		return intsToLine(Arrays.asList(ints));
	}

	public static LineElements intsToLine(List<Integer> ints) {
		return collectionToLine(ints, //
				(i) -> Integer.toString(i));
	}

	public static LineElements doublesToLine(Double... doubles) {
		return doublesToLine(Arrays.asList(doubles));
	}

	public static LineElements doublesToLine(List<Double> doubles) {
		return collectionToLine(doubles, //
				(d) -> Double.toString(d));
	}

	/////////////////////////////////////////////////////////////////////////

	public static LineElements statesToLine(Set<State> states) {
		return collectionToLine(states, //
				(s) -> s.getLabel());
	}

	public static LineElements symbolsToLine(Set<Symbol> symbols) {
		return collectionToLine(symbols, //
				(s) -> s.getValue());
	}

	public static LineElements alphabetToLine(Alphabet alphabet) {
		return symbolsToLine(alphabet);
	}

	public static LineElements wordToLine(Word word) {
		return collectionToLine(word.getSymbols(), //
				(s) -> s.getValue());
	}

	public static LineElements fuzzyStateToLine(FuzzyState fuzzyState) {
		return collectionToLine(fuzzyState.toMap().entrySet(), //
				(t) -> t.getKey().getLabel() + "/" + t.getValue().getValue());
	}

	/////////////////////////////////////////////////////////////////////////

	private static <E> LineElements collectionToLine(Collection<E> collection, Function<E, String> toStringFunction) {
		return new LineElements(collection.stream() //
				.map((i) -> toStringFunction.apply(i)) //
				.collect(Collectors.toList()));
	}


}
