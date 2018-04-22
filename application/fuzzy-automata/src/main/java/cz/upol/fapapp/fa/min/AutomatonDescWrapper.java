package cz.upol.fapapp.fa.min;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Symbol;

/**
 * Base interface encapsulating description of automaton (its initiial states,
 * final states and degrees of transitions) for particular type of data. See
 * {@link AutomatonStatesWrapper} and {@link AutomatonPartsWrapper}.
 * 
 * @author martin
 *
 * @param <I>
 */
public interface AutomatonDescWrapper<I> {

	/**
	 * Returns degree of which is given input initial in this automaton.
	 * 
	 * @param input
	 * @return
	 */
	public Degree initalIn(I input);

	/**
	 * Returns degree of which is given input final in this automaton.
	 * 
	 * @param input
	 * @return
	 */
	public Degree finalIn(I input);

	/**
	 * Returns degree of which might be transition from given element over given
	 * symbol to given element performed.
	 * 
	 * @param from
	 * @param over
	 * @param to
	 * @return
	 */
	public Degree transition(I from, Symbol over, I to);

}
