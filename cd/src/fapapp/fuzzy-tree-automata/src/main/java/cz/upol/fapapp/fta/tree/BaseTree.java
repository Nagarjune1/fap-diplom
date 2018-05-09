package cz.upol.fapapp.fta.tree;

import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;

/**
 * General structure for tree node.
 * @author martin
 *
 */
public abstract class BaseTree {
	private final Symbol label;

	public BaseTree(Symbol label) {
		super();
		this.label = label;
	}

	public Symbol getLabel() {
		return label;
	}

	///////////////////////////////////////////////////////////////////////////

	public abstract void validate(Alphabet nonterminals, Alphabet terminals);
	///////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		BaseTree other = (BaseTree) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseTree [label=" + label + "]";
	}

	///////////////////////////////////////////////////////////////////////////

}
