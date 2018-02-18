package cz.upol.fapapp.cfa.impls.droplets;

import java.util.Random;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.core.misc.Logger;

/**
 * Common generator of droplets. Generates random droplets, with optionally
 * specified min and max values.
 * 
 * @author martin
 *
 */
public class CommonDropletsGenerator implements DropletsGenerator {

	private final int count;
	private final Random random;
	private final double minValue;
	private final double maxValue;

	public CommonDropletsGenerator(int count, int seed, double minValue, double maxValue) {
		this.count = count;
		this.random = new Random(seed);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public CommonDropletsGenerator(int count, int seed, double minValue) {
		this(count, seed, minValue, 1.0);
	}

	public CommonDropletsGenerator(int count, int seed) {
		this(count, seed, 0.0, 1.0);
	}

	/*************************************************************************/

	@Override
	public void generate(DropletsConfiguration config) {
		for (int i = 0; i < count; i++) {
			generateOneDroplet(config);
		}
	}

	/*************************************************************************/
	private void generateOneDroplet(DropletsConfiguration config) {

		int i = random.nextInt(config.getSize());
		int j = random.nextInt(config.getSize());

		CellState cell = generateDropletCell();

		Logger.get().moreinfo("Setting droplet at " + i + " " + j + " with value " + cell.getValue());

		config.setCell(i, j, cell);
		config.setDroplet(i, j, true);
	}

	private CellState generateDropletCell() {
		double value = generateDropletValue();

		return new CellState(value);
	}

	protected double generateDropletValue() {
		double rand = random.nextDouble();

		return (maxValue - minValue) * rand + minValue;
	}

}
