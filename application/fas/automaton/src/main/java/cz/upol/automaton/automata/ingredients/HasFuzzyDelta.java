package cz.upol.automaton.automata.ingredients;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.NFAedge;

public interface HasFuzzyDelta extends HasDelta<FuzzySet<Transition>, NFAedge>, IsFuzzy {
	/**
	 * Přidá zadaný přechod o zadaném stupni pravdivosti.
	 * 
	 * @param transition
	 * @param degree
	 */
	public void addTransition(Transition transition, Degree degree);

	/**
	 * Zadanému přechodu změní stupeň pravdivosti.
	 * 
	 * @param transition
	 * @param degree
	 */
	public void updateTransitionDegree(Transition transition, Degree degree);

	/**
	 * Upraví přechod (resp. currentTransition nahradí newTransition).
	 * 
	 * @param currentTransition
	 * @param newTransition
	 */
	public void updateTransition(Transition currentTransition,
			Transition newTransition);

	/**
	 * Odstraní přechod.
	 * 
	 * @param transition
	 */
	public void removeTransition(Transition transition);

	/**
	 * Vrátí stupeň pravdivosti, v jakém je zadaný přechod pravdivý.
	 * 
	 * @param transition
	 * @return
	 */
	public Degree degreeOfTransition(Transition transition);

	/**
	 * Vytvoří a vrátí novou fuzzy množinu s přechody.
	 * 
	 * @return
	 */
	public FuzzySet<Transition> newTransitionsFuzzySet();

}
