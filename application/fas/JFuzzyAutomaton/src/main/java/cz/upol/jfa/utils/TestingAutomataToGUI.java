package cz.upol.jfa.utils;

import cz.upol.automaton.TestingAutomata;
import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.AllStringsAlphabet;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.jfa.automata.DetermFuzzyAutomatonToGUI;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;
import cz.upol.jfa.viewer.Position;

public class TestingAutomataToGUI {

	public static NondetFuzzyAutomatonToGUI createLikeYpsilonAutomaton() {

		Alphabet alphabet = new AllStringsAlphabet();
		LukasiewiczLogic logic = new LukasiewiczLogic();
		NondetFuzzyAutomatonToGUI mata = new NondetFuzzyAutomatonToGUI(logic, alphabet);

		Symbol sa = new Symbol('a'), sb = new Symbol('b'), sc = new Symbol('c');
		State q0 = new State("q0"), q1 = new State("q1"), q2 = new State("q2"), q3 = new State(
				"q3");

		Transition t1 = new Transition(q0, sa, q1), t2 = new Transition(q1, sb,
				q2), t3 = new Transition(q2, sc, q1), t4 = new Transition(q0,
				sa, q2), t5 = new Transition(q2, sa, q3);

		mata.addState(q0, new Position(40, 50));
		mata.addState(q1, new Position(450, 90));
		mata.addState(q2, new Position(280, 260));
		mata.addState(q3, new Position(160, 390));

		mata.setInitialState(q0, new Rational0to1Number(0.3333333));
		mata.setInitialState(q1, new Rational0to1Number(0.1));
		mata.setFiniteState(q1, new Rational0to1Number(0.2));
		mata.setFiniteState(q2, new Rational0to1Number(0.9));
		mata.setFiniteState(q3, logic.getOne());

		mata.addTransition(t1, new Rational0to1Number(0.2));
		mata.addTransition(t2, new Rational0to1Number(0.3));
		mata.addTransition(t3, new Rational0to1Number(0.4));
		mata.addTransition(t4, new Rational0to1Number(0.5));
		mata.addTransition(t5, new Rational0to1Number(0.5));

		return mata;
	}

	public static NondetFuzzyAutomatonToGUI createAnotherTriangle() {

		Alphabet alphabet = new AllStringsAlphabet();
		FuzzyLogic logic = new LukasiewiczLogic();

		NondetFuzzyAutomatonToGUI mata = new NondetFuzzyAutomatonToGUI(logic, alphabet);

		Symbol sa = new Symbol('a'), sb = new Symbol('b'), sc = new Symbol('c');
		State s1 = new State("q0"), s2 = new State("q1"), s3 = new State("q2''");

		Transition t1 = new Transition(s1, sa, s2), t2 = new Transition(s2, sb,
				s3), t3 = new Transition(s3, sc, s2), t4 = new Transition(s1,
				sa, s3), t5 = new Transition(s1, sc, s3), t6 = new Transition(
				s2, sb, s2);

		mata.addState(s1, new Position(60, 30));
		mata.addState(s2, new Position(40, 250));
		mata.addState(s3, new Position(240, 190));

		mata.setInitialState(s1, new Rational0to1Number(0.3333));
		mata.setInitialState(s2, new Rational0to1Number(0.1));
		mata.setFiniteState(s2, new Rational0to1Number(0.2));
		mata.setFiniteState(s3, new Rational0to1Number(0.9));

		mata.addTransition(t1, new Rational0to1Number(0.2));
		mata.addTransition(t2, new Rational0to1Number(0.3));
		mata.addTransition(t3, new Rational0to1Number(0.4));
		mata.addTransition(t4, new Rational0to1Number(0.5));
		mata.addTransition(t5, new Rational0to1Number(0.6));
		mata.addTransition(t6, new Rational0to1Number(0.7));

		return mata;
	}

	public static DetermFuzzyAutomatonToGUI createOneStateAutomaton() {
		DeterministicFuzzyAutomata mata = TestingAutomata.createOneStateAutomaton();
		return new DetermFuzzyAutomatonToGUI(mata);
	}

	public static NondetFuzzyAutomatonToGUI createTriangle() {
		NondetermisticFuzzyAutomata mata = TestingAutomata.createTriangleAutomaton();
		return new NondetFuzzyAutomatonToGUI(mata);
	}

}
