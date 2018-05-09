package cz.upol.fapapp.fa.automata;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.fa.modifs.AutomataCreator;

public class FuzAutWithEpsilonMovesTest {

	@BeforeClass
	public static void beforeClass() {
		 //TNorms.setTnorm(new GodelTNorm());
	}

	@Test
	public void test() {
		FuzAutWithEpsilonMoves automaton = AutomataCreator.createAutomatonWET(10);

		FuzzyState from = new FuzzyState(CollectionsUtils.singletonFuzzySet(automaton.getStates(), new State("q_0")));

		FuzzyState to = automaton.computeEpsilonClosure(from);


		System.out.println(to);
		
		isBetween(Degree.ONE, Degree.ONE, to.degreeOf(new State("q_0")));
		isBetween(new Degree(0.7), Degree.ONE, to.degreeOf(new State("q_1")));
		isBetween(Degree.ZERO, new Degree(0.3), to.degreeOf(new State("q_2")));


	}

	private void isBetween(Degree min, Degree max, Degree value) {

		assertTrue(min.compareTo(value) <= 0);
		assertTrue(max.compareTo(value) >= 0);
	}

}
