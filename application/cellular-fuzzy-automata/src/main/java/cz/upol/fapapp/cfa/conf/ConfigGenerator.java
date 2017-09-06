package cz.upol.fapapp.cfa.conf;

import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import cz.upol.fapapp.cfa.automata.CellState;

public class ConfigGenerator {

	public CFAConfiguration generateFilled(int size, CellState cell) {
		return generate(size, //
				() -> cell.getValue());
	}

	public CFAConfiguration generateBival(int size, int seed, double ratio) {
		return generateRandom(size, seed, //
				(d) -> (d > ratio ? 1 : 0));
	}

	public CFAConfiguration generateDoubles(int size, int seed) {
		return generateRandom(size, seed, //
				(d) -> d);
	}

	public CFAConfiguration generateBidiag(int size) {
		return generate(size, //
				(i, j) -> ((i == j) || (size - i == j)) ? 1.0 : 0.0);
	}

	/**************************************************************************/

	private CFAConfiguration generate(int size, DoubleSupplier valueSupplier) {
		return generate(size, //
				(i, j) -> valueSupplier.getAsDouble());
	}

	private CFAConfiguration generateRandom(int size, int seed, DoubleUnaryOperator valueSupplier) {
		Random random = new Random(seed);

		return generate(size, //
				(i, j) -> valueSupplier.applyAsDouble(random.nextDouble()));
	}

	private CFAConfiguration generate(int size, ConfigsCoordsValueMapper mapper) {
		CFAConfiguration config = new CFAConfiguration(size);

		config.forEach((i, j, xValue) -> {
			double value = mapper.compute(i, j);
			CellState cell = new CellState(value);

			config.setCell(i, j, cell);
		});

		return config;
	}

	/**************************************************************************/

	@FunctionalInterface
	private static interface ConfigsCoordsValueMapper {
		public double compute(int i, int j);
	}
}
