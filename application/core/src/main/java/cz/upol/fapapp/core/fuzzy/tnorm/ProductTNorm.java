package cz.upol.fapapp.core.fuzzy.tnorm;

public class ProductTNorm extends SimpleTNorm {

	@Override
	protected double tnorm(double first, double second) {
		return first * second;
	}

	@Override
	protected double tconorm(double first, double second) {
		return first + second - first * second;
	}

}
