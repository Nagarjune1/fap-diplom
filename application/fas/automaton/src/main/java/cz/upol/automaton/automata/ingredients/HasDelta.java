package cz.upol.automaton.automata.ingredients;

import java.util.Set;

import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.AbstractEdge;

public interface HasDelta<TT, ET extends AbstractEdge<?>> {

	public TT getDelta();

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
	public TT transitionsFrom(State from);

	/**
	 * Vrátí přechody ze zadaného stavu přes zadaný symbol.
	 * 
	 * @param from
	 * @param symbol
	 * @return
	 */
	public TT transitionsFromAndOver(State from, Symbol symbol);

	/**
	 * Vrátí přechody mezi dvěma stavy.
	 * 
	 * @param from
	 * @param to
	 * @see {@link #getEdge(State, State)}
	 * @return
	 */
	public TT transitionsFromTo(State from, State to);

	/**
	 * Vrátí hranu mezi zadanými dvěma stavy.
	 * 
	 * @param from
	 * @param to
	 * @see {@link #transitionsFromTo(State, State)}
	 * @return
	 */
	public ET getEdge(State from, State to);

	/**
	 * Vrátí hrany vycházející ze zadanáho stavu.
	 * 
	 * @param from
	 * @return
	 */
	public Set<ET> getOutgoingEdges(State from);

	/**
	 * Vrátí (všechny) hrany v automatu.
	 * 
	 * @return
	 */
	public Set<ET> getEdges();

	/**
	 * Přidá zadanou hranu (tj. všechny její přechody).
	 * 
	 * @param edge
	 */
	public void addEdge(ET edge);

	/**
	 * Odebere zadanou hranu (tj. všechny její přechody).
	 * 
	 * @param edge
	 */
	public void removeEdge(ET edge);

	/**
	 * Upraví zadanou hranu (tj. přidá, odebere nebo zaktualizuje stupeň
	 * pravidivosti u svých přechodů).
	 * 
	 * @param edge
	 */
	public void updateEdge(ET edge);

	/**
	 * Vrátí přechody, které incidují se zadaným stavem
	 * 
	 * @param state
	 * @return
	 */
	public TT getTransitionsWithState(State state);

}