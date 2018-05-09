package cz.upol.fapapp.fa.automata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} parser of {@link BaseFuzzyAutomaton}.
 * 
 * @author martin
 *
 */
public class FATIMParser extends TIMObjectParser<BaseFuzzyAutomaton> {

	public static final String TYPE = "fuzzy automaton";

	public FATIMParser() {
		super(FATIMParser.TYPE);
	}

	@Override
	public BaseFuzzyAutomaton process(TIMFileData data) {
		Alphabet alphabet = processAlphabet(data);
		Set<State> states = processStates(data);
		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = processTransitions(data);
		FuzzySet<State> initialStates = processInitials(data);
		FuzzySet<State> finalStates = processFinals(data);

		// TODO check alphabet and states consistency

		return new FuzzyAutomaton(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	private Set<State> processStates(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "states", "Q");
		return TIMObjectParserComposerTools.parseStates(elements);
	}

	private Alphabet processAlphabet(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "alphabet", "Sigma");
		return TIMObjectParserComposerTools.parseAlphabet(elements);
	}

	private FuzzyTernaryRelation<State, Symbol, State> processTransitions(TIMFileData data) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "transition function", "mu");

		Map<Triple<State, Symbol, State>, Degree> result = new HashMap<>();

		for (LineElements line : lines) {
			State from = TIMObjectParserComposerTools.parseState(line.getIth(0));
			Symbol over = TIMObjectParserComposerTools.parseSymbol(line.getIth(1));
			State to = TIMObjectParserComposerTools.parseState(line.getIth(2));
			Degree degree = TIMObjectParserComposerTools.parseDegree(line.getIth(3));

			Triple<State, Symbol, State> triple = new Triple<State, Symbol, State>(from, over, to);
			result.put(triple, degree);
		}

		return new FuzzyTernaryRelation<>(result);
	}

	private FuzzySet<State> processInitials(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "initial states", "sigma");
		return new FuzzySet<>(TIMObjectParserComposerTools.parseFuzzyState(elements).toMap());
	}

	private FuzzySet<State> processFinals(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "final states", "eta");
		return new FuzzySet<>(TIMObjectParserComposerTools.parseFuzzyState(elements).toMap());
	}

}
