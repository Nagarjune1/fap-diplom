package cz.upol.fapapp.core.fuzzy.tnorm;

import cz.upol.fapapp.core.fuzzy.Degree;

public interface BaseTNorm {
	public Degree tnorm(Degree first, Degree second);
	public Degree tconorm(Degree first, Degree second);
}
