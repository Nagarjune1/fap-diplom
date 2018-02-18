package cz.upol.feda.lingvar;

import java.util.Collection;
import java.util.Set;

import cz.upol.fapapp.core.misc.CollectionsUtils;

/**
 * Ligvistic variable is human-to-fuzzy interaction. Is defined by name and
 * labels (bulk of {@link BaseLingVarLabel}).
 * 
 * @author martin
 *
 */
public class LingvisticVariable {
	private final String name;
	private final Set<BaseLingVarLabel> labels;

	public LingvisticVariable(String name, Set<BaseLingVarLabel> labels) {
		super();
		this.name = name;
		this.labels = labels;
		setLabelsOfThisVariable(labels);
	}

	public LingvisticVariable(String name, BaseLingVarLabel... labels) {
		this(name, CollectionsUtils.toSet(labels));
	}

	/////////////////////////////////////////////////////////////////////////////

	/**
	 * To each of given labels sets its variable to this instance.
	 * 
	 * @param labels
	 */
	private void setLabelsOfThisVariable(Set<BaseLingVarLabel> labels) {
		labels.forEach((l) -> l.setVariable(this));
	}

	public String getName() {
		return name;
	}

	public Set<BaseLingVarLabel> getLabels() {
		return labels;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Finds label of given name in this variable. If not found, throws
	 * exception.
	 * 
	 * @param label
	 * @return
	 */
	public BaseLingVarLabel getLabel(String label) throws IllegalArgumentException {
		return labels.stream() //
				.filter((l) -> l.getLabel().equals(label)) //
				.findAny().orElseThrow( //
						() -> new IllegalArgumentException("LingVarLabel " + name + " does not exist in " + name));
	}

	/**
	 * Finds variable of given name in given set of variables. If not found,
	 * throws exception.
	 * 
	 * @param name
	 * @param variables
	 * @return
	 */
	public static LingvisticVariable getVariable(String name, Collection<LingvisticVariable> variables)
			throws IllegalArgumentException {
		return variables.stream() //
				.filter((v) -> v.getName().equals(name)) //
				.findAny().orElseThrow( //
						() -> new IllegalArgumentException("LingVar " + name + " does not exist"));
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		LingvisticVariable other = (LingvisticVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LingvisticVariable [name=" + name + ", labels=" + labels.stream().map((l) -> l.getLabel()) + "]";
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
