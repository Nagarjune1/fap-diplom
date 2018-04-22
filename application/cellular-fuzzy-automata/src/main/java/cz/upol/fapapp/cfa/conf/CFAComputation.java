package cz.upol.fapapp.cfa.conf;

import java.io.PrintStream;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomaton;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.misc.Printable;

/**
 * Computation of cellular fuzzy automata. Holds the current configuration,
 * initial configuration, number of generation and also link to the automaton.
 * 
 * @author martin
 *
 */
public class CFAComputation implements Printable {

	private final CellularFuzzyAutomaton automaton;
	private final CFAConfiguration initialConfig;
	private final int initialGeneration;

	private CFAConfiguration currentConfig;
	private int currentGeneration;

	public CFAComputation(CellularFuzzyAutomaton automaton, CFAConfiguration config, int generation) {
		super();

		if (automaton.getSize() != config.getSize()) {
			throw new IllegalArgumentException(
					"Automaton and config size differs (" + automaton.getSize() + " and " + config.getSize() + ")");
		}

		this.automaton = automaton;
		this.initialConfig = config;
		this.initialGeneration = generation;
		this.currentConfig = config;
		this.currentGeneration = generation;
	}

	public CFAComputation(CellularFuzzyAutomaton automaton, CFAConfiguration currentConfig) {
		this(automaton, currentConfig, 0);
	}

	public CellularFuzzyAutomaton getAutomaton() {
		return automaton;
	}

	public CFAConfiguration getConfig() {
		return currentConfig;
	}

	public int getGeneration() {
		return currentGeneration;
	}

	/**************************************************************************/

	/**
	 * Resets back to the initial configuration.
	 */
	public void reset() {
		currentConfig = initialConfig;
		currentGeneration = initialGeneration;
	}

	/**
	 * Performs computation using the automata and changes this object's state
	 * to the computed next generation.
	 */
	public void toNextGeneration() {
		currentConfig = automaton.computeNextGeneration(currentConfig);
		currentGeneration++;

		Logger.get().debug("Computed generation " + currentGeneration);
	}

	/**************************************************************************/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((automaton == null) ? 0 : automaton.hashCode());
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
		CFAComputation other = (CFAComputation) obj;
		if (automaton == null) {
			if (other.automaton != null)
				return false;
		} else if (!automaton.equals(other.automaton))
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
		return "CellularAutomatonComputation [automaton=" + automaton + ", initialConfig=" + initialConfig
				+ ", initialGeneration=" + initialGeneration + ", currentConfig=" + currentConfig
				+ ", currentGeneration=" + currentGeneration + "]";
	}

	/**************************************************************************/

	@Override
	public void print(PrintStream to) {
		to.println("CFAComputation:");
		to.println("automaton:" + this.getAutomaton());
		to.println("generation:" + this.getGeneration());
		Printable.print(to, new CFAConfTIMComposer(), this.getConfig());

	}

}
