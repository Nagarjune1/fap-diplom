package cz.upol.jfa.editor.processors;

import java.util.Map;

import cz.upol.automaton.misc.State;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.editor.JFAEditor;
import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.interactivity.PositioningUtilities;
import cz.upol.jfa.viewer.interactivity.ViewerActionEvent;
import cz.upol.jfa.viewer.interactivity.ViewerSelection;

public class AddingStateProcessor implements
		EditorEventProcessor {
	private final EditorActionsLogic logic;

	public AddingStateProcessor(EditorActionsLogic logic) {
		this.logic = logic;
	}

	@Override
	public void process(ViewerActionEvent event) {
		if (logic.checkAndProcessLMB(event)) {
			return;
		}

		JFAEditor editor = logic.getEditor();

		EditorActions actions = logic.getActions();

		editor.changeSelection((ViewerSelection) null);
		logic.getEditor().automatonChanged();

		Position position = event.getPosition();
		Position succPos = movePosition(position);

		actions.createState(succPos);

		editor.automatonChanged();
	}

	public Position movePosition(Position position) {
		BaseAutomatonToGUI automaton = logic.getEditor().getAutomaton();
		PositioningUtilities utils = new PositioningUtilities(automaton);
		Map<State, Position> postions = automaton.getProvider().getStatesPositions();

		return utils.moveStateToAllowed(null, position, postions);
	}

}
