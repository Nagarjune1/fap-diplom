package cz.upol.jfa.viewer.colors;

import java.awt.Color;

import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.jfa.config.Configuration;

public class ExportColors implements AbstractColorsDirectory {

	private final ColorsSet colors;

	public ExportColors(boolean colorful) {
		if (colorful) {
			colors = Configuration.get().getColourExportColors();
		} else {
			colors = Configuration.get().getBlackWhiteExportColors();
		}
	}

	@Override
	public Color getBackground() {
		return colors.getBackground();
	}

	@Override
	public Color getLabelBg() {
		return colors.getLabelBg();
	}

	@Override
	public Color getStateBg(State state) {
		return colors.getStateBg();
	}

	@Override
	public Color getStateCircle(State state) {
		return colors.getStateCircle();
	}

	@Override
	public Color getStateLabel(State state) {
		return colors.getStateLabel();
	}

	@Override
	public Color getInitialText(State state) {
		return colors.getInitialText();
	}

	@Override
	public Color getFiniteText(State state) {
		return colors.getFiniteText();
	}

	@Override
	public Color getEdgeArrow(AbstractEdge<?> line) {
		return colors.getEdgeArrow();
	}

	@Override
	public Color getEdgeLabel(AbstractEdge<?> line) {
		return colors.getEdgeLabel();
	}

}
