package cz.upol.jfa.viewer.interactivity;

import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.painting.ViewerConfig;

public class PositioningConfig {

	public static final PositioningConfig DEFAULT = new PositioningConfig();

	/**
	 * Minimální velikost automatu (např. pokud je úplně prázdný).
	 */
	private Position minimalSize = new Position(400, 400);

	/**
	 * Minimální vzádlenost, na kterou se může dostat posunem stav k jinému.
	 * stavu
	 */
	private int minStatesDistance = 3 * ViewerConfig.DEFAULT.getStateRadius();

	/**
	 * Požadovaná vzdálenost uzlů na "kruhu" při generovaných pozicích.
	 */
	private int generatedPositionsDistance = 2 * minStatesDistance;

	/**
	 * Pozice nejlevějšího (x) a nehornějšího (y) bodu "kruhu", na kterém se
	 * budou nacházet stavy s vygenerovanými pozicemi.
	 */
	private Position generatedPositionsOffset = new Position(
			1.5 * getMinStatesDistance(), 1.5 * getMinStatesDistance());

	/**
	 * Posun dalšího přidaného uzlu.
	 */
	private Position nextPositionOffset = new Position(40, 65);

	/**
	 * Prvotní pozice přidaného uzlu.
	 */
	private Position firstPosition = new Position(
			3 * ViewerConfig.DEFAULT.getStateRadius(),
			3 * ViewerConfig.DEFAULT.getStateRadius());

	/**
	 * Vzdálenost od středu stavu, která je považována za pozici (kliku) ve
	 * stavu
	 */
	private int stateClickRange = ViewerConfig.DEFAULT.getStateRadius();

	/**
	 * Vzdálenost od hrany, která je považována za pozici (kliku) na hranu
	 */
	private int edgeClickRange = 2 * ViewerConfig.DEFAULT.getArrowSize();

	public PositioningConfig() {
	}

	public Position getMinimalSize() {
		return minimalSize;
	}

	public int getGeneratedPositionsDistance() {
		return generatedPositionsDistance;
	}

	public void setGeneratedPositionsDistance(int generatedPositionsDistance) {
		this.generatedPositionsDistance = generatedPositionsDistance;
	}

	public Position getGeneratedPositionsOffset() {
		return generatedPositionsOffset;
	}

	public void setGeneratedPositionsOffset(Position generatedPositionsOffset) {
		this.generatedPositionsOffset = generatedPositionsOffset;
	}

	public Position getNextPositionOffset() {
		return nextPositionOffset;
	}

	public void setMinimalSize(Position minimalSize) {
		this.minimalSize = minimalSize;
	}

	public void setNextPositionOffset(Position nextPositionOffset) {
		this.nextPositionOffset = nextPositionOffset;
	}

	public int getStateClickRange() {
		return stateClickRange;
	}

	public void setStateClickRange(int stateClickRange) {
		this.stateClickRange = stateClickRange;
	}

	public int getEdgeClickRange() {
		return edgeClickRange;
	}

	public void setEdgeClickRange(int edgeClickRange) {
		this.edgeClickRange = edgeClickRange;
	}

	public Position getFirstPosition() {
		return firstPosition;
	}

	public void setFirstPosition(Position firstPosition) {
		this.firstPosition = firstPosition;
	}

	public int getMinStatesDistance() {
		return minStatesDistance;
	}

	public void setMinStatesDistance(int minStatesDistance) {
		this.minStatesDistance = minStatesDistance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + edgeClickRange;
		result = prime * result
				+ ((firstPosition == null) ? 0 : firstPosition.hashCode());
		result = prime * result + generatedPositionsDistance;
		result = prime
				* result
				+ ((generatedPositionsOffset == null) ? 0
						: generatedPositionsOffset.hashCode());
		result = prime * result + minStatesDistance;
		result = prime * result
				+ ((minimalSize == null) ? 0 : minimalSize.hashCode());
		result = prime
				* result
				+ ((nextPositionOffset == null) ? 0 : nextPositionOffset
						.hashCode());
		result = prime * result + stateClickRange;
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
		PositioningConfig other = (PositioningConfig) obj;
		if (edgeClickRange != other.edgeClickRange)
			return false;
		if (firstPosition == null) {
			if (other.firstPosition != null)
				return false;
		} else if (!firstPosition.equals(other.firstPosition))
			return false;
		if (generatedPositionsDistance != other.generatedPositionsDistance)
			return false;
		if (generatedPositionsOffset == null) {
			if (other.generatedPositionsOffset != null)
				return false;
		} else if (!generatedPositionsOffset
				.equals(other.generatedPositionsOffset))
			return false;
		if (minStatesDistance != other.minStatesDistance)
			return false;
		if (minimalSize == null) {
			if (other.minimalSize != null)
				return false;
		} else if (!minimalSize.equals(other.minimalSize))
			return false;
		if (nextPositionOffset == null) {
			if (other.nextPositionOffset != null)
				return false;
		} else if (!nextPositionOffset.equals(other.nextPositionOffset))
			return false;
		if (stateClickRange != other.stateClickRange)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PositioningConfig [minimalSize=" + minimalSize
				+ ", minStatesDistance=" + minStatesDistance
				+ ", generatedPositionsDistance=" + generatedPositionsDistance
				+ ", generatedPositionsOffset=" + generatedPositionsOffset
				+ ", nextPositionOffset=" + nextPositionOffset
				+ ", firstPosition=" + firstPosition + ", stateClickRange="
				+ stateClickRange + ", edgeClickRange=" + edgeClickRange + "]";
	}

}