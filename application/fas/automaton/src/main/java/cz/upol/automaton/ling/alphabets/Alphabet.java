package cz.upol.automaton.ling.alphabets;

import java.util.Set;
import java.util.Spliterator;

import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.sets.Externisator;
import cz.upol.automaton.sets.HasExterernalRepresentation;

/**
 * @author  martin
 */
public interface Alphabet extends Set<Symbol>, HasExterernalRepresentation, SetOfExternisables<Symbol> {

	/**
	 * @uml.property  name="description"
	 */
	public String getDescription();

	public Externisator<Symbol> getSymbolsExternisator();

	@Override
	public Spliterator<Symbol> spliterator();

}
