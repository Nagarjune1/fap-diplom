package cz.upol.fapap.pa.automata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.upol.fapap.core.QuadriaryRelation;
import cz.upol.fapap.core.QuadriaryRelation.Quadrituple;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.probability.Probability;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class PATIMFParser extends TIMObjectParser<ProbablisticAutomaton> {
	
	public static final String TYPE = "probabilistic automaton";

	public PATIMFParser() {
		super(TYPE);
	}

	@Override
	public ProbablisticAutomaton process(TIMFileData data) {
		Alphabet alphabet = processAlphabet(data);
		Set<State> states = processStates(data);
		QuadriaryRelation<State, Symbol, State, Probability> transitionFunction = processTransitions(data);
		Set<State> initialStates = processInitials(data);
		Set<State> finalStates = processFinals(data);

		//TODO check alphabet and states consistency
		
		return new ProbablisticAutomaton(states, alphabet, transitionFunction, initialStates, finalStates);
	}

	private Set<State> processStates(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "states", "Q");
		return TIMObjectParserComposerTools.parseStates(elements);
	}

	private Alphabet processAlphabet(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "alphabet", "Sigma");
		return TIMObjectParserComposerTools.parseAlphabet(elements);
	}

	private QuadriaryRelation<State, Symbol, State, Probability> processTransitions(TIMFileData data) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "transition function", "mu");

		Set<Quadrituple<State, Symbol, State, Probability>> result = new HashSet<>();

		for (LineElements line : lines) {
			State from = TIMObjectParserComposerTools.parseState(line.getIth(0));
			Symbol over = TIMObjectParserComposerTools.parseSymbol(line.getIth(1));
			State to = TIMObjectParserComposerTools.parseState(line.getIth(2));
			Probability prob = TIMObjectParserComposerTools.parseProbability(line.getIth(3));

			Quadrituple<State, Symbol, State, Probability> quadrituple = new Quadrituple<>(from, over, to, prob);
			
			result.add(quadrituple);
		}

		return new QuadriaryRelation<>(result);
	}

	private Set<State> processInitials(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "initial states", "sigma");
		return TIMObjectParserComposerTools.parseStates(elements);
	}

	private Set<State> processFinals(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "final states", "eta");
		return TIMObjectParserComposerTools.parseStates(elements);
	}


}
