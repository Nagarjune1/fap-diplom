package cz.upol.fapapp.fa.min;

import cz.upol.fapapp.core.automata.State;

/**
 * Interface specifying how the automaton's set of states may be partitioned,
 * that means sperated to particular classes of similarity.
 * 
 * @author martin
 *
 */
public interface AutomataPartitioner {

	/**
	 * Computes the partition of automaton's states.
	 * @return
	 */
	public SetsPartition<State> compute();

}
