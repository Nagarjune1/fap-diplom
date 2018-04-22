package cz.upol.feda.power.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.lingvar.BaseLingVarLabel;
import cz.upol.fapapp.core.lingvar.GaussianLingVarLabel;
import cz.upol.fapapp.core.lingvar.LinearLingVarLabel;
import cz.upol.fapapp.core.lingvar.LingVarValue;
import cz.upol.fapapp.core.lingvar.LingvisticVariable;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.TernaryRelation;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.feda.automata.EventDrivenFuzzyAutomaton;
import cz.upol.feda.event.FuzzyEvent;
import cz.upol.feda.event.FuzzyEventsSequence;
import cz.upol.feda.power.data.DeviceState;
import cz.upol.feda.power.data.ElectricalDevice;
import cz.upol.feda.power.data.PowerConsumptionChange;

/**
 * Transformer from the envrironment of power consumption to the fuzzy event
 * driven automata.
 * 
 * @author martin
 *
 */
public class PowerConsumptionsToFEDA {

	private final double melt;
	private final boolean useGaussianVars;
	private final Degree transitionThreshold;

	/**
	 * Creates instance. The melt param specifies how melted or smooth should
	 * the transitions be.
	 * 
	 * If useGaussian vars is not speficied, generated automata will operate
	 * with {@link LinearLingVarLabel} instead of {@link GaussianLingVarLabel}.
	 * 
	 * The transition threshold specifies how high degree has the potential
	 * transtion have to be included in the resulting automaton.
	 * 
	 * @param melt
	 */
	public PowerConsumptionsToFEDA(double melt, boolean useGaussianVars, Degree transitionThreshold) {
		super();
		this.melt = melt;
		this.useGaussianVars = useGaussianVars;
		this.transitionThreshold = transitionThreshold;
	}

	/**
	 * Converts to automaton.
	 * 
	 * @param devices
	 * @param changes
	 * @param initialSituation
	 * @return
	 */
	public EventDrivenFuzzyAutomaton toAutomaton(Set<ElectricalDevice> devices, Set<PowerConsumptionChange> changes,
			Map<ElectricalDevice, DeviceState> initialSituation) {

		LingvisticVariable lingVar = toVar(changes);
		Set<LingvisticVariable> eventsAlphabet = CollectionsUtils.toSet(lingVar);

		Set<Map<ElectricalDevice, DeviceState>> combinations = devicesToCombinations(devices);
		Set<State> states = combinationsToStates(combinations);

		FuzzySet<Map<ElectricalDevice, DeviceState>> initialsSituation = CollectionsUtils
				.singletonFuzzySet(combinations, initialSituation);
		FuzzySet<State> initialStates = situationToFuzzyState(initialsSituation.toMap());

		TernaryRelation<State, BaseLingVarLabel, State> transitionFunction = toTransitionFunction(combinations,
				lingVar);

		return new EventDrivenFuzzyAutomaton(states, eventsAlphabet, transitionFunction, initialStates);
	}

