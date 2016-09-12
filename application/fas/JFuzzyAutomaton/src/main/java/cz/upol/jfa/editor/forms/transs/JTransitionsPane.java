package cz.upol.jfa.editor.forms.transs;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import cz.upol.automaton.automata.ingredients.HasFuzzyDelta;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.Transition;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;

public class JTransitionsPane extends JPanel {

	private static final long serialVersionUID = -6227080083387716747L;

	private final List<ActionListener> listeners = new LinkedList<>();
	private final Window owner;

	private final BaseAutomatonToGUI automaton;

	private final Set<JEditTransitionPane> transitionsPanes;
	private JAddTranitionPane addPane;

	public JTransitionsPane(Window owner, BaseAutomatonToGUI automaton) {

		this.owner = owner;
		this.automaton = automaton;

		this.transitionsPanes = new HashSet<>();

		initializeComponents();

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

	}

	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}

	public void removeActionListener(ActionListener l) {
		listeners.remove(l);
	}

	private void initializeComponents() {

		addPane = new JAddTranitionPane(owner, automaton);
		addPane.addActionListener(new TransitionPaneActionListener(this));

		add(addPane);
	}

	public FuzzySet<Transition> getFuzzyTransitions() {
		FuzzySet<Transition> transitions = ((HasFuzzyDelta) automaton)
				.newTransitionsFuzzySet();

		for (JEditTransitionPane transPane : transitionsPanes) {
			Transition transition = transPane.getTransition();
			Degree degree = transPane.getDegree();

			transitions.insert(transition, degree);
		}

		return transitions;
	}

	public Set<Transition> getTransitions() {
		Set<Transition> transitions = new TreeSet<>();

		for (JEditTransitionPane transPane : transitionsPanes) {
			Transition transition = transPane.getTransition();

			transitions.add(transition);
		}

		return transitions;
	}

	public void setToTransitions(FuzzySet<Transition> transitions) {
		removeAll();
		transitionsPanes.clear();

		for (Transition transition : transitions) {
			Degree degree = AutomatonDesc.getDegreeOfTransition(automaton,
					transition);
			createAndAddPane(transition, degree);
		}

		add(addPane);
	}

	public void setToTransitions(Set<Transition> transitions) {
		removeAll();
		transitionsPanes.clear();

		for (Transition transition : transitions) {
			createAndAddPane(transition, null);
		}

		add(addPane);
	}

	private JEditTransitionPane createAndAddPane(Transition transition,
			Degree degree) {

		JEditTransitionPane pane = new JEditTransitionPane(owner, automaton,
				transition, degree);

		pane.addActionListener(new TransitionPaneActionListener(this));

		add(pane);

		transitionsPanes.add(pane);

		return pane;
	}

	public void addInvoked(Symbol symbol, Degree degree) {
		if (AutomatonDesc.hasDegreeOfTransition(automaton)) {
			if (symbol == null || degree == null) {
				return;
			}
		} else {
			if (symbol == null) {
				return;
			}
		}

		remove(addPane);

		Transition transition = new Transition(null, symbol, null);
		createAndAddPane(transition, degree);

		add(addPane);
		addPane.setupValues();

		fireEvent();

		revalidate();
		repaint();
	}

	public void changeInvoked(JEditTransitionPane pane, Symbol symbol,
			Degree degree) {

		if (symbol == null || degree == null) {	//TOFO FIME tady to neprojde
			return;
		}

		pane.change(symbol, degree);

		fireEvent();
	}

	public void removeInvoked(JEditTransitionPane pane) {
		transitionsPanes.remove(pane);
		remove(pane);

		fireEvent();

		revalidate();
		repaint();

	}

	public void fireEvent() {
		int id = (int) System.currentTimeMillis();
		String command = "Event on " + this.toString();
		long when = System.currentTimeMillis();

		ActionEvent e = new ActionEvent(this, id, command, when, 0);

		for (ActionListener l : listeners) {
			l.actionPerformed(e);
		}
	}

	public static class TransitionPaneActionListener implements ActionListener {

		private final JTransitionsPane transsPane;

		public TransitionPaneActionListener(JTransitionsPane transsPane) {
			this.transsPane = transsPane;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			TransPaneEvent event = (TransPaneEvent) e;

			if (event.getPane() instanceof JAddTranitionPane) {
				transsPane.addInvoked(event.getSymbol(), event.getDegree());
			} else {
				JEditTransitionPane pane = (JEditTransitionPane) event
						.getPane();

				if (event.getType() == TransPaneEventType.SUMBIT_BUTT_CLICKED) {
					transsPane.removeInvoked(pane);
				} else {
					transsPane.changeInvoked(pane, event.getSymbol(),
							event.getDegree());
				}
			}
		}
	}

}
