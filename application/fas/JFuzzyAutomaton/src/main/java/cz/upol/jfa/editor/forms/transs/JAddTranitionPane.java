package cz.upol.jfa.editor.forms.transs;

import java.awt.Window;

import javax.swing.border.MatteBorder;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.misc.Transition;
import cz.upol.jfa.automata.BaseAutomatonToGUI;

public class JAddTranitionPane extends JAbstractTransPane {

	private static final long serialVersionUID = 5835304564596128655L;

	private Transition transition;
	private Degree degree;

	public JAddTranitionPane(Window owner, BaseAutomatonToGUI automaton) {
		super(owner, automaton);

		setupValues();

		setBorder(new MatteBorder(10, 0, 0, 0, getBackground()));
	}

	@Override
	public Transition getTransition() {
		return transition;
	}

	@Override
	public Degree getDegree() {
		return degree;
	}

	@Override
	public void setupValues() {
		symbolCmbBx.setSelectedItem(null);
		if (degreeCmbBx != null) { // !!!
			degreeCmbBx.setSelectedItem(null);
		}
		submitButt.setText("Přidat");
	}

}
