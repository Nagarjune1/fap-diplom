package cz.upol.automaton.automata.ingredients;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.misc.State;

public interface HasFuzzyFiniteStates extends IsFuzzy, HasStates{
	public FuzzySet<State> getFiniteStates();

	/**
	 * Nastaví zadaný stav jako konečný v zadaném stupni. Stav musí být stavem
	 * tohoto automatu, jinak volá výjimku. Pokud stav již jako koncový je,
	 * hodnotu stupně mu změní.
	 * 
	 * @param state
	 * @param degree
	 */
	public void setFiniteState(State state, Degree degree);

	/**
	 * Vrátí stupeň pravdivosti, v jakém je zadaný stav koncovým.
	 * 
	 * @param state
	 * @return
	 */
	public Degree degreeOfFiniteState(State state);
}
