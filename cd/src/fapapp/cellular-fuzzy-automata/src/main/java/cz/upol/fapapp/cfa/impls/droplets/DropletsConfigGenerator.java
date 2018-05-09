package cz.upol.fapapp.cfa.impls.droplets;

/**
 * Generator of various droplets configurations.
 * 
 * @author martin
 *
 */
public class DropletsConfigGenerator {

	public DropletsConfigGenerator() {
	}

	/**
	 * Generates configuration with given count of droplets valued form min to
	 * max.
	 * 
	 * @param size
	 * @param count
	 * @param seed
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public DropletsConfiguration generate(int size, int count, int seed, double minValue, double maxValue) {
		CommonDropletsGenerator generator = new CommonDropletsGenerator(count, seed, minValue, maxValue);
		return generate(size, generator);
	}

	/**
	 * Generates configuration with given count of droplets and min value of
	 * droplets (max is default, 1).
	 * 
	 * @param size
	 * @param count
	 * @param seed
	 * @param minValue
	 * @return
	 */
	public DropletsConfiguration generate(int size, int count, int seed, double minValue) {
		CommonDropletsGenerator generator = new CommonDropletsGenerator(count, seed, minValue);
		return generate(size, generator);
	}

	/**
	 * Generates configuration with given count of droplets ranged from 0 to 1.
	 * 
	 * @param size
	 * @param count
	 * @param seed
	 * @return
	 */
	public DropletsConfiguration generate(int size, int count, int seed) {
		CommonDropletsGenerator generator = new CommonDropletsGenerator(count, seed);
		return generate(size, generator);
	}

	/**
	 * Generates configuration with given generator.
	 * 
	 * @param size
	 * @param generator
	 * @return
	 */
	public DropletsConfiguration generate(int size, DropletsGenerator generator) {
		DropletsConfiguration config = new DropletsConfiguration(size);

		generator.generate(config);

		return config;
	}
}
