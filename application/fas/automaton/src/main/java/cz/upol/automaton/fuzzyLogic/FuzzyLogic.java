package cz.upol.automaton.fuzzyLogic;

import cz.upol.automaton.sets.Externisator;
import cz.upol.automaton.sets.HasExterernalRepresentation;

/**
 * TODO deprecated  fuzzy logic
 * @author martin
 */
public abstract class FuzzyLogic implements ResiduedLattice,
		HasExterernalRepresentation {

	/**
	 * @uml.property name="description"
	 */
	public abstract String getDescription();

	public abstract Externisator<Degree> getUniverseElementsExternisator();

	@Override
	public String externalize() {
		return getDescription();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getDescription() == null) ? 0 : getDescription().hashCode());
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
		FuzzyLogic other = (FuzzyLogic) obj;
		if (getDescription() == null) {
			if (other.getDescription() != null)
				return false;
		} else if (!getDescription().equals(other.getDescription()))
			return false;
		return true;
	}

}
