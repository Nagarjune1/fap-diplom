package cz.upol.jfa.viewer.painting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import cz.upol.jfa.viewer.Position;

public class ViewerConfig {

	public static final Set<String> FORMATS = new HashSet<>(
			Arrays.asList(ImageIO.getWriterFormatNames()));

	public static final ViewerConfig DEFAULT = new ViewerConfig();

	/**
	 * Poloměr kruhu stavu.
	 */
	private int stateRadius = 20;

	/**
	 * Rozestup kružnic u stavu
	 */
	private int stateMidcircDistance = 4;

	/**
	 * Velikost šipek
	 */
	private int arrowSize = 6;
	/**
	 * Vzdálenost začátků a konců šipek od středů stavů
	 */
	private int arrowPadding = stateRadius + 5;
	/**
	 * Posun popisku u šipky od středu stavu
	 */
	private Position arrowLabelPadding = new Position(arrowPadding + arrowSize,
			15);

	/**
	 * Posun středu smyčky od středu stavu
	 */
	private Position loopCenter = new Position(//
			-(3 * arrowPadding) / 4,//
			(3 * arrowPadding) / 4);//

	/**
	 * Poloměr smyčky
	 */
	private int loopRadius = 2 * arrowSize;

	/**
	 * Pozice popisku smyčky od středu stavu
	 */
	private Position loopLabelPading = new Position(loopCenter.getX(),
			loopCenter.getY() + 20);

	private String exportFormat = "PNG";

	public ViewerConfig() {
	}

	public int getStateRadius() {
		return stateRadius;
	}

	public void setStateRadius(int stateRadius) {
		this.stateRadius = stateRadius;
	}

	public int getStateMidcircDistance() {
		return stateMidcircDistance;
	}

	public void setStateMidcircDistance(int stateMidcircDistance) {
		this.stateMidcircDistance = stateMidcircDistance;
	}

	public int getArrowSize() {
		return arrowSize;
	}

	public void setArrowSize(int arrowSize) {
		this.arrowSize = arrowSize;
	}

	public int getArrowPadding() {
		return arrowPadding;
	}

	public void setArrowPadding(int arrowPadding) {
		this.arrowPadding = arrowPadding;
	}

	public Position getArrowLabelPadding() {
		return arrowLabelPadding;
	}

	public void setArrowLabelPadding(Position arrowLabelPadding) {
		this.arrowLabelPadding = arrowLabelPadding;
	}

	public Position getLoopCenter() {
		return loopCenter;
	}

	public void setLoopCenter(Position loopCenter) {
		this.loopCenter = loopCenter;
	}

	public int getLoopRadius() {
		return loopRadius;
	}

	public void setLoopRadius(int loopRadius) {
		this.loopRadius = loopRadius;
	}

	public Position getLoopLabelPading() {
		return loopLabelPading;
	}

	public void setLoopLabelPading(Position loopLabelPading) {
		this.loopLabelPading = loopLabelPading;
	}

	public String getExportFormat() {
		return exportFormat;
	}

	public void setExportFormat(String exportFormat) {
		this.exportFormat = exportFormat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((arrowLabelPadding == null) ? 0 : arrowLabelPadding
						.hashCode());
		result = prime * result + arrowPadding;
		result = prime * result + arrowSize;
		result = prime * result
				+ ((exportFormat == null) ? 0 : exportFormat.hashCode());
		result = prime * result
				+ ((loopCenter == null) ? 0 : loopCenter.hashCode());
		result = prime * result
				+ ((loopLabelPading == null) ? 0 : loopLabelPading.hashCode());
		result = prime * result + loopRadius;
		result = prime * result + stateMidcircDistance;
		result = prime * result + stateRadius;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewerConfig other = (ViewerConfig) obj;
		if (arrowLabelPadding == null) {
			if (other.arrowLabelPadding != null)
				return false;
		} else if (!arrowLabelPadding.equals(other.arrowLabelPadding))
			return false;
		if (arrowPadding != other.arrowPadding)
			return false;
		if (arrowSize != other.arrowSize)
			return false;
		if (exportFormat == null) {
			if (other.exportFormat != null)
				return false;
		} else if (!exportFormat.equals(other.exportFormat))
			return false;
		if (loopCenter == null) {
			if (other.loopCenter != null)
				return false;
		} else if (!loopCenter.equals(other.loopCenter))
			return false;
		if (loopLabelPading == null) {
			if (other.loopLabelPading != null)
				return false;
		} else if (!loopLabelPading.equals(other.loopLabelPading))
			return false;
		if (loopRadius != other.loopRadius)
			return false;
		if (stateMidcircDistance != other.stateMidcircDistance)
			return false;
		if (stateRadius != other.stateRadius)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ViewerConfig [stateRadius=" + stateRadius
				+ ", stateMidcircDistance=" + stateMidcircDistance
				+ ", arrowSize=" + arrowSize + ", arrowPadding=" + arrowPadding
				+ ", arrowLabelPadding=" + arrowLabelPadding + ", loopCenter="
				+ loopCenter + ", loopRadius=" + loopRadius
				+ ", loopLabelPading=" + loopLabelPading + ", exportFormat="
				+ exportFormat + "]";
	}

}
