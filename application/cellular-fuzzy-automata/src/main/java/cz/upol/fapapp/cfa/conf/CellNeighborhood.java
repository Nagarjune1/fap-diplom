package cz.upol.fapapp.cfa.conf;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArray;
import cz.upol.fapapp.cfa.misc.TwoDimArray.TwoDimArrForEach;

/**
 * Neihborhood of the cell. Represented as {@link TwoDimArray} from -1 to +1.
 * 
 * @author martin
 *
 */
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

	/**
	 * Gets cell at given position (both -1, 0 or +1).
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public CellState get(int i, int j) {
		return cells.get(i, j);
	}

	/**
	 * Sets at given position (both -1, 0 or +1).
	 * 
	 * @param i
	 * @param j
	 * @param cell
	 */
	public void set(int i, int j, CellState cell) {
		cells.set(i, j, cell);
	}

	/**************************************************************************/

	/**
	 * Returns count of elements in this neighbors (in fact 9 if with center, 8
	 * without).
	 * 
	 * @param withCenter
	 * @return
	 */
	public int count(boolean withCenter) {
		int size = cells.getSize();

		if (withCenter) {
			return size * size;
		} else {
			return size * size - 1;
		}
	}

	/**
	 * Computes sum of all cells inside.
	 * 
	 * @param withCenter
	 * @return
	 */
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

	/**
	 * Computes average value.
	 * 
	 * @param withCenter
	 * @return
	 */
	public double avg(boolean withCenter) {
		double sum = sum(withCenter);
		double count = (+2 - -1) * (+2 - -1) - (withCenter ? 0 : 1);

		return sum / count;
	}

	/**
	 * Runs given function for each cell.
	 * 
	 * @param function
	 */
	// TODO use me!
	public void forEach(TwoDimArrForEach<CellState> function) {
		cells.forEach(function);
	}

}
