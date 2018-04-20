package cz.upol.fapapp.fa.min;

import cz.upol.fapapp.core.automata.State;

public interface AutomataPartitioner {

	public SetsPartition<State> compute();

}
