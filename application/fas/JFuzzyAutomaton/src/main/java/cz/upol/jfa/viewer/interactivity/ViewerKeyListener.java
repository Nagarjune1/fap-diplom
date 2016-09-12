package cz.upol.jfa.viewer.interactivity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cz.upol.jfa.viewer.JFuzzyAutomatonViewer;
import cz.upol.jfa.viewer.Position;

public class ViewerKeyListener implements KeyListener {
	private final JFuzzyAutomatonViewer jfa;

	public ViewerKeyListener(JFuzzyAutomatonViewer jfa) {
		this.jfa = jfa;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!jfa.isEnabled()) {
			return;
		}

		ViewerEventType type = ViewerEventType.KEYPRESS;

		Position where = null; // TODO
		ViewerSelection on = null; // TODO
		long when = e.getWhen();
		int id = e.getID();

		ViewerActionEvent event = new ViewerActionEvent(jfa, type, on, where, id,
				when);	//TODO uložení klávesy

		jfa.fireActions(event);

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
