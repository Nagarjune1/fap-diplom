package cz.upol.jfa.viewer.colors;

import java.awt.Color;

import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.jfa.viewer.JFuzzyAutomatonViewer;

public class ViewColorsDirectory implements AbstractColorsDirectory {
	private final ColorsSet normal = DefaultColors.getNormalViewColors();
	private final ColorsSet selected = DefaultColors.getSelectedViewColors();
	private final ColorsSet disabled = DefaultColors.getDisabledViewColors();

	private final JFuzzyAutomatonViewer automaton;

	public ViewColorsDirectory(JFuzzyAutomatonViewer automaton) {
		this.automaton = automaton;
	}

	/**
	 * Zvolí aktuální sadu barev (normal/disabled)
	 * 
	 * @return
	 */
	protected ColorsSet chooseColors() {
		if (!automaton.isEnabled()) {
			return disabled;
		} else {
			return normal;
		}
	}

	/**
	 * Zvolí aktuální sadu barev pro zvolený stav
	 * 
	 * @param ofState
	 * @return
	 */
	protected ColorsSet chooseColors(State ofState) {
		if (!automaton.isEnabled()) {
			return disabled;
		}

		if (automaton.getSelection().isSelectedState(ofState)) {
			return selected;
		} else {
			return normal;
		}
	}

	/**
	 * Zvolí aktuální sadu barev pro zvolenou hranu
	 * 
	 * @param edge
	 * @return
	 */
	protected ColorsSet chooseColors(AbstractEdge<?>  edge) {
		if (!automaton.isEnabled()) {
			return disabled;
		}

		if (automaton.getSelection().isSelectedEdge(edge)) {
			return selected;
		} else {
			return normal;
		}
	}

	@Override
	public Color getBackground() {
		return chooseColors().getBackground();
	}

	@Override
	public Color getLabelBg() {
		return chooseColors().getLabelBg();
	}

	@Override
	public Color getStateBg(State state) {
		return chooseColors(state).getStateBg();
	}

	@Override
	public Color getStateCircle(State state) {
		return chooseColors(state).getStateCircle();
	}

	@Override
	public Color getStateLabel(State state) {
		return chooseColors(state).getStateLabel();
	}

	@Override
	public Color getInitialText(State state) {
		return chooseColors(state).getInitialText();
	}

	@Override
	public Color getFiniteText(State state) {
		return chooseColors(state).getFiniteText();
	}

	@Override
	public Color getEdgeArrow(AbstractEdge<?> line) {
		return chooseColors(line).getEdgeArrow();
	}

	@Override
	public Color getEdgeLabel(AbstractEdge<?>  line) {
		return chooseColors(line).getEdgeLabel();
	}

}
