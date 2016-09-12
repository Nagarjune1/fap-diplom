package cz.upol.jfa.viewer.interactivity;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import cz.upol.automaton.misc.State;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.viewer.JFuzzyAutomatonViewer;
import cz.upol.jfa.viewer.Position;

public class ViewerMotionListener implements MouseMotionListener {

	private final JFuzzyAutomatonViewer jfa;

	public ViewerMotionListener(JFuzzyAutomatonViewer jfa) {
		this.jfa = jfa;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!jfa.isEnabled()) {
			return;
		}

		if (jfa.getSelection().isSelectedState()) {
			State state = jfa.getSelection().getSelectedState();
			// TODO bacha, pracujuju se selected. Co na to módy?
			Position newPosition = new Position(e.getPoint());

			AutomatonDesc.moveStateTo(jfa.getAutomaton(), state, newPosition);
			jfa.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
