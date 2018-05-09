package cz.upol.fapapp.cfa.conf;

import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.core.misc.Logger;

/**
 * Generator for {@link CFAConfiguration}s.
 * 
 * @author martin
 *
 */
public class ConfigGenerator {

	/**
	 * Generates configuration of given size filled with given cell.
	 * 
	 * @param size
	 * @param cell
	 * @return
	 */
	public CFAConfiguration generateFilled(int size, CellState cell) {
		return generate(size, //
				() -> cell.getValue());
	}

	/**
	 * Generates configuration of given size filled randomly with 0 a 1.
	 * 
	 * @param size
	 * @param seed
	 * @param ratio
	 *            number of ones to number of zeros
	 * @return
	 */
	public CFAConfiguration generateBival(int size, int seed, double ratio) {
		Logger.get().debug("Generating bivalent config of size " + size + ", seed " + seed + " and ratio " + ratio);
		return generateRandom(size, seed, //
				(d) -> (d > ratio ? 1 : 0));
	}

	/**
	 * Generates configuration of given size filled with random numbers.
	 * 
	 * @param size
	 * @param seed
	 * @return
	 */
	public CFAConfiguration generateDoubles(int size, int seed) {
		Logger.get().debug("Generating config with doubles of size " + size + " with seed " + seed);
		return generateRandom(size, seed, //
				(d) -> d);
	}

	/**
	 * Generates configuration of given size with bidiagonal filled by ones and
	 * everythind else with zeros.
	 * 
	 * @param size
	 * @return
	 */
	public CFAConfiguration generateBidiag(int size) {
		Logger.get().debug("Generating bidiagonal config of " + size);
		return generate(size, //
				(i, j) -> ((i == j) || (size - i == j)) ? 1.0 : 0.0);
	}

	
	////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Generates configuration of given size with given supplier.
	 * 
	 * @param size
	 * @param valueSupplier
	 * @return
	 */
	private CFAConfiguration generate(int size, DoubleSupplier valueSupplier) {
		return generate(size, //
				(i, j) -> valueSupplier.getAsDouble());
	}

	/**
	 * Generates configuration with given random operator.
	 * 
	 * @param size
	 * @param seed
	 * @param valueSupplier
	 * @return
	 */
	private CFAConfiguration generateRandom(int size, int seed, DoubleUnaryOperator valueSupplier) {
		Random random = new Random(seed);

		return generate(size, //
				(i, j) -> valueSupplier.applyAsDouble(random.nextDouble()));
	}

	/**
	 * Generates configuration with given coords mapper.
	 * 
	 * @param size
	 * @param mapper
	 * @return
	 */
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

	/**
	 * Function mapping coordinates to double.
	 * 
	 * @author martin
	 *
	 */
	@FunctionalInterface
	private static interface ConfigsCoordsValueMapper {
		public double compute(int i, int j);
	}
}
