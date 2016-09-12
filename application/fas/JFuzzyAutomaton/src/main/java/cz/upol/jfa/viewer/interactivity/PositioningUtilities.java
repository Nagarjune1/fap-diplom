package cz.upol.jfa.viewer.interactivity;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.upol.automaton.automata.ingredients.HasDelta;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.config.Configuration;
import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.painting.GeometricUtilities;
import cz.upol.jfa.viewer.painting.ViewerConfig;

public class PositioningUtilities {
	public final GeometricUtilities GEOM = new GeometricUtilities();

	private final Configuration config = Configuration.get();

	private Position nextStatePosition = config.getPositioning()
			.getFirstPosition();

	private final BaseAutomatonToGUI automaton;

	public PositioningUtilities(BaseAutomatonToGUI automaton) {
		this.automaton = automaton;
	}

	/**
	 * Vyhledá a vrátí (první) stav, který se nachází v dosahu coords.
	 * 
	 * @param coords
	 * @return
	 */
	public State findState(Position coords) {
		for (Entry<State, Position> stateEntry : automaton.getProvider()
				.getStatesPositions().entrySet()) {

			Position stateCenter = stateEntry.getValue();

			double stateClickRange = config.getPositioning()
					.getStateClickRange();

			if (GEOM.isInCircle(stateCenter, stateClickRange, coords)) {
				return stateEntry.getKey();
			}
		}

		return null;
	}

	/**
	 * Vyhledá a vrátí první hranu (i smyčku), která je v dosahu coords. Pokud
	 * existuje osobousměrná, vrací tu, pro niž je bod coords vpravo. Jinak vrací
	 * null.
	 * 
	 * @param coords
	 * @return
	 */
	public AbstractEdge<?> findTransition(Position coords) {
		Set<AbstractEdge<?>> edges = (Set<AbstractEdge<?>>) ((HasDelta<?, ?>) automaton).getEdges();

		for (AbstractEdge<?> edge : edges) {
			if (isOnEdge(edge, coords)) {
				return edge;
			}
		}

		return null;
	}

	/**
	 * Vrací true, pokud je bod coords na hraně (nebo smyčce) edge.
	 * 
	 * @param edge
	 * @param coords
	 * @return
	 */
	private boolean isOnEdge(AbstractEdge<?> edge, Position coords) {
		Position from = automaton.getProvider().findPosition(edge.getFrom());
		Position to = automaton.getProvider().findPosition(edge.getTo());
		PositioningConfig poss = config.getPositioning();
		ViewerConfig views = config.getViewerParams();

		if (isLoop(edge)) {
			Position loopCenter = GEOM.move(from, views.getLoopCenter());
			return GEOM.isOnCircle(loopCenter, views.getLoopRadius(), coords,
					poss.getEdgeClickRange());
		} else {
			boolean checkOrientation = hasEdgeInOtherDirection(edge);
			return GEOM.isOnLine(from, to, coords, poss.getEdgeClickRange(),
					checkOrientation);
		}
	}

	/**
	 * Vrací true, pokud má automat hranu edge (tzn. alespoň jeden přechod) také
	 * v opačném směru.
	 * 
	 * @param edge
	 * @return
	 */
	private boolean hasEdgeInOtherDirection(AbstractEdge<?> edge) {
		AbstractEdge<?> oppositeEdge = ((HasDelta<?, ?>) automaton).getEdge(edge.getTo(), edge.getFrom());
		return oppositeEdge.hasSomeTransition();
	}

	/**
	 * Vrací true, pokud je edge smyčka.
	 * 
	 * @param edge
	 * @return
	 */
	public boolean isLoop(AbstractEdge<?> edge) {
		return edge.getFrom().equals(edge.getTo());
	}

	/**
	 * Určí pozici posunu stavu state na pozici position. Pokud position
	 * koliduje s pozicí jiného stavu (je k němu blíž než
	 * {@link ViewerConfig#minStatesDistance}), tak ho odpovídajícím způsobem
	 * odsune (vrátí jako nový objekt).
	 * 
	 * @param state
	 * @param position
	 * @param statesPositions
	 * @return
	 */
	public Position moveStateToAllowed(State state, Position position,
			Map<State, Position> statesPositions) {

		final double minDistance = config.getPositioning()
				.getMinStatesDistance();
		Position newPosition = position;

		for (Entry<State, Position> entry : statesPositions.entrySet()) {
			Position statePosition = entry.getValue();

			if (entry.getKey().equals(state)) {
				continue;
			}

			boolean intersects = GEOM.isInCircle(statePosition, minDistance,
					newPosition);

			if (intersects) {
				newPosition = GEOM.outsideOf(statePosition, newPosition,
						minDistance);
			}
		}

		return newPosition;
	}

	/**
	 * Vrací stav, který má střed na pozici position. Pokud tkaový není vrací
	 * null.
	 * 
	 * @param position
	 * @return
	 */
	public State getStateOnPosition(Position position) {
		for (Entry<State, Position> stateEntry : automaton.getProvider().getStatesPositions()
				.entrySet()) {

			if (stateEntry.getValue().equals(position)) {
				return stateEntry.getKey();
			}
		}

		return null;
	}

	/**
	 * Vyčíslí (přibližně, nepočítá hrany a přidává rezervu) velikost automatu.
	 * Pokud je (X a/nebo Y) menší než minimální velikost, zvýší ji na ni.
	 * 
	 * @return
	 */
	public Position calculateSize() {
		double sizeX = 0, sizeY = 0;

		for (Entry<State, Position> stateEntry : automaton.getProvider().getStatesPositions()
				.entrySet()) {
			Position statePosition = stateEntry.getValue();

			double stateBoundX = statePosition.x + 5
					* config.getViewerParams().getStateRadius();
			double stateBoundY = statePosition.y + 3
					* config.getViewerParams().getStateRadius();

			sizeX = Math.max(sizeX, stateBoundX);
			sizeY = Math.max(sizeY, stateBoundY);
		}

		Position minimal = config.getPositioning().getMinimalSize();
		sizeX = Math.max(sizeX, minimal.getX());
		sizeY = Math.max(sizeY, minimal.getY());

		return new Position(sizeX, sizeY);
	}

	/**
	 * Vrátí pozici, do které se má vložit následující nový stav a posune se na
	 * další pozici.
	 * 
	 * @return
	 */
	public Position nextStatePosition() {
		Position currentStatePosition = nextStatePosition;

		Position nextPosOffset = config.getPositioning()
				.getNextPositionOffset();

		int nextX = nextStatePosition.getIntX() + nextPosOffset.getIntX();
		int nextY = nextStatePosition.getIntY() + nextPosOffset.getIntY();

		nextStatePosition = new Position(nextX, nextY);

		return currentStatePosition;
	}

	/**
	 * Vrátí pozici (na kružnici) zadaného stavu.
	 * 
	 * @param index
	 *            pořadové číslo stavu (0 <= index < count)
	 * @param count
	 *            celkový počet stavů
	 * @return
	 */
	public Position statePosition(int index, int count) {
		// FIXME pro count == 1 asi nefunguje správně. A co count == 0?

		PositioningConfig poss = config.getPositioning();

		double unitAngle = (1.0 / count) * (2 * Math.PI);
		double angle = index * unitAngle;
		double radius = (poss.getGeneratedPositionsDistance() / 2)
				/ (Math.sin(unitAngle / 2));

		double x = (Math.sin(angle) * radius) + radius
				+ poss.getGeneratedPositionsOffset().getX();
		double y = (Math.cos(angle) * radius) + radius
				+ poss.getGeneratedPositionsOffset().getY();

		return new Position(x, y);

	}

}
