package cz.upol.fapapp.cfa.impls.images;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;
import cz.upol.fapapp.cfa.misc.MutableDouble;
import cz.upol.fapapp.cfa.mu.SimpleTransitionFunction;
import cz.upol.fapapp.cfa.outers.CloningOuterSupplier;

public class ConvolutionalCFA extends CellularFuzzyAutomata {

	public ConvolutionalCFA(int size, ConvolutionalCore core) {
		super(size, //
				new ConvolutionalMu(core), //
				new CloningOuterSupplier());
	}

	public static class ConvolutionalMu extends SimpleTransitionFunction {

		private final double coreSum;
		private final ConvolutionalCore core;

		public ConvolutionalMu(ConvolutionalCore core) {
			this.core = core;
			this.coreSum = sum(core);
		}

		private static double sum(ConvolutionalCore core) {
			MutableDouble sum = new MutableDouble(0.0);

			core.forEach(//
					(i, j, coef) -> sum.add(coef));

			return sum.getValue();
		}

		@Override
		public CellState computeNext(CellState cell, CellNeighborhood neig) {
			double value = cell.getValue();

			double newValue = applyCore(value, neig, core);

			return new CellState(newValue);
		}

		private double applyCore(double value, CellNeighborhood neighbors, ConvolutionalCore core) {
			MutableDouble sum = new MutableDouble(0.0);

			core.forEach((i, j, coef) -> {
				CellState neighCell = neighbors.get(i - 1, j - 1);
				double neighValue = neighCell.getValue();

				double val = neighValue * coef;
				sum.add(val);
			});

			return sum.getValue() / coreSum;
		}

	}

}
