package cz.upol.fapapp.cfa.outers;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;

public interface CFAOuterCellSupplier {
	
	public CellState computeOuterCell(int i, int j, CFAConfiguration config);

}
