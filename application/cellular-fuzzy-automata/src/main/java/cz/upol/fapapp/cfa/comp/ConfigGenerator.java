package cz.upol.fapapp.cfa.comp;

import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import cz.upol.fapapp.cfa.automata.CellState;

public class ConfigGenerator {

	public CommonConfiguration generateFilled(int size, CellState cell) {
		return generate(size, //
				() -> cell.getValue());
	}

	public CommonConfiguration generateBival(int size, int seed, double ratio) {
		return generateRandom(size, seed, //
				(d) -> (d > ratio ? 1 : 0));
	}

	public CommonConfiguration generateDoubles(int size, int seed) {
		return generateRandom(size, seed, //
				(d) -> d);
	}

	public CommonConfiguration generateBidiag(int size) {
		return generate(size, //
				(i, j) -> ((i == j) || (size - i == j)) ? 1.0 : 0.0);
	}

	/**************************************************************************/

	private CommonConfiguration generate(int size, DoubleSupplier valueSupplier) {
		return generate(size, //
				(i, j) -> valueSupplier.getAsDouble());
	}

	private CommonConfiguration generateRandom(int size, int seed, DoubleUnaryOperator valueSupplier) {
		Random random = new Random(seed);

		return generate(size, //
				(i, j) -> valueSupplier.applyAsDouble(random.nextDouble()));
	}

	private CommonConfiguration generate(int size, ConfigsCoordsValueMapper mapper) {
		CommonConfiguration config = new CommonConfiguration(size);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				double value = mapper.compute(i, j);
				CellState cell = new CellState(value);

				config.setCell(i, j, cell);
			}
		}

		return config;
	}

	/**************************************************************************/

	@FunctionalInterface
	private static interface ConfigsCoordsValueMapper {
		public double compute(int i, int j);
	}
}
