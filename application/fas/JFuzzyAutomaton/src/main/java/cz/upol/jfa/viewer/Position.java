package cz.upol.jfa.viewer;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Position extends Point2D.Double {
	private static final long serialVersionUID = 7479725414864193575L;

	public Position(double x, double y) {
		super(x, y);
	}

	public Position(int x, int y) {
		super(x, y);
	}

	public Position(Point point) {
		super(point.x, point.y);
	}

	public int getIntX() {
		return Math.round((float) super.getX());
	}

	public int getIntY() {
		return Math.round((float) super.getY());
	}

	public Dimension toDimension() {
		return new Dimension(getIntX(), getIntY());
	}

}
