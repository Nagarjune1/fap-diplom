package cz.upol.fapapp.cfa.impls.droplets;

public class DropletsConfigGenerator {

	public DropletsConfigGenerator() {
	}

	public DropletsConfiguration generate(int size, int count, int seed, double minValue, double maxValue) {
		CommonDropletsGenerator generator = new CommonDropletsGenerator(count, seed, minValue, maxValue);
		return generate(size, generator);
	}

	public DropletsConfiguration generate(int size, int count, int seed, double minValue) {
		CommonDropletsGenerator generator = new CommonDropletsGenerator(count, seed, minValue);
		return generate(size, generator);
	}

	public DropletsConfiguration generate(int size, int count, int seed) {
		CommonDropletsGenerator generator = new CommonDropletsGenerator(count, seed);
		return generate(size, generator);
	}

	public DropletsConfiguration generate(int size, DropletsGenerator generator) {
		DropletsConfiguration config = new DropletsConfiguration(size);

		generator.generate(config);

		return config;
	}
}
