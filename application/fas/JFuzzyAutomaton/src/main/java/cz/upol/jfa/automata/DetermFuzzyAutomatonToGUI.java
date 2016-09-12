package cz.upol.jfa.automata;

import java.util.Set;

import cz.upol.automaton.automata.definition.DetermFuzzyAutomataDefinition;
import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.jfa.viewer.Position;

public class DetermFuzzyAutomatonToGUI extends DeterministicFuzzyAutomata
		implements BaseAutomatonToGUI {

	private final AutomataGUIProvider provider;

	public DetermFuzzyAutomatonToGUI(ResiduedLattice residuedLattice,
			Alphabet alphabet) {
		super(residuedLattice, alphabet);
		provider = new AutomataGUIProvider(this);
	}

	public DetermFuzzyAutomatonToGUI(ResiduedLattice residuedLattice,
			Set<State> states, Alphabet alphabet, State initialState,
			FuzzySet<State> finiteStates, Set<Transition> delta) {

		super(residuedLattice, states, alphabet, initialState, finiteStates,
				delta);
		provider = new AutomataGUIProvider(this);
	}

	public DetermFuzzyAutomatonToGUI(DetermFuzzyAutomataDefinition other) {
		super(other);
		provider = new AutomataGUIProvider(this);
	}

	public AutomataGUIProvider getProvider() {
		return provider;
	}

	@Override
	public State findStateByLabel(String label) {
		return provider.findStateByLabel(label);
	}

	@Override
	public boolean addState() {
		State state = provider.addState();
		return super.addState(state);
	}

	@Override
	public boolean addState(State state) {
		provider.addState(state);
		return super.addState(state);
	}

	@Override
	public boolean addState(State state, Position position) {
		provider.addState(state, position);
		return super.addState(state);
	}

	@Override
	public State addOrRenameState(State state, Position position) {
		State newState = provider.addOrRenameState(state, position);
		super.addState(newState);
		return newState;
	}

	@Override
	public void removeState(State state) {
		super.removeState(state);
		provider.removeState(state);
	}

	@Override
	public void moveStateTo(State state, Position toPosition) {
		provider.moveStateTo(state, toPosition);
	}

	@Override
	public State findState(Position position) {
		return provider.findState(position);
	}

	public Position getSize() {
		return provider.getSize();
	}

}
