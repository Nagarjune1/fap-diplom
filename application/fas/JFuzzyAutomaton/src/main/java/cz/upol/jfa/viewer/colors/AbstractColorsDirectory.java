package cz.upol.jfa.viewer.colors;

import java.awt.Color;

import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;

public interface AbstractColorsDirectory {

	/**
	 * Aktuální barva pozadí komponenty
	 * 
	 * @return
	 */
	public abstract Color getBackground();

	/**
	 * Aktuální barva pozadí popisků
	 * 
	 * @return
	 */
	public abstract Color getLabelBg();

	/**
	 * Aktuální barva pozadí stavu state
	 * 
	 * @param state
	 * @return
	 */
	public abstract Color getStateBg(State state);

	/**
	 * Aktuální barva kružnic stavu state
	 * 
	 * @param state
	 * @return
	 */
	public abstract Color getStateCircle(State state);

	/**
	 * Aktuální barva popisku stavu state
	 * 
	 * @param state
	 * @return
	 */
	public abstract Color getStateLabel(State state);

	/**
	 * Aktuální barva popisku "počáteční ve stupní" stavu state
	 * 
	 * @param state
	 * @return
	 */
	public abstract Color getInitialText(State state);

	/**
	 * Aktuální barva popisku "koncový ve stupní" stavu state
	 * 
	 * @param state
	 * @return
	 */
	public abstract Color getFiniteText(State state);

	/**
	 * Barva šipky přechodu line
	 * 
	 * @param line
	 * @return
	 */
	public abstract Color getEdgeArrow(AbstractEdge<?> line);

	/**
	 * Barva popisku přechodu line
	 * 
	 * @param line
	 * @return
	 */
	public abstract Color getEdgeLabel(AbstractEdge<?> line);

}