package cz.upol.fapapp.core.fuzzy.tnorm;

/**
 * Implements t-norm and t-conorm as min and max respectivelly.
 * @author martin
 *
 */
public class GodelTNorm extends SimpleTNorm {

	@Override
	protected double tnorm(double first, double second) {
		return Math.min(first, second);
	}

	@Override
	protected double tconorm(double first, double second) {
		return Math.max(first, second);
	}

}
