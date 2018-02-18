package cz.upol.fapapp.cfa.automaton;

import org.junit.Test;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomaton;
import cz.upol.fapapp.cfa.conf.CFAComputation;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.ConfigGenerator;
import cz.upol.fapapp.cfa.impls.common.BivalentGameOfLifeCFA;
import cz.upol.fapapp.core.misc.Logger;

public class CellularFuzzyAutomatonTest {

	private static final ConfigGenerator GEN = new ConfigGenerator();
	private static final int SIZE = 10;

	@Test
	public void testToNextGeneration() {
		

		CFAConfiguration initConfig = GEN.generateBival(SIZE, 42, 0.5);
		CellularFuzzyAutomaton automaton = new BivalentGameOfLifeCFA(SIZE);
		CFAComputation comp = new CFAComputation(automaton, initConfig);

		automaton.print(System.out);
		comp.print(System.out);

		comp.toNextGeneration();
		automaton.print(System.out);

		comp.toNextGeneration();
		automaton.print(System.out);

		// TODO: test IMPLEMENTME
		Logger.get().warning("Test not implemented");
	}

	/**************************************************************************/

}
