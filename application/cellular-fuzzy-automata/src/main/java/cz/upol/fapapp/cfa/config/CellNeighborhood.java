package cz.upol.fapapp.cfa.config;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArray;

public class CellNeighborhood {

	private final TwoDimArray<CellState> cells;

	public CellNeighborhood() {
		super();
		this.cells = new TwoDimArray<>(-1, +1);
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

	public double sum() {
		double sum = 0.0;
		for (CellState cell : cells) {
			sum += cell.getValue();
		}
		return sum;
	}

	public double avg() {
		double sum = sum();
		double count = (+1 - -1) * (+1 - -1);

		return sum / count;
	}

}
