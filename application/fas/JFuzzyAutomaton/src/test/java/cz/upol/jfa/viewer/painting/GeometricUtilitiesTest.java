package cz.upol.jfa.viewer.painting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.painting.GeometricUtilities;

public class GeometricUtilitiesTest {
	private static final double EPSILON = 0.001;
	final GeometricUtilities geom = new GeometricUtilities();

	final Position p00 = new Position(0, 0);
	final Position p01 = new Position(0, 1);
	final Position p10 = new Position(1, 0);
	final Position p11 = new Position(1, 1);
	final Position pOn60Deg = new Position(-1000, (int) Math.sqrt(3000000.0));

	@Test
	public void testPositionsDistance() {
		final Position p03 = new Position(0, 3);
		final Position p40 = new Position(4, 0);

		// reflexivita
		assertEquals(0.0, geom.positionsDistance(p00, p00), EPSILON);
		assertEquals(0.0, geom.positionsDistance(p11, p11), EPSILON);

		// jednotkovost a symetrie
		assertEquals(1.0, geom.positionsDistance(p00, p01), EPSILON);
		assertEquals(1.0, geom.positionsDistance(p01, p00), EPSILON);

		// jednotková diagonála
		assertEquals(Math.sqrt(2.0), geom.positionsDistance(p00, p11), EPSILON);

		// pravoúhlý trojúhelník 3,4,5
		assertEquals(3.0, geom.positionsDistance(p00, p03), EPSILON);
		assertEquals(4.0, geom.positionsDistance(p00, p40), EPSILON);
		assertEquals(5.0, geom.positionsDistance(p03, p40), EPSILON);
	}

	@Test
	public void testPositionsAngle() {
		final Position p10 = new Position(1, 0);
		final Position pm10 = new Position(-1, 0);
		final Position p0m1 = new Position(0, -1);

		// reflexivita
		assertEquals(0.0, geom.positionsAngle(p00, p00), EPSILON);
		assertEquals(0.0, geom.positionsAngle(pm10, pm10), EPSILON);

		// jednotkovost (násobky pi/2)
		assertEquals(0.0, geom.positionsAngle(p00, p10), EPSILON);
		assertEquals(Math.PI, geom.positionsAngle(p00, pm10), EPSILON);
		assertEquals(Math.PI / 2, geom.positionsAngle(p00, p01), EPSILON);
		assertEquals(-Math.PI / 2, geom.positionsAngle(p00, p0m1), EPSILON);

		// antisymetrie k předchozím
		assertEquals(Math.PI, geom.positionsAngle(p10, p00), EPSILON);
		assertEquals(0.0, geom.positionsAngle(pm10, p00), EPSILON);
		assertEquals(-Math.PI / 2, geom.positionsAngle(p01, p00), EPSILON);
		assertEquals(Math.PI / 2, geom.positionsAngle(p0m1, p00), EPSILON);

		// pi/4 a 2pi/3
		assertEquals(Math.PI / 4, geom.positionsAngle(p00, p11), EPSILON);
		assertEquals(2 * Math.PI / 3, geom.positionsAngle(p00, pOn60Deg),
				EPSILON);

	}

	@Test
	public void testAngleOfLines() {
		Position pm10 = new Position(-1, 0);

		// nulový úhel
		assertEquals(0.0, geom.angleOfLines(p00, p10, p10), EPSILON);
		assertEquals(0.0, geom.angleOfLines(p11, p00, p00), EPSILON);

		// pravý úhel
		assertEquals(Math.PI / 2, geom.angleOfLines(p00, p10, p01), EPSILON);
		assertEquals(Math.PI / 2, geom.angleOfLines(p10, p00, p11), EPSILON);

		// 45°
		assertEquals(Math.PI / 4, geom.angleOfLines(p00, p11, p01), EPSILON);
		assertEquals(Math.PI / 4, geom.angleOfLines(p00, p10, p11), EPSILON);
		assertEquals(Math.PI / 4, geom.angleOfLines(p00, p01, p11), EPSILON);

		// 135°
		assertEquals(3 * Math.PI / 4, geom.angleOfLines(p00, pm10, p11),
				EPSILON);

		// 180°
		assertEquals(Math.PI, geom.angleOfLines(p00, pm10, p10), EPSILON);
	}

	@Test
	public void testDistanceOfLine() {
		// bod na přímce
		assertEquals(0.0, geom.distanceOfLine(p00, p01, p01), EPSILON);
		assertEquals(0.0, geom.distanceOfLine(p00, p01, p00), EPSILON);

		// jednotkovost
		assertEquals(1.0, geom.distanceOfLine(p00, p01, p11), EPSILON);
		assertEquals(1.0, geom.distanceOfLine(p00, p10, p11), EPSILON);

		// jednotková diagonála
		assertEquals(Math.sqrt(2) / 2, geom.distanceOfLine(p00, p11, p10),
				EPSILON);
		assertEquals(Math.sqrt(2) / 2, geom.distanceOfLine(p00, p11, p01),
				EPSILON);

	}

