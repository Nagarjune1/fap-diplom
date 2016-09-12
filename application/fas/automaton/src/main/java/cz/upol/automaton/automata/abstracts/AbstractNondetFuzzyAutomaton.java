package cz.upol.automaton.automata.abstracts;

import cz.upol.automaton.automata.ingredients.HasAlphabet;
import cz.upol.automaton.automata.ingredients.HasStates;
import cz.upol.automaton.automata.ingredients.HasFuzzyDelta;
import cz.upol.automaton.automata.ingredients.HasFuzzyFiniteStates;
import cz.upol.automaton.automata.ingredients.HasFuzzyInitialStates;
import cz.upol.automaton.automata.ingredients.IsFuzzy;

public interface AbstractNondetFuzzyAutomaton extends BaseAutomaton, IsFuzzy,
		HasAlphabet, HasStates, HasFuzzyDelta, HasFuzzyInitialStates,
		HasFuzzyFiniteStates {

}
