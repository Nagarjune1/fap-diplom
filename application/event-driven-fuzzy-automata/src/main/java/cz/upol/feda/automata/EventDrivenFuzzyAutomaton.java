package cz.upol.feda.automata;

import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.lingvar.BaseLingVarLabel;
import cz.upol.fapapp.core.lingvar.LingvisticVariable;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.TernaryRelation;
import cz.upol.feda.event.FuzzyEvent;
import cz.upol.feda.event.FuzzyEventsSequence;

/**
 * Implementation of event drive fuzzy automaton.
 * 
 * @author martin
 *
 */
public class EventDrivenFuzzyAutomaton extends BaseEventDrivenFA {

	public EventDrivenFuzzyAutomaton(Set<State> states, Set<LingvisticVariable> eventsAlphabet,
			TernaryRelation<State, BaseLingVarLabel, State> transitionFunction, FuzzySet<State> initialStates) {
		super(states, eventsAlphabet, transitionFunction, initialStates);
	}

	@Override
	public State run(FuzzyEventsSequence events) {
		FuzzyState state = runEvents(events);
		return state.withMaxDegree();
	}

	public FuzzyState runEvents(FuzzyEventsSequence events) {
		Logger.get().debug("Starting from: " + initialStates + " events: " + events);
		FuzzyState currentState = new FuzzyState(initialStates);

		for (FuzzyEvent event : events.getEvents()) {
			currentState = runEvent(currentState, event);
		}

		Logger.get().debug("Completed in: " + currentState);
		return currentState;
	}

	protected FuzzyState runEvent(FuzzyState fromFuzzyState, FuzzyEvent event) {
		Map<State, Degree> toFuzzyStateMap = CollectionsUtils.createMap(states, Degree.ZERO);

		Logger.get().debug("Going from : " + fromFuzzyState + " at " + event + " ...");
		
		for (State from : states) {
			for (BaseLingVarLabel label : event.getLabels()) {
				Set<State> tos = transitionFunction.get(from, label);

				for (State to : tos) {

					Degree fromDegree = fromFuzzyState.degreeOf(from);

					Degree transitionDegree = event.degreeOf(label);

					Degree toDegreeOld = toFuzzyStateMap.get(to);
					Degree toDegreeNew = TNorms.getTnorm().tnorm(fromDegree, transitionDegree);
					Degree toDegree = TNorms.getTnorm().tconorm(toDegreeOld, toDegreeNew);

					toFuzzyStateMap.put(to, toDegree);
				}
			}
		}

		FuzzyState toFuzzyState = new FuzzyState(toFuzzyStateMap);
		
		Logger.get().debug("Into fuzzy state: " + toFuzzyState);
		return toFuzzyState;

	}

}
