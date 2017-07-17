package cz.upol.fapapp.core.infile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet.FuzzyTuple;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;

public class ObjectParserTools {

	public static Symbol parseSymbol(String str) {
		if (Symbol.EMPTY.getValue().equals(str)) {
			return Symbol.EMPTY;
		} else {
			return new Symbol(str);
		}
	}

	public static Alphabet parseMultilinedAlphabet(List<LineItems> lines) {
		return new Alphabet(lines.stream() //
				.flatMap((l) -> l.getItems().stream()) //
				.map((s) -> new Symbol(s)) //
				.collect(Collectors.toSet()));
	}

	public static Set<State> parseMultilinedStates(List<LineItems> lines) {
		return lines.stream() //
				.flatMap((l) -> l.getItems().stream()) //
				.map((s) -> new State(s)) //
				.collect(Collectors.toSet());
	}

	public static FuzzyState parseFuzzyState(LineItems items) {
		return new FuzzyState(items.getItems().stream().map((i) -> {
			// TODO OOOO validate & check !!!! also improvised solution
			String parts[] = i.split("/");
			if (parts.length != 2) {
				System.err.println("Missing or uneccessary / in " + i);
				return null;
			}
			String stateStr = parts[0];
			String degreeStr = parts[1];

			State state = new State(stateStr);
			Degree degree = new Degree(Double.parseDouble(degreeStr));

			return new FuzzyTuple<>(state, degree);
		}).collect(Collectors.toSet()));
	}

	public static LineItems statesToLine(Set<State> states) {
		return new LineItems(states.stream() //
				.map((s) -> s.getLabel()) //
				.collect(Collectors.toList()));

	}

	public static LineItems symbolsToLine(Set<Symbol> symbols) {
		return new LineItems(symbols.stream() //
				.map((s) -> s.getValue()) //
				.collect(Collectors.toList()));

	}

	public static LineItems wordToLine(Word word) {
		return new LineItems(word.getSymbols().stream() //
				.map((s) -> s.getValue()) //
				.collect(Collectors.toList()));
	}

	public static LineItems fuzzyStateToLine(FuzzyState fuzzyState) {
		return new LineItems(fuzzyState.getTuples().stream() //
				.map((t) -> t.getDomain().getLabel() + "/" + t.getTarget().getValue()) //
				.collect(Collectors.toList()));
	}

}
