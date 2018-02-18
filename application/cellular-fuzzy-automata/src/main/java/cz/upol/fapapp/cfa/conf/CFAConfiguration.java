package cz.upol.fapapp.cfa.conf;

import java.io.PrintStream;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArray;
import cz.upol.fapapp.cfa.misc.TwoDimArray.TwoDimArrForEach;
import cz.upol.fapapp.cfa.outers.CFAOuterCellSupplier;
import cz.upol.fapapp.core.misc.Printable;

/**
 * Configuration of cellular fuzzy automat. In fact {@link TwoDimArray} of {@link CellState} of size m.
 * @author martin
 *
 */
public class CFAConfiguration implements Printable {

	private final int m;
	private final TwoDimArray<CellState> cells;

	public CFAConfiguration(int m, TwoDimArray<CellState> cells) {
		super();
		this.m = m;
		this.cells = cells;
	}

	public CFAConfiguration(int m, CellState dflt) {
		super();
		this.m = m;
		this.cells = new TwoDimArray<>(0, m, dflt);
	}

	public CFAConfiguration(int m) {
		super();
		this.m = m;
		this.cells = new TwoDimArray<>(0, m, new CellState(0.0));
	}

	/*************************************************************************/

	public int getSize() {
		return m;
	}

	public TwoDimArray<CellState> toArray() {
		return cells;
	}

	public void setCell(int i, int j, CellState cell) {
		cells.set(i, j, cell);
	}

	public CellState getCell(int i, int j) {
		return cells.get(i, j);
	}

	/*************************************************************************/

	public CFAConfiguration cloneItself() {
		return new CFAConfiguration(m);
	}

	public CellNeighborhood getNeighbors(int atI, int atJ, CFAOuterCellSupplier outerSupplier) {
		CellNeighborhood neighborhood = new CellNeighborhood();

		for (int k = -1; k <= +1; k++) {
			for (int l = -1; l <= +1; l++) {
				int row = atI + k;
				int col = atJ + l;

				CellState cell;
				if (row < 0 || row >= m) {
					cell = outerSupplier.computeOuterCell(row, col, this);
				} else if (col < 0 || col >= m) {
					cell = outerSupplier.computeOuterCell(row, col, this);
				} else {
					cell = cells.get(row, col);
				}

				neighborhood.set(k, l, cell);
			}
		}

		return neighborhood;
	}

	/*************************************************************************/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
		result = prime * result + m;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CFAConfiguration other = (CFAConfiguration) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		if (m != other.m)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommonConfiguration [m=" + m + ", cells="
				+ (cells.toString().length() > 50 ? (cells.toString().substring(0, 50) + "...") : cells.toString())
				+ "]";
	}

	/*************************************************************************/

	@Override
	public void print(PrintStream to) {
		Printable.print(to, new CFAConfTIMComposer(), this);
	}

	// TODO use me!
	public void forEach(TwoDimArrForEach<CellState> function) {
		cells.forEach(function);
	}

}