	/**
	 * Converts to automaton's transition function.
	 * 
	 * @param combinations
	 * @param lingVar
	 * @return
	 */
	protected TernaryRelation<State, BaseLingVarLabel, State> toTransitionFunction(
			Set<Map<ElectricalDevice, DeviceState>> combinations, LingvisticVariable lingVar) {

		Set<Triple<State, BaseLingVarLabel, State>> result = new HashSet<>();

		for (Map<ElectricalDevice, DeviceState> fromComb : combinations) {
			State from = combinationToState(fromComb);
			double powerOfFrom = combinationToPower(fromComb);

			for (Map<ElectricalDevice, DeviceState> toComb : combinations) {
				State to = combinationToState(toComb);
				double powerOfTo = combinationToPower(toComb);

				double powerChange = powerOfTo - powerOfFrom;
				LingVarValue changeVal = new LingVarValue(powerChange);

				for (BaseLingVarLabel label : lingVar.getLabels()) {
					Degree degree = label.compute(changeVal);

					if (transitionThreshold.isLessOrEqual(degree)) {
						Triple<State, BaseLingVarLabel, State> triple = new Triple<>(from, label, to);
						result.add(triple);
					}
				}

			}
		}

		return new TernaryRelation<>(result);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates fuzzy state holding "how much given combination x has given
	 * power". The combinations are taken over devices.
	 * 
	 * @param power
	 * @param devices
	 * @return
	 */
	public FuzzyState powerToFuzzyState(double power, Set<ElectricalDevice> devices) {
		Map<Map<ElectricalDevice, DeviceState>, Degree> situation = powerToSituation(power, devices);

		return situationToFuzzyState(situation);
	}

	/**
	 * Creates fuzzy state holding "how much given combination x has given
	 * power".
	 * 
	 * @param power
	 * @param devices
	 * @return
	 */
	protected Map<Map<ElectricalDevice, DeviceState>, Degree> powerToSituation(double power,
			Set<ElectricalDevice> devices) {
		Set<Map<ElectricalDevice, DeviceState>> combinations = devicesToCombinations(devices);
		Map<Map<ElectricalDevice, DeviceState>, Degree> result = new HashMap<>(devices.size());

		PowerConsumptionChange change = new PowerConsumptionChange("tmp-change-from-nowhere", power);
		BaseLingVarLabel label = toLabel(change);

		for (Map<ElectricalDevice, DeviceState> combination : combinations) {
			Double statePower = combinationToPower(combination);
			LingVarValue stateVal = new LingVarValue(statePower);

			Degree degree = label.compute(stateVal);

			result.put(combination, degree);
		}

		return result;
	}

	/**
	 * Just converts given map to fuzzy state.
	 * 
	 * @param situation
	 * @return
	 */
	protected FuzzyState situationToFuzzyState(Map<Map<ElectricalDevice, DeviceState>, Degree> situation) {
		return new FuzzyState(//
				situation.keySet().stream() //
						.collect(Collectors.toMap( //
								(s) -> combinationToState(s), //
								(s) -> situation.get(s)))); //
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts all devices combinations to correspoding states.
	 * 
	 * @param devices
	 * @return
	 */
	public Set<State> devicesToStates(Set<ElectricalDevice> devices) {
		Set<Map<ElectricalDevice, DeviceState>> combinations = devicesToCombinations(devices);
		return combinationsToStates(combinations);
	}

	/**
	 * Converts given cobinations to correspoding states.
	 * 
	 * @param combinations
	 * @return
	 */
	protected Set<State> combinationsToStates(Set<Map<ElectricalDevice, DeviceState>> combinations) {
		return combinations.stream() //
				.map((c) -> combinationToState(c)) //
				.collect(Collectors.toSet());
	}

	/**
	 * Computes combinations of all given devices and states combinations.
	 * 
	 * @param devices
	 * @return
	 */
	protected Set<Map<ElectricalDevice, DeviceState>> devicesToCombinations(Set<ElectricalDevice> devices) {

		Map<ElectricalDevice, DeviceState> current = new HashMap<>();

		Set<ElectricalDevice> remaining = new HashSet<>(devices);
		remaining.addAll(devices);

		Set<Map<ElectricalDevice, DeviceState>> result = new HashSet<>();

		listCombinations(current, remaining, result);

		return result;
	}

	/**
	 * Recursive call for listing all combinations.
	 * 
	 * @param current
	 * @param remaining
	 * @param result
	 * @see PowerConsumptionsToFEDA#devicesToCombinations(Set)
	 */
	private void listCombinations(Map<ElectricalDevice, DeviceState> current, Set<ElectricalDevice> remaining,
			Set<Map<ElectricalDevice, DeviceState>> result) {

		if (remaining.isEmpty()) {
			Map<ElectricalDevice, DeviceState> state = new HashMap<>(current);
			result.add(state);

		} else {

			for (ElectricalDevice device : new ArrayList<>(remaining)) {
				remaining.remove(device);

				for (DeviceState state : device.getPossibleStates()) {
					current.put(device, state);

					listCombinations(current, remaining, result);
				}

				remaining.add(device);
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts given combination to corresponding state. The state's label will
	 * look like: {@code "q_foo-is-H_bar-is-L"}.
	 * 
	 * @param combination
	 * @return
	 */
	protected State combinationToState(Map<ElectricalDevice, DeviceState> combination) {
		StringBuilder stb = new StringBuilder("q");

		for (Entry<ElectricalDevice, DeviceState> entry : combination.entrySet()) {
			String devName = entry.getKey().getName();
			String stateName = entry.getValue().getName();

			stb.append("_");
			stb.append(devName);
			stb.append("-is-");
			stb.append(stateName);
		}

		return new State(stb.toString());
	}

	/**
	 * Converts back state to situation. Warning: in fact lookups in all
	 * combinations the state's one, such uneffective!
	 * 
	 * @param devices
	 * @param state
	 * @return
	 */
	protected Map<ElectricalDevice, DeviceState> stateToSituation(Set<ElectricalDevice> devices, State state) {
		Set<Map<ElectricalDevice, DeviceState>> combinations = devicesToCombinations(devices);

		return combinations.stream() //
				.filter((c) -> combinationToState(c).equals(state)) //
				.findAny().orElseThrow(() -> new IllegalArgumentException("No such situation: " + state)); //
	}

	/**
	 * Computes total power of given states combination's power.
	 * 
	 * @param current
	 * @return
	 */
	protected double combinationToPower(Map<ElectricalDevice, DeviceState> current) {
		return current.values().stream() //
				.collect(Collectors.summarizingDouble((s) -> s.getPowerConsumption()))//
				.getSum();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates ligvistic variable holding all power changes.
	 * 
	 * @param changes
	 * @return
	 */
	public LingvisticVariable toVar(Set<PowerConsumptionChange> changes) {
		Set<BaseLingVarLabel> labels = changes.stream() //
				.map((ch) -> toLabel(ch)) //
				.collect(Collectors.toSet());

		Logger.get().debug("Created labels for ling var power: " + labels);

		return new LingvisticVariable("power", labels);
	}

	/**
	 * Converts given change to label of ligvistic variable.
	 * 
	 * @param change
	 * @return
	 * @see #toVar(Set)
	 */
	private BaseLingVarLabel toLabel(PowerConsumptionChange change) {
		String name = change.getName();
		double power = change.getPowerChange();

		if (useGaussianVars) {
			return toGaussianLabel(name, power);
		} else {
			return toLinearLabel(name, power);
		}
	}

	private BaseLingVarLabel toLinearLabel(String name, double power) {
		LingVarValue activationStart = new LingVarValue(power - 2 * melt);
		LingVarValue activationTop = new LingVarValue(power - melt);
		LingVarValue activationDecrease = new LingVarValue(power + melt);
		LingVarValue activationFinish = new LingVarValue(power + 2 * melt);

		return new LinearLingVarLabel(name, activationStart, activationTop, activationDecrease, activationFinish);
	}

	private BaseLingVarLabel toGaussianLabel(String name, double power) {
		LingVarValue center = new LingVarValue(power);
		LingVarValue leftSigma = new LingVarValue(power + -2 * melt);
		LingVarValue rightSigma = new LingVarValue(power + +2 * melt);

		return new GaussianLingVarLabel(name, center, leftSigma, rightSigma);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts given values to events sequence.
	 * 
	 * @param changes
	 * @param initial
	 * @param powerConsumptions
	 * @return
	 */
	public FuzzyEventsSequence toEvents(Set<PowerConsumptionChange> changes, Map<ElectricalDevice, DeviceState> initial,
			List<Double> powerConsumptions) {

		LingvisticVariable powerChangesVar = toVar(changes);
		return toEvents(initial, powerConsumptions, powerChangesVar);
	}

	/**
	 * Converts given values to events sequence.
	 * 
	 * @param initial
	 * @param powerConsumptions
	 * @param powerChangesVar
	 * @return
	 */
	public FuzzyEventsSequence toEvents(Map<ElectricalDevice, DeviceState> initial, List<Double> powerConsumptions,
			LingvisticVariable powerChangesVar) {
		double previous = combinationToPower(initial);

		Logger.get().debug("Consumption " + previous + " is initial");

		List<FuzzyEvent> events = new ArrayList<>(powerConsumptions.size());

		for (Double consumption : powerConsumptions) {
			double change = consumption - previous;

			FuzzyEvent event = toEvent(change, powerChangesVar);
			events.add(event);

			Logger.get().debug("Consumption " + consumption + " makes change " + change + ", so event " + event);

			previous = consumption;
		}

		return new FuzzyEventsSequence(events);
	}

	/**
	 * Converts given change and lingvistic variable to corresponding fuzzy
	 * event.
	 * 
	 * @param change
	 * @param powerChangesVar
	 * @return
	 */
	private FuzzyEvent toEvent(double change, LingvisticVariable powerChangesVar) {

		Set<LingvisticVariable> vars = CollectionsUtils.toSet(powerChangesVar);
		LingVarValue value = new LingVarValue(change);
		Map<LingvisticVariable, LingVarValue> values = CollectionsUtils.createMap(vars, value);

		return new FuzzyEvent(values);
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
