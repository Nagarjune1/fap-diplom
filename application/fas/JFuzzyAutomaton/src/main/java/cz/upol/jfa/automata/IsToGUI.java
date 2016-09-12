package cz.upol.jfa.automata;

import cz.upol.automaton.misc.State;
import cz.upol.jfa.viewer.Position;

public interface IsToGUI {

	public abstract State findStateByLabel(String label);

	public abstract boolean addState();

	public abstract boolean addState(State state, Position position);

	public abstract State addOrRenameState(State state, Position position);

	public abstract void moveStateTo(State state, Position toPosition);

	public abstract State findState(Position position);

}