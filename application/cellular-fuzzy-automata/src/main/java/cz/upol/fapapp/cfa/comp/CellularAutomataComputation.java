package cz.upol.fapapp.cfa.comp;

import java.io.PrintStream;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.misc.Printable;

public class CellularAutomataComputation implements Printable {

	private final CellularFuzzyAutomata automata;
	private final CFAConfiguration initialConfig;
	private final int initialGeneration;

	private CFAConfiguration currentConfig;
	private int currentGeneration;

	public CellularAutomataComputation(CellularFuzzyAutomata automata, CFAConfiguration config, int generation) {
		super();
		this.automata = automata;
		this.initialConfig = config;
		this.initialGeneration = generation;
		this.currentConfig = config;
		this.currentGeneration = generation;
	}

	public CellularAutomataComputation(CellularFuzzyAutomata automata, CFAConfiguration currentConfig) {
		super();
		this.automata = automata;
		this.initialConfig = currentConfig;
		this.currentConfig = currentConfig;
		this.initialGeneration = 0;
		this.currentGeneration = 0;
	}

	public CellularFuzzyAutomata getAutomata() {
		return automata;
	}

	public CFAConfiguration getConfig() {
		return currentConfig;
	}

	public int getGeneration() {
		return currentGeneration;
	}

	/**************************************************************************/

	public void reset() {
		currentConfig = initialConfig;
		currentGeneration = initialGeneration;
	}

	public void toNextGeneration() {
		currentConfig = automata.computeNextGeneration(currentConfig);
		currentGeneration++;

		Logger.get().moreinfo("Computed generation " + currentGeneration);
	}

	@Deprecated
	public void toNextsGenerations(int count) {
		throw new UnsupportedOperationException();
	}

	/**************************************************************************/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((automata == null) ? 0 : automata.hashCode());
		result = prime * result + ((initialConfig == null) ? 0 : initialConfig.hashCode());
		result = prime * result + initialGeneration;
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
		CellularAutomataComputation other = (CellularAutomataComputation) obj;
		if (automata == null) {
			if (other.automata != null)
				return false;
		} else if (!automata.equals(other.automata))
			return false;
		if (initialConfig == null) {
			if (other.initialConfig != null)
				return false;
		} else if (!initialConfig.equals(other.initialConfig))
			return false;
		if (initialGeneration != other.initialGeneration)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CellularAutomataComputation [automata=" + automata + ", initialConfig=" + initialConfig
				+ ", initialGeneration=" + initialGeneration + ", currentConfig=" + currentConfig
				+ ", currentGeneration=" + currentGeneration + "]";
	}

	/**************************************************************************/


	@Override
	public void print(PrintStream to) {
		Printable.print(to, new CFACompTIMComposer(), this);
	}

	
}
