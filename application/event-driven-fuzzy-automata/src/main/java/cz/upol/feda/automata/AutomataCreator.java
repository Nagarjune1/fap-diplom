package cz.upol.feda.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.sets.TernaryRelation;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.feda.event.FuzzyEvent;
import cz.upol.feda.event.FuzzyEventsSequence;
import cz.upol.feda.lingvar.BaseLingVarLabel;
import cz.upol.feda.lingvar.LinearLingVarLabel;
import cz.upol.feda.lingvar.LingVarValue;
import cz.upol.feda.lingvar.LingvisticVariable;
import cz.upol.feda.lingvar.UnaryVarLabel;

public class AutomataCreator {

	public static EventDrivenFuzzyAutomata createSome() {
		final State stateQ0 = new State("q_0");
		final State stateQ1 = new State("q_1");
		final State stateQ2 = new State("q_2");

		final BaseLingVarLabel labelXL = new LinearLingVarLabel("low", //
				new LingVarValue(0.0), new LingVarValue(0.0), new LingVarValue(40.0), new LingVarValue(60.0));
		final BaseLingVarLabel labelXH = new LinearLingVarLabel("hig", //
				new LingVarValue(40.0), new LingVarValue(60.0), new LingVarValue(100.0), new LingVarValue(100.0));
		final BaseLingVarLabel labelPO = new UnaryVarLabel("pulse");

		final LingvisticVariable varX = new LingvisticVariable("the-X", labelXL, labelXH);
		final LingvisticVariable varO = new LingvisticVariable("occured", labelPO);

		Set<State> states = new TreeSet<>();
		states.add(stateQ0);
		states.add(stateQ1);
		states.add(stateQ2);

		Set<LingvisticVariable> eventsAlphabet = new HashSet<>();
		eventsAlphabet.add(varX);
		eventsAlphabet.add(varO);

		Set<Triple<State, BaseLingVarLabel, State>> transitions = new HashSet<>();
		transitions.add(new Triple<State, BaseLingVarLabel, State>(stateQ0, labelXL, stateQ1));
		transitions.add(new Triple<State, BaseLingVarLabel, State>(stateQ0, labelXH, stateQ2));
		transitions.add(new Triple<State, BaseLingVarLabel, State>(stateQ1, labelXL, stateQ0));
		transitions.add(new Triple<State, BaseLingVarLabel, State>(stateQ2, labelXH, stateQ2));

		transitions.add(new Triple<State, BaseLingVarLabel, State>(stateQ1, labelPO, stateQ2));
		transitions.add(new Triple<State, BaseLingVarLabel, State>(stateQ2, labelPO, stateQ1));

		TernaryRelation<State, BaseLingVarLabel, State> transitionFunction = new TernaryRelation<>(transitions);

		Map<State, Degree> initials = new HashMap<>();
		initials.put(stateQ0, Degree.ZERO);
		initials.put(stateQ1, Degree.ONE);
		initials.put(stateQ2, new Degree(0.5));
		FuzzySet<State> initialStates = new FuzzySet<>(initials);

		Map<State, Degree> finals = new HashMap<>();
		finals.put(stateQ0, new Degree(0.1));
		finals.put(stateQ1, Degree.ONE);
		finals.put(stateQ2, new Degree(0.2));
		FuzzySet<State> finalStates = new FuzzySet<>(finals);

		return new EventDrivenFuzzyAutomata(states, eventsAlphabet, transitionFunction, initialStates, finalStates);
	}

	public static Set<LingvisticVariable> createSomeLingVars() {

		BaseLingVarLabel labelXL = new LinearLingVarLabel("low", //
				new LingVarValue(0.0), new LingVarValue(0.0), new LingVarValue(40.0), new LingVarValue(60.0));
		BaseLingVarLabel labelXH = new LinearLingVarLabel("hig", //
				new LingVarValue(40.0), new LingVarValue(60.0), new LingVarValue(100.0), new LingVarValue(100.0));
		BaseLingVarLabel labelPO = new UnaryVarLabel("pulse");

		LingvisticVariable varX = new LingvisticVariable("the-X", labelXL, labelXH);
		LingvisticVariable varO = new LingvisticVariable("occured", labelPO);

		Set<LingvisticVariable> lingVars = new HashSet<>();
		lingVars.add(varX);
		lingVars.add(varO);

		return lingVars;
	}

	public static FuzzyEventsSequence createSomeEvents(Set<LingvisticVariable> vars) {
		final LingvisticVariable varX = LingvisticVariable.getVariable("the-X", vars);
		final LingvisticVariable varO = LingvisticVariable.getVariable("occured", vars);

		// final BaseLingVarLabel labelXL = varX.getLabel("low");
		// final BaseLingVarLabel labelXH = varX.getLabel("hight");
		// final BaseLingVarLabel labelPO = varO.getLabel("pulse");

		FuzzyEvent event1 = createSomeEvent(varX, varO, 1.0, true);
		FuzzyEvent event2 = createSomeEvent(varX, varO, 45.0, false);
		FuzzyEvent event3 = createSomeEvent(varX, varO, 3.0, true);
		FuzzyEvent event4 = createSomeEvent(varX, varO, 58.0, true);

		return new FuzzyEventsSequence(event1, event2, event3, event4);
	}

	public static FuzzyEvent createSomeEvent(LingvisticVariable varX, LingvisticVariable varO, double valueOfX,
			boolean isP) {

		Map<LingvisticVariable, LingVarValue> map = new HashMap<>(2);

		map.put(varX, new LingVarValue(valueOfX));

		if (isP) {
			map.put(varO, new LingVarValue(42));
		}

		return new FuzzyEvent(map);
	}
}
