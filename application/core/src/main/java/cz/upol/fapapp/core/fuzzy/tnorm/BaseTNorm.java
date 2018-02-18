package cz.upol.fapapp.core.fuzzy.tnorm;

import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * Specifies general t-norm and t-conorm (i.e. methods
 * {@link #tnorm(Degree, Degree)} and {@link #tconorm(Degree, Degree)}) of
 * degrees.
 * 
 * @author martin
 *
 */
public interface BaseTNorm {
	public Degree tnorm(Degree first, Degree second);

	public Degree tconorm(Degree first, Degree second);
}
