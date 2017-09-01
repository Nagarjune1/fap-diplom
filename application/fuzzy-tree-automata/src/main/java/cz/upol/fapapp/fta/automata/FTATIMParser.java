package cz.upol.fapapp.fta.automata;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class FTATIMParser extends TIMObjectParser<FuzzyTreeAutomata> {

	protected static final String TYPE = "Fuzzy tree automata";
	protected static final String OVER_TO_TO_SEPARATOR_TOKEN = "->";

	public FTATIMParser() {
		super(TYPE);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public FuzzyTreeAutomata process(TIMFileData data) {
		Set<State> states = processStates(data);
		Alphabet nonterminals = processNonterminals(data);
		Alphabet terminals = processTerminals(data);
		Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction = processTransitionFunction(data);
		Set<State> finalStates = processFinalStates(data);

		return new FuzzyTreeAutomata(states, nonterminals, terminals, transitionFunction, finalStates);
	}

	///////////////////////////////////////////////////////////////////////////

	private Set<State> processStates(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "states", "Q");
		return TIMObjectParserComposerTools.parseStates(elements);
	}

	private Alphabet processNonterminals(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "nonterminals", "N");
		return TIMObjectParserComposerTools.parseAlphabet(elements);
	}

	private Alphabet processTerminals(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "terminals", "T");
		return TIMObjectParserComposerTools.parseAlphabet(elements);
	}

	private Map<Symbol, BinaryRelation<Word, FuzzyState>> processTransitionFunction(TIMFileData data) {
		Map<String, Map<Word, FuzzyState>> pseudoresult = new HashMap<>();
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "transition function", "mu");

		for (LineElements line : lines) {
			String over = line.getIth(0);
			Map<Word, FuzzyState> transs = pseudoresult.get(over);
			if (transs == null) {
				transs = new HashMap<>();
				pseudoresult.put(over, transs);
			}

			Word word = processOver(line);
			FuzzyState fuzzyState = processFuzzyState(line, word);

			if (fuzzyState != null) {
				transs.put(word, fuzzyState);
			}
		}

		return toRelation(pseudoresult);
	}

	///////////////////////////////////////////////////////////////////////////

	private Set<State> processFinalStates(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "final states", "finals", "F");
		return TIMObjectParserComposerTools.parseStates(elements);
	}

	private Word processOver(LineElements line) {
		List<String> oversAndRest = line.rest(1).getElements();
		List<Symbol> overSymbols = new LinkedList<>();

		for (String overOrRest : oversAndRest) {
			if (OVER_TO_TO_SEPARATOR_TOKEN.equals(overOrRest)) {
				break;
			}
			String over = overOrRest;
			Symbol symbol = TIMObjectParserComposerTools.parseEmptyableSymbol(over);
			overSymbols.add(symbol);
		}

		Word word = new Word(overSymbols);
		return word;
	}

	private FuzzyState processFuzzyState(LineElements line, Word word) {
		int fuzzyStateFrom = line.firstIndexOf(OVER_TO_TO_SEPARATOR_TOKEN) + 1;

		LineElements fuzzyStateStr = line.rest(fuzzyStateFrom);
		FuzzyState fuzzyState = TIMObjectParserComposerTools.parseFuzzyState(fuzzyStateStr);

		return fuzzyState;
	}

	private Map<Symbol, BinaryRelation<Word, FuzzyState>> toRelation(Map<String, Map<Word, FuzzyState>> pseudoresult) {
		return pseudoresult.keySet().stream() //
				.collect(Collectors.toMap( //
						(s) -> new Symbol(s), //
						(s) -> new BinaryRelation<>(pseudoresult.get(s))));
	}

}
