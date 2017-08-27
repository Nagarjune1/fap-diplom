package cz.upol.fapapp.cfa.config;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArray;

public interface CFAConfiguration {

	public TwoDimArray<CellState> toArray();

	public void setCell(int i, int j, CellState cell);

	public CellState getCell(int i, int j);

	public CellNeighborhood getNeighbors(int i, int j);

	public CellState defaultCell();

	public CFAConfiguration cloneItself();

}
