package cz.upol.automaton;

import java.util.Set;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.NFAedge;

@Deprecated
public interface FuzzyAutomatonAbstractImpl {
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
	 * Upraví stav (resp. nahradí currentState novým newState a vše patřičn
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
	 * V nespecifikovaném pořadí projde všechny přechody, které mají "nenulový"
	 * stupeň.
	 * 
	 * @return
	 */
	public Iterable<Transition> iterateOverTransitions();

	/**
	 * Vrátí (fuzzy) množinu s přechody ze zadaného stavu.
	 * 
	 * @param from
	 * @return
	 */
	public FuzzySet<Transition> transitionsFrom(State from);

	/**
	 * Vrátí přechody ze zadaného stavu přes zadaný symbol.
	 * 
	 * @param from
	 * @param symbol
	 * @return
	 */
	public FuzzySet<Transition> transitionsFromAndOver(State from, Symbol symbol);

	/**
	 * Vrátí přechody mezi dvěma stavy.
	 * 
	 * @param from
	 * @param to
	 * @se {@link #getEdge(State, State)}
	 * @return
	 */
	public FuzzySet<Transition> transitionsFromTo(State from, State to);

	/**
	 * Vrátí hranu mezi zadanými dvěma stavy.
	 * 
	 * @param from
	 * @param to
	 * @see {@link #transitionsFromTo(State, State)}
	 * @return
	 */
	public NFAedge getEdge(State from, State to);

	/**
	 * Vrátí hrany vycházející ze zadanáho stavu.
	 * 
	 * @param from
	 * @return
	 */
	public Set<NFAedge> getOutgoingEdges(State from);

	/**
	 * Vrátí (všechny) hrany v automatu.
	 * 
	 * @return
	 */
	public Set<NFAedge> getEdges();

	/**
	 * Přidá zadanou hranu (tj. všechny její přechody).
	 * 
	 * @param edge
	 */
	public void addEdge(NFAedge edge);

	/**
	 * Odebere zadanou hranu (tj. všechny její přechody).
	 * 
	 * @param edge
	 */
	public void removeEdge(NFAedge edge);

	/**
	 * Upraví zadanou hranu (tj. přidá, odebere nebo zaktualizuje stupeň
	 * pravidivosti u svých přechodů).
	 * 
	 * @param edge
	 */
	public void updateEdge(NFAedge edge);

	/**
	 * Vrátí přechody, které incidují se zadaným stavem
	 * 
	 * @param state
	 * @return
	 */
	public Set<Transition> getTransitionsWithState(State state);

	/**
	 * Vrátí stupeň pravdivosti, v jakém je zadaný přechod pravdivý.
	 * 
	 * @param transition
	 * @return
	 */
	public Degree degreeOfTransition(Transition transition);

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
