package cz.upol.fapapp.core.fuzzy.tnorm;

public class TNorms {
	private static final BaseTNorm DEFAULT = new ProductTNorm();
	
	private static BaseTNorm tnorm = DEFAULT;
	
	public static BaseTNorm getTnorm() {
		return tnorm;
	}
	
	public static void setTnorm(BaseTNorm tnorm) {
		TNorms.tnorm = tnorm;
	}
	
	
	
}
