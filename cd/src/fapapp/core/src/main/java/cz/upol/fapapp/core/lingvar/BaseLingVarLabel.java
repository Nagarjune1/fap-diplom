package cz.upol.fapapp.core.lingvar;

import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * General interace for {@link LingvisticVariable}'s label as well as it's
 * mapping function.
 * 
 * @author martin
 *
 */
public interface BaseLingVarLabel {

	/**
	 * Return the text label of this label.
	 * 
	 * @return
	 */
	public String getLabel();

	/**
	 * Compute degree of truth for given value.
	 * 
	 * @param value
	 * @return
	 */
	public Degree compute(LingVarValue value);

	/**
	 * Make this as label of given variable. The label should be asssigned to
	 * variable only once!
	 * 
	 * @param variable
	 */
	void setVariable(LingvisticVariable variable);

	/**
	 * Returns the variable this label belongs to.
	 * 
	 * @return
	 */
	public LingvisticVariable getVariable();

}
