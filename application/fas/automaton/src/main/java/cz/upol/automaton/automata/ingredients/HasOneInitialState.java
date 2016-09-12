package cz.upol.automaton.automata.ingredients;

import cz.upol.automaton.misc.State;

public interface HasOneInitialState extends HasStates {
	public boolean isInitial(State state);
	
	public State getInitialState();

	public void setInitialState(State newState);
}
