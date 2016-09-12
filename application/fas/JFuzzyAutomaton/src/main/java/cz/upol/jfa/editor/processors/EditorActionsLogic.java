package cz.upol.jfa.editor.processors;

import java.awt.Frame;

import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.editor.EditorStatus;
import cz.upol.jfa.editor.JFAEditor;
import cz.upol.jfa.editor.forms.JEditEdgeDial;
import cz.upol.jfa.editor.forms.JEditStateDial;
import cz.upol.jfa.viewer.interactivity.ViewerActionEvent;

public class EditorActionsLogic implements EditorEventProcessor {

	private final JFAEditor editor;

	private final EditorActions actions;
	private final JEditStateDial editStateDial;
	private final JEditEdgeDial editEdgeDial;

	private final EditorEventProcessor deletingModeProcessor;
	private final EditorEventProcessor addingEdgeProcessor;
	private final EditorEventProcessor addingStateProcessor;
	private final EditorEventProcessor normalModeProcessor;

	public EditorActionsLogic(JFAEditor editor) {

		this.editor = editor;

		Frame owner = editor.getOwner();
		BaseAutomatonToGUI automaton = editor.getAutomaton();

		this.actions = new EditorActions(this);

		this.editStateDial = new JEditStateDial(owner, automaton);
		this.editEdgeDial = new JEditEdgeDial(owner, automaton);

		this.normalModeProcessor = new NormalModeProcessor(this);
		this.addingStateProcessor = new AddingStateProcessor(this);
		this.addingEdgeProcessor = new AddingEdgeProcessor(this);
		this.deletingModeProcessor = new DeletingModeProcessor(this);
	}

	public JEditStateDial getEditStateDial() {
		return editStateDial;
	}

	public JEditEdgeDial getEditEdgeDial() {
		return editEdgeDial;
	}

	public JFAEditor getEditor() {
		return editor;
	}

	public EditorActions getActions() {
		return actions;
	}

	@Override
	public void process(ViewerActionEvent event) {
		EditorEventProcessor processor = selectProcessor();
		processor.process(event);
	}

	public EditorEventProcessor selectProcessor() {
		switch (editor.getStatus()) {
		case NORMAL:
			return normalModeProcessor;
		case ADDING_STATE:
			return addingStateProcessor;
		case ADDING_EDGE:
			return addingEdgeProcessor;
		case DELETING:
			return deletingModeProcessor;
		}

		return null;
	}

	/**
	 * Pokud event hlásí pravé tlačítko myši, přejde do normálního stavu a vrací
	 * true. Jinak false.
	 * 
	 * @param event
	 * @return
	 */
	public boolean checkAndProcessLMB(ViewerActionEvent event) {
		if (event.getType().isRightButton()) {
			editor.setState(EditorStatus.NORMAL);

			return true;
		}

		return false;
	}

}
