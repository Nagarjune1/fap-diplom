package cz.upol.fapapp.feda.automata;

import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.lingvar.BaseLingVarLabel;
import cz.upol.fapapp.core.lingvar.LingVarsTIMComposer;
import cz.upol.fapapp.core.lingvar.LingvisticVariable;
import cz.upol.fapapp.core.sets.TernaryRelation;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectComposer} of {@link EventDrivenFuzzyAutomaton}.
 * @author martin
 *
 */
public class EDFATIMComposer extends TIMObjectComposer<EventDrivenFuzzyAutomaton> {

	private final LingVarsTIMComposer lingVarsComposer = new LingVarsTIMComposer("whatewer", "events");

	public EDFATIMComposer() {
		super(EDFATIMParser.TYPE);
	}

	@Override
	protected void process(EventDrivenFuzzyAutomaton object, TIMFileData data) {
		processStates(object.getStates(), data);
		processEvents(object.getEventsAlphabet(), data);
		processTransitions(object.getTransitionFunction(), data);
		processInitials(object.getInitialStates(), data);
	}

	private void processStates(Set<State> states, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.statesToLine(states);
		data.add("states", line);
	}

	private void processEvents(Set<LingvisticVariable> eventsAlphabet, TIMFileData data) {
		lingVarsComposer.process(eventsAlphabet, data);
	}

	private void processTransitions(TernaryRelation<State, BaseLingVarLabel, State> transitionFunction,
			TIMFileData data) {

		for (Triple<State, BaseLingVarLabel, State> triple : transitionFunction.getTuples()) {
			State from = triple.getFirst();
			BaseLingVarLabel over = triple.getSecond();
			State to = triple.getThird();
			
			LineElements line = transitionToLine(from, over, to);
			data.add("transition function", line);
		}
	}

	private LineElements transitionToLine(State from, BaseLingVarLabel over, State to) {
		String fromStr = from.getLabel();
		String varName = over.getVariable().getName();
		String labelName = over.getLabel();
		String toStr = to.getLabel();

		LineElements line = new LineElements("from", fromStr, "if", varName, "is", labelName, "to", toStr);
		return line;
	}

	private void processInitials(FuzzySet<State> initialStates, TIMFileData data) {
		FuzzyState fuzzyState = new FuzzyState(initialStates);
		LineElements line = TIMObjectParserComposerTools.fuzzyStateToLine(fuzzyState);
		data.add("initial states", line);
	}


}
