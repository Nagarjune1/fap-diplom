package cz.upol.fas.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import cz.upol.jfa.editor.EditorStatus;

public class JEditorToolbar extends JToolBar {

	private static final long serialVersionUID = -2696975559672300923L;

	private final JEditorCard owner;

	private JToggleButton selectingModeButt;
	private JToggleButton addStateModeButt;
	private JToggleButton addEdgeModeButt;
	private JToggleButton deletingModeButt;

	public JEditorToolbar(JEditorCard owner) {
		super();
		this.owner = owner;

		setFloatable(false);
		initializeComponents();
	}

	private void initializeComponents() {

		selectingModeButt = new JToggleButton("Výběr");
		selectingModeButt.addActionListener(new ChangeModeButtActionListener(
				owner, EditorStatus.NORMAL));
		this.add(selectingModeButt);
		selectingModeButt.setSelected(true);

		addStateModeButt = new JToggleButton("Přidat stav");
		addStateModeButt.addActionListener(new ChangeModeButtActionListener(
				owner, EditorStatus.ADDING_STATE));
		this.add(addStateModeButt);

		addEdgeModeButt = new JToggleButton("Přidat hranu");
		addEdgeModeButt.addActionListener(new ChangeModeButtActionListener(
				owner, EditorStatus.ADDING_EDGE));
		this.add(addEdgeModeButt);

		deletingModeButt = new JToggleButton("Odstranit");
		deletingModeButt.addActionListener(new ChangeModeButtActionListener(
				owner, EditorStatus.DELETING));
		this.add(deletingModeButt);

		ButtonGroup group = new ButtonGroup();
		group.add(selectingModeButt);
		group.add(addStateModeButt);
		group.add(addEdgeModeButt);
		group.add(deletingModeButt);
	}

	public void changeActiveButtton(EditorStatus status) {
		switch (status) {
		case NORMAL:
			selectingModeButt.setSelected(true);
			break;
		case ADDING_STATE:
			addStateModeButt.setSelected(true);
			break;
		case ADDING_EDGE:
			addEdgeModeButt.setSelected(true);
			break;
		case DELETING:
			deletingModeButt.setSelected(true);
			break;
		default:
			break;
		}
	}

	public static class ChangeModeButtActionListener implements ActionListener {

		private final JEditorCard card;
		private final EditorStatus state;

		public ChangeModeButtActionListener(JEditorCard card,
				EditorStatus state) {
			this.card = card;
			this.state = state;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			card.getEditor().setState(state);
		}

	}

}
