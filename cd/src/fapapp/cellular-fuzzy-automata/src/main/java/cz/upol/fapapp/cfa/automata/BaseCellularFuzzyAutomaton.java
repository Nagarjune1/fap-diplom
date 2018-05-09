package cz.upol.fapapp.cfa.automata;

import java.io.PrintStream;

import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import cz.upol.fapapp.cfa.outers.CFAOuterCellSupplier;
import cz.upol.fapapp.core.automata.BaseAutomaton;

/**
 * Base class for cellular fuzzy automata, holding just the definition data and
 * specifiing the methods to be implemented.
 * 
 * @author martin
 *
 */
public abstract class BaseCellularFuzzyAutomaton implements BaseAutomaton {

	protected final int size;
	protected final CFATransitionFunction mu;
	protected final CFAOuterCellSupplier outers;

	public BaseCellularFuzzyAutomaton(int size, CFATransitionFunction mu, CFAOuterCellSupplier outers) {
		super();
		this.size = size;
		this.mu = mu;
		this.outers = outers;
	}

	public int getSize() {
		return size;
	}

	public CFATransitionFunction getMu() {
		return mu;
	}

	public CFAOuterCellSupplier getOuters() {
		return outers;
	}

	/**************************************************************************/

	public abstract CFAConfiguration computeNextGeneration(CFAConfiguration currentConfig);

	/**************************************************************************/

	@Override
	public String toString() {
		return "BaseCellularFuzzyAutomaton [size=" + size + ", mu=" + mu + ", outers=" + outers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mu == null) ? 0 : mu.hashCode());
		result = prime * result + ((outers == null) ? 0 : outers.hashCode());
		result = prime * result + size;
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
		BaseCellularFuzzyAutomaton other = (BaseCellularFuzzyAutomaton) obj;
		if (mu == null) {
			if (other.mu != null)
				return false;
		} else if (!mu.equals(other.mu))
			return false;
		if (outers == null) {
			if (other.outers != null)
				return false;
		} else if (!outers.equals(other.outers))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public void print(PrintStream to) {
		to.println(this.toString());
	}

}