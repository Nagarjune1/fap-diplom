package cz.upol.automaton;

import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.AllStringsAlphabet;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.ling.alphabets.CharactersAlphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;

public class TestingAutomata {
	/**
	 * Vytvoří jednoduchý jednostavový automat s jedním přechodem (smyčkou).
	 * 
	 * @return
	 */
	public static DeterministicFuzzyAutomata createOneStateAutomaton() {
		FuzzyLogic logic = new LukasiewiczLogic();
		Alphabet alphabet = new AllStringsAlphabet();

		DeterministicFuzzyAutomata automaton = new DeterministicFuzzyAutomata(
				logic, alphabet);

		Degree //
		dg1 = new Rational0to1Number(0.5); //

		Symbol //
		symbolA = new Symbol("a"), //
		symbolB = new Symbol("b"); //

		State //
		state = new State("q0");//

		Transition //
		trans1 = new Transition(state, symbolA, state), //
		trans2 = new Transition(state, symbolB, state);//

		automaton.addState(state);
		automaton.addTransition(trans1);
		automaton.addTransition(trans2);

		automaton.setInitialState(state);
		automaton.setFiniteState(state, dg1);

		return automaton;
	}

	/**
	 * Vytvoří "trojúhelníkový automat" (tři uzly, tři hrany).
	 * 
	 * @return
	 */
	public static NondetermisticFuzzyAutomata createTriangleAutomaton() {
		FuzzyLogic logic = new LukasiewiczLogic();
		Alphabet alphabet = new CharactersAlphabet(true, false, true);

		NondetermisticFuzzyAutomata automaton = new NondetermisticFuzzyAutomata(
				logic, alphabet);

		Degree //
		dg1 = new Rational0to1Number(0.2), //
		dg2 = new Rational0to1Number(0.45), //
		dg3 = new Rational0to1Number(0.1), //
		dg4 = new Rational0to1Number(0.333), //
		dg5 = new Rational0to1Number(0.42), //
		dg6 = logic.getOne(), //
		dg7 = logic.getZero();

		Symbol //
		symbolA = new Symbol("a"), //
		symbolB = new Symbol("b"), //
		symbolC = new Symbol("c"), //
		symbolD = new Symbol("d"); //

		State //
		state0 = new State("q0"), //
		state1 = new State("q1"), //
		state2 = new State("q2"); //

		Transition //
		trans1 = new Transition(state0, symbolA, state1), //
		trans2 = new Transition(state1, symbolB, state1), //
		trans3 = new Transition(state1, symbolA, state2), //
		trans4 = new Transition(state2, symbolB, state1), //
		trans5 = new Transition(state2, symbolC, state0), //
		trans6 = new Transition(state2, symbolD, state0);//

		automaton.addState(state0);
		automaton.addState(state1);
		automaton.addState(state2);

		automaton.addTransition(trans1, dg2);
		automaton.addTransition(trans2, dg3);
		automaton.addTransition(trans3, dg1);
		automaton.addTransition(trans4, dg4);
		automaton.addTransition(trans5, dg6);
		automaton.addTransition(trans6, dg2);

		automaton.setInitialState(state0, dg6);
		automaton.setInitialState(state1, dg5);
		automaton.setInitialState(state2, dg4);
		automaton.setFiniteState(state2, dg7);

		return automaton;
	}

}
