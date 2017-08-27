package cz.upol.fapapp.cfa.automata;

import java.io.PrintStream;

import cz.upol.fapapp.cfa.config.CFAConfiguration;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import cz.upol.fapapp.core.automata.BaseAutomata;

public abstract class BaseCellularFuzzyAutomata implements BaseAutomata {

	protected final int size;
	protected final CFATransitionFunction mu;

	public BaseCellularFuzzyAutomata(int size, CFATransitionFunction mu) {
		super();
		this.size = size;
		this.mu = mu;
	}

	/**************************************************************************/

	public int getSize() {
		return size;
	}

	public CFATransitionFunction getMu() {
		return mu;
	}

	/**************************************************************************/

	public abstract CFAConfiguration getCurrentConfig();

	public abstract int getCurrentGeneration();

	public abstract void toNextGeneration();

	public abstract void toNextGenerations(int count);

	/**************************************************************************/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mu == null) ? 0 : mu.hashCode());
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
		BaseCellularFuzzyAutomata other = (BaseCellularFuzzyAutomata) obj;
		if (mu == null) {
			if (other.mu != null)
				return false;
		} else if (!mu.equals(other.mu))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseCellularFuzzyAutomata [size=" + size + ", mu=" + mu + "]";
	}

	@Override
	public void print(PrintStream to) {
		CFAFileComposer composer = new CFAFileComposer();
		String string = composer.compose((CellularFuzzyAutomata) this);
		to.println(string);
	}

}