package cz.upol.fapapp.core.fuzzy.tnorm;

import cz.upol.fapapp.core.fuzzy.Degree;

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
