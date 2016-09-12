package cz.upol.jfa.testingFrames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.ling.alphabets.CharactersAlphabet;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;
import cz.upol.jfa.editor.EditorActionEvent;
import cz.upol.jfa.editor.EditorStatus;
import cz.upol.jfa.editor.JFAEditor;
import cz.upol.jfa.io.IOManager;
import cz.upol.jfa.utils.RandomFATGCreator;
import cz.upol.jfa.utils.TestingAutomataToGUI;
import cz.upol.jfa.utils.xml.XMLFileException;

public class JFAEditorTestingFrame extends JFrame {

	private static final long serialVersionUID = 8486559904712500160L;
	private JFAEditor editor;
	private JRadioButton selectingModeButt;
	private JRadioButton addStateModeButt;
	private JRadioButton addEdgeModeButt;
	private JRadioButton deletingModeButt;

	public JFAEditorTestingFrame() {
		super("JFuzzyAutomatonEditor testing form");

		initializeComponents();

		setPreferredSize(new Dimension(900, 500));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	private void initializeComponents() {
		JPanel headers = new JPanel();
		headers.setLayout(new BoxLayout(headers, BoxLayout.PAGE_AXIS));

		final String description = "This frame demonstrates "
				+ JFAEditor.class.getName()
				+ " component."
				+ " By radio buttons can by changed internal state of editor behavior.";

		JTextArea desc = new JTextArea(description);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.setEditable(false);
		desc.setOpaque(false);
		headers.add(desc, BorderLayout.NORTH);

		JPanel controlsPane = new JPanel(new FlowLayout());
		controlsPane.add(initializeChangeModePane());

		JButton saveButt = new JButton("Save");
		saveButt.addActionListener(new SaveButtActionListener(this));
		controlsPane.add(saveButt);

		JButton loadButt = new JButton("Load");
		loadButt.addActionListener(new LoadButtActionListener(this));
		controlsPane.add(loadButt);

		JButton randNFAButt = new JButton("Random NFA");
		randNFAButt
				.addActionListener(new RandomButtActionListener(this, false));
		controlsPane.add(randNFAButt);

		JButton randDFAButt = new JButton("Random DFA");
		randDFAButt.addActionListener(new RandomButtActionListener(this, true));
		controlsPane.add(randDFAButt);

		headers.add(controlsPane);

		NondetFuzzyAutomatonToGUI automaton = TestingAutomataToGUI
				.createAnotherTriangle();
		editor = new JFAEditor(this, automaton);
		editor.addActionListener(new EditorActionListener(this));

		getContentPane().add(headers, BorderLayout.NORTH);
		getContentPane().add(editor, BorderLayout.CENTER);
	}

	private JPanel initializeChangeModePane() {
		JPanel modeChangePane = new JPanel(new FlowLayout());
		modeChangePane.setBorder(new TitledBorder(new LineBorder(modeChangePane
				.getForeground()), "State"));

		selectingModeButt = new JRadioButton("Selecting");
		selectingModeButt.addActionListener(new ChangeModeButtActionListener(
				this, EditorStatus.NORMAL));
		modeChangePane.add(selectingModeButt);
		selectingModeButt.setSelected(true);

		addStateModeButt = new JRadioButton("Add state");
		addStateModeButt.addActionListener(new ChangeModeButtActionListener(
				this, EditorStatus.ADDING_STATE));
		modeChangePane.add(addStateModeButt);

		addEdgeModeButt = new JRadioButton("Add edge");
		addEdgeModeButt.addActionListener(new ChangeModeButtActionListener(
				this, EditorStatus.ADDING_EDGE));
		modeChangePane.add(addEdgeModeButt);

		deletingModeButt = new JRadioButton("Delete");
		deletingModeButt.addActionListener(new ChangeModeButtActionListener(
				this, EditorStatus.DELETING));
		modeChangePane.add(deletingModeButt);

		ButtonGroup group = new ButtonGroup();
		group.add(selectingModeButt);
		group.add(addStateModeButt);
		group.add(addEdgeModeButt);
		group.add(deletingModeButt);

		return modeChangePane;
	}

	public void openAutomaton(BaseAutomatonToGUI automaton) {
		getContentPane().remove(editor);

		editor = new JFAEditor(this, automaton);
		editor.addActionListener(new EditorActionListener(this));

		getContentPane().add(editor, BorderLayout.CENTER);
		selectingModeButt.setSelected(true);

		pack();
		invalidate();
	}

	public void changeStatusOnControls(EditorStatus status) {
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

		private final JFAEditorTestingFrame frame;
		private final EditorStatus state;

		public ChangeModeButtActionListener(JFAEditorTestingFrame frame,
				EditorStatus state) {
			this.frame = frame;
			this.state = state;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.editor.setState(state);
		}

	}

	public static class EditorActionListener implements ActionListener {

		private final JFAEditorTestingFrame frame;

		public EditorActionListener(JFAEditorTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(e instanceof EditorActionEvent)) {
				return;
			}

			EditorActionEvent event = (EditorActionEvent) e;
			frame.changeStatusOnControls(event.getStatus());
		}

	}

	public static class LoadButtActionListener implements ActionListener {
		private final JFAEditorTestingFrame frame;

		public LoadButtActionListener(JFAEditorTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				File file = new File("automaton.xml");
				BaseAutomatonToGUI automaton = new IOManager().load(file);
				frame.openAutomaton(automaton);
				System.out.println("Načteno");
			} catch (XMLFileException e1) {
				JOptionPane.showMessageDialog(frame, e1.getMessage());
			}
		}

	}

	public static class SaveButtActionListener implements ActionListener {
		private final JFAEditorTestingFrame frame;

		public SaveButtActionListener(JFAEditorTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			BaseAutomatonToGUI automaton = frame.editor.getAutomaton();
			try {
				File file = new File("automaton.xml");
				new IOManager().save(automaton, file);
				System.out.println("Uloženo");
			} catch (XMLFileException e1) {
				JOptionPane.showMessageDialog(frame, e1.getMessage());
			}

		}

	}

	public static class RandomButtActionListener implements ActionListener {

		private final JFAEditorTestingFrame frame;
		private final boolean deterministic;

		public RandomButtActionListener(JFAEditorTestingFrame frame,
				boolean determinstic) {
			this.frame = frame;
			this.deterministic = determinstic;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			CharactersAlphabet alphabet = new CharactersAlphabet(true, false,
					false);

			BaseAutomatonToGUI automaton = new RandomFATGCreator().createFATG(
					deterministic, new LukasiewiczLogic(), alphabet, 5, 5, 2,
					4, "a", "b", "c");

			frame.openAutomaton(automaton);
		}

	}
}
