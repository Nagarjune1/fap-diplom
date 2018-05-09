package cz.upol.fapapp.cfa.impls.images;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomaton;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;
import cz.upol.fapapp.cfa.mu.SimpleTransitionFunction;
import cz.upol.fapapp.cfa.outers.CloningOuterSupplier;

/**
 * Cellular automata performing "My" BE-filter.
 * 
 * @author martin
 *
 */
public class MyBEFilterAutomaton extends CellularFuzzyAutomaton {

	public MyBEFilterAutomaton(int size, double epsilon) {
		super(size, //
				new MyBEFilterMu(epsilon), //
				new CloningOuterSupplier());
	}

	/**
	 * Transition function of "My" BE-filter.
	 * 
	 * @author martin
	 *
	 */
	public static class MyBEFilterMu extends SimpleTransitionFunction {

		private final double epsilon;

		public MyBEFilterMu(double epsilon) {
			this.epsilon = epsilon;
		}

		@Override
		public CellState computeNext(CellState cell, CellNeighborhood neighbors) {
			double val = cell.getValue();
			double neig = neighbors.avg(false);

			double newVal = myBrokenFunction(val, neig);

			return new CellState(newVal);
		}

		private double myBrokenFunction(double x, double y) {
			if (x == y) {
				return x;
			}

			boolean invert = (x > y);

			double zRaw;
			if (invert) {
				zRaw = epsilon * y - epsilon + 1.0;
			} else {
				zRaw = epsilon * y;
			}

			double z = Math.max(0.0, Math.min(1.0, zRaw));
			return z;
		}
	}

}
