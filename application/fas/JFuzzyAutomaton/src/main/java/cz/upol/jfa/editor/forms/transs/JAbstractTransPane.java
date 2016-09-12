package cz.upol.jfa.editor.forms.transs;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.sets.Externisator;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.utils.swing.cmbbxwe.JComboBoxWthExternalisable;

public abstract class JAbstractTransPane extends JPanel {

	private static final long serialVersionUID = -2587746535604416199L;

	private final List<ActionListener> listeners = new LinkedList<>();
	protected final Window owner;

	private final BaseAutomatonToGUI automaton;

	protected JComboBoxWthExternalisable<Symbol> symbolCmbBx;
	protected JComboBoxWthExternalisable<Degree> degreeCmbBx;
	protected JButton submitButt;

	public JAbstractTransPane(Window owner, BaseAutomatonToGUI automaton) {
		super();

		this.owner = owner;
		this.automaton = automaton;

		initializeComponents();

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));

	}

	public abstract Transition getTransition();

	public abstract Degree getDegree();

	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}

	public void removeActionListener(ActionListener l) {
		listeners.remove(l);
	}

	protected void initializeComponents() {
		Alphabet alphabet = AutomatonDesc.alphabet(automaton);
		Externisator<Symbol> alphabetExternisator = alphabet
				.getSymbolsExternisator();

		JLabel overLabel = new JLabel(" přes symbol ");
		add(overLabel);

		symbolCmbBx = new JComboBoxWthExternalisable<>(alphabet,
				alphabetExternisator);

		symbolCmbBx.addActionListener(new SymbolCmbActionListener(this));
		add(symbolCmbBx);

		if (AutomatonDesc.hasDegreeOfTransition(automaton)) {
			ResiduedLattice residuedLattice = AutomatonDesc
					.residuedLattice(automaton);
			Set<Degree> logicElements = residuedLattice.getElements();
			Externisator<Degree> logicExternisator = residuedLattice
					.getUniverseElementsExternisator();

			JLabel degreeLabel = new JLabel(" ve stupni ");
			add(degreeLabel);

			degreeCmbBx = new JComboBoxWthExternalisable<>(logicElements,
					logicExternisator);
			degreeCmbBx.addActionListener(new DegreeCmbActionListener(this));
			add(degreeCmbBx);
		}

		submitButt = new JButton();
		submitButt.setPreferredSize(new Dimension(100, submitButt
				.getPreferredSize().height));
		submitButt.addActionListener(new TTPSubmitButtActionListener(this));
		add(submitButt);

		JLabel buttSpacer = new JLabel(" ");
		add(buttSpacer);

	}

	/**
	 * Potomci by si dle uvážení (až budou mít inicializována ve slotech všechna
	 * potřebná data) měla ručně tuto metodu zavolat v konstruktoru.
	 */
	public abstract void setupValues();

	public void submitButtClicked(int id, long when) {
		tryToFireEvent(TransPaneEventType.SUMBIT_BUTT_CLICKED, id, when);
	}

	public void degreeChanged(int id, long when) {
		tryToFireEvent(TransPaneEventType.DEGREE_CHANGED, id, when);
	}

	public void symbolChanged(int id, long when) {
		tryToFireEvent(TransPaneEventType.SYMBOL_CHANGED, id, when);
	}

	public void tryToFireEvent(TransPaneEventType type, int id, long when) {
		boolean valid = checkCorrectness();
		System.out
				.println("mám plivat? " + valid + ", páč: "
						+ symbolCmbBx.getSelectedItem() + " a " + (degreeCmbBx == null ? "Vůbec"
						: degreeCmbBx.getSelectedItem()));
		if (!valid) {
			return;
		}

		String command = "Event " + type + " on " + this.toString();

		Degree degree = degreeCmbBx == null ? null : (Degree) degreeCmbBx
				.getSelectedItem();// !!!
		Symbol symbol = (Symbol) symbolCmbBx.getSelectedItem();

		ActionEvent e = new TransPaneEvent(this, type, symbol, degree, id,
				command, when);

		for (ActionListener l : listeners) {
			l.actionPerformed(e);
		}
	}

	protected boolean checkCorrectness() {
		if (AutomatonDesc.hasDegreeOfTransition(automaton)) {
			return symbolCmbBx.getSelectedItem() != null
					&& degreeCmbBx.getSelectedItem() != null;
		} else {
			return symbolCmbBx.getSelectedItem() != null;
		}
	}

	public static class TTPSubmitButtActionListener implements ActionListener {

		private final JAbstractTransPane pane;

		public TTPSubmitButtActionListener(JAbstractTransPane pane) {
			this.pane = pane;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			pane.submitButtClicked(e.getID(), e.getWhen());
		}
	}

	public static class DegreeCmbActionListener implements ActionListener {
		private final JAbstractTransPane pane;

		public DegreeCmbActionListener(JAbstractTransPane pane) {
			this.pane = pane;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			pane.degreeChanged(e.getID(), e.getWhen());
		}

	}

	public static class SymbolCmbActionListener implements ActionListener {
		private final JAbstractTransPane pane;

		public SymbolCmbActionListener(JAbstractTransPane pane) {
			this.pane = pane;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			pane.symbolChanged(e.getID(), e.getWhen());
		}

	}

}