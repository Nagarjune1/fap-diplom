package cz.upol.fapapp.core.lingvar;

import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * Implements {@link BaseLingVarLabel} (in fact extends
 * {@link SimpleVarLabelImpl}) using Gaussian function (in fact two of them, one
 * for values smaller than center, another for the grater).
 * 
 * The parameters (left, right)sigma are the sigma parameters of the Gaussian
 * function.
 * 
 * @author martin
 *
 */
public class GaussianLingVarLabel extends SimpleVarLabelImpl {

	private final LingVarValue center;
	private final LingVarValue leftSigma;
	private final LingVarValue rightSigma;

	public GaussianLingVarLabel(String label, LingVarValue center, LingVarValue leftSigma, LingVarValue rightSigma) {
		super(label);

		this.center = center;
		this.leftSigma = leftSigma;
		this.rightSigma = rightSigma;
	}

	public GaussianLingVarLabel(String label, LingVarValue center, LingVarValue sigma) {
		this(label, center, //
				new LingVarValue(center.getValue() - sigma.getValue()), //
				new LingVarValue(center.getValue() + sigma.getValue())); //
	}

	public GaussianLingVarLabel(String label, LingVarValue sigma) {
		this(label, new LingVarValue(0.0), sigma);
	}

	public LingVarValue getCenter() {
		return center;
	}

	public LingVarValue getLeftSigma() {
		return leftSigma;
	}

	public LingVarValue getRightSigma() {
		return rightSigma;
	}

	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public Degree compute(LingVarValue value) {
		double val = value.getValue();
		double centered = val - center.getValue();

		double degree = 0.0;

		if (centered > 0) {
			degree = computePositiveGaussHalf(centered, rightSigma.getValue());
		}
		if (centered < 0) {
			degree = computePositiveGaussHalf(-centered, leftSigma.getValue());
		}
		if (centered == 0) {
			degree = Degree.ONE.getValue();
		}

		return new Degree(degree);
	}

	protected static double computePositiveGaussHalf(double value, double sigma) {
		double result = Math.exp( //
				-((value * value) //
						/ (2 * sigma * sigma)));
		return result;
	}

	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((center == null) ? 0 : center.hashCode());
		result = prime * result + ((leftSigma == null) ? 0 : leftSigma.hashCode());
		result = prime * result + ((rightSigma == null) ? 0 : rightSigma.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GaussianLingVarLabel other = (GaussianLingVarLabel) obj;
		if (center == null) {
			if (other.center != null)
				return false;
		} else if (!center.equals(other.center))
			return false;
		if (leftSigma == null) {
			if (other.leftSigma != null)
				return false;
		} else if (!leftSigma.equals(other.leftSigma))
			return false;
		if (rightSigma == null) {
			if (other.rightSigma != null)
				return false;
		} else if (!rightSigma.equals(other.rightSigma))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GaussianVarLabel[" + getLabel() + " _" + leftSigma + "/" + center + "\\" + rightSigma + "_]";
	}

}
