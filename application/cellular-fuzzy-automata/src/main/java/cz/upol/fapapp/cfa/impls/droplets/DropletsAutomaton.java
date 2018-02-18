package cz.upol.fapapp.cfa.impls.droplets;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomaton;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;
import cz.upol.fapapp.cfa.mu.PowerfullTransitionFunction;
import cz.upol.fapapp.cfa.outers.CloningOuterSupplier;

/**
 * Automata performing droplets simulation. Based on
 * {@link https://sanojian.github.io/cellauto/}
 * 
 * @author martin
 *
 */
public class DropletsAutomaton extends CellularFuzzyAutomaton {

	public DropletsAutomaton(int size, DropletsGenerator generator) {
		super(size, //
				new DropletsMu(generator), //
				new CloningOuterSupplier());
	}

	/**
	 * The transition function of {@link DropletsAutomaton}.
	 * 
	 * @author martin
	 *
	 */
	public static class DropletsMu extends PowerfullTransitionFunction {

		private DropletsGenerator generator;

		public DropletsMu(DropletsGenerator generator) {
			this.generator = generator;
		}

		@Override
		public void beforeNextGeneration(CFAConfiguration config) {
			DropletsConfiguration cfg = (DropletsConfiguration) config;

			config.forEach((i, j, c) -> {
				cfg.setPrev(i, j, cfg.getCell(i, j));
				cfg.setCell(i, j, cfg.getNext(i, j));
			});

			generator.generate(cfg);
		}

		@Override
		public void process(int i, int j, CellState cell, CellNeighborhood neig, CFAConfiguration config) {
			DropletsConfiguration cfg = (DropletsConfiguration) config;
			CellState prev = cfg.getPrev(i, j);

			if (cfg.isDroplet(i, j)) {

				neig.forEach((ni, nj, nc) -> {
					if (ni != 0 && nj != 0) {
						int neigI = i + ni;
						int neigJ = j + nj;
						if (isOutOfBounds(neigI, neigJ, config.getSize())) {
							return;
						}
						cfg.setCell(neigI, neigJ, decreaseCellValue(cell));
						cfg.setPrev(neigI, neigJ, decreaseCellValue(prev));
					}
				});

				cfg.setDroplet(i, j, false);
			}

			CellState nextCell = computeNextCell(i, j, neig, prev);
			cfg.setNext(i, j, nextCell);
		}

		/*************************************************************************/

		private static CellState computeNextCell(int i, int j, CellNeighborhood neig, CellState prev) {
			double avg = neig.avg(false);
			double newNextRaw = 0.99 * (2 * avg - prev.getValue());
			double newNext = normalize(newNextRaw);
			return new CellState(newNext);
		}

		private static CellState decreaseCellValue(CellState cell) {
			double value = cell.getValue();
			double half = value / 1.1; // TODO put back original 2.0
			return new CellState(half);
		}

		/*************************************************************************/

		private static boolean isOutOfBounds(int i, int j, int size) {
			return (i < 0 || i >= size) || (j < 0 || j >= size);
		}

		protected static double normalize(double raw) {
			return Math.max(0, Math.min(1, raw));
		}

	}
}
