package cz.upol.fas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import cz.upol.fas.components.JEditorCard;
import cz.upol.fas.components.JEditorsCardsPane;
import cz.upol.fas.components.JMainMenuBar;
import cz.upol.fas.config.AutomataType;
import cz.upol.fas.frames.JNewAutomataFrame;
import cz.upol.jfa.automata.BaseAutomatonToGUI;

public class JFASMainFrame extends JFrame {
	private static final long serialVersionUID = 8142310894645444242L;

	private static final String TITLE = "Fuzzy Automaton Simulator";
	private static final Dimension MINIMUM_SIZE = new Dimension(200, 200);
	private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

	private final DialsUtils dials = new DialsUtils(this);

	private JMainMenuBar mainMenuBar;

	private JEditorsCardsPane cardsPane;

	public JFASMainFrame() {
		super(TITLE);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initializeSizes();

		initializeComponents();
		pack();

	}

	public DialsUtils getFileUtils() {
		return dials;
	}

	private void initializeSizes() {
		setMinimumSize(MINIMUM_SIZE);
		setPreferredSize(PREFERRED_SIZE);
	}

	private void initializeComponents() {
		mainMenuBar = new JMainMenuBar(this);
		getContentPane().add(mainMenuBar, BorderLayout.NORTH);

		cardsPane = new JEditorsCardsPane(this);
		getContentPane().add(cardsPane, BorderLayout.CENTER);
	}

	public JEditorCard getCurrentCard() {
		if (cardsPane == null) {
			return null;
		}

		return cardsPane.getCurrentCard();
	}

	public void newAutomaton(AutomataType type) {
		JNewAutomataFrame newAutomataFrame;
		newAutomataFrame = new JNewAutomataFrame(this, type);
		newAutomataFrame.setVisible(true);

		if (newAutomataFrame.getSaved() == true) {
			BaseAutomatonToGUI automaton = newAutomataFrame.getAutomata();
			tryToOpenAutomata(automaton);
		}
	}

	public void openAutomaton() {
		File file = dials.openAutomatonFile();
		BaseAutomatonToGUI automaton = dials.loadAutomaton(file);

		tryToOpenAutomata(automaton);
	}
	

	private void tryToOpenAutomata(BaseAutomatonToGUI automaton) {
		if (automaton != null) {
			cardsPane.openAutomaton(automaton);
			mainMenuBar.setEnabledToActionsWRA(true);
		}
	}
	
	public void closeCurrentAutomaton() {
		if (dials.confirmCloseAutomaton()) {
			cardsPane.closeAutomaton(getCurrentCard());
		}
	}


	public void close() {
		if (dials.confirmClose()) {
			this.dispose();
		}
	}

}
