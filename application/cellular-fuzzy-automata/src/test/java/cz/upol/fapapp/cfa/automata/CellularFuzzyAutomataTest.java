package cz.upol.fapapp.cfa.automata;

import org.junit.Test;

import cz.upol.fapapp.cfa.config.CFAConfiguration;
import cz.upol.fapapp.cfa.config.ConfigGenerator;
import cz.upol.fapapp.cfa.mu.BivalGameOfLifeMu;
import cz.upol.fapapp.core.misc.Logger;

public class CellularFuzzyAutomataTest {

	private static final ConfigGenerator GEN = new ConfigGenerator();
	private static final int SIZE = 10;

	@Test
	public void testToNextGeneration() {
		BivalGameOfLifeMu gol = new BivalGameOfLifeMu();

		CFAConfiguration initConfig = GEN.generateBival(SIZE, BivalGameOfLifeMu.DEAD, 42);
		CellularFuzzyAutomata automata = new CellularFuzzyAutomata(SIZE, gol, initConfig);

		automata.print(System.out);

		automata.toNextGeneration();
		automata.print(System.out);

		automata.toNextGeneration();
		automata.print(System.out);

		// TODO: test IMPLEMENTME
		Logger.get().warning("Test not implemented");
	}

	/**************************************************************************/

}
