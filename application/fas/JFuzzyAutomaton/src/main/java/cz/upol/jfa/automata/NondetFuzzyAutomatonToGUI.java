package cz.upol.jfa.automata;

import java.util.Map;

import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.jfa.viewer.Position;

public class NondetFuzzyAutomatonToGUI extends NondetermisticFuzzyAutomata
		implements BaseAutomatonToGUI {

	private final AutomataGUIProvider provider;

	public NondetFuzzyAutomatonToGUI(NondetermisticFuzzyAutomata automaton,
			Map<State, Position> states) {
		super(automaton);
		provider = new AutomataGUIProvider(this);
	}

	public NondetFuzzyAutomatonToGUI(NondetermisticFuzzyAutomata automaton) {
		super(automaton);
		provider = new AutomataGUIProvider(this);
	}

	public NondetFuzzyAutomatonToGUI(FuzzyLogic logic, Alphabet alphabet) {
		super(logic, alphabet);
		provider = new AutomataGUIProvider(this);
	}

	public AutomataGUIProvider getProvider() {
		return provider;
	}

	public State findStateByLabel(String label) {
		return provider.findStateByLabel(label);
	}

	public boolean addState() {
		State state = provider.addState();
		return super.addState(state);
	}

	@Override
	public boolean addState(State state) {
		provider.addState(state);
		return super.addState(state);
	}

	public boolean addState(State state, Position position) {
		provider.addState(state, position);
		return super.addState(state);
	}

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

	public void moveStateTo(State state, Position toPosition) {
		provider.moveStateTo(state, toPosition);
	}

	public State findState(Position position) {
		return provider.findState(position);
	}

	public Position getSize() {
		return provider.getSize();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NondetFuzzyAutomatonToGUI other = (NondetFuzzyAutomatonToGUI) obj;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NondetFuzzyAutomatonToGUI [provider=" + provider
				+ ", toString()=" + super.toString() + "]";
	}

}
