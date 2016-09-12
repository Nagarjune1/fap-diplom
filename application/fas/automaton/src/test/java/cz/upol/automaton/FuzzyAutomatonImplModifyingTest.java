package cz.upol.automaton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.NFAedge;

/**
 * Testuje datamodifikující metody u {@link FuzzyAutomatonImpl}. Předpokládá
 * funkční {@link TestingAutomata}.
 * 
 * @author martin
 * 
 */
public class FuzzyAutomatonImplModifyingTest {

	private static final State STATE_Q0 = new State("q0");
	private static final State STATE_Q1 = new State("q1");
	private static final State STATE_Q2 = new State("q2");

	private static final Symbol SYMBOL_A = new Symbol("a");
	private static final Symbol SYMBOL_B = new Symbol("b");

	/**
	 * @uml.property name="automaton"
	 * @uml.associationEnd
	 */
	private NondetermisticFuzzyAutomata automaton;

	@Before
	public void setUp() throws Exception {
		automaton = TestingAutomata.createTriangleAutomaton();
	}

	@Test
	public void testAddState() {
		int oldCount = countStates();

		State newState = new State("q0''");
		automaton.addState(newState);

		int newCount = countStates();

		assertEquals(1, newCount - oldCount); // jasný, ne?

	}

	@Test
	public void testRemoveState() {
		int oldCount = countStates();

		State oldState = STATE_Q1;
		automaton.removeState(oldState);

		int newCount = countStates();

		assertEquals(1, oldCount - newCount); // jasný, ne?

		FuzzySet<Transition> transs = automaton
				.getTransitionsWithState(oldState);
		assertTrue(transs.isEmpty());

		// neexistující stav nemůže být ani počáteční ani koncový
		Degree initialIn = automaton.degreeOfInitialState(oldState);
		assertTrue(initialIn.equals(automaton.getResiduedLattice().getZero()));
		Degree finiteIn = automaton.degreeOfFiniteState(oldState);
		assertTrue(finiteIn.equals(automaton.getResiduedLattice().getZero()));

	}

	@Test
	public void testUpdateState() {
		int oldCountStates = countStates();
		int oldCountTranss = countTransitions();

		State oldState = STATE_Q0;
		State newState = new State("q0''");
		automaton.updateState(oldState, newState);

		int newCountStates = countStates();
		int newCountTranss = countTransitions();

		// počet stavů i hran se nezměnil
		assertTrue(newCountStates == oldCountStates);
		assertTrue(newCountTranss == oldCountTranss);

		// upraveny byly i hrany
		FuzzySet<Transition> transs = automaton.transitionsFrom(newState);
		Transition tran = new Transition(newState, SYMBOL_A, STATE_Q1);
		assertEquals(new Rational0to1Number(0.45), transs.find(tran));

		// starý stav již v počátečních ani koncových neexistuje
		Degree initialInOld = automaton.degreeOfInitialState(oldState);
		assertTrue(initialInOld
				.equals(automaton.getResiduedLattice().getZero()));
		Degree finiteInOld = automaton.degreeOfFiniteState(oldState);
		assertTrue(finiteInOld.equals(automaton.getResiduedLattice().getZero()));

		// nový po něm převzal stupeň počátečnosti a koncovosti
		Degree initialInNew = automaton.degreeOfInitialState(newState);
		assertTrue(initialInNew == automaton.getResiduedLattice().getOne());
		Degree finiteInNew = automaton.degreeOfFiniteState(newState);
		assertTrue(finiteInNew == automaton.getResiduedLattice().getZero());

	}

	@Test
	public void testAddTransition() {
		int oldCount = countTransitions();

		Transition trans = new Transition(STATE_Q1, SYMBOL_A, STATE_Q0);
		automaton.addTransition(trans, new Rational0to1Number(0.55));

		int newCount = countTransitions();

		// přidávání je jasný
		assertEquals(1, newCount - oldCount);
		assertEquals(new Rational0to1Number(0.55),
				automaton.degreeOfTransition(trans));
	}

	@Test
	public void testUpdateTransitionDegree() {
		int oldCount = countTransitions();

		Transition trans = new Transition(STATE_Q0, SYMBOL_A, STATE_Q1);
		automaton.updateTransitionDegree(trans, new Rational0to1Number(0.99));

		int newCount = countTransitions();

		// počet se nesmí změnit
		assertEquals(0, newCount - oldCount);
		assertEquals(new Rational0to1Number(0.99),
				automaton.degreeOfTransition(trans));

	}

