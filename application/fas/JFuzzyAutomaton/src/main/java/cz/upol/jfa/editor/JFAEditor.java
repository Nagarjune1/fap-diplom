package cz.upol.jfa.editor;

import java.awt.Frame;
import java.awt.event.ActionListener;

import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.viewer.JFuzzyAutomatonViewer;
import cz.upol.jfa.viewer.interactivity.ViewerSelection;

public class JFAEditor extends JFuzzyAutomatonViewer {

	private static final long serialVersionUID = -3385293112639157145L;

	private final Frame owner;
	private EditorStatus status;

	public JFAEditor(Frame owner, BaseAutomatonToGUI automaton) {
		super(automaton);

		this.owner = owner;

		addActionListener(new EditorActionListener(this));
		status = EditorStatus.NORMAL;
	}

	public Frame getOwner() {
		return owner;
	}

	public void setState(EditorStatus status) {
		this.status = status;
		super.changeSelection((ViewerSelection) null);

		fireStatusChanged();
	}

	private void fireStatusChanged() {
		int id = hashCode() + status.hashCode();
		String command = "Changed Status to " + status;
		long when = System.currentTimeMillis();
		int modifiers = 0;

		EditorActionEvent event = new EditorActionEvent(this, status, id,
				command, when, modifiers);

		fireActions(event);
	}

	public void fireActions(EditorActionEvent event) {
		for (ActionListener listener : getListeners()) {
			listener.actionPerformed(event);
		}

	}

	public EditorStatus getStatus() {
		return status;
	}

}
