package cz.upol.fapapp.cfa.mu;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;

public interface CFATransitionFunction {

	public void beforeNextGeneration(CFAConfiguration config);

	public CellState perform(int i, int j, CellState cell, CellNeighborhood neig, CFAConfiguration config);

}
