package cz.upol.jfa.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.upol.jfa.editor.processors.EditorActionsLogic;
import cz.upol.jfa.viewer.interactivity.ViewerActionEvent;

public class EditorActionListener implements
		ActionListener {

	private final EditorActionsLogic logic;

	public EditorActionListener(JFAEditor jfaEditor) {
		this.logic = new EditorActionsLogic(jfaEditor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(e instanceof ViewerActionEvent)) {
			return;
		}

		ViewerActionEvent event = (ViewerActionEvent) e;
		logic.process(event);
	}
}
