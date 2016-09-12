package cz.upol.automaton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.DFAedge;
import cz.upol.automaton.misc.edges.NFAedge;

/**
 * Testovací třída pro testování "dotazovacích" metod nad
 * {@link FuzzyAutomatonImpl}. Předpokládá, že {@link TestingAutomata} funguje a
 * vrací funkční testovací automaty dle požadavků.
 * 
 * @author martin
 * 
 */
public class FuzzyAutomatonImplQueryingTest {

	/**
	 * @uml.property name="oneStateAtm"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final DeterministicFuzzyAutomata oneStateAtm = TestingAutomata
			.createOneStateAutomaton();

	/**
	 * @uml.property name="triangleAtm"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final NondetermisticFuzzyAutomata triangleAtm = TestingAutomata
			.createTriangleAutomaton();

	/**
	 * @uml.property name="sTATE_Q0"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final State STATE_Q0 = new State("q0");
	/**
	 * @uml.property name="sTATE_Q1"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final State STATE_Q1 = new State("q1");
	/**
	 * @uml.property name="sTATE_Q2"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final State STATE_Q2 = new State("q2");

	/**
	 * @uml.property name="sYMBOL_A"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final Symbol SYMBOL_A = new Symbol("a");
	/**
	 * @uml.property name="sYMBOL_B"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final Symbol SYMBOL_B = new Symbol("b");
	/**
	 * @uml.property name="sYMBOL_C"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final Symbol SYMBOL_C = new Symbol("c");
	/**
	 * @uml.property name="sYMBOL_D"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final Symbol SYMBOL_D = new Symbol("d");

	@Before
	public void setUp() throws Exception {
	}

	@SuppressWarnings("unused")
	@Test
	public void testIterateOverStates() {
		int statesCount;

		statesCount = 0;

		for (State state : oneStateAtm.iterateOverStates()) {
			statesCount++;
		}

		assertEquals(1, statesCount);

		statesCount = 0;

		for (State state : triangleAtm.iterateOverStates()) {
			statesCount++;
		}

		assertEquals(3, statesCount);

	}

	@SuppressWarnings("unused")
	@Test
	public void testIterateOverTransitions() {
		int transitionsCount;

		transitionsCount = 0;

		for (Transition transition : oneStateAtm.iterateOverTransitions()) {
			transitionsCount++;
		}

		assertEquals(2, transitionsCount);

		transitionsCount = 0;

		for (Transition transition : triangleAtm.iterateOverTransitions()) {
			transitionsCount++;
		}

		assertEquals(6, transitionsCount);
	}

	@Test
	public void testTransitionsFrom() {
		Set<Transition> osTranss = oneStateAtm.transitionsFrom(STATE_Q0);

		checkContainsTransition(osTranss, STATE_Q0, STATE_Q0, SYMBOL_A, true);
		checkContainsTransition(osTranss, STATE_Q0, STATE_Q0, SYMBOL_B, true);
		checkContainsTransition(osTranss, STATE_Q0, STATE_Q0, SYMBOL_C, false); // nepoužitý
																				// symbol
		checkContainsTransition(osTranss, STATE_Q0, STATE_Q1, SYMBOL_A, false); // nepoužitý
																				// stav

		FuzzySet<Transition> triTranss = triangleAtm.transitionsFrom(STATE_Q2);

		checkDegreeOfTransition(triTranss, STATE_Q2, STATE_Q1, SYMBOL_B, 0.333);
		checkDegreeOfTransition(triTranss, STATE_Q2, STATE_Q0, SYMBOL_C, 1.0);// zdvojený
																				// přechod
		checkDegreeOfTransition(triTranss, STATE_Q2, STATE_Q0, SYMBOL_D, 0.45);

		checkDegreeOfTransition(triTranss, STATE_Q1, STATE_Q0, SYMBOL_B, -1);// není
																				// ze
																				// stavu
																				// q2
	}

	@Test
	public void testTransitionsFromAndOver() {
		Set<Transition> osTranss = oneStateAtm.transitionsFromAndOver(STATE_Q0,
				SYMBOL_A);

		checkContainsTransition(osTranss, STATE_Q0, STATE_Q0, SYMBOL_A, true);
		checkContainsTransition(osTranss, STATE_Q0, STATE_Q0, SYMBOL_B, false); // není
																				// přes
																				// symbol
																				// a
		checkContainsTransition(osTranss, STATE_Q0, STATE_Q1, SYMBOL_A, false); // nepoužitý
																				// stav

		FuzzySet<Transition> triTranss = triangleAtm.transitionsFromAndOver(
				STATE_Q1, SYMBOL_B);

		checkDegreeOfTransition(triTranss, STATE_Q1, STATE_Q1, SYMBOL_B, 0.1);
		checkDegreeOfTransition(triTranss, STATE_Q1, STATE_Q2, SYMBOL_A, -1); // není
																				// přes
																				// symbol
																				// b
		checkDegreeOfTransition(triTranss, STATE_Q2, STATE_Q1, SYMBOL_B, -1); // není
																				// ze
																				// stavu
																				// q2
	}

	@Test
	public void testTransitionsFromTo() {
		Set<Transition> osTranss = oneStateAtm.transitionsFromTo(STATE_Q0,
				STATE_Q0);

		checkContainsTransition(osTranss, STATE_Q0, STATE_Q0, SYMBOL_A, true);
		checkContainsTransition(osTranss, STATE_Q0, STATE_Q0, SYMBOL_B, true);
		checkContainsTransition(osTranss, STATE_Q0, STATE_Q0, SYMBOL_C, false); // nepoužitý
																				// symbol

		FuzzySet<Transition> triTranss = triangleAtm.transitionsFromTo(
				STATE_Q2, STATE_Q0);

		checkDegreeOfTransition(triTranss, STATE_Q2, STATE_Q0, SYMBOL_C, 1.0);// zdvojený
																				// přechod
		checkDegreeOfTransition(triTranss, STATE_Q2, STATE_Q0, SYMBOL_D, 0.45);

		checkDegreeOfTransition(triTranss, STATE_Q1, STATE_Q1, SYMBOL_B, -1); // není
																				// z
																				// q2
		checkDegreeOfTransition(triTranss, STATE_Q2, STATE_Q1, SYMBOL_B, -1); // není
																				// do
																				// q0
	}

	@Test
	public void testGetEdge() {
		DFAedge e1 = oneStateAtm.getEdge(STATE_Q0, STATE_Q0);
		assertEquals(STATE_Q0, e1.getFrom());
		assertEquals(STATE_Q0, e1.getTo());
		checkContainsTransition(e1.getTransitions(), STATE_Q0, STATE_Q0,
				SYMBOL_A, true);
		checkContainsTransition(e1.getTransitions(), STATE_Q0, STATE_Q0,
				SYMBOL_B, true);
		checkContainsTransition(e1.getTransitions(), STATE_Q0, STATE_Q0,
				SYMBOL_C, false); // nepoužitý symbol

		NFAedge e2 = triangleAtm.getEdge(STATE_Q0, STATE_Q0);
		checkDegreeOfTransition(e2.getTransitions(), STATE_Q0, STATE_Q0,
				SYMBOL_A, -1); // hrana nemá žádný přechod
		checkDegreeOfTransition(e2.getTransitions(), STATE_Q0, STATE_Q0,
				SYMBOL_B, -1);
		checkDegreeOfTransition(e2.getTransitions(), STATE_Q0, STATE_Q0,
				SYMBOL_C, -1);
		checkDegreeOfTransition(e2.getTransitions(), STATE_Q0, STATE_Q0,
				SYMBOL_D, -1);

		NFAedge e3 = triangleAtm.getEdge(STATE_Q2, STATE_Q0);
		assertEquals(STATE_Q2, e3.getFrom());
		assertEquals(STATE_Q0, e3.getTo());
		checkDegreeOfTransition(e3.getTransitions(), STATE_Q2, STATE_Q0,
				SYMBOL_C, 1.0);// zdvojený přechod
		checkDegreeOfTransition(e3.getTransitions(), STATE_Q2, STATE_Q0,
				SYMBOL_D, 0.45);
		checkDegreeOfTransition(e3.getTransitions(), STATE_Q1, STATE_Q1,
				SYMBOL_B, -1); // není z q2
		checkDegreeOfTransition(e3.getTransitions(), STATE_Q2, STATE_Q1,
				SYMBOL_B, -1); // není do q0

		NFAedge e4 = triangleAtm.getEdge(STATE_Q0, STATE_Q1);
		checkDegreeOfTransition(e4.getTransitions(), STATE_Q0, STATE_Q1,
				SYMBOL_A, 0.45); // jenom tenhle
		checkDegreeOfTransition(e4.getTransitions(), STATE_Q2, STATE_Q0,
				SYMBOL_D, -1); // není z q0
		checkDegreeOfTransition(e4.getTransitions(), STATE_Q0, STATE_Q2,
				SYMBOL_B, -1); // neexstující přechod

	}

	@Test
	public void testGetOutgoingEdges() {
		Set<DFAedge> edges1 = oneStateAtm.getOutgoingEdges(STATE_Q0);

		checkContainsDFA(edges1, STATE_Q0, STATE_Q0, true);
		checkContainsDFA(edges1, STATE_Q0, STATE_Q1, false); // neexistující
																// stav

		Set<NFAedge> edges2 = triangleAtm.getOutgoingEdges(STATE_Q1);

		checkContainsNFA(edges2, STATE_Q1, STATE_Q1, true);
		checkContainsNFA(edges2, STATE_Q1, STATE_Q2, true);
		checkContainsNFA(edges2, STATE_Q0, STATE_Q1, false); // není z q1
		checkContainsNFA(edges2, STATE_Q1, STATE_Q0, false); // neexistující
																// přechod

		Set<NFAedge> edges3 = triangleAtm.getOutgoingEdges(STATE_Q2);

		checkContainsNFA(edges3, STATE_Q2, STATE_Q1, true);
		checkContainsNFA(edges3, STATE_Q2, STATE_Q0, true);
		checkContainsNFA(edges3, STATE_Q2, STATE_Q2, false); // neexistující
																// přechod

	}

	@Test
	public void testGetEdges() {
		Set<DFAedge> edges1 = oneStateAtm.getEdges();

		checkContainsDFA(edges1, STATE_Q0, STATE_Q0, true);
		checkContainsDFA(edges1, STATE_Q0, STATE_Q1, false); // neexistující
																// stav

		Set<NFAedge> edges2 = triangleAtm.getEdges();

		checkContainsNFA(edges2, STATE_Q0, STATE_Q1, true); // všechny
		checkContainsNFA(edges2, STATE_Q1, STATE_Q1, true);
		checkContainsNFA(edges2, STATE_Q1, STATE_Q2, true);
		checkContainsNFA(edges2, STATE_Q2, STATE_Q1, true);
		checkContainsNFA(edges2, STATE_Q2, STATE_Q0, true);
		checkContainsNFA(edges2, STATE_Q0, STATE_Q0, false); // neexistující
																// přechod
	}

	@Test
	public void testGetTransitionsWithState() {
		Set<Transition> transs1 = oneStateAtm.getTransitionsWithState(STATE_Q0);
		assertEquals(2, transs1.size()); // už to bylo mnohokrát otestováno

		FuzzySet<Transition> transs2 = triangleAtm
				.getTransitionsWithState(STATE_Q0);
		checkContains(transs2, STATE_Q0, STATE_Q1, SYMBOL_A, true); // ze stavu
																	// q0
		checkContains(transs2, STATE_Q2, STATE_Q0, SYMBOL_C, true); // do stavu
																	// q0
		checkContains(transs2, STATE_Q2, STATE_Q0, SYMBOL_D, true);
		checkContains(transs2, STATE_Q0, STATE_Q0, SYMBOL_A, false); // neexistující
																		// přechod

		FuzzySet<Transition> transs3 = triangleAtm
				.getTransitionsWithState(STATE_Q1);
		checkContains(transs3, STATE_Q1, STATE_Q1, SYMBOL_B, true); // smyčka
		checkContains(transs3, STATE_Q0, STATE_Q1, SYMBOL_A, true); // do stavu
																	// q1
		checkContains(transs3, STATE_Q2, STATE_Q1, SYMBOL_B, true); //
		checkContains(transs3, STATE_Q1, STATE_Q2, SYMBOL_A, true); // ze stavu
																	// q1
		checkContains(transs3, STATE_Q2, STATE_Q0, SYMBOL_B, false); // neprochází
																		// stavem
																		// q1

	}

	private void checkContainsTransition(Set<Transition> transitions,
			State from, State to, Symbol over, boolean contains) {

		Transition transition = new Transition(from, over, to);
		boolean really = transitions.contains(transition);

		assertEquals("Failed on " + transition.externalize(), contains, really);
	}

	private void checkDegreeOfTransition(FuzzySet<Transition> transitions,
			State from, State to, Symbol over, double degree) {

		Transition transition = new Transition(from, over, to);
		if (degree != -1) {
			Degree expectedDegree = new Rational0to1Number(degree);
			Degree actualDegree = transitions.find(transition);
			assertEquals(expectedDegree, actualDegree);
		} else {
			Degree actualDegree = transitions.find(transition);
			if (actualDegree instanceof Rational0to1Number) {
				Rational0to1Number number = (Rational0to1Number) actualDegree;
				assertTrue(number == null || number.getValue() == 0.0);
			} else {
				fail("Not a " + Rational0to1Number.class.getName());
			}
		}
	}

	private void checkContainsDFA(Set<DFAedge> edges, State from, State to,
			boolean containsOrNot) {

		DFAedge edge = new DFAedge(from, to);

		if (containsOrNot) {
			assertTrue(edges.contains(edge));
		} else {
			assertFalse(edges.contains(edge));
		}
	}

	private void checkContainsNFA(Set<NFAedge> edges, State from, State to,
			boolean containsOrNot) {

		NFAedge edge = new NFAedge(from, to, triangleAtm);

		if (containsOrNot) {
			assertTrue(edges.contains(edge));
		} else {
			assertFalse(edges.contains(edge));
		}
	}

	private void checkContains(FuzzySet<Transition> transitions, State from,
			State to, Symbol over, boolean containsOrNot) {

		Transition transition = new Transition(from, over, to);
		if (containsOrNot) {
			assertTrue(transitions.contains(transition));
		} else {
			assertFalse(transitions.contains(transition));
		}
	}

}
