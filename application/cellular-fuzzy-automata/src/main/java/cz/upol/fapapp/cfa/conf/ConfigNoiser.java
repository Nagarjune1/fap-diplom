package cz.upol.fapapp.cfa.conf;

import java.util.Random;
import java.util.function.ToDoubleBiFunction;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.core.misc.Logger;

public class ConfigNoiser {

	public ConfigNoiser() {
	}

	public void addNoiseSaltAndPepper(CFAConfiguration config, int seed, double ratio, double whitesRatio) {
		Logger.get().moreinfo("Generating salt and pepper noise with seed " + seed + ", ratio " + ratio
				+ " and whitesRatio " + whitesRatio);

		addNoise(config, seed, ratio, //
				(c, r) -> (r.nextDouble() < whitesRatio ? 1.0 : 0.0));//
	}

	public void addImpulseNoise(CFAConfiguration config, int seed, double ratio) {
		Logger.get().moreinfo("Generating impulse noise with seed " + seed + ", ratio " + ratio);

		addNoise(config, seed, ratio, //
				(c, r) -> r.nextDouble());//
	}

	public void addGeneralNoise(CFAConfiguration config, int seed, double ratio,
			ToDoubleBiFunction<CellState, Random> valuesProvider) {

		Logger.get().moreinfo(
				"Generating general noise with seed " + seed + ", ratio " + ratio + " and provider: " + valuesProvider);

		addNoise(config, seed, ratio, //
				valuesProvider);//

	}

	private void addNoise(CFAConfiguration config, int seed, double ratio,
			ToDoubleBiFunction<CellState, Random> valuesProvider) {
		int count = computeCount(config, ratio);

		Random random = new Random(seed);

		for (int i = 0; i < count; i++) {
			addNoisedCell(config, random, valuesProvider);
		}

	}

	protected int computeCount(CFAConfiguration config, double ratio) {
		int countOfCells = config.getSize() * config.getSize();
		int countOfNoises = (int) (countOfCells * ratio);

		return countOfNoises;
	}

	private void addNoisedCell(CFAConfiguration config, Random random,
			ToDoubleBiFunction<CellState, Random> valuesProvider) {

		int size = config.getSize();

		int i = random.nextInt(size);
		int j = random.nextInt(size);

		CellState currentCell = config.getCell(i, j);

		double value = valuesProvider.applyAsDouble(currentCell, random);

		CellState newCell = new CellState(value);
		config.setCell(i, j, newCell);
	}
}
