package cz.upol.feda.lingvar;

import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * Linggvistic variable label using tuple of linear functions. The function works as follows:
 * <pre>
 * degree
 * 1 +        +---------+
 *   |       /.         .\
 *   |      / .         . \  
 *   |     /  .         .  \      
 *   |    /   .         .   \
 *   |   /    .         .    \
 * 0 +==+-----+---------+-----+======> value
 *    start  top   decrease finish
 *
 * </pre>
 * @author martin
 *
 */
public class LinearLingVarLabel extends SimpleVarLabelImpl {

	private final LingVarValue activationStart;
	private final LingVarValue activationTop;
	private final LingVarValue activationDecrease;
	private final LingVarValue activationFinish;

	public LinearLingVarLabel(String label, LingVarValue activationStart, LingVarValue activationTop,
			LingVarValue activationDecrease, LingVarValue activationFinish) {
		super(label);

		this.activationStart = activationStart;
		this.activationTop = activationTop;
		this.activationDecrease = activationDecrease;
		this.activationFinish = activationFinish;
	}

	// /////////////////////////////////////////////////////////////////////////

	public LingVarValue getActivationStart() {
		return activationStart;
	}

	public LingVarValue getActivationTop() {
		return activationTop;
	}

	public LingVarValue getActivationDecrease() {
		return activationDecrease;
	}

	public LingVarValue getActivationFinish() {
		return activationFinish;
	}
	
	// /////////////////////////////////////////////////////////////////////////


	@Override
	public Degree compute(LingVarValue value) {
		return compute(activationStart.getValue(), activationTop.getValue(), //
				activationDecrease.getValue(), activationFinish.getValue(), //
				value.getValue());
	}

	private Degree compute(double activationStart, double activationTop, double activationDecrease,
			double activationFinish, double value) {

		if (value <= activationStart || value >= activationFinish) {
			return Degree.ZERO;
		}

		if (value >= activationTop && value <= activationDecrease) {
			return Degree.ONE;
		}

		if (value > activationStart && value < activationTop) {
			return compute(activationStart, activationTop, value);
		}

		if (value > activationDecrease && value < activationFinish) {
			return compute(activationFinish, activationDecrease, value);
		}

		return null;
	}

	protected static Degree compute(double valueOfBottom, double valueOfTop, double value) {

		double angle = 1.0 / (valueOfTop - valueOfBottom);
		double offset = -angle * valueOfBottom;
		double result = angle * value + offset;

		return new Degree(result);
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "SimpleLingVarLabel [label=" + label + ", _ " + activationStart + " / " + activationTop + " - "
				+ activationDecrease + " \\ " + activationFinish + "_]";
	}

}
