package cz.upol.fas.components;

import java.awt.Dimension;
import java.io.File;

import cz.upol.fas.JFASMainFrame;
import cz.upol.fas.components.cardsPanel.JCloseableCard;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;

public class JEditorsCardsPane extends JCloseablesTabbedPane {

	private static final long serialVersionUID = -4968864101094469541L;
	public static final String UNNAMED_TITLE = "(nepojmenovaný)";

	private final JFASMainFrame mainFrame;

	public JEditorsCardsPane(JFASMainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;

		setPreferredSize(new Dimension(100, 100));

		addDefaultTab();

	}

	public JFASMainFrame getMainFrame() {
		return mainFrame;
	}

	public void addDefaultTab() {

		// TODO asi prázdnej (nedeterministickej) automat, ne?
		// A nebo prostě "bílo"?

		// JLabel content = new JLabel("Welcome to JFAS");
		// addTab("Hello", null, content, "Welcome");
	}

	public void openAutomaton(NondetFuzzyAutomatonToGUI automaton, File file) {
		JCloseableCard card = new JEditorCard(this, automaton, file);

		addTab(UNNAMED_TITLE, null, card, UNNAMED_TITLE);
		// card.changeTitle();
		setSelectedIndex(getTabCount() - 1);
	}

	public void openAutomaton(BaseAutomatonToGUI automaton) {
		JCloseableCard card = new JEditorCard(this, automaton);

		addTab(UNNAMED_TITLE, null, card, UNNAMED_TITLE);
		setSelectedIndex(getTabCount() - 1);
	}

	public void closeAutomaton(JCloseableCard card) {
		remove(card);
	}

	public JEditorCard getCurrentCard() {
		return (JEditorCard) getSelectedComponent();
	}

}
