package cz.upol.feda.automata;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.feda.event.FuzzyEvent;
import cz.upol.feda.event.FuzzyEventsSequence;
import cz.upol.feda.lingvar.LingvisticVariable;

public class EventDrivenFuzzyAutomataTest {

	@BeforeClass
	public static void init() {

	}

	@Test
	public void testPrint() {
		EventDrivenFuzzyAutomata automata = AutomataCreator.createSome();
		automata.print(System.out);
	}

	@Test
	public void testRunEvent() {
		EventDrivenFuzzyAutomata automata = AutomataCreator.createSome();

		Set<LingvisticVariable> vars = automata.getEventsAlphabet();
		final LingvisticVariable varX = LingvisticVariable.getVariable("the-X", vars);
		final LingvisticVariable varO = LingvisticVariable.getVariable("occured", vars);

		FuzzyState current;
		FuzzyState expected;

		current = createFuzzyState(1.0, 0.0, 0.0);
		expected = createFuzzyState(1.0, 0.0, 0.0);
		assertEquals(expected, current);

		FuzzyEvent event1 = AutomataCreator.createSomeEvent(varX, varO, 3.0, false);
		current = automata.runEvent(current, event1);
		expected = createFuzzyState(0.0, 1.0, 0.0);
		assertEquals(expected, current);

		FuzzyEvent event2 = AutomataCreator.createSomeEvent(varX, varO, 99.0, true);
		current = automata.runEvent(current, event2);
		expected = createFuzzyState(0.0, 0.0, 1.0);
		assertEquals(expected, current);

		FuzzyEvent event3 = AutomataCreator.createSomeEvent(varX, varO, 98.0, false);
		current = automata.runEvent(current, event3);
		expected = createFuzzyState(0.0, 0.0, 1.0);
		assertEquals(expected, current);

		FuzzyEvent event4 = AutomataCreator.createSomeEvent(varX, varO, 97.0, true);
		current = automata.runEvent(current, event4);
		expected = createFuzzyState(0.0, 1.0, 1.0);
		assertEquals(expected, current);

		FuzzyEvent event5 = AutomataCreator.createSomeEvent(varX, varO, 50.0, false);
		current = automata.runEvent(current, event5);
		expected = createFuzzyState(0.5, 0.0, 0.5);
		assertEquals(expected, current);

		// TODO here

	}

	@Test
	public void testRunEvents() {

		EventDrivenFuzzyAutomata automata = AutomataCreator.createSome();

		FuzzyEventsSequence events = AutomataCreator.createSomeEvents(automata.getEventsAlphabet());

		FuzzyState actualFS = automata.runEvents(events);

		double degreeOfQ0 = actualFS.degreeOf(new State("q_0")).getValue();
		double degreeOfQ1 = actualFS.degreeOf(new State("q_1")).getValue();
		double degreeOfQ2 = actualFS.degreeOf(new State("q_2")).getValue();
		assertTrue(0.06 < degreeOfQ0 && degreeOfQ0 < 0.07);
		assertTrue(0.76 < degreeOfQ1 && degreeOfQ1 < 0.77);
		assertTrue(0.19 < degreeOfQ2 && degreeOfQ2 < 0.21);

	}

	@Test
	public void testRun() {

		EventDrivenFuzzyAutomata automata = AutomataCreator.createSome();

		FuzzyEventsSequence events = AutomataCreator.createSomeEvents(automata.getEventsAlphabet());

		State expectedS = new State("q_1");
		State actualS = automata.run(events);

		assertEquals(expectedS, actualS);
	}

	// /////////////////////////////////////////////////////////////////////////

	private FuzzyState createFuzzyState(double degreeOfQ0, double degreeOfQ1, double degreeOfQ2) {

		Map<State, Degree> map = new HashMap<>();

		map.put(new State("q_0"), new Degree(degreeOfQ0));
		map.put(new State("q_1"), new Degree(degreeOfQ1));
		map.put(new State("q_2"), new Degree(degreeOfQ2));

		return new FuzzyState(map);
	}

}
