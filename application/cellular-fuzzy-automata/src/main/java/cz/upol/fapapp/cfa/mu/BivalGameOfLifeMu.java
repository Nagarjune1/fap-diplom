package cz.upol.fapapp.cfa.mu;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.config.CellNeighborhood;

public class BivalGameOfLifeMu implements CFATransitionFunction {

	public static final CellState ALIVE = new CellState(1.0);
	public static final CellState DEAD = new CellState(0.0);

	public BivalGameOfLifeMu() {
	}

	@Override
	public CellState perform(int i, int j, CellState cell, CellNeighborhood neig) {
		double sum = neig.sum();

		if (ALIVE.equals(cell) && sum < 2) {
			return DEAD;
		}

		if (ALIVE.equals(cell) && sum > 3) {
			return DEAD;
		}

		if (DEAD.equals(cell) && sum == 4) {
			return ALIVE;
		}

		return cell;
	}

}
