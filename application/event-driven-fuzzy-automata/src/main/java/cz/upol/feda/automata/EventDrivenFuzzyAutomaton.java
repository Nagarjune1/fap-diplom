package cz.upol.feda.automata;

import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.sets.TernaryRelation;
import cz.upol.feda.event.FuzzyEvent;
import cz.upol.feda.event.FuzzyEventsSequence;
import cz.upol.feda.lingvar.BaseLingVarLabel;
import cz.upol.feda.lingvar.LingvisticVariable;

/**
 * Implementation of event drive fuzzy automaton.
 * @author martin
 *
 */
public class EventDrivenFuzzyAutomaton extends BaseEventDrivenFA {

	public EventDrivenFuzzyAutomaton(Set<State> states,
			Set<LingvisticVariable> eventsAlphabet,
			TernaryRelation<State, BaseLingVarLabel, State> transitionFunction,
			FuzzySet<State> initialStates, FuzzySet<State> finalStates) {
		super(states, eventsAlphabet, transitionFunction, initialStates,
				finalStates);
	}

	@Override
	public State run(FuzzyEventsSequence events) {
		FuzzyState state = runEvents(events);
		return state.withMaxDegree();
	}

	public FuzzyState runEvents(FuzzyEventsSequence events) {
		FuzzyState currentState = new FuzzyState(initialStates);

		for (FuzzyEvent event : events.getEvents()) {
			currentState = runEvent(currentState, event);
		}

		return new FuzzyState(FuzzySet.intersect(currentState, finalStates));
	}

	protected FuzzyState runEvent(FuzzyState fromFuzzyState, FuzzyEvent event) {
		Map<State, Degree> toFuzzyStateMap = CollectionsUtils.createMap(states,
				Degree.ZERO);

		for (State from : states) {
			for (BaseLingVarLabel label : event.getLabels()) {
				State to = transitionFunction.getOrNot(from, label);
				if (to == null) {
					continue;
				}

				Degree fromDegree = fromFuzzyState.degreeOf(from);

				Degree transitionDegree = event.degreeOf(label);

				Degree toDegreeOld = toFuzzyStateMap.get(to);
				Degree toDegreeNew = TNorms.getTnorm().tnorm(fromDegree,
						transitionDegree);
				Degree toDegree = TNorms.getTnorm().tconorm(toDegreeOld,
						toDegreeNew);

				toFuzzyStateMap.put(to, toDegree);
			}
		}

		FuzzyState toFuzzyState = new FuzzyState(toFuzzyStateMap);
		return toFuzzyState;

	}

}
