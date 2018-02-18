package cz.upol.feda.lingvar;

import java.util.Collection;
import java.util.Set;

import cz.upol.fapapp.core.sets.CollectionsUtils;

public class LingvisticVariable {
	private final String name;
	private final Set<BaseLingVarLabel> labels;

	public LingvisticVariable(String name, Set<BaseLingVarLabel> labels) {
		super();
		this.name = name;
		this.labels = labels;
		setLabelsOfThisVariable(labels);
	}

	private void setLabelsOfThisVariable(Set<BaseLingVarLabel> labels) {
		labels.forEach((l) -> l.setVariable(this));
	}

	public LingvisticVariable(String name, BaseLingVarLabel... labels) {
		this(name, CollectionsUtils.toSet(labels));
	}

	public String getName() {
		return name;
	}

	public Set<BaseLingVarLabel> getLabels() {
		return labels;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public BaseLingVarLabel getLabel(String label) {
		return labels.stream() //
				.filter((l) -> l.getLabel().equals(label)) //
				.findAny().orElseThrow( //
						() -> new IllegalArgumentException("LingVarLabel " + name + " does not exist in " + name));
	}

	public static LingvisticVariable getVariable(String name, Collection<LingvisticVariable> variables) {
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
