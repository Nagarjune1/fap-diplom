package cz.upol.fapapp.fta.tree;

import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;

/**
 * Atomic tree (leaf node).
 * @author martin
 *
 */
public class AtomicTree extends BaseTree {

	public AtomicTree(Symbol leaf) {
		super(leaf);
	}


	/////////////////////////////////////////////////////////////////

	@Override
	public void validate(Alphabet nonterminals, Alphabet terminals) {
		if (!terminals.contains(this.getLabel())) {
			throw new IllegalArgumentException("Not a leaf (" + getLabel() + " is not in " + terminals + ")");
		}
	}

	/////////////////////////////////////////////////////////////////


	@Override
	public String toString() {
		return "AtomicTree [leaf=" + getLabel() + "]";
	}

}
