package cz.upol.automaton.automata.abstracts;

import cz.upol.automaton.automata.ingredients.HasAlphabet;
import cz.upol.automaton.automata.ingredients.HasDelta;
import cz.upol.automaton.automata.ingredients.HasStates;
import cz.upol.automaton.misc.edges.AbstractEdge;

public interface SimpleAutomaton<TS, E extends AbstractEdge<TS>> extends
		HasAlphabet, HasStates, HasDelta<TS, E> {

}
