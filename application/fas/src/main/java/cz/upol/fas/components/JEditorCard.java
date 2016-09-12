package cz.upol.fas.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JScrollPane;

import cz.upol.fas.components.cardsPanel.JCloseableCard;
import cz.upol.fas.config.ExportFormat;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.editor.EditorActionEvent;
import cz.upol.jfa.editor.JFAEditor;

public class JEditorCard extends JCloseableCard {
	private static final long serialVersionUID = -2354624011256384431L;

	private final JEditorsCardsPane owner;

	private JEditorToolbar toolbar;
	private JFAEditor editor;

	private File file;

	public JEditorCard(JEditorsCardsPane owner, BaseAutomatonToGUI automaton) {

		this(owner, automaton, null);
	}

	public JEditorCard(JEditorsCardsPane owner, BaseAutomatonToGUI automaton,
			File file) {

		super(owner);

		this.owner = owner;
		this.file = file;

		setLayout(new BorderLayout());

		initializeComponents(automaton);
		changeTitle();
	}

	public BaseAutomatonToGUI getAutomaton() {
		return editor.getAutomaton();
	}

	public JEditorToolbar getToolbar() {
		return toolbar;
	}

	public JFAEditor getEditor() {
		return editor;
	}

	private void initializeComponents(BaseAutomatonToGUI automaton) {
		toolbar = new JEditorToolbar(this);
		add(toolbar, BorderLayout.NORTH);

		editor = new JFAEditor(owner.getMainFrame(), automaton);
		editor.addActionListener(new EditorActionListener(this));

		JScrollPane pane = new JScrollPane(editor);
		// TODO zoom panel by to ještě chtělo ...
		add(pane, BorderLayout.CENTER);
	}

	public void changeTitle() {
		if (file != null) {
			String fileName = file.getName();
			String path = file.getAbsolutePath();
			setTabHeader(fileName);
			setTabTooltip(path);
			repaint();
			invalidate();
			revalidate();
			System.out.println("Udelano vsecko!");
		} else {
			setTabHeader(JEditorsCardsPane.UNNAMED_TITLE);
			setTabTooltip(JEditorsCardsPane.UNNAMED_TITLE);
		}
	}

	public void saveAs() {
		file = owner.getMainFrame().getFileUtils()
				.openFileAndSaveAutomaton(getAutomaton());

		changeTitle();
	}

	public void save() {
		if (file == null) {
			saveAs();
		} else {
			owner.getMainFrame().getFileUtils()
					.saveAutomaton(getAutomaton(), file);
		}
	}

	public void export(ExportFormat format) {
		owner.getMainFrame().getFileUtils()
				.openFileAndExport(getAutomaton(), format);
	}

	public static class EditorActionListener implements ActionListener {

		private final JEditorCard card;

		public EditorActionListener(JEditorCard card) {
			this.card = card;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(e instanceof EditorActionEvent)) {
				return;
			}

			EditorActionEvent event = (EditorActionEvent) e;
			card.getToolbar().changeActiveButtton(event.getStatus());
		}

	}

}
