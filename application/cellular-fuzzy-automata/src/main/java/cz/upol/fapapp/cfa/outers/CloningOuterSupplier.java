package cz.upol.fapapp.cfa.outers;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;

public class CloningOuterSupplier implements CFAOuterCellSupplier {

	public CloningOuterSupplier() {
	}

	@Override
	public CellState computeOuterCell(int i, int j, CFAConfiguration config) {
		int size = config.getSize();
		if (i < 0) {
			i = 0;
		}
		if (i >= size) {
			i = size - 1;
		}
		if (j < 0) {
			j = 0;
		}
		if (j >= size) {
			j = size - 1;
		}

		return config.getCell(i, j);
	}

}
