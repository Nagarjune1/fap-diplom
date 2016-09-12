package cz.upol.jfa.editor.forms.transs;

import java.awt.Window;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.jfa.automata.BaseAutomatonToGUI;

public class JEditTransitionPane extends JAbstractTransPane {

	private static final long serialVersionUID = -3839169286682932425L;

	private Transition transition;
	private Degree degree;

	public JEditTransitionPane(Window owner, BaseAutomatonToGUI automaton,
			Transition transition, Degree degree) {

		super(owner, automaton);

		this.transition = transition;
		this.degree = degree;

		setupValues();
	}

	@Override
	public Transition getTransition() {
		return transition;
	}

	@Override
	public Degree getDegree() {
		return degree;
	}

	public void change(Symbol symbol, Degree degree) {
		State from = this.transition.getFrom();
		State to = this.transition.getTo();

		this.transition = new Transition(from, symbol, to);
		this.degree = degree;
	}

	@Override
	public void setupValues() {
		symbolCmbBx.setSelectedItem(transition.getOver());
		if (degreeCmbBx != null) {
			degreeCmbBx.setSelectedItem(degree);
		}
		submitButt.setText("Odebrat");
	}

}
