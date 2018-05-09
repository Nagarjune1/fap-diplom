package cz.upol.fapapp.core.fuzzy.tnorm;

import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * Implements t-norm and t-conorm as max(0, x + y - 1) and min(x + y, 0).
 * @author martin
 *
 */
public class LukasiewiczTNorm extends SimpleTNorm {

	private static final double ZERO = Degree.ZERO.getValue();
	private static final double ONE = Degree.ONE.getValue();

	@Override
	protected double tnorm(double first, double second) {
		return Math.max(ZERO, first + second - ONE);
	}

	@Override
	protected double tconorm(double first, double second) {
		return Math.min(first + second, ONE);
	}

}
