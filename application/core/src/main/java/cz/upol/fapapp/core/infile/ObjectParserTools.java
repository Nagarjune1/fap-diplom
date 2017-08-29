package cz.upol.fapapp.core.infile;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet.FuzzyTuple;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;

public class ObjectParserTools {
	
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
		try {
			double degreeDouble = Double.parseDouble(degreeStr);
			return new Degree(degreeDouble);
		} catch (Exception e) {
			throw new IllegalArgumentException("Not a degree: " + degreeStr);
		}
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

	public static Alphabet parseMultilinedAlphabet(List<LineItems> lines) {
		return new Alphabet(lines.stream() //
				.flatMap((l) -> l.getItems().stream()) //
				.map((s) -> parseSymbol(s)) //
				.collect(Collectors.toSet()));
	}

	public static Set<State> parseMultilinedStates(List<LineItems> lines) {
		return lines.stream() //
				.flatMap((l) -> l.getItems().stream()) //
				.map((s) -> parseState(s)) //
				.collect(Collectors.toSet());
	}

	public static FuzzyState parseFuzzyState(LineItems items) {
		return new FuzzyState(items.getItems().stream() //
				.map((i) -> parseFuzzyTuple(i, (s) -> parseState(s))) //
				.collect(Collectors.toSet())); //
	}

	/////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////

	public static LineItems intsToLine(Integer... ints) {
		return intsToLine(Arrays.asList(ints));
	}

	public static LineItems intsToLine(List<Integer> ints) {
		return collectionToLine(ints, //
				(i) -> Integer.toString(i));
	}

	public static LineItems doublesToLine(List<Double> doubles) {
		return collectionToLine(doubles, //
				(d) -> Double.toString(d));
	}

	/////////////////////////////////////////////////////////////////////////

	public static LineItems statesToLine(Set<State> states) {
		return collectionToLine(states, //
				(s) -> s.getLabel());
	}

	public static LineItems symbolsToLine(Set<Symbol> symbols) {
		return collectionToLine(symbols, //
				(s) -> s.getValue());
	}

	public static LineItems wordToLine(Word word) {
		return collectionToLine(word.getSymbols(), //
				(s) -> s.getValue());
	}

	public static LineItems fuzzyStateToLine(FuzzyState fuzzyState) {
		return collectionToLine(fuzzyState.getTuples(), //
				(t) -> t.getDomain().getLabel() + "/" + t.getTarget().getValue());
	}

	/////////////////////////////////////////////////////////////////////////

	private static <E> LineItems collectionToLine(Collection<E> collection, Function<E, String> toStringFunction) {
		return new LineItems(collection.stream() //
				.map((i) -> toStringFunction.apply(i)) //
				.collect(Collectors.toList()));
	}

}
