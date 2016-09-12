package cz.upol.jfa.editor.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cz.upol.automaton.automata.ingredients.HasFuzzyDelta;
import cz.upol.automaton.automata.ingredients.HasStates;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.automaton.misc.edges.DFAedge;
import cz.upol.automaton.misc.edges.NFAedge;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.editor.forms.transs.JTransitionsPane;
import cz.upol.jfa.utils.swing.JSaveableAndCloseableDial;
import cz.upol.jfa.utils.swing.cmbbxwe.JComboBoxWthExternalisable;

public class JEditEdgeDial extends JSaveableAndCloseableDial {

	private static final long serialVersionUID = 4414010999337357025L;

	private final BaseAutomatonToGUI automaton;

	private State from;
	private State to;
	private AbstractEdge<?> edge;

	private boolean statesEditable;

	private JTransitionsPane transPane;
	private JComboBoxWthExternalisable<State> fromStateCbx;
	private JComboBoxWthExternalisable<State> toStateCbx;

	public JEditEdgeDial(Frame owner, BaseAutomatonToGUI automaton) {
		super(owner, "Přechod", true);

		this.automaton = automaton;

		initializeComponents();
		setLocation(40, 40); // TODO něco rozumnýho ...
	}

	public boolean isStatesEditable() {
		return statesEditable;
	}

	public AbstractEdge<?> getEdge() {
		return edge;
	}

	@Override
	public JPanel createContentPane() {
		JPanel pane = new JPanel(new BorderLayout());
		JPanel statesPane = createStatesPane();

		transPane = new JTransitionsPane(this, automaton);

		pane.add(statesPane, BorderLayout.NORTH);

		JPanel dynamicPane = new JPanel();
		dynamicPane.add(transPane);

		JScrollPane scrollPane = new JScrollPane(dynamicPane);
		// TODO bylo by fajn dycky odskrolovat dolů ...
		scrollPane.setPreferredSize(new Dimension(500, 300));

		pane.add(scrollPane, BorderLayout.CENTER);
		return pane;
	}

	private JPanel createStatesPane() {
		Set<State> states = getAutomatonStates(automaton);

		JPanel pane = new JPanel(new FlowLayout());

		JLabel fromLabel = new JLabel("Přechody ze stavu");
		fromStateCbx = new JComboBoxWthExternalisable<>(states,
				State.EXTERNISATOR);

		JPanel spacer = new JPanel();
		// spacer.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel toLabel = new JLabel("do stavu");
		toStateCbx = new JComboBoxWthExternalisable<>(states,
				State.EXTERNISATOR);

		pane.add(fromLabel);
		pane.add(fromStateCbx);
		pane.add(spacer);
		pane.add(toLabel);
		pane.add(toStateCbx);

		return pane;
	}

	/**
	 * Otevře formulář pro vyrvoření úplně nové hrany (tj. včetně volby stavů z
	 * a do).
	 */
	public void setToNewEdge() {
		this.edge = null;
		this.statesEditable = true;

		setupComponents();
	}

	/**
	 * Otevře formulář pro editaci zadané hrany (tj, neumožní změnu stavů from a
	 * to).
	 * 
	 * @param edge
	 */
	public void setToEdge(AbstractEdge<?> edge) {
		this.edge = edge;
		this.statesEditable = false;

		setupComponents();

	}

	private void setupComponents() {
		// State from = null, to = null;
		FuzzySet<Transition> fuzzyTransitions = null;
		Set<Transition> transitions = null;

		if (edge != null) {
			from = edge.getFrom();
			to = edge.getTo();

			if (AutomatonDesc.hasDegreeOfTransition(automaton)) {
				fuzzyTransitions = ((NFAedge) edge).getTransitions();
			} else {
				transitions = ((DFAedge) edge).getTransitions();
			}
		}

		setupCbx(fromStateCbx, from);
		setupCbx(toStateCbx, to);

		if (AutomatonDesc.hasDegreeOfTransition(automaton)) {
			if (fuzzyTransitions == null) {
				fuzzyTransitions = ((HasFuzzyDelta) automaton)
						.newTransitionsFuzzySet();
			}
			transPane.setToTransitions(fuzzyTransitions);
		} else {
			if (transitions == null) {
				transitions = new TreeSet<>();
			}
			transPane.setToTransitions(transitions);
		}

		pack();
		setVisible(true);
	}

