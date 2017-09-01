package cz.upol.fapapp.cfa.mu;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.comp.CFAConfiguration;
import cz.upol.fapapp.cfa.comp.CellNeighborhood;

public class BivalGameOfLifeImpl implements CFATransitionFunction, CFAOuterCellSupplier {

	public static final CellState ALIVE = new CellState(1.0);
	public static final CellState DEAD = new CellState(0.0);

	public BivalGameOfLifeImpl() {
	}

	/*************************************************************************/

	@Override
	public CellState perform(int i, int j, CellState cell, CellNeighborhood neig) {
		double sum = neig.sum() - cell.getValue();

		if (ALIVE.equals(cell) && sum < 2) {
			return DEAD;
		}

		if (ALIVE.equals(cell) && sum > 3) {
			return DEAD;
		}

		if (DEAD.equals(cell) && sum == 3) {
			return ALIVE;
		}

		return cell;
	}

	@Override
	public CellState computeOuterCell(int i, int j, CFAConfiguration config) {
		return DEAD;
	}

	/*************************************************************************/

	@Override
	public int hashCode() {
		int result = 1;
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
		return true;
	}

	@Override
	public String toString() {
		return "BivalGameOfLifeImpl[]";
	}

}
