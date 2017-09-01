package cz.upol.fapapp.cfa.mu;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.comp.CellNeighborhood;

public interface CFATransitionFunction {

	public CellState perform(int i, int j, CellState cell, CellNeighborhood neig);

}