	@Test
	public void testRemoveTransition() {
		int oldCount = countTransitions();

		Transition trans = new Transition(STATE_Q0, SYMBOL_A, STATE_Q1);
		automaton.removeTransition(trans);

		int newCount = countTransitions();

		// taky žádná věda
		assertEquals(-1, newCount - oldCount);
		assertEquals(automaton.getResiduedLattice().getZero(),
				automaton.degreeOfTransition(trans));

	}

	@Test
	public void testUpdateTransition() {
		int oldCount = countTransitions();

		Transition oldTrans = new Transition(STATE_Q0, SYMBOL_A, STATE_Q1);
		Transition newTrans = new Transition(STATE_Q0, SYMBOL_B, STATE_Q1);
		automaton.updateTransition(oldTrans, newTrans);

		int newCount = countTransitions();

		// počet se nesmí změnit, starý zmizí nový získá jeho stupeň
		assertEquals(0, newCount - oldCount);
		assertEquals(automaton.getResiduedLattice().getZero(),
				automaton.degreeOfTransition(oldTrans));
		assertEquals(new Rational0to1Number(0.45),
				automaton.degreeOfTransition(newTrans));
	}

	@Test
	public void testAddEdge() {
		int oldCount = countTransitions();

		NFAedge edge = createEdge();
		automaton.addEdge(edge);

		int newCount = countTransitions();

		assertEquals(1, newCount - oldCount);
		for (Transition trans : edge.getTransitions()) {
			Degree degree = edge.getTransitions().find(trans);
			assertEquals(degree, automaton.degreeOfTransition(trans));
		}

	}

	@Test
	public void testRemoveEdge() {
		int oldCount = countTransitions();

		NFAedge edge = automaton.getEdge(STATE_Q2, STATE_Q0);

		Transition excludedTrans = new Transition(STATE_Q2, SYMBOL_A, STATE_Q0);

		edge.getTransitions().remove(excludedTrans);
		automaton.removeEdge(edge);

		int newCount = countTransitions();

		assertEquals(-2, newCount - oldCount);
		for (Transition trans : edge.getTransitions()) {
			assertEquals(automaton.getResiduedLattice().getZero(),
					automaton.degreeOfTransition(trans));
		}
	}

	@Test
	public void testUpdateEdge() {
		int oldCount = countTransitions();

		NFAedge edge = automaton.getEdge(STATE_Q2, STATE_Q1);
		// odebereme existující přechod a přidáme jiný (pořád však z q2 do q1)
		Transition excludedTrans = new Transition(STATE_Q2, SYMBOL_B, STATE_Q1);
		Transition includedTrans = new Transition(STATE_Q2, SYMBOL_A, STATE_Q1);

		edge.getTransitions().remove(excludedTrans);
		edge.getTransitions().insert(includedTrans,
				new Rational0to1Number(0.12));

		automaton.updateEdge(edge);

		int newCount = countTransitions();

		assertEquals(0, newCount - oldCount);
		assertEquals(automaton.getResiduedLattice().getZero(),
				automaton.degreeOfTransition(excludedTrans));
		assertEquals(new Rational0to1Number(0.12),
				automaton.degreeOfTransition(includedTrans));

	}

	private NFAedge createEdge() {

		State from = STATE_Q1;
		State to = STATE_Q0;
		FuzzySet<Transition> transitions = automaton.newTransitionsFuzzySet();

		Symbol over = SYMBOL_B;
		Rational0to1Number degree = new Rational0to1Number(0.32);

		Transition trans1 = new Transition(from, over, to);
		transitions.insert(trans1, degree);

		NFAedge edge = new NFAedge(from, to, transitions);
		return edge;
	}

	@SuppressWarnings("unused")
	private int countStates() {
		int count = 0;

		for (State state : automaton.iterateOverStates()) {
			count++;
		}
		return count;
	}

	@SuppressWarnings("unused")
	private int countTransitions() {
		int count = 0;

		for (Transition transition : automaton.iterateOverTransitions()) {
			count++;

		}
		return count;
	}

}
