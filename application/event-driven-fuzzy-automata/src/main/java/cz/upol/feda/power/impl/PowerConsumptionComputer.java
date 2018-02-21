package cz.upol.feda.power.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.lingvar.LingvisticVariable;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.feda.automata.EventDrivenFuzzyAutomaton;
import cz.upol.feda.event.FuzzyEventsSequence;
import cz.upol.feda.power.data.DeviceState;
import cz.upol.feda.power.data.ElectricalDevice;
import cz.upol.feda.power.data.PowerConsumptionChange;
import cz.upol.feda.power.data.PowerConsumptionComputaitionData;

/**
 * Main class for running power consumption computation.
 * 
 * @author martin
 *
 */
public class PowerConsumptionComputer {

	/**
	 * Runs computation of given data. Returns the most appropiate situation.
	 * 
	 * @param data
	 *            Data to be computed
	 * @param useGaussians
	 *            should be used gaussian ling var labels or linear?
	 * @param transitionThreshold
	 *            at least how much truthy transition have to be?
	 * @return
	 */
	public Map<ElectricalDevice, DeviceState> compute(PowerConsumptionComputaitionData data, boolean useGaussians,
			Degree transitionThreshold) {
		
		double melt = data.getMelt();
		final PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(melt, useGaussians, transitionThreshold);

		Map<ElectricalDevice, DeviceState> initialSituation = data.getInitial();
		Set<PowerConsumptionChange> changes = data.getChanges();
		Set<ElectricalDevice> devices = data.getDevices();
		List<Double> powerConsumptions = data.getConsumptions();

		EventDrivenFuzzyAutomaton automaton = pctf.toAutomaton(devices, changes, initialSituation);

		LingvisticVariable powerChangesVar = pctf.toVar(changes);

		FuzzyEventsSequence events = pctf.toEvents(initialSituation, powerConsumptions, powerChangesVar);

		checkAndLog(automaton, events);

		State state = automaton.run(events);

		Map<ElectricalDevice, DeviceState> situation = pctf.stateToSituation(devices, state);
		return situation;
	}

	private void checkAndLog(EventDrivenFuzzyAutomaton automaton, FuzzyEventsSequence events) {
		if (Logger.get().isVerbose()) {
			Logger.get().moreinfo("Using automaton:");
			automaton.print(Logger.META_STREAM);

			Logger.get().moreinfo("Over events:");
			events.print(Logger.META_STREAM);
		}
	}

}
