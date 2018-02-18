package cz.upol.feda.lingvar;

import cz.upol.fapapp.core.fuzzy.Degree;

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
