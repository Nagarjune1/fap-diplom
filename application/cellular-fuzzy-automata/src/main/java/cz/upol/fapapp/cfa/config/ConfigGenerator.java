package cz.upol.fapapp.cfa.config;

import java.util.Random;
import java.util.function.Function;

import cz.upol.fapapp.cfa.automata.CellState;

public class ConfigGenerator {

	public CommonConfiguration generateZeros(int size, CellState defaultCell) {
		return generate(size, defaultCell, 0, //
				(r) -> 0.0);	//TODO fixme
	}

	
	public CommonConfiguration generateBival(int size, CellState defaultCell, int seed) {
		return generate(size, defaultCell, seed, //
				(r) -> (double) r.nextInt(2));
	}

	public CommonConfiguration generateDoubles(int size, CellState defaultCell, int seed) {
		return generate(size, defaultCell, seed, //
				(r) -> r.nextDouble());
	}

	/**************************************************************************/

	private CommonConfiguration generate(int size, CellState defaultCell, int seed,
			Function<Random, Double> valueSupplier) {
		
		CommonConfiguration config = new CommonConfiguration(size, defaultCell);
		Random random = new Random(seed);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				double value = valueSupplier.apply(random);
				CellState cell = new CellState(value);

				config.setCell(i, j, cell);
			}
		}

		return config;
	}
}