	@Test
	public void testAngleToIntersection() {
		final Position p99 = new Position(9, 9);
		final Position p23 = new Position(2, 3);
		final Position p04 = new Position(0, 4);

		final double r1 = 1.0;
		final double r2Sqrt2 = 2 * Math.sqrt(2.0);
		final double r2 = 2.0;
		final double r5 = 5.0;

		// prázdný průnik
		assertEquals(0.0, geom.angleToIntersection(p00, r1, p99, r1), EPSILON);

		// rovnostranný jednotkový trojúhelník
		assertEquals(Math.PI / 3, geom.angleToIntersection(p00, r1, p10, r1),
				EPSILON);

		// rovnostranný jednotkový diagonální trojúhelník
		assertEquals(Math.PI / 4, geom.angleToIntersection(p00, r1, p11, r1),
				EPSILON);

		// r1 = 2, r2 = 1 -> P=(2,2)
		double result = Math.atan(3.0 / 2.0) - Math.atan(2.0 / 2.0);
		assertEquals(result, geom.angleToIntersection(p00, r2Sqrt2, p23, r1),
				EPSILON);

		// výsledný úhel je tupý
		double result2 = Math.acos(-5.0 / 16.0);
		assertEquals(result2, geom.angleToIntersection(p00, r2, p04, r5),
				EPSILON);

	}

	@Test
	public void testOutsideOf() {
		final double dist05 = 0.5;
		final double dist1 = 1.0;
		final double dist2 = 2.0;

		// na ose X - netřeba posouvat
		assertEqlPositions(new Position(1, 0),
				geom.outsideOf(p00, p10, dist05), EPSILON);
		// na ose X - mezní případ - bod je v minimální vzdálenosti
		assertEqlPositions(new Position(1, 0), geom.outsideOf(p00, p10, dist1),
				EPSILON);
		// na ose X - posouváme
		assertEqlPositions(new Position(2, 0), geom.outsideOf(p00, p10, dist2),
				EPSILON);

		// na ose Y - netřeba posouvat
		assertEqlPositions(new Position(0, 1),
				geom.outsideOf(p00, p01, dist05), EPSILON);
		// na ose Y - mezní případ - bod je v minimální vzdálenosti
		assertEqlPositions(new Position(0, 1), geom.outsideOf(p00, p01, dist1),
				EPSILON);
		// na ose Y - posouváme
		assertEqlPositions(new Position(0, 2), geom.outsideOf(p00, p01, dist2),
				EPSILON);

		// z [0,1] až na [-1,1]
		assertEqlPositions(new Position(-1, 1),
				geom.outsideOf(p11, p01, dist2), EPSILON);
	}

	@Test
	public void testIsTriangleAcute() {
		Position phalf2 = new Position(0.5, 2);
		Position p21 = new Position(2, 1);

		assertTrue(geom.isTriangleAcuteAtBase(p00, p10, phalf2));
		assertFalse(geom.isTriangleAcuteAtBase(p00, p10, p21));
	}

	@Test
	public void testIsOnRightSide() {
		// je na úsečce
		assertFalse(geom.isOnRightSide(p00, p11, p11));

		// je vpravo
		assertTrue(geom.isOnRightSide(p00, p11, p10));
		assertTrue(geom.isOnRightSide(p00, p01, p10));
		assertTrue(geom.isOnRightSide(p11, p00, p01));

		// je vlevo
		assertFalse(geom.isOnRightSide(p00, p11, p01));
		assertFalse(geom.isOnRightSide(p00, p10, p11));
		assertFalse(geom.isOnRightSide(p11, p00, p10));
	}

	@Test
	public void testRadToDeg() {
		assertEquals(0.0, GeometricUtilities.radToDeg(0.0), EPSILON);
		assertEquals(30.0, GeometricUtilities.radToDeg(Math.PI / 6), EPSILON);
		assertEquals(45.0, GeometricUtilities.radToDeg(Math.PI / 4), EPSILON);
		assertEquals(90.0, GeometricUtilities.radToDeg(Math.PI / 2), EPSILON);
		assertEquals(180.0, GeometricUtilities.radToDeg(Math.PI), EPSILON);
		assertEquals(360.0, GeometricUtilities.radToDeg(2 * Math.PI), EPSILON);
		assertEquals(-90.0, GeometricUtilities.radToDeg(-Math.PI / 2), EPSILON);
	}

	private void assertEqlPositions(Position position1, Position position2,
			double epsilon) {

		assertEquals(position1 + " differs in X out of " + position2,//
				position1.getX(), position2.getX(), epsilon);

		assertEquals(position1 + " differs in X out of " + position2,//
				position1.getY(), position2.getY(), epsilon);
	}

}
