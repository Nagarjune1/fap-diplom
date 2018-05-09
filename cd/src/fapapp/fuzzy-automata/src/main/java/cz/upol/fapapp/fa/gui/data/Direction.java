package cz.upol.fapapp.fa.gui.data;

import cz.upol.fapapp.core.ling.Symbol;
import javafx.geometry.Point2D;

/**
 * Represents the direction of segment or path at all. The directon is based on
 * angle and can be one of eight ones (left, top, right, bottom and all four
 * between them).
 * 
 * @author martin
 *
 */
public enum Direction {
	NOPE("-"), //
	UP("u"), LEFT("l"), RIGHT("r"), DOWN("d"), //
	UP_LEFT("ul"), UP_RIGHT("ur"), DOWN_LEFT("dl"), DOWN_RIGHT("dr"); //

	private final Symbol symbol;

	private Direction(String symbolLabel) {
		this.symbol = new Symbol(symbolLabel);
	}

	public Symbol toSymbol() {
		return this.symbol;
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Computes direction between theese two points.
	 * @param from
	 * @param to
	 * @return
	 */
	public static Direction compute(Point2D from, Point2D to) {
		double angle = computeAngle(from, to);
		return angleToDirection(angle);
	}

	/**
	 * Converts angle to given direction. The angle may be between -pi and +pi.
	 * @param angle
	 * @return
	 */
	protected static Direction angleToDirection(double angle) {
		if (angle >= (+7 * Math.PI / 8) || angle < (-7 * Math.PI / 8)) {
			return Direction.LEFT;
		}
		if (angle >= (-7 * Math.PI / 8) && angle < (-5 * Math.PI / 8)) {
			return Direction.DOWN_LEFT;
		}
		if (angle >= (-5 * Math.PI / 8) && angle < (-3 * Math.PI / 8)) {
			return Direction.DOWN;
		}
		if (angle >= (-3 * Math.PI / 8) && angle < (-1 * Math.PI / 8)) {
			return Direction.DOWN_RIGHT;
		}
		if (angle >= (-1 * Math.PI / 8) && angle < (+1 * Math.PI / 8)) {
			return Direction.RIGHT;
		}
		if (angle >= (+1 * Math.PI / 8) && angle < (+3 * Math.PI / 8)) {
			return Direction.UP_RIGHT;
		}
		if (angle >= (+3 * Math.PI / 8) && angle < (+5 * Math.PI / 8)) {
			return Direction.UP;
		}
		if (angle >= (+5 * Math.PI / 8) && angle < (+7 * Math.PI / 8)) {
			return Direction.UP_LEFT;
		}

		return Direction.NOPE;
	}

	/**
	 * Computes angle between theese two points.
	 * @param from
	 * @param to
	 * @return
	 */
	protected static double computeAngle(Point2D from, Point2D to) {
		double diffX = to.getX() - from.getX();
		double diffY = to.getY() - from.getY();

		if (diffX == 0 && diffY == 0) {
			return Double.NaN;
		}

		return Math.atan2(diffY, diffX);
	}
	
	/**
	 * Flips, that means inverts the direction roun the X-axis, of this direction.  
	 * @return
	 */
	public Direction flip() {
		switch (this) {
		case DOWN:
			return Direction.UP;
		case DOWN_LEFT:
			return Direction.UP_LEFT;
		case DOWN_RIGHT:
			return Direction.UP_RIGHT;
		case LEFT:
			return LEFT;
		case NOPE:
			return NOPE;
		case RIGHT:
			return RIGHT;
		case UP:
			return Direction.DOWN;
		case UP_LEFT:
			return Direction.DOWN_LEFT;
		case UP_RIGHT:
			return Direction.DOWN_RIGHT;
		default:
			return null;
		}
	}
}
