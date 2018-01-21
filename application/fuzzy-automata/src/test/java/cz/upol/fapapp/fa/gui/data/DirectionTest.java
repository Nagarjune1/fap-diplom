package cz.upol.fapapp.fa.gui.data;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.geometry.Point2D;

public class DirectionTest {

	private static final double EPSILON = 0.001;
	private static final double PI_4 = Math.PI / 4.0;

	@Test
	public void testAngleToDirection() {
		// bases
		assertEquals(Direction.LEFT, Direction.angleToDirection(-4.0 * PI_4));
		assertEquals(Direction.DOWN_LEFT, Direction.angleToDirection(-3.0 * PI_4));
		assertEquals(Direction.DOWN, Direction.angleToDirection(-2.0 * PI_4));
		assertEquals(Direction.DOWN_RIGHT, Direction.angleToDirection(-1.0 * PI_4));
		assertEquals(Direction.RIGHT, Direction.angleToDirection(0.0 * PI_4));
		assertEquals(Direction.UP_RIGHT, Direction.angleToDirection(+1.0 * PI_4));
		assertEquals(Direction.UP, Direction.angleToDirection(+2.0 * PI_4));
		assertEquals(Direction.UP_LEFT, Direction.angleToDirection(+3.0 * PI_4));
		assertEquals(Direction.LEFT, Direction.angleToDirection(+4.0 * PI_4));

		// around the 180°
		assertEquals(Direction.LEFT, Direction.angleToDirection(+4.0 * PI_4 - 0.01));
		assertEquals(Direction.LEFT, Direction.angleToDirection(-4.0 * PI_4 + 0.01));

		// 60 and 120 °
		assertEquals(Direction.UP_RIGHT, Direction.angleToDirection(+1.0 * Math.PI / 3.0));
		assertEquals(Direction.UP_LEFT, Direction.angleToDirection(+2.0 * Math.PI / 3.0));
		assertEquals(Direction.DOWN_RIGHT, Direction.angleToDirection(-1.0 * Math.PI / 3.0));
		assertEquals(Direction.DOWN_LEFT, Direction.angleToDirection(-2.0 * Math.PI / 3.0));
	}

	@Test
	public void testComputeAngle() {
		final Point2D point00 = new Point2D(0.0, 0.0);
		final Point2D point01 = new Point2D(0.0, 1.0);
		final Point2D point10 = new Point2D(1.0, 0.0);
		final Point2D point11 = new Point2D(1.0, 1.0);

		assertEquals(0.0 * PI_4, Direction.computeAngle(point00, point10), EPSILON);
		assertEquals(+4.0 * PI_4, Direction.computeAngle(point10, point00), EPSILON);
		// assertEquals(-4.0 * PI_4, Direction.computeAngle(point10, point00),
		// EPSILON);
		assertEquals(+2.0 * PI_4, Direction.computeAngle(point00, point01), EPSILON);
		assertEquals(-2.0 * PI_4, Direction.computeAngle(point01, point00), EPSILON);
		assertEquals(+1.0 * PI_4, Direction.computeAngle(point00, point11), EPSILON);
		assertEquals(-3.0 * PI_4, Direction.computeAngle(point11, point00), EPSILON);
		assertEquals(+3.0 * PI_4, Direction.computeAngle(point10, point01), EPSILON);
		assertEquals(-1.0 * PI_4, Direction.computeAngle(point01, point10), EPSILON);
		assertEquals(Double.NaN, Direction.computeAngle(point00, point00), EPSILON);
		assertEquals(Double.NaN, Direction.computeAngle(point11, point11), EPSILON);

		final Point2D point12 = new Point2D(1.0, 2.0);
		assertEquals(Math.atan(2.0 / 1.0), Direction.computeAngle(point00, point12), EPSILON);
		// TODO completeme
	}

}
