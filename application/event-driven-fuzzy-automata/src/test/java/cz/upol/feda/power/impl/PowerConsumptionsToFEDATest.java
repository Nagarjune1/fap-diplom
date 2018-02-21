package cz.upol.feda.power.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.lingvar.BaseLingVarLabel;
import cz.upol.fapapp.core.lingvar.LinearLingVarLabel;
import cz.upol.fapapp.core.lingvar.LingvisticVariable;
import cz.upol.fapapp.core.sets.TernaryRelation;
import cz.upol.feda.automata.EventDrivenFuzzyAutomaton;
import cz.upol.feda.event.FuzzyEvent;
import cz.upol.feda.event.FuzzyEventsSequence;
import cz.upol.feda.power.data.DeviceState;
import cz.upol.feda.power.data.ElectricalDevice;
import cz.upol.feda.power.data.PCCDTestDataCreator;
import cz.upol.feda.power.data.PowerConsumptionChange;
import cz.upol.feda.power.impl.PowerConsumptionsToFEDA;

public class PowerConsumptionsToFEDATest {

	private static final double DELTA = 0.0001;

	private static final boolean USE_GAUSSIANS = false;
	private static final Degree TRANSTION_THRESHOLD = Degree.ONE;

	private static final State FAN_OFF_LAMP_OFF_TV_OFF = new State("q_fan-is-off_lamp-is-off_tv-is-off");
	private static final State FAN_WARM_LAMP_ON_TV_STBY = new State("q_fan-is-warm_lamp-is-on_tv-is-standby");
	private static final State FAN_HOT_LAMP_OFF_TV_STBY = new State("q_fan-is-hot_lamp-is-off_tv-is-standby");

