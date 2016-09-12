package cz.upol.jfa.viewer.colors;

import java.awt.Color;

public class ColorsSet {
	public final static Color TRANSPARENT_WHITE = new Color(255, 255, 255, 0);

	/**
	 * Barva pozadí celého automatu.
	 */
	private Color background;
	/**
	 * Barva pozadí popisku mimo stav (tedy stupně počátečnosti a koncovosti a
	 * popisku hrany).
	 */
	private Color labelBg;
	/**
	 * Barva pozadí kruhu stavu.
	 */
	private Color stateBg;
	/**
	 * Barva kružnice stavu.
	 */
	private Color stateCircle;
	/**
	 * Barva popisku stavu.
	 */
	private Color stateLabel;
	/**
	 * Barva textu stupně počátečnosti stavu.
	 */
	private Color initialText;
	/**
	 * Barva textu stupně koncovosti stavu.
	 */
	private Color finiteText;
	/**
	 * Barva hrany.
	 */
	private Color edgeArrow;
	/**
	 * Barva popisku u hrany.
	 */
	private Color edgeLabel;

	public ColorsSet() {
	}

	public ColorsSet(Color background, Color stateBg, Color labelBg,
			Color stateFg, Color externLabelsFg, Color edgeFg) {

		setForegrounds(stateFg, externLabelsFg, edgeFg);
		setBackgrounds(background, stateBg, labelBg);
	}

	public ColorsSet(Color background, Color foreground) {

		setForegrounds(foreground);
		setBackgrounds(background);
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public Color getLabelBg() {
		return labelBg;
	}

	public void setLabelBg(Color labelBg) {
		this.labelBg = labelBg;
	}

	public Color getStateBg() {
		return stateBg;
	}

	public void setStateBg(Color stateBg) {
		this.stateBg = stateBg;
	}

	public Color getStateCircle() {
		return stateCircle;
	}

	public void setStateCircle(Color stateCircle) {
		this.stateCircle = stateCircle;
	}

	public Color getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(Color stateLabel) {
		this.stateLabel = stateLabel;
	}

	public Color getInitialText() {
		return initialText;
	}

	public void setInitialText(Color initialText) {
		this.initialText = initialText;
	}

	public Color getFiniteText() {
		return finiteText;
	}

	public void setFiniteText(Color finiteText) {
		this.finiteText = finiteText;
	}

	public Color getEdgeArrow() {
		return edgeArrow;
	}

	public void setEdgeArrow(Color edgeArrow) {
		this.edgeArrow = edgeArrow;
	}

	public Color getEdgeLabel() {
		return edgeLabel;
	}

	public void setEdgeLabel(Color edgeLabel) {
		this.edgeLabel = edgeLabel;
	}

	/**
	 * Nastaví barvy popředí.
	 * 
	 * @param state
	 *            barva kružnice a popisku stavu
	 * @param externLabels
	 *            barva stupňů počátečnosti a koncovosti stavu
	 * @param edge
	 *            barva hrany a jejího popisku
	 */
	public void setForegrounds(Color state, Color externLabels, Color edge) {
		this.stateCircle = state;
		this.stateLabel = state;
		this.initialText = externLabels;
		this.finiteText = externLabels;
		this.edgeArrow = edge;
		this.edgeLabel = edge;
	}

	/**
	 * Nastaví barvy popředí.
	 * 
	 * @param color
	 */
	public void setForegrounds(Color color) {
		setForegrounds(color, color, color);
	}

	/**
	 * Nastaví barvy pozadí.
	 * 
	 * @param background
	 *            barva pozadí celého automatu
	 * @param state
	 *            barva pozadí stavu
	 * @param label
	 *            barva pozadí popisků
	 */
	public void setBackgrounds(Color background, Color state, Color label) {
		this.background = background;
		this.labelBg = label;
		this.stateBg = state;
	}

	/**
	 * Nastaví barvy pozadí.
	 * 
	 * @param color
	 */
	public void setBackgrounds(Color color) {
		setBackgrounds(color, color, color);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((background == null) ? 0 : background.hashCode());
		result = prime * result
				+ ((edgeArrow == null) ? 0 : edgeArrow.hashCode());
		result = prime * result
				+ ((edgeLabel == null) ? 0 : edgeLabel.hashCode());
		result = prime * result
				+ ((finiteText == null) ? 0 : finiteText.hashCode());
		result = prime * result
				+ ((initialText == null) ? 0 : initialText.hashCode());
		result = prime * result + ((labelBg == null) ? 0 : labelBg.hashCode());
		result = prime * result + ((stateBg == null) ? 0 : stateBg.hashCode());
		result = prime * result
				+ ((stateCircle == null) ? 0 : stateCircle.hashCode());
		result = prime * result
				+ ((stateLabel == null) ? 0 : stateLabel.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColorsSet other = (ColorsSet) obj;
		if (background == null) {
			if (other.background != null)
				return false;
		} else if (!background.equals(other.background))
			return false;
		if (edgeArrow == null) {
			if (other.edgeArrow != null)
				return false;
		} else if (!edgeArrow.equals(other.edgeArrow))
			return false;
		if (edgeLabel == null) {
			if (other.edgeLabel != null)
				return false;
		} else if (!edgeLabel.equals(other.edgeLabel))
			return false;
		if (finiteText == null) {
			if (other.finiteText != null)
				return false;
		} else if (!finiteText.equals(other.finiteText))
			return false;
		if (initialText == null) {
			if (other.initialText != null)
				return false;
		} else if (!initialText.equals(other.initialText))
			return false;
		if (labelBg == null) {
			if (other.labelBg != null)
				return false;
		} else if (!labelBg.equals(other.labelBg))
			return false;
		if (stateBg == null) {
			if (other.stateBg != null)
				return false;
		} else if (!stateBg.equals(other.stateBg))
			return false;
		if (stateCircle == null) {
			if (other.stateCircle != null)
				return false;
		} else if (!stateCircle.equals(other.stateCircle))
			return false;
		if (stateLabel == null) {
			if (other.stateLabel != null)
				return false;
		} else if (!stateLabel.equals(other.stateLabel))
			return false;
		return true;
	}

	public String toString() {
		return "Colors [background=" + background + ", labelBg=" + labelBg
				+ ", stateBg=" + stateBg + ", stateCircle=" + stateCircle
				+ ", stateLabel=" + stateLabel + ", initialText=" + initialText
				+ ", finiteText=" + finiteText + ", edgeArrow=" + edgeArrow
				+ ", edgeLabel=" + edgeLabel + "]";
	}

}
