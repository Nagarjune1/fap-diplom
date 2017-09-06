package cz.upol.fapapp.cfa.impls.common;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;
import cz.upol.fapapp.cfa.mu.SimpleTransitionFunction;
import cz.upol.fapapp.cfa.outers.ConstantOuterSupplier;

public class BivalentGameOfLifeCFA extends CellularFuzzyAutomata {

	public static final CellState ALIVE = new CellState(1.0);
	public static final CellState DEAD = new CellState(0.0);

	public BivalentGameOfLifeCFA(int size) {
		super(size, //
				new BivalGameOfLifeMu(), //
				new ConstantOuterSupplier(DEAD));
	}

	/*************************************************************************/

	public static class BivalGameOfLifeMu extends SimpleTransitionFunction {

		@Override
		public CellState computeNext(CellState cell, CellNeighborhood neig) {
			double sum = neig.sum(false);

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
	}
}