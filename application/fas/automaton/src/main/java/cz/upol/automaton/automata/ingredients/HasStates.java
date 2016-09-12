package cz.upol.automaton.automata.ingredients;

import java.util.Set;

import cz.upol.automaton.misc.State;

public interface HasStates {
	public Set<State> getStates();
	
	/**
	 * Přidá zadaný stav. Vrací, zda-li se již v automatu přidávaný stav
	 * vyskytoval.
	 * 
	 * @param state
	 */
	public boolean addState(State state);
	
	/**
	 * Odebere zadaný stav (včetně přechodů a konečného/počátečního stavu).
	 * 
	 * @param state
	 */
	public void removeState(State state);
	
	/**
	 * Upraví stav (resp. nahradí currentState novým newState a vše patřičně
	 * přizpůsobí).
	 * 
	 * @param currentState
	 * @param newState
	 */
	public void updateState(State currentState, State newState);
	
	/**
	 * Projde v nespecifikovaném pořadí všechny stavy.
	 * 
	 * @return
	 */
	public Iterable<State> iterateOverStates();
	
	/**
	 * Vrátí počet stavů.
	 * 
	 * @return
	 */
	public int getStatesCount();
}
