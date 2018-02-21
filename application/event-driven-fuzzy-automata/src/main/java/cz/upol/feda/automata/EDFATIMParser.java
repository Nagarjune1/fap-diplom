package cz.upol.feda.automata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.lingvar.BaseLingVarLabel;
import cz.upol.fapapp.core.lingvar.LingVarsTIMParser;
import cz.upol.fapapp.core.lingvar.LingvisticVariable;
import cz.upol.fapapp.core.sets.TernaryRelation;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} of {@link EventDrivenFuzzyAutomaton}.
 * 
 * @author martin
 *
 */
public class EDFATIMParser extends TIMObjectParser<EventDrivenFuzzyAutomaton> {

	public static final String TYPE = "event driven fuzzy automaton";

	private final LingVarsTIMParser lingVarsParser = new LingVarsTIMParser("whatever", "events", "events alphabet");

	public EDFATIMParser() {
		super(TYPE);
	}

	@Override
	public EventDrivenFuzzyAutomaton process(TIMFileData data) {
		Set<LingvisticVariable> eventsAlphabet = processAlphabet(data);
		Set<State> states = processStates(data);
		TernaryRelation<State, BaseLingVarLabel, State> transitionFunction = processTransitions(data, eventsAlphabet);
		FuzzySet<State> initialStates = processInitials(data);

		// TODO check alphabet and states consistency

		return new EventDrivenFuzzyAutomaton(states, eventsAlphabet, transitionFunction, initialStates);
	}

	private Set<State> processStates(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "states", "Q");
		return TIMObjectParserComposerTools.parseStates(elements);
	}

	private Set<LingvisticVariable> processAlphabet(TIMFileData data) {
		return lingVarsParser.process(data);
	}

	private TernaryRelation<State, BaseLingVarLabel, State> processTransitions(TIMFileData data,
			Set<LingvisticVariable> vars) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "transition function", "mu");

		Set<Triple<State, BaseLingVarLabel, State>> result = new HashSet<>();

		for (LineElements line : lines) {
			TIMObjectParserComposerTools.lenthAtLeast(line, 7);
			TIMObjectParserComposerTools.is(line, 0, "from");
			State from = TIMObjectParserComposerTools.parseState(line.getIth(1));
			TIMObjectParserComposerTools.is(line, 2, "if");
			String varName = line.getIth(3);
			TIMObjectParserComposerTools.is(line, 4, "is");
			String labelName = line.getIth(5);
			TIMObjectParserComposerTools.is(line, 6, "to");
			State to = TIMObjectParserComposerTools.parseState(line.getIth(7));

			BaseLingVarLabel over = inferLabel(varName, labelName, vars);

			Triple<State, BaseLingVarLabel, State> triple = new Triple<>(from, over, to);
			result.add(triple);
		}

		return new TernaryRelation<>(result);
	}

	private BaseLingVarLabel inferLabel(String varName, String labelName, Set<LingvisticVariable> vars) {
		LingvisticVariable var = LingvisticVariable.getVariable(varName, vars);
		return var.getLabel(labelName);
	}

	private FuzzySet<State> processInitials(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "initial states", "sigma");
		return new FuzzySet<>(TIMObjectParserComposerTools.parseFuzzyState(elements).toMap());
	}

}
