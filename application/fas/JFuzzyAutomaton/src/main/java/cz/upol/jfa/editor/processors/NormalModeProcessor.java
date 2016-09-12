package cz.upol.jfa.editor.processors;

import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.jfa.editor.JFAEditor;
import cz.upol.jfa.viewer.interactivity.ViewerActionEvent;
import cz.upol.jfa.viewer.interactivity.ViewerEventType;
import cz.upol.jfa.viewer.interactivity.ViewerSelection;

public class NormalModeProcessor implements
		EditorEventProcessor {

	private final EditorActionsLogic logic;

	public NormalModeProcessor(EditorActionsLogic logic) {
		this.logic = logic;
	}

	@Override
	public void process(ViewerActionEvent event) {
		ViewerSelection selection = event.getOn();
		JFAEditor editor = logic.getEditor();
		EditorActions actions = logic.getActions();

		editor.changeSelection(selection);
		logic.getEditor().automatonChanged();

		if (event.getType().equals(ViewerEventType.LMB_DOUBLECLICK)) {
			if (selection.isSelectedState()) {

				State state = selection.getSelectedState();
				actions.changeState(state);
				editor.changeSelection((ViewerSelection) null);
			}

			if (selection.isSelectedEdge()) {
				AbstractEdge<?> edge = selection.getSelectedEdge();
				actions.changeEdge(edge);

				editor.changeSelection((ViewerSelection) null);
			}

			if (!selection.isSelected()) {
				actions.createState(event.getPosition());

			}
		}

		logic.getEditor().automatonChanged();
	}

}
