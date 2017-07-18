package cz.upol.fapapp.fta.tree;

import cz.upol.fapapp.core.ling.Alphabet;

public class PartRankedAlphabet {
	private final Alphabet terminals;
	private final Alphabet nonterminals;

	public PartRankedAlphabet(Alphabet terminals, Alphabet nonterminals) {
		super();
		this.terminals = terminals;
		this.nonterminals = nonterminals;
	}

	public Alphabet getTerminals() {
		return terminals;
	}

	public Alphabet getNonterminals() {
		return nonterminals;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nonterminals == null) ? 0 : nonterminals.hashCode());
		result = prime * result + ((terminals == null) ? 0 : terminals.hashCode());
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
		PartRankedAlphabet other = (PartRankedAlphabet) obj;
		if (nonterminals == null) {
			if (other.nonterminals != null)
				return false;
		} else if (!nonterminals.equals(other.nonterminals))
			return false;
		if (terminals == null) {
			if (other.terminals != null)
				return false;
		} else if (!terminals.equals(other.terminals))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PartRankedAlphabet [terminals=" + terminals + ", nonterminals=" + nonterminals + "]";
	}

}
