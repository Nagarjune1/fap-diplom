package cz.upol.fapapp.fta.automata;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.infile.InputFileData;
import cz.upol.fapapp.core.infile.InputFileObjectParser;
import cz.upol.fapapp.core.infile.LineItems;
import cz.upol.fapapp.core.infile.ObjectParserTools;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.sets.BinaryRelation;

public class FTAInputFileParser extends InputFileObjectParser<FuzzyTreeAutomata> {

	protected static final String TYPE = "Fuzzy tree automata";
	protected static final String OVER_TO_TO_SEPARATOR_TOKEN = "->";

	public FTAInputFileParser() {
		super(TYPE);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public FuzzyTreeAutomata process(InputFileData data) {
		Set<State> states = null;
		Alphabet nonterminals = null;
		Alphabet terminals = null;
		Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction = null;
		Set<State> finalStates = null;

		for (String key : data.listKeys()) {
			List<LineItems> lines = data.getItemsOf(key);

			switch (key) {
			case InputFileData.TYPE_KEY:
				break;
			case "states":
			case "Q":
				states = processStates(lines);
				break;
			case "nonterminals":
			case "N":
				nonterminals = processNonterminals(lines);
				break;
			case "terminals":
			case "T":
				terminals = processTerminals(lines);
				break;
			case "transition function":
			case "mu":
				transitionFunction = processTransitionFunction(lines);
				break;
			case "final states":
			case "F":
				finalStates = processFinalStates(lines);
				break;
			default:
				System.err.println("Warningn: Unknown key" + key);

			}
		}

		return new FuzzyTreeAutomata(states, nonterminals, terminals, transitionFunction, finalStates);
	}

	///////////////////////////////////////////////////////////////////////////

	private Set<State> processStates(List<LineItems> lines) {
		return ObjectParserTools.parseMultilinedStates(lines);
	}

	private Alphabet processNonterminals(List<LineItems> lines) {
		return ObjectParserTools.parseMultilinedAlphabet(lines);
	}

	private Alphabet processTerminals(List<LineItems> lines) {
		return ObjectParserTools.parseMultilinedAlphabet(lines);
	}

	private Map<Symbol, BinaryRelation<Word, FuzzyState>> processTransitionFunction(List<LineItems> lines) {
		Map<Symbol, BinaryRelation<Word, FuzzyState>> resultMap = new HashMap<>();

		String currentOver = null;
		Map<Word, FuzzyState> currentTransitionMap = null;
		for (LineItems line : lines) {
			if (!line.getIth(0).equals(currentOver)) {
				currentOver = line.getIth(0);

				Symbol overSymbol = ObjectParserTools.parseSymbol(currentOver);
				currentTransitionMap = new HashMap<>();
				BinaryRelation<Word, FuzzyState> currentTransition = new BinaryRelation<>(currentTransitionMap);

				resultMap.put(overSymbol, currentTransition);
			}

			Word word = processOver(line);
			FuzzyState fuzzyState = processFuzzyState(line, word);

			currentTransitionMap.put(word, fuzzyState);
		}

		return resultMap;
	}

	private Word processOver(LineItems line) {
		List<String> oversAndRest = line.rest(1).getItems();
		List<Symbol> overSymbols = new LinkedList<>();

		for (String overOrRest : oversAndRest) {
			if (OVER_TO_TO_SEPARATOR_TOKEN.equals(overOrRest)) {
				break;
			}
			String over = overOrRest;
			Symbol symbol = ObjectParserTools.parseSymbol(over);
			overSymbols.add(symbol);
		}

		Word word = new Word(overSymbols);
		return word;
	}

	private FuzzyState processFuzzyState(LineItems line, Word word) {
		int fuzzyStateFrom = 1 + word.getLength() + 1;
		LineItems fuzzyStateStr = line.rest(fuzzyStateFrom);
		FuzzyState fuzzyState = ObjectParserTools.parseFuzzyState(fuzzyStateStr);
		return fuzzyState;
	}

	private Set<State> processFinalStates(List<LineItems> lines) {
		return ObjectParserTools.parseMultilinedStates(lines);
	}

}
