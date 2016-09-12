package cz.upol.jfa.viewer.painting;

import cz.upol.jfa.viewer.Position;

public class GeometricUtilities {

	public GeometricUtilities() {
	}

	/**
	 * Vrátí vzdálenost bodů from a to.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public double positionsDistance(Position from, Position to) {
		double xDiff = to.x - from.x;
		double yDiff = to.y - from.y;
		return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}

	/**
	 * Vrátí úhel, který svírá vektor (from, to) s kladnou částí osy x, a to z
	 * intervalu (-pi, pi). Pokud from == to vrací 0.0.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public double positionsAngle(Position from, Position to) {
		double xDiff = to.x - from.x;
		double yDiff = to.y - from.y;
		return Math.atan2(yDiff, xDiff);
	}

	/**
	 * Spočítá úhel, který svírá vektor (form, line1To) s (from, line2Too)
	 * 
	 * @param from
	 * @param line1To
	 * @param line2To
	 * @return
	 */
	public double angleOfLines(Position from, Position line1To, Position line2To) {
		double lengthP0P1 = positionsDistance(from, line1To);
		double lengthP0P2 = positionsDistance(from, line2To);
		double lengthP1P2 = positionsDistance(line1To, line2To);

		double numerator = lengthP1P2 * lengthP1P2 - lengthP0P1 * lengthP0P1
				- lengthP0P2 * lengthP0P2;
		double denominator = -2 * lengthP0P1 * lengthP0P2;

		if (denominator == 0.0) {
			return Math.PI / 2;
		} else {
			return Math.acos(numerator / denominator);
		}
	}

	/**
	 * Vrátí vzdálenost bodu Position od přímky dané body lineFrom a lineTo.
	 * 
	 * @param lineFrom
	 * @param lineTo
	 * @param position
	 * @return
	 */
	public double distanceOfLine(Position lineFrom, Position lineTo,
			Position position) {
		double angleOfLine = positionsAngle(lineFrom, lineTo);
		double angleOfEdge = positionsAngle(lineFrom, position);
		double angleAtFrom = angleOfLine - angleOfEdge;
		double lengthOfEdge = positionsDistance(lineFrom, position);

		return Math.abs(lengthOfEdge * Math.sin(angleAtFrom));
	}

	/**
	 * Vrátí (kladný) úhel, který svírá spojnice (center1, center2) středů
	 * kružnic s polopřímkou (center1, P), kde P je průsečík obou kružnic. Pokud
	 * se neprotínají vrací 0.0.
	 * 
	 * @param center1
	 * @param radius1
	 * @param center2
	 * @param radius2
	 * @return
	 */
	public double angleToIntersection(Position center1, double radius1,
			Position center2, double radius2) {

		double distBtwnCenters = positionsDistance(center1, center2);

		if (distBtwnCenters > (radius1 + radius2)) {
			return 0.0;
		}

		double numerator = radius2 * radius2 - radius1 * radius1
				- distBtwnCenters * distBtwnCenters;
		double denominator = -2 * radius1 * distBtwnCenters;

		return Math.acos(numerator / denominator);
	}

	/**
	 * Je-li potřeba, tak posune (vytvoří nový) bod dynamicPos dále od bodu staticPos směrem od
	 * bodu dynamicPos tak, aby byla jejich vzdálenost minimálně minDistance.
	 * Pokud je vzdálen dostatečně, vrací centerTo.
	 * 
	 * @param staticPos
	 * @param dynamicPos
	 * @param minDistance
	 * @return
	 */
	public Position outsideOf(Position staticPos, Position dynamicPos,
			double minDistance) {
		
		double distance = positionsDistance(staticPos, dynamicPos);
		if (distance > minDistance) {
			return dynamicPos;
		}
		
		double angle = positionsAngle(staticPos, dynamicPos);
		double newPx = staticPos.getX() + minDistance * Math.cos(angle);
		double newPy = staticPos.getY() + minDistance * Math.sin(angle);
		
		return new Position(newPx, newPy);
	}

	/**
	 * Vrací true, pokud je bod Position uvnitř kružnice (center, radius).
	 * 
	 * @param center
	 * @param radius
	 * @param position
	 * @return
	 */
	public boolean isInCircle(Position center, double radius, Position position) {
		double distance = center.distance(position);

		return distance < radius;
	}

	/**
	 * Vrací true, je-li bod Position na kružnici (center, radius) (+-delta)
	 * 
	 * @param center
	 * @param radius
	 * @param position
	 * @param delta
	 * @return
	 */
	public boolean isOnCircle(Position center, int radius, Position position,
			double delta) {

		double distance = center.distance(position);
		return distance > (radius - delta) && distance < (radius + delta);
	}

	/**
	 * Vrací true, je-li bod position na úsečce (from, to) (+-delta). Pokud je
	 * checkOrientation = true, pak vrací true pouze pokud se position nachází
	 * vpravo při pohledu ve směru úsečky.
	 * 
	 * @param from
	 * @param to
	 * @param position
	 * @param delta
	 * @param checkOrientation
	 * @return
	 */
	public boolean isOnLine(Position from, Position to, Position position,
			double delta, boolean checkOrientation) {

		double distance = distanceOfLine(from, to, position);

		if (distance > delta) {
			return false;
		}

		if (!isTriangleAcuteAtBase(from, to, position)) {
			return false;
		}

		if (checkOrientation) {
			if (isOnRightSide(from, to, position)) {
				return false;
			}
		}

		return true;

	}

	/**
	 * Vrací true, pokud má trojúhelník (basePoint1, basePoint2, p3) úhly při
	 * basePoint1 a basePoint2 ostré.
	 * 
	 * @param basePoint1
	 * @param basePoint2
	 * @param p3
	 */
	public boolean isTriangleAcuteAtBase(Position basePoint1,
			Position basePoint2, Position p3) {
		double angleAtBP1 = angleOfLines(basePoint1, basePoint2, p3);

		if (angleAtBP1 > Math.PI / 2) {
			return false;
		}

		double angleAtBP2 = angleOfLines(basePoint2, basePoint1, p3);

		if (angleAtBP2 > Math.PI / 2) {
			return false;
		}

		return true;
	}

	/**
	 * Vrací true, pokud je bod position "napravo" při pohledu ve směru úsečky
	 * (from, to).
	 * 
	 * @param from
	 * @param to
	 * @param position
	 * @return
	 */
	protected boolean isOnRightSide(Position from, Position to,
			Position position) {

		double angleFromTo = positionsAngle(from, to);
		double angleFromP = positionsAngle(from, position);

		if (angleFromTo < 0.0) {
			angleFromTo += 2 * Math.PI;
		}

		if (angleFromP < 0.0) {
			angleFromP += 2 * Math.PI;
		}

		return (angleFromP < angleFromTo);
	}

	/**
	 * Spočítá bod vzniklý posunutím bodu from o offset
	 * 
	 * @param from
	 * @param offset
	 * @return
	 */
	public Position move(Position from, Position offset) {
		return new Position(from.x + offset.x, from.y + offset.y);
	}

	/**
	 * Převede radiány na stupně.
	 * 
	 * @param rad
	 * @return
	 */
	public static double radToDeg(double rad) {
		return rad * 180.0 / Math.PI;
	}

}
