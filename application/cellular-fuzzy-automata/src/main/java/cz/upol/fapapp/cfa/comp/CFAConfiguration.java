package cz.upol.fapapp.cfa.comp;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArray;
import cz.upol.fapapp.cfa.mu.CFAOuterCellSupplier;
import cz.upol.fapapp.core.misc.Printable;

public interface CFAConfiguration extends Printable {

	public TwoDimArray<CellState> toArray();

	public int getSize();

	public void setCell(int i, int j, CellState cell);

	public CellState getCell(int i, int j);

	public CellNeighborhood getNeighbors(int i, int j, CFAOuterCellSupplier outerSupplier);

	public CFAConfiguration cloneItself();

}
