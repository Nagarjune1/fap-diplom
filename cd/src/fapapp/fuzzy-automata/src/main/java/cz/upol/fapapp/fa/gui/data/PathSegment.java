package cz.upol.fapapp.fa.gui.data;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

/**
 * Path segment is list of points.
 * 
 * @author martin
 *
 */
public class PathSegment {
	private final List<Point2D> points;

	public PathSegment() {
		points = new ArrayList<>(50);
	}

	public void addPoint(Point2D point) {
		points.add(point);
	}

	public Point2D getFirstPoint() {
		if (points.isEmpty()) {
			return null;
		} else {
			return points.get(0);
		}
	}

	public Point2D getLastPoint() {
		if (points.isEmpty()) {
			return null;
		} else {
			return points.get(points.size() - 1);
		}
	}

	public List<Point2D> getPoints() {
		return points;
	}

	public Direction getDirection() {
		Direction onScreen = directionOnScreen();
		if (onScreen != null) {
			return onScreen.flip();
		} else {
			return null;
		}
	}

	public Direction directionOnScreen() {
		if (points.size() < 2) {
			return null;
		}

		Point2D from = getFirstPoint();
		Point2D to = getLastPoint();

		Direction onScreen = Direction.compute(from, to);
		return onScreen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
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
		PathSegment other = (PathSegment) obj;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PathSegment [dir=" + getDirection() + ", count=" + points.size() + "]";
	}

}
