package cz.upol.fapapp.cfa.impls.images;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;
import cz.upol.fapapp.cfa.misc.MutableDouble;
import cz.upol.fapapp.cfa.mu.SimpleTransitionFunction;
import cz.upol.fapapp.cfa.outers.CloningOuterSupplier;

/**
 * Based on SahUguSah-SalPepNoiFilFuzCelAut, simplified.
 * 
 * @author martin
 *
 */
public class NoiseReductionAutomata extends CellularFuzzyAutomata {

	public NoiseReductionAutomata(int size) {
		super(size, //
				new NoiseReductionMu(), //
				new CloningOuterSupplier());
	}

	public static class NoiseReductionMu extends SimpleTransitionFunction {

		@Override
		public CellState computeNext(CellState cell, CellNeighborhood neig) {

			if (isNoised(cell, neig)) {
				return cell;
			} else {
				return generateNewCell(neig);
			}
		}

		private boolean isNoised(CellState cell, CellNeighborhood neig) {

			double min = computeMaxOrMin(cell, neig, false);
			double max = computeMaxOrMin(cell, neig, true);
			double cellValue = cell.getValue();

			return (min < cellValue && cellValue < max);
		}

		private CellState generateNewCell(CellNeighborhood neig) {
			double newCellValue = neig.avg(false);
			return new CellState(newCellValue);
		}

		private double computeMaxOrMin(CellState center, CellNeighborhood neighbors, boolean isMax) {

			double initVal = neighbors.get(-1, -1).getValue();
			MutableDouble current = new MutableDouble(initVal);

			neighbors.forEach((i, j, neigh) -> {
				if (i == 0 && j == 0) {
					return;
				}

				if (isMax && neigh.getValue() > current.getValue()) {
					current.setValue(neigh.getValue());
				}
				if (!isMax && neigh.getValue() < current.getValue()) {
					current.setValue(neigh.getValue());
				}
			});

			return current.getValue();
		}

	}

}
