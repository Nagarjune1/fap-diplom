package cz.upol.automaton.automata.ingredients;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.misc.State;

public interface HasFuzzyInitialStates extends IsFuzzy, HasStates{
	public FuzzySet<State> getInitialStates();
	
	/**
	 * Nastaví zadaný stav jako počáteční v zadaném stupni. Stav musí být stavem
	 * tohoto automatu, jinak volá výjimku. Pokud stav již jako počíteční je,
	 * hodnotu stupně mu změní.
	 * 
	 * @param state
	 * @param degree
	 */
	public void setInitialState(State state, Degree degree);

	/**
	 * Vrátí stupeň pravdivosti, v jakém je zadaný stav počátečním.
	 * 
	 * @param state
	 * @return
	 */
	public Degree degreeOfInitialState(State state);

}
