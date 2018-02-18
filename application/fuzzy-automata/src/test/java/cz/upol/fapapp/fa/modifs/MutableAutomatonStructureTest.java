package cz.upol.fapapp.fa.modifs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.tnorm.GodelTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

public class MutableAutomatonStructureTest {

	private MutableAutomatonStructure struct;

	@BeforeClass
	public static void beforeClas() {
		TNorms.setTnorm(new GodelTNorm());
	}

	@Before
	public void init() {
		FuzzyAutomaton automaton = AutomataCreator.createAutomaton1();
		struct = new MutableAutomatonStructure(automaton);
	}

	@Test
	public void testNoMutation() {
		checkHasState("q_0", 1.0, 0.4);
		checkHasState("q_1", 0.1, 0.8);
		checkHasNotState("q_XXX");

		checkHasTransition("q_0", "a", "q_1", 0.5);
		checkHasTransition("q_0", "a", "q_0", 0.2);
		checkHasTransition("q_1", "b", "q_0", 1.0);

		checkHasNotTransition("q_1", "a", "q_1");
		checkHasNotTransition("q_XXX", "a", "q_YYY");
		checkHasNotTransition("q_1", "ZZZ", "q_1");

	}

	@Test
	public void testAddState() {
		State state1 = new State("q'");
		Degree initialIn1 = new Degree(0.45);
		Degree finalIn1 = new Degree(0.65);

		struct.addState(state1, initialIn1, finalIn1);

		checkHasState("q'", 0.45, 0.65);

		State state2 = new State("q'");
		Degree initialIn2 = new Degree(0.85);
		Degree finalIn2 = new Degree(0.1);

		struct.addState(state2, initialIn2, finalIn2);

		checkHasState("q'", 0.85, 0.65);
	}

	@Test
	public void testRemoveState() {
		State state = new State("q_1");

		struct.removeState(state);

		checkHasState("q_0", 1.0, 0.4);
		checkHasNotState("q_1");

		checkHasTransition("q_0", "a", "q_0", 0.2);
		checkHasNotTransition("q_0", "a", "q_1");
		checkHasNotTransition("q_1", "b", "q_0");
	}

	@Test
	public void testReplaceState() {
		State oldState1 = new State("q_1");
		State newState1 = new State("q_X");

		struct.replaceState(oldState1, newState1);

		checkHasState("q_0", 1.0, 0.4);
		checkHasState("q_X", 0.1, 0.8);
		checkHasNotState("q_1");

		checkHasTransition("q_0", "a", "q_X", 0.5);
		checkHasTransition("q_0", "a", "q_0", 0.2);
		checkHasTransition("q_X", "b", "q_0", 1.0);

		checkHasNotTransition("q_0", "a", "q_1");
		checkHasNotTransition("q_1", "b", "q_0");

		State oldState2 = new State("q_X");
		State newState2 = new State("q_0");

		struct.replaceState(oldState2, newState2);

		checkHasState("q_0", 1.0, 0.8);
		checkHasNotState("q_X");

		checkHasTransition("q_0", "a", "q_0", 0.5);
		checkHasTransition("q_0", "b", "q_0", 1.0);
	}

	@Test
	public void testAddTransition() {
		State from1 = new State("q_0");
		Symbol over1 = new Symbol("b");
		State to1 = new State("q_1");
		Degree degree1 = new Degree(0.11);

		struct.addTransition(from1, over1, to1, degree1);
		checkHasTransition("q_0", "b", "q_1", 0.11);

		State from2 = new State("q_0");
		Symbol over2 = new Symbol("b");
		State to2 = new State("q_1");
		Degree degree2 = new Degree(0.99);

		struct.addTransition(from2, over2, to2, degree2);
		checkHasTransition("q_0", "b", "q_1", 0.99);
	}

	@Test
	public void testRemoveTransition() {
		State from = new State("q_0");
		Symbol over = new Symbol("a");
		State to = new State("q_1");

		struct.removeTransition(from, over, to);

		checkHasNotTransition("q_0", "a", "q_1");
	}

	@Test
	public void testReplaceTransition() {
		State oldFrom1 = new State("q_0");
		Symbol oldOver1 = new Symbol("a");
		State oldTo1 = new State("q_1");

		State newFrom1 = new State("q_1");
		Symbol newOver1 = new Symbol("a");
		State newTo1 = new State("q_1");

		struct.replaceTransition(oldFrom1, oldOver1, oldTo1, newFrom1, newOver1, newTo1);

		checkHasNotTransition("q_0", "a", "q_1");
		checkHasTransition("q_1", "a", "q_1", 0.5);
		
		State oldFrom2 = new State("q_1");
		Symbol oldOver2 = new Symbol("a");
		State oldTo2 = new State("q_1");

		State newFrom2 = new State("q_1");
		Symbol newOver2 = new Symbol("b");
		State newTo2 = new State("q_0");

		struct.replaceTransition(oldFrom2, oldOver2, oldTo2, newFrom2, newOver2, newTo2);

		checkHasNotTransition("q_1", "a", "q_1");
		checkHasTransition("q_1", "b", "q_0", 1.0);
	}

	/////////////////////////////////////////////////////////////////////////

	private void checkHasState(String stateLbl, Double initialInVal, Double finalInVal) {
		State state = new State(stateLbl);

		assertTrue(struct.getStates().contains(state));

		if (initialInVal != null) {
			Degree initialIn = new Degree(initialInVal);
			assertEquals(initialIn, struct.getInitialStates().get(state));
		}

		if (finalInVal != null) {
			Degree finalIn = new Degree(finalInVal);
			assertEquals(finalIn, struct.getFinalStates().get(state));
		}
	}

	private void checkHasNotState(String stateLbl) {
		State state = new State(stateLbl);

		assertFalse(struct.getStates().contains(state));

	}

	private void checkHasTransition(String fromLbl, String overVal, String toLbl, Double degreeVal) {
		State from = new State(fromLbl);
		Symbol over = new Symbol(overVal);
		State to = new State(toLbl);

		Degree degree = new Degree(degreeVal);

		Triple<State, Symbol, State> transition = new Triple<State, Symbol, State>(from, over, to);

		assertEquals(degree, struct.getTransitionFunction().get(transition));
	}

	private void checkHasNotTransition(String fromLbl, String overVal, String toLbl) {
		State from = new State(fromLbl);
		Symbol over = new Symbol(overVal);
		State to = new State(toLbl);

		Triple<State, Symbol, State> transition = new Triple<State, Symbol, State>(from, over, to);

		assertEquals(null, struct.getTransitionFunction().get(transition));
	}

}
