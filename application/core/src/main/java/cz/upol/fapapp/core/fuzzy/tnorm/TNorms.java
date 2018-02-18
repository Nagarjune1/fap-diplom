package cz.upol.fapapp.core.fuzzy.tnorm;

/**
 * Static singleton configuration holding current {@link BaseTNorm}
 * implementation.
 * 
 * @author martin
 *
 */
public class TNorms {
	private static final BaseTNorm DEFAULT = new ProductTNorm();

	private static BaseTNorm tnorm = DEFAULT;

	/**
	 * Gets currently in-use t-norm/conorm implementation.
	 * 
	 * @return
	 * @see TNorms#setTnorm(BaseTNorm)
	 */
	public static BaseTNorm getTnorm() {
		return tnorm;
	}

	/**
	 * Changes the current t-norm/conorm implementation.
	 * 
	 * @param tnorm
	 */
	public static void setTnorm(BaseTNorm tnorm) {
		TNorms.tnorm = tnorm;
	}

}
