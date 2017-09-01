package cz.upol.fapapp.cfa.automata;

import cz.upol.fapapp.cfa.comp.CFAConfiguration;
import cz.upol.fapapp.cfa.comp.CellNeighborhood;
import cz.upol.fapapp.cfa.mu.CFAOuterCellSupplier;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;

public class CellularFuzzyAutomata extends BaseCellularFuzzyAutomata {

	public CellularFuzzyAutomata(int size, CFATransitionFunction mu, CFAOuterCellSupplier outers) {
		super(size, mu, outers);
	}

	/**************************************************************************/

	
	@Override
	public CFAConfiguration computeNextGeneration(CFAConfiguration currentConfig) {
		CFAConfiguration newConfig = currentConfig.cloneItself();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				CellState cell = currentConfig.getCell(i, j);
				CellNeighborhood neig = currentConfig.getNeighbors(i, j, outers);

				CellState newCell = mu.perform(i, j, cell, neig);
				newConfig.setCell(i, j, newCell);
			}
		}

		return newConfig;
	}
	
	/**************************************************************************/

	
	
}
