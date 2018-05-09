package cz.upol.fapapp.cfa.mu;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;

/**
 * Complete transition function doing some process at each step of computation.
 * 
 * @author martin
 *
 */
public abstract class PowerfullTransitionFunction implements CFATransitionFunction {

	public PowerfullTransitionFunction() {
	}

	@Override
	public CellState perform(int i, int j, CellState cell, CellNeighborhood neig, CFAConfiguration config) {
		process(i, j, cell, neig, config);

		return config.getCell(i, j);
	}

	public abstract void process(int i, int j, CellState cell, CellNeighborhood neig, CFAConfiguration config);

}
