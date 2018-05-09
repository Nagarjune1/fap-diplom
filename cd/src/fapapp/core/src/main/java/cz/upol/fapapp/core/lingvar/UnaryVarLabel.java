package cz.upol.fapapp.core.lingvar;

import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * Represents "unary" lingvistic variable label. This label is always true, i.e. whatever the value is, it has always degree {@link Degree#ONE}.
 * @author martin
 *
 */
public class UnaryVarLabel extends SimpleVarLabelImpl {

	public UnaryVarLabel(String label) {
		super(label);
	}

	public UnaryVarLabel(LingvisticVariable variable, String label) {
		super(variable, label);
	}

	@Override
	public Degree compute(LingVarValue value) {
		// the value here is unneccessary
		return Degree.ONE;
	}

}
