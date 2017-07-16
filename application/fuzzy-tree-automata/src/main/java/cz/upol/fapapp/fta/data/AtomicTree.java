package cz.upol.fapapp.fta.data;

import cz.upol.fapapp.core.ling.Symbol;

public class AtomicTree extends BaseTree {

	public AtomicTree(Symbol leaf) {
		super(leaf);
	}

	@Override
	public String toString() {
		return "AtomicTree [leaf=" + getLabel() + "]";
	}

}
