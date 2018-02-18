package cz.upol.fapapp.core.fuzzy.tnorm;

/**
 * Implements t-norm and t-conorm as x * y and (x + y) - (x * y) respectivelly.
 * 
 * @author martin
 *
 */
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