	private void setupCbx(JComboBoxWthExternalisable<State> stateCbbx,
			State state) {

		stateCbbx.dataUpdatedTo(getAutomatonStates(automaton));

		stateCbbx.setSelectedItem(state);

		stateCbbx.setEnabled(statesEditable);
	}

	@Override
	public List<String> tryToSave() {
		List<String> errors = new LinkedList<>();

		checkAndProcessStates(errors);

		if (!errors.isEmpty()) {
			return errors;
		}

		AbstractEdge<?> newEdge;
		if (AutomatonDesc.hasDegreeOfTransition(automaton)) {
			FuzzySet<Transition> transitions = checkAndProcessFuzzyTransitions(errors);
			newEdge = new NFAedge(from, to, transitions);

		} else {
			Set<Transition> transitions = checkAndProcessDetermTransitions(errors);
			newEdge = new DFAedge(from, to, transitions);
		}

		if (!errors.isEmpty()) {
			return errors;
		}

		this.edge = newEdge;

		return errors;
	}

	private void checkAndProcessStates(List<String> errors) {
		from = (State) fromStateCbx.getSelectedItem();
		if (from == null) {
			errors.add("Select 'from' state");
		} else {

		}

		to = (State) toStateCbx.getSelectedItem();
		if (to == null) {
			errors.add("Select 'to' state");
		}
	}

	private FuzzySet<Transition> checkAndProcessFuzzyTransitions(
			List<String> errors) {
		FuzzySet<Transition> oldTranss = transPane.getFuzzyTransitions();

		return setFuzzyTransitionsFromAndTo(oldTranss, from, to);
	}

	private Set<Transition> checkAndProcessDetermTransitions(List<String> errors) {
		
		System.out
		.println("beru si to");
		Set<Transition> oldTranss = transPane.getTransitions();

		Set<Transition> newTranss = setTransitionsFromAndTo(oldTranss, from, to);

		checkSymbolsDeterminism(from, newTranss, errors);

		return newTranss;
	}

	private void checkSymbolsDeterminism(State from, Set<Transition> newTranss,
			List<String> errors) {

		for (Transition transition : newTranss) {
			boolean incident = AutomatonDesc.hasIncidentingState(automaton,
					transition);

			if (incident) {
				errors.add("Cannot go over " + transition.getOver().getValue()
						+ ", symbol is used on another transition");
			}
		}
	}

	public static Set<State> getAutomatonStates(BaseAutomatonToGUI automaton) {
		Set<State> states = new TreeSet<>();

		for (State state : ((HasStates) automaton).iterateOverStates()) {
			states.add(state);
		}

		return states;
	}

	public FuzzySet<Transition> setFuzzyTransitionsFromAndTo(
			FuzzySet<Transition> oldTranss, State from, State to) {

		FuzzySet<Transition> newTranss = ((HasFuzzyDelta) automaton)
				.newTransitionsFuzzySet();

		for (Transition oldTrans : oldTranss) {
			Symbol over = oldTrans.getOver();
			Degree degree = oldTranss.find(oldTrans);

			Transition newTrans = new Transition(from, over, to);
			newTranss.insert(newTrans, degree);
		}

		return newTranss;
	}

	protected Set<Transition> setTransitionsFromAndTo(
			Set<Transition> oldTranss, State from, State to) {

		Set<Transition> newTranss = new TreeSet<>();

		for (Transition oldTrans : oldTranss) {
			Symbol over = oldTrans.getOver();

			newTranss.add(new Transition(from, over, to));
		}

		return newTranss;
	}

}
