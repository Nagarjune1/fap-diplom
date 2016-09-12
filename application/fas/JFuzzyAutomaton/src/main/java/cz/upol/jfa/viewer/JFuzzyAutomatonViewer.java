package cz.upol.jfa.viewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.viewer.colors.AbstractColorsDirectory;
import cz.upol.jfa.viewer.colors.ViewColorsDirectory;
import cz.upol.jfa.viewer.interactivity.ViewerActionEvent;
import cz.upol.jfa.viewer.interactivity.ViewerKeyListener;
import cz.upol.jfa.viewer.interactivity.ViewerMotionListener;
import cz.upol.jfa.viewer.interactivity.ViewerMouseListener;
import cz.upol.jfa.viewer.interactivity.ViewerSelection;
import cz.upol.jfa.viewer.painting.ViewerPainter;

public class JFuzzyAutomatonViewer extends JComponent {

	private static final long serialVersionUID = 9007613354371244289L;

	private final BaseAutomatonToGUI automaton;
	private final ViewerPainter painter;

	private final ViewerSelection selection = new ViewerSelection();

	private final List<ActionListener> listeners = new LinkedList<>();

	public JFuzzyAutomatonViewer(final BaseAutomatonToGUI automaton) {
		super();
		this.automaton = automaton;
		AbstractColorsDirectory colors = new ViewColorsDirectory(this);
		this.painter = new ViewerPainter(automaton, colors);

		addMouseListener(new ViewerMouseListener(this));
		addMouseMotionListener(new ViewerMotionListener(this));
		addKeyListener(new ViewerKeyListener(this));

		automatonChanged();
	}

	public BaseAutomatonToGUI getAutomaton() {
		return automaton;
	}

	public ViewerSelection getSelection() {
		return selection;
	}

	protected List<ActionListener> getListeners() {
		return listeners;
	}

	/**
	 * Provede změnu vybraného objektu podle toho, kam se kliklo (na stav,
	 * přechod, jinam - do prázdna). Vrací, zda došlo ke změně výběru.
	 * 
	 * @param clickedAt
	 * @return
	 */
	public boolean changeSelection(Position clickedAt) {
		ViewerSelection clickedOn = clickedOn(clickedAt);
		return changeSelection(clickedOn);
	}

	/**
	 * Provede změnu výběru na zadaný (může být i null). Vrací, zda došlo ke
	 * změně výběru. V případě potřeby překreslí.
	 * 
	 * @param newSelection
	 * @return
	 */
	public boolean changeSelection(ViewerSelection newSelection) {
		boolean changed;

		if (newSelection == null) {
			changed = selection.isSelected();
			selection.unselect();
		} else {
			changed = !selection.equals(newSelection);
			selection.select(newSelection);
		}

		if (changed) {
			automatonChanged();
		}

		return changed;
	}

	/**
	 * Zjistí na co bylo kliknuto (stav, hrana, nic) a to vrátí jako instanci
	 * výběru.
	 * 
	 * @param clickedAt
	 * @return
	 */
	public ViewerSelection clickedOn(Position clickedAt) {

		State state = getAutomaton().getProvider().getPositioning().findState(clickedAt);
		if (state != null) {
			return new ViewerSelection(state);
		}

		AbstractEdge<?> edge = getAutomaton().getProvider().getPositioning()
				.findTransition(clickedAt);
		if (edge != null) {
			return new ViewerSelection(edge);
		}

		return new ViewerSelection();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		painter.paintJFA(graphics);
	}

	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}

	public void fireActions(ViewerActionEvent event) {
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}

	}

	public void automatonChanged() {
		repaint();// TODO případně redraw či tak něco ...

		Dimension size = automaton.getProvider().getSize().toDimension();
		setPreferredSize(size);
	}

}