	@Test
	public void testCombinations() {
		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(90.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<ElectricalDevice> devices = PCCDTestDataCreator.listDevices();
		Set<Map<ElectricalDevice, DeviceState>> combinations = pctf.devicesToCombinations(devices);

		// states.forEach((s) -> System.out.println(s));
		assertEquals(3 * 4 * 2, combinations.size());

		// all remanining is tested in testToStates
	}

	@Test
	public void testToStates() {
		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(90.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<ElectricalDevice> devices = PCCDTestDataCreator.listDevices();
		Set<Map<ElectricalDevice, DeviceState>> combinations = pctf.devicesToCombinations(devices);

		Set<State> states = pctf.combinationsToStates(combinations);

		assertTrue(states.contains(FAN_OFF_LAMP_OFF_TV_OFF));
		assertTrue(states.contains(FAN_WARM_LAMP_ON_TV_STBY));
		assertTrue(states.contains(FAN_HOT_LAMP_OFF_TV_STBY));
	}

	@Test
	public void testToPowers() {
		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(90.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<ElectricalDevice> devs = PCCDTestDataCreator.listDevices();

		assertEquals(0 + 0 + 0, powerOf(pctf, devs, FAN_OFF_LAMP_OFF_TV_OFF), DELTA);
		assertEquals(600 + 50 + 20, powerOf(pctf, devs, FAN_WARM_LAMP_ON_TV_STBY), DELTA);
		assertEquals(900 + 0 + 20, powerOf(pctf, devs, FAN_HOT_LAMP_OFF_TV_STBY), DELTA);

	}

	@Test
	public void testToVar() {
		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(90.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<PowerConsumptionChange> changes = PCCDTestDataCreator.listChanges();
		LingvisticVariable var = pctf.toVar(changes);

		assertEquals(changes.size(), var.getLabels().size());

		LinearLingVarLabel dec = (LinearLingVarLabel) var.getLabel("decrease");
		assertEquals(-780, dec.getActivationStart().getValue(), DELTA);
		assertEquals(-690, dec.getActivationTop().getValue(), DELTA);
		assertEquals(-510, dec.getActivationDecrease().getValue(), DELTA);
		assertEquals(-420, dec.getActivationFinish().getValue(), DELTA);

		LinearLingVarLabel noc = (LinearLingVarLabel) var.getLabel("no-change");
		assertEquals(-180, noc.getActivationStart().getValue(), DELTA);
		assertEquals(-90, noc.getActivationTop().getValue(), DELTA);
		assertEquals(+90, noc.getActivationDecrease().getValue(), DELTA);
		assertEquals(+180, noc.getActivationFinish().getValue(), DELTA);
	}

	@Test
	public void testToTransitionFunction() {

		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(400.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<ElectricalDevice> devices = PCCDTestDataCreator.listDevices();
		Set<PowerConsumptionChange> changes = PCCDTestDataCreator.listChanges();

		LingvisticVariable lingVar = pctf.toVar(changes);
		Set<Map<ElectricalDevice, DeviceState>> combinations = pctf.devicesToCombinations(devices);

		TernaryRelation<State, BaseLingVarLabel, State> transitionFunction = //
				pctf.toTransitionFunction(combinations, lingVar);

		// increases (btw with the transitivity ;-) )
		BaseLingVarLabel inc = lingVar.getLabel("increase");
		assertTrue(transitionFunction.get(FAN_OFF_LAMP_OFF_TV_OFF, inc).contains(FAN_WARM_LAMP_ON_TV_STBY));
		assertTrue(transitionFunction.get(FAN_WARM_LAMP_ON_TV_STBY, inc).contains(FAN_HOT_LAMP_OFF_TV_STBY));
		assertTrue(transitionFunction.get(FAN_OFF_LAMP_OFF_TV_OFF, inc).contains(FAN_HOT_LAMP_OFF_TV_STBY));

		// decreases
		BaseLingVarLabel dec = lingVar.getLabel("decrease");
		assertTrue(transitionFunction.get(FAN_WARM_LAMP_ON_TV_STBY, dec).contains(FAN_OFF_LAMP_OFF_TV_OFF));

		// no changes
		BaseLingVarLabel noc = lingVar.getLabel("no-change");
		assertTrue(transitionFunction.get(FAN_OFF_LAMP_OFF_TV_OFF, noc).contains(FAN_OFF_LAMP_OFF_TV_OFF));
		assertTrue(transitionFunction.get(FAN_WARM_LAMP_ON_TV_STBY, noc).contains(FAN_WARM_LAMP_ON_TV_STBY));

		// also negatives
		assertFalse(transitionFunction.get(FAN_OFF_LAMP_OFF_TV_OFF, dec).contains(FAN_WARM_LAMP_ON_TV_STBY));
		assertFalse(transitionFunction.get(FAN_WARM_LAMP_ON_TV_STBY, inc).contains(FAN_OFF_LAMP_OFF_TV_OFF));
		assertFalse(transitionFunction.get(FAN_OFF_LAMP_OFF_TV_OFF, inc).contains(FAN_OFF_LAMP_OFF_TV_OFF));
		assertFalse(transitionFunction.get(FAN_OFF_LAMP_OFF_TV_OFF, dec).contains(FAN_OFF_LAMP_OFF_TV_OFF));
		assertFalse(transitionFunction.get(FAN_WARM_LAMP_ON_TV_STBY, inc).contains(FAN_WARM_LAMP_ON_TV_STBY));
		assertFalse(transitionFunction.get(FAN_WARM_LAMP_ON_TV_STBY, dec).contains(FAN_HOT_LAMP_OFF_TV_STBY));

		// transitionFunction.get(FAN_OFF_LAMP_OFF_TV_OFF,
		// inc).forEach(System.out::println);

	}

	@Test
	public void testToEvents() {
		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(400.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<PowerConsumptionChange> changes = PCCDTestDataCreator.listChanges();
		LingvisticVariable var = pctf.toVar(changes);

		List<Double> powers = new ArrayList<>(Arrays.asList(250.0, 900.0, 150.0));

		Map<ElectricalDevice, DeviceState> initial = PCCDTestDataCreator.createInitial();
		FuzzyEventsSequence seq = pctf.toEvents(initial, powers, var);

		LingvisticVariable lingVar = pctf.toVar(changes);

		BaseLingVarLabel inc = lingVar.getLabel("increase");
		BaseLingVarLabel dec = lingVar.getLabel("decrease");
		BaseLingVarLabel noc = lingVar.getLabel("no-change");

		// 0 -> 250, noc + inc
		FuzzyEvent evt0 = seq.getEvents().get(0);
		assertEquals(Degree.ZERO, evt0.degreeOf(dec));
		assertEquals(Degree.ONE, evt0.degreeOf(noc));
		assertEquals(Degree.ONE, evt0.degreeOf(inc));

		// 250 -> 900, inc + noc (a bit)
		FuzzyEvent evt1 = seq.getEvents().get(1);
		assertEquals(Degree.ZERO, evt1.degreeOf(dec));
		assertEquals(0.375, evt1.degreeOf(noc).getValue(), DELTA);
		assertEquals(Degree.ONE, evt1.degreeOf(inc));

		// 900 -> 150, dec + noc (a bit)
		FuzzyEvent evt2 = seq.getEvents().get(2);
		assertEquals(Degree.ONE, evt2.degreeOf(dec));
		assertEquals(0.125, evt2.degreeOf(noc).getValue(), DELTA);
		assertEquals(Degree.ZERO, evt2.degreeOf(inc));

		// System.out.println(seq.toString().replaceAll("\\]", "\n"));
	}

	@Test
	public void testToFuzzyState() {
		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(400.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<ElectricalDevice> devices = PCCDTestDataCreator.listDevices();

		double power = 150;

		FuzzyState fs = pctf.powerToFuzzyState(power, devices);

		assertEquals(1.000, fs.degreeOf(FAN_OFF_LAMP_OFF_TV_OFF).getValue(), DELTA);
		assertEquals(0.700, fs.degreeOf(FAN_WARM_LAMP_ON_TV_STBY).getValue(), DELTA);
		assertEquals(0.075, fs.degreeOf(FAN_HOT_LAMP_OFF_TV_STBY).getValue(), DELTA);
	}

	@Test
	public void testToAutomaton() {
		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(400.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<ElectricalDevice> devices = PCCDTestDataCreator.listDevices();
		Set<PowerConsumptionChange> changes = PCCDTestDataCreator.listChanges();
		Map<ElectricalDevice, DeviceState> initialSituation = //
				pctf.stateToSituation(devices, FAN_OFF_LAMP_OFF_TV_OFF);
		EventDrivenFuzzyAutomaton automaton = pctf.toAutomaton(devices, changes, initialSituation);

		List<Double> powers = new ArrayList<>(Arrays.asList(0.0, 250.0, 900.0, 150.0, 10.0));
		LingvisticVariable var = pctf.toVar(changes);

		Map<ElectricalDevice, DeviceState> initial = PCCDTestDataCreator.createInitial();

		FuzzyEventsSequence events = pctf.toEvents(initial, powers, var);

		// ok finally ready to run!
		FuzzyState fs = automaton.runEvents(events);

		assertEquals(1.000, fs.degreeOf(FAN_OFF_LAMP_OFF_TV_OFF).getValue(), DELTA);
		assertEquals(1.000, fs.degreeOf(FAN_WARM_LAMP_ON_TV_STBY).getValue(), DELTA);
		assertEquals(1.000, fs.degreeOf(FAN_HOT_LAMP_OFF_TV_STBY).getValue(), DELTA);

		// fs.toMap().entrySet().forEach(System.out::println);

	}

	@Test
	public void runSomeTestsOnAutomaton() {
		runTest(1.0, 0.033333, 0.0, //
				10.0);

		runTest(0.9939751, 1.0, 1.0, //
				10.0, 480.0);

		runTest(0.0, 1.0, 1.0, //
				50.0, 70.0, 670.0);

		runTest(0.5, 1.0, 1.0, //
				30.0, 810.0, 1260.0);

		runTest(0.99999, 1.0, 1.0, //
				50.0, 120.0, 30.0, 430.0, 250.0);

		runTest(1.0, 1.0, 1.0, //
				380.0, 420.0, 60.0, 490.0, 680.0, 340.0, 250.0, 40.0);

		runTest(0.0, 0.0, 0.0, //
				380.0, 85660.0, 200.0);
	}

	private void runTest(double offDegree, double warmDegree, double hotDegree, Double... powers) {
		PowerConsumptionsToFEDA pctf = new PowerConsumptionsToFEDA(300.0, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		Set<ElectricalDevice> devices = PCCDTestDataCreator.listDevices();
		Set<PowerConsumptionChange> changes = PCCDTestDataCreator.listChanges();
		Map<ElectricalDevice, DeviceState> initialSituation = //
				pctf.stateToSituation(devices, FAN_OFF_LAMP_OFF_TV_OFF);
		EventDrivenFuzzyAutomaton automaton = pctf.toAutomaton(devices, changes, initialSituation);

		LingvisticVariable var = pctf.toVar(changes);

		List<Double> powersList = new ArrayList<>(Arrays.asList(powers));
		Map<ElectricalDevice, DeviceState> initial = PCCDTestDataCreator.createInitial();

		FuzzyEventsSequence events = pctf.toEvents(initial, powersList, var);

		// ok finally ready to run!
		FuzzyState fs = automaton.runEvents(events);

		// System.out.println(fs.degreeOf(FAN_OFF_LAMP_OFF_TV_OFF) + "," //
		// + fs.degreeOf(FAN_WARM_LAMP_ON_TV_STBY) + ", " //
		// + fs.degreeOf(FAN_HOT_LAMP_OFF_TV_STBY)); //

		assertEquals(offDegree, fs.degreeOf(FAN_OFF_LAMP_OFF_TV_OFF).getValue(), DELTA);
		assertEquals(warmDegree, fs.degreeOf(FAN_WARM_LAMP_ON_TV_STBY).getValue(), DELTA);
		assertEquals(hotDegree, fs.degreeOf(FAN_HOT_LAMP_OFF_TV_STBY).getValue(), DELTA);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////

	private double powerOf(PowerConsumptionsToFEDA pctf, Set<ElectricalDevice> devices, State state) {

		Set<Map<ElectricalDevice, DeviceState>> combinations = pctf.devicesToCombinations(devices);

		for (Map<ElectricalDevice, DeviceState> comb : combinations) {
			State st = pctf.combinationToState(comb);
			if (state.equals(st)) {
				return pctf.combinationToPower(comb);
			}
		}

		throw new IllegalArgumentException("State " + state + " does not exist");
	}

}
