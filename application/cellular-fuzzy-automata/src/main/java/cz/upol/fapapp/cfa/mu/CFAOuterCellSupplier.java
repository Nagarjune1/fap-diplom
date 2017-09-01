package cz.upol.fapapp.cfa.mu;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.comp.CFAConfiguration;

public interface CFAOuterCellSupplier {
	public CellState computeOuterCell(int i, int j, CFAConfiguration config);

}
