package cz.upol.automaton.automata.abstracts;

import cz.upol.automaton.automata.ingredients.HasAlphabet;
import cz.upol.automaton.automata.ingredients.HasStates;
import cz.upol.automaton.automata.ingredients.HasBivalentDeterministicDelta;
import cz.upol.automaton.automata.ingredients.HasFuzzyFiniteStates;
import cz.upol.automaton.automata.ingredients.HasOneInitialState;
import cz.upol.automaton.automata.ingredients.IsFuzzy;

public interface AbstractDetermFuzzyAutomaton extends BaseAutomaton,
		IsFuzzy, HasAlphabet, HasStates, HasBivalentDeterministicDelta,
		HasOneInitialState, HasFuzzyFiniteStates {

}
