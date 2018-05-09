package cz.upol.fapapp.cfa.mu;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;

/**
 * Interface specifying transition function.
 * 
 * @author martin
 *
 */
public interface CFATransitionFunction {

	/**
	 * Does some preparation before the next generation starts.
	 * 
	 * @param config
	 */
	public void beforeNextGeneration(CFAConfiguration config);

	/**
	 * Computes state of cell at given coordinates to be at the next generation.
	 * 
	 * @param i
	 * @param j
	 * @param cell
	 * @param neig
	 * @param config
	 * @return
	 */
	public CellState perform(int i, int j, CellState cell, CellNeighborhood neig, CFAConfiguration config);

}
