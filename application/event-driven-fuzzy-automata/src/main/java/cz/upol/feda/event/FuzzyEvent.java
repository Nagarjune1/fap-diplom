package cz.upol.feda.event;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.feda.lingvar.BaseLingVarLabel;
import cz.upol.feda.lingvar.LingVarValue;
import cz.upol.feda.lingvar.LingvisticVariable;

/**
 * Fuzzy event is list (in fact {@link Map}) of {@link LingvisticVariable}s and
 * their {@link LingVarValue}s.
 * 
 * @author martin
 *
 */
public class FuzzyEvent {

	private final Map<LingvisticVariable, LingVarValue> values;

	public FuzzyEvent(Map<LingvisticVariable, LingVarValue> values) {
		super();
		this.values = values;
	}

	public Map<LingvisticVariable, LingVarValue> getValuesMap() {
		return values;
	}

	public Set<LingvisticVariable> getVariables() {
		return values.keySet();
	}

	public Set<BaseLingVarLabel> getLabels() {
		return values.keySet().stream() //
				.flatMap((v) -> v.getLabels().stream()) //
				.collect(Collectors.toSet());
	}

	public LingVarValue valueOf(LingvisticVariable variable) {
		return values.get(variable);
	}

	public Degree degreeOf(BaseLingVarLabel label) {
		LingvisticVariable variable = label.getVariable();
		LingVarValue value = valueOf(variable);
		return label.compute(value);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		FuzzyEvent other = (FuzzyEvent) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FuzzyEvent [values=" + values + "]";
	}

}
