package cz.upol.fapapp.cfa.automata;

import cz.upol.fapapp.cfa.config.CFAConfiguration;
import cz.upol.fapapp.cfa.config.CellNeighborhood;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import cz.upol.fapapp.core.misc.Logger;

public class CellularFuzzyAutomata extends BaseCellularFuzzyAutomata {
	private int generation;
	private CFAConfiguration config;

	public CellularFuzzyAutomata(int size, CFATransitionFunction mu, CFAConfiguration initialConfig) {
		super(size, mu);

		this.generation = 0;
		this.config = initialConfig;
	}

	/**************************************************************************/

	public CFAConfiguration getCurrentConfig() {
		return config;
	}

	
	public int getCurrentGeneration() {
		return generation;
	}

	/**************************************************************************/

	public void toNextGeneration() {
		generation++;
		Logger.get().moreinfo("Going to generation " + generation);

		CFAConfiguration newConfig = computeNextGeneration(config);

		config = newConfig;
	}

	public void toNextGenerations(int count) {
		for (int i = 0; i < count; i++) {
			toNextGeneration();
		}
	}

	/**************************************************************************/

	CFAConfiguration computeNextGeneration(CFAConfiguration currentConfig) {
		CFAConfiguration newConfig = currentConfig.cloneItself();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				CellState cell = currentConfig.getCell(i, j);
				CellNeighborhood neig = currentConfig.getNeighbors(i, j);

				CellState newCell = mu.perform(i, j, cell, neig);
				newConfig.setCell(i, j, newCell);
			}
		}

		return newConfig;
	}
}
