package cz.upol.fapapp.cfa.automata;

import org.junit.Test;

import cz.upol.fapapp.cfa.comp.CFAConfiguration;
import cz.upol.fapapp.cfa.comp.CellularAutomataComputation;
import cz.upol.fapapp.cfa.comp.ConfigGenerator;
import cz.upol.fapapp.cfa.mu.BivalGameOfLifeImpl;
import cz.upol.fapapp.core.misc.Logger;

public class CellularFuzzyAutomataTest {

	private static final ConfigGenerator GEN = new ConfigGenerator();
	private static final int SIZE = 10;

	@Test
	public void testToNextGeneration() {
		BivalGameOfLifeImpl gol = new BivalGameOfLifeImpl();

		CFAConfiguration initConfig = GEN.generateBival(SIZE, 42, 0.5);
		CellularFuzzyAutomata automata = new CellularFuzzyAutomata(SIZE, gol, gol);
		CellularAutomataComputation comp = new CellularAutomataComputation(automata, initConfig);

		automata.print(System.out);
		comp.print(System.out);

		comp.toNextGeneration();
		automata.print(System.out);

		comp.toNextGeneration();
		automata.print(System.out);

		// TODO: test IMPLEMENTME
		Logger.get().warning("Test not implemented");
	}

	/**************************************************************************/

}
