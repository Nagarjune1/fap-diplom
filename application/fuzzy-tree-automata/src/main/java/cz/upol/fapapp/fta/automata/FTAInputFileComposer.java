package cz.upol.fapapp.fta.automata;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.infile.InputFileData;
import cz.upol.fapapp.core.infile.InputFileObjectComposer;
import cz.upol.fapapp.core.infile.LineItems;
import cz.upol.fapapp.core.infile.ObjectParserTools;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;

public class FTAInputFileComposer extends InputFileObjectComposer<FuzzyTreeAutomata> {

	public FTAInputFileComposer() {
		super(FTAInputFileParser.TYPE);
	}

	@Override
	protected void process(FuzzyTreeAutomata object, InputFileData data) {
		processStates(object.getStates(), data);
		processNonterminals(object.getNonterminals(), data);
		processTerminals(object.getTerminals(), data);
		processTransitionFunction(object.getTransitionFunction(), data);
		processFinalStates(object.getFinalStates(), data);

	}

	///////////////////////////////////////////////////////////////////////////

	private void processStates(Set<State> states, InputFileData data) {
		data.start("states");
		LineItems line = ObjectParserTools.statesToLine(states);
		data.add("states", line);
	}

	private void processNonterminals(Alphabet nonterminals, InputFileData data) {
		data.start("nonterminals");
		LineItems line = ObjectParserTools.symbolsToLine(nonterminals);
		data.add("nonterminals", line);
	}

	private void processTerminals(Alphabet terminals, InputFileData data) {
		data.start("terminals");
		LineItems line = ObjectParserTools.symbolsToLine(terminals);
		data.add("terminals", line);
	}

	private void processTransitionFunction(Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction,
			InputFileData data) {
		
		data.start("transition function");
		
		for (Entry<Symbol, BinaryRelation<Word, FuzzyState>> entry: transitionFunction.entrySet()) {
			Symbol currentSymbol = entry.getKey();
			
			List<String> items = new LinkedList<>();
			items.add(currentSymbol.getValue());
			
			for (Couple<Word, FuzzyState> couple: entry.getValue().getTuples()) {
				LineItems word = ObjectParserTools.wordToLine(couple.getDomain());
				items.addAll(word.getItems());
				
				items.add(FTAInputFileParser.OVER_TO_TO_SEPARATOR_TOKEN);
				
				LineItems fuzzyState = ObjectParserTools.fuzzyStateToLine(couple.getTarget());
				items.addAll(fuzzyState.getItems());
			}
			
			LineItems line = new LineItems(items);
			data.add("transition function", line);	
		}
	}

	private void processFinalStates(Set<State> finalStates, InputFileData data) {
		data.start("final states");
		LineItems line = ObjectParserTools.statesToLine(finalStates);
		data.add("final states", line);
	}

	///////////////////////////////////////////////////////////////////////////

}
