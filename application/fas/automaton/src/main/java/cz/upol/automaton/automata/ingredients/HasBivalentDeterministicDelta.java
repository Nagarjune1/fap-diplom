package cz.upol.automaton.automata.ingredients;

import java.util.Set;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.DFAedge;

public interface HasBivalentDeterministicDelta extends
		HasDelta<Set<Transition>, DFAedge> {
	public Set<Transition> getDelta();

	/**
	 * Přidá zadaný přechod o zadaném stupni pravdivosti.
	 * 
	 * @param transition
	 * @param degree
	 */
	public void addTransition(Transition transition);

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
	 * Vrací true, pokud symbol přechodu transition už automat obsahuje v jiném
	 * přechodu z téhož stavu.
	 * 
	 * @param transition
	 * @return
	 */
	public boolean hasIncidentingState(Transition transition);
}
