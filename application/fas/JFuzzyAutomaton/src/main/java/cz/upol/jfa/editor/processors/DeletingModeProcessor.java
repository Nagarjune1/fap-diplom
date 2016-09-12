package cz.upol.jfa.editor.processors;

import cz.upol.jfa.editor.JFAEditor;
import cz.upol.jfa.viewer.interactivity.ViewerActionEvent;
import cz.upol.jfa.viewer.interactivity.ViewerSelection;

public class DeletingModeProcessor implements
		EditorEventProcessor {
	private final EditorActionsLogic logic;

	public DeletingModeProcessor(EditorActionsLogic logic) {
		this.logic = logic;
	}

	@Override
	public void process(ViewerActionEvent event) {
		if (logic.checkAndProcessLMB(event)) {
			return;
		}

		ViewerSelection selection = event.getOn();
		JFAEditor editor = logic.getEditor();

		editor.changeSelection(selection);
		logic.getEditor().automatonChanged();

		EditorActions actions = logic.getActions();

		if (selection.isSelectedState()) {
			actions.deleteState(selection.getSelectedState());
		}

		if (selection.isSelectedEdge()) {
			actions.deleteEdge(selection.getSelectedEdge());
		}
	}

}
