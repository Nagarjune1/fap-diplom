package cz.upol.fapapp.core.fuzzy.tnorm;

import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * Base abstract class for t-norm and t-conorm computations. Repacks abstract
 * methods to compute not with {@link Degree}, but with plain doubles.
 * 
 * @author martin
 *
 */
public abstract class SimpleTNorm implements BaseTNorm {

	@Override
	public Degree tnorm(Degree first, Degree second) {
		return new Degree(tnorm(first.getValue(), second.getValue()));
	}

	protected abstract double tnorm(double first, double second);

	@Override
	public Degree tconorm(Degree first, Degree second) {
		return new Degree(tconorm(first.getValue(), second.getValue()));
	}

	protected abstract double tconorm(double first, double second);

}
