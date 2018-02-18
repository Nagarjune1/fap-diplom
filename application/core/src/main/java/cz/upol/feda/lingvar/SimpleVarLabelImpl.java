package cz.upol.feda.lingvar;

/**
 * Base class for common general lingvistic variable.
 * 
 * @author martin
 *
 */
public abstract class SimpleVarLabelImpl implements BaseLingVarLabel {

	private LingvisticVariable variable;
	protected final String label;

	public SimpleVarLabelImpl(String label) {
		super();
		this.variable = null;
		this.label = label;
	}

	public SimpleVarLabelImpl(LingvisticVariable variable, String label) {
		super();
		this.variable = variable;
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public LingvisticVariable getVariable() {
		return variable;
	}

	@Override
	public void setVariable(LingvisticVariable variable) {
		if (this.variable != null && variable != null) {
			throw new IllegalStateException("Label " + label + " is already assigned to " + this.variable.getName());
		}
		this.variable = variable;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleVarLabelImpl other = (SimpleVarLabelImpl) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (variable == null) {
			if (other.getVariable() != null)
				return false;
		} else if (!variable.equals(other.getVariable()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleVarLabelImpl [label=" + label + "]";
	}

}