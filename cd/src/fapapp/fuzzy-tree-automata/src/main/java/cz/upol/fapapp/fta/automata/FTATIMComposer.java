package cz.upol.fapapp.fta.automata;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectComposer} of {@link FuzzyTreeAutomaton}.
 * 
 * @author martin
 *
 */
public class FTATIMComposer extends TIMObjectComposer<FuzzyTreeAutomaton> {

	public FTATIMComposer() {
		super(FTATIMParser.TYPE);
	}

	@Override
	protected void process(FuzzyTreeAutomaton object, TIMFileData data) {
		processStates(object.getStates(), data);
		processNonterminals(object.getNonterminals(), data);
		processTerminals(object.getTerminals(), data);
		processTransitionFunction(object.getTransitionFunction(), data);
		processFinalStates(object.getFinalStates(), data);

	}

	///////////////////////////////////////////////////////////////////////////

	private void processStates(Set<State> states, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.statesToLine(states);
		data.add("states", line);
	}

	private void processNonterminals(Alphabet nonterminals, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.symbolsToLine(nonterminals);
		data.add("nonterminals", line);
	}

	private void processTerminals(Alphabet terminals, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.symbolsToLine(terminals);
		data.add("terminals", line);
	}

	private void processTransitionFunction(Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction,
			TIMFileData data) {

		for (Entry<Symbol, BinaryRelation<Word, FuzzyState>> entry : transitionFunction.entrySet()) {
			Symbol currentSymbol = entry.getKey();

			for (Couple<Word, FuzzyState> couple : entry.getValue().getTuples()) {
				LineElements line = generateTransitionLine(currentSymbol, couple);
				data.add("transition function", line);
			}
		}
	}

	private void processFinalStates(Set<State> finalStates, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.statesToLine(finalStates);
		data.add("final states", line);
	}

	///////////////////////////////////////////////////////////////////////////

	private LineElements generateTransitionLine(Symbol currentSymbol, Couple<Word, FuzzyState> couple) {
		List<String> items = new LinkedList<>();
		items.add(currentSymbol.getValue());

		LineElements word = TIMObjectParserComposerTools.wordToLine(couple.getDomain());
		items.addAll(word.getElements());

		items.add(FTATIMParser.OVER_TO_TO_SEPARATOR_TOKEN);

		LineElements fuzzyState = TIMObjectParserComposerTools.fuzzyStateToLine(couple.getTarget());
		items.addAll(fuzzyState.getElements());

		LineElements line = new LineElements(items);
		return line;
	}

}
