package cz.upol.fapapp.fa.min;

import java.util.Set;

import cz.upol.fapapp.core.automata.State;

public interface AutomataPartitioner {

	public Set<Set<State>> compute();

}
