package cz.upol.feda.lingvar;

import cz.upol.fapapp.core.fuzzy.Degree;

public interface BaseLingVarLabel {
	
	public String getLabel();
	
	public Degree compute(LingVarValue value);

	
	public void setVariable(LingvisticVariable variable);

	public LingvisticVariable getVariable();

}
