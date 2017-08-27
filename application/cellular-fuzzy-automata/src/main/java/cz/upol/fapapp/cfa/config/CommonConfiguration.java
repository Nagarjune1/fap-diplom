package cz.upol.fapapp.cfa.config;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArray;

public class CommonConfiguration implements CFAConfiguration {

	private final int m;
	private final CellState defaultCell;
	private final TwoDimArray<CellState> cells;

	public CommonConfiguration(int m, CellState defaultCell, TwoDimArray<CellState> cells) {
		super();
		this.m = m;
		this.defaultCell = defaultCell;
		this.cells = cells;
	}

	public CommonConfiguration(int m, CellState defaultCell) {
		super();
		this.m = m;
		this.defaultCell = defaultCell;
		this.cells = new TwoDimArray<>(0, m);
	}

	@Override
	public TwoDimArray<CellState> toArray() {
		return cells;
	}

	@Override
	public void setCell(int i, int j, CellState cell) {
		cells.set(i, j, cell);
	}

	@Override
	public CellState getCell(int i, int j) {
		return cells.get(i, j);
	}

	@Override
	public CellNeighborhood getNeighbors(int atI, int atJ) {
		CellNeighborhood neighborhood = new CellNeighborhood();

		for (int k = -1; k < +1; k++) {
			for (int l = -1; l < +1; l++) {
				int row = atI + k;
				int col = atJ + l;

				CellState cell;
				if (row < 0 || row > m) {
					cell = defaultCell();
				} else if (col < 0 || col > m) {
					cell = defaultCell();
				} else {
					cell = cells.get(row, col);
				}

				neighborhood.set(k, l, cell);
			}
		}

		return neighborhood;
	}

	@Override
	public CellState defaultCell() {
		return defaultCell;
	}

	@Override
	public CFAConfiguration cloneItself() {
		return new CommonConfiguration(m, defaultCell);
	}

}
