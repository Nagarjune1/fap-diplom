package cz.upol.jfa.automata;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.upol.automaton.automata.ingredients.HasStates;
import cz.upol.automaton.misc.State;
import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.interactivity.PositioningUtilities;

public class AutomataGUIProvider {
	private static final String UNIQE_STATE_ADDED_CHAR = "'";

	private final PositioningUtilities positioning;

	private final BaseAutomatonToGUI automaton;
	private final Map<State, Position> statesPositions;

	public AutomataGUIProvider(BaseAutomatonToGUI automaton) {
		this.automaton = automaton;
		this.positioning = new PositioningUtilities(automaton);
		this.statesPositions = initializeStatesPositions(((HasStates) automaton)
				.getStates());
	}

	public Map<State, Position> getStatesPositions() {
		return statesPositions;
	}

	public PositioningUtilities getPositioning() {
		return positioning;
	}

	public State findStateByLabel(String label) {
		for (State state : ((HasStates) automaton).iterateOverStates()) {
			if (state.getLabel().equals(label)) {
				return state;
			}
		}
		return null;
	}

	/**
	 * Vytvoří nový stav (s dalším číslem v pořadí) a přidá ho do automatu.
	 * Neřeší duplicity (je-li již takový stav v automatu, nahradí jej a vrací
	 * true). Duplicity řeší {@link #addOrRenameState(State, Position)}.
	 * 
	 * @return
	 */
	public State addState() {
		State state = new State(nextStateLabel());
		addState();
		return state;
	}

	/**
	 * Přidá zadaný stav na další pozici v pořadí. Neřeší duplicity (je-li již
	 * takový stav v automatu, nahradí jej a vrací true). Duplicity řeší
	 * {@link #addOrRenameState(State, Position)}.
	 * 
	 * @param state
	 * @return
	 */
	public void addState(State state) {
		Position position = positioning.nextStatePosition();
		addState(state, position);
	}

	/**
	 * Přidá zadaný stav na zadanou pozici. Neřeší duplicity (je-li již takový
	 * stav v automatu, nahradí jej a vrací true). Duplicity řeší
	 * {@link #addOrRenameState(State, Position)}.
	 * 
	 * @param state
	 * @param position
	 * @return
	 */
	public void addState(State state, Position position) {
		statesPositions.put(state, position);
	}

	/**
	 * Pokusí se přidat zadaný stav na zadanou pozici a pokud již tkaový stav
	 * existuje, vytvoří jeho přejmenováním nový, který přidá nísto něj a nový
	 * vrátí.
	 * 
	 * @param state
	 * @param position
	 * @return
	 */
	public State addOrRenameState(State state, Position position) {
		boolean occured = findState(position) != null;

		if (!occured) {
			State newState = createNewUniqueState(state);
			((IsToGUI) automaton).addState(newState, position);
			return newState;
		} else {
			return state;
		}
	}

	private boolean containsState(State state) {
		for (State other : ((HasStates) automaton).iterateOverStates()) {
			if (other.equals(state)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Odstraní zadaný stav.
	 * 
	 * @param state
	 */
	public void removeState(State state) {
		statesPositions.remove(state);
	}

	/**
	 * Přesune stav na zadanou (resp. jí nejbližší povolenou) pozici.
	 * 
	 * @param state
	 * @param toPosition
	 */
	public void moveStateTo(State state, Position toPosition) {
		Position newPosition = positioning.moveStateToAllowed(state,
				toPosition, statesPositions);

		statesPositions.put(state, newPosition);
	}

	/**
	 * Vyhledá pozici zadaného stavu.
	 * 
	 * @param state
	 * @return
	 */
	public Position findPosition(State state) {
		return statesPositions.get(state);
	}

	/**
	 * Vyhledá stav, který se nachází (přímo na) zadané pozici.
	 * 
	 * @param position
	 * @return
	 */
	public State findState(Position position) {
		for (Entry<State, Position> entry : statesPositions.entrySet()) {
			if (position.equals(entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;
	}

	/**
	 * Vrátí aktuální velikost automatu
	 * 
	 * @return
	 */
	public Position getSize() {
		return positioning.calculateSize();
	}

	/**
	 * Vrátí popisek pro další nově vytvářený stav.
	 * 
	 * @return
	 */
	public String nextStateLabel() {
		return "q" + ((HasStates) automaton).getStatesCount();
	}

	/**
	 * Zadanému stavu vytvoří nový tak, aby měl unikátní název (přidáním znaků).
	 * 
	 * @param state
	 * @return
	 */
	private State createNewUniqueState(State state) {
		String label = state.getLabel();
		State newState = new State(label);

		while (containsState(newState)) {
			label += UNIQE_STATE_ADDED_CHAR;
			newState = new State(newState);
		}

		return newState;
	}

	protected Map<State, Position> initializeStatesPositions(Set<State> states) {
		Map<State, Position> positions = new LinkedHashMap<>();

		int count = ((HasStates) automaton).getStatesCount();
		int index = 0;

		for (State state : states) {
			Position position = positioning.statePosition(index, count);
			positions.put(state, position);

			index++;
		}

		return positions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((automaton == null) ? 0 : automaton.hashCode());
		result = prime * result
				+ ((positioning == null) ? 0 : positioning.hashCode());
		result = prime * result
				+ ((statesPositions == null) ? 0 : statesPositions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutomataGUIProvider other = (AutomataGUIProvider) obj;
		if (automaton == null) {
			if (other.automaton != null)
				return false;
		} else if (!automaton.equals(other.automaton))
			return false;
		if (positioning == null) {
			if (other.positioning != null)
				return false;
		} else if (!positioning.equals(other.positioning))
			return false;
		if (statesPositions == null) {
			if (other.statesPositions != null)
				return false;
		} else if (!statesPositions.equals(other.statesPositions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AutomataGUIProvider [positioning=" + positioning
				+ ", automaton=" + automaton + ", statesPositions="
				+ statesPositions + "]";
	}

}