package cz.upol.fapapp.cfa.conf;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArray;
import cz.upol.fapapp.cfa.misc.TwoDimArray.TwoDimArrForEach;

public class CellNeighborhood {

	private final TwoDimArray<CellState> cells;

	public CellNeighborhood() {
		super();
		this.cells = new TwoDimArray<>((-1), (+1 + 1), new CellState(0.0));
	}

	public CellNeighborhood(TwoDimArray<CellState> cells) {
		super();
		this.cells = cells;
	}

	/**************************************************************************/

	public CellState get(int i, int j) {
		return cells.get(i, j);
	}

	public void set(int i, int j, CellState cell) {
		cells.set(i, j, cell);
	}

	/**************************************************************************/

	public int count(boolean withCenter) {
		int size = cells.getSize();

		if (withCenter) {
			return size * size;
		} else {
			return size * size - 1;
		}
	}

	public double sum(boolean withCenter) {
		double sum = 0.0;
		for (CellState cell : cells) {
			sum += cell.getValue();
		}

		if (!withCenter) {
			CellState center = get(0, 0);
			sum -= center.getValue();
		}

		return sum;
	}

	public double avg(boolean withCenter) {
		double sum = sum(withCenter);
		double count = (+2 - -1) * (+2 - -1) - (withCenter ? 0 : 1);

		return sum / count;
	}

	// TODO use me!
	public void forEach(TwoDimArrForEach<CellState> function) {
		cells.forEach(function);
	}

}
