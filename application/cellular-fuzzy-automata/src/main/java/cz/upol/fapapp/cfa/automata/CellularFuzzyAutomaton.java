package cz.upol.fapapp.cfa.automata;

import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import cz.upol.fapapp.cfa.outers.CFAOuterCellSupplier;

/**
 * Cellular automata implementation.
 * 
 * @author martin
 *
 */
public class CellularFuzzyAutomaton extends BaseCellularFuzzyAutomaton {

	public CellularFuzzyAutomaton(int size, CFATransitionFunction mu, CFAOuterCellSupplier outers) {
		super(size, mu, outers);
	}

	/**************************************************************************/

	@Override
	public CFAConfiguration computeNextGeneration(CFAConfiguration currentConfig) {
		CFAConfiguration newConfig = currentConfig.cloneItself();

		mu.beforeNextGeneration(currentConfig);

		currentConfig.forEach((i, j, cell) -> {
			CellNeighborhood neig = currentConfig.getNeighbors(i, j, outers);

			CellState newCell = mu.perform(i, j, cell, neig, currentConfig);
			newConfig.setCell(i, j, newCell);
		});

		return newConfig;
	}

	/**************************************************************************/

}
