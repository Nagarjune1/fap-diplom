package cz.upol.fapapp.fa.gui.misc;

import cz.upol.fapapp.fa.gui.data.Direction;
import cz.upol.fapapp.fa.gui.data.PathSegment;
import javafx.geometry.Point2D;

public class FromStartStrategy extends BaseSimpleSegmentStrategy {

	public FromStartStrategy() {
	}
	
	@Override
	protected boolean isNewSegmentBySegment(PathSegment currentSegment, Point2D newPosition, int shaking) {
		
		if (currentSegment.getPoints().size() < shaking) {
			return false;
		}
		
		Point2D fromPosition = currentSegment.getFirstPoint();
		Direction currentDirection = currentSegment.directionOnScreen();
		
		if (fromPosition == null || newPosition == null || currentDirection == null) {
			return false;
		}
		
		Direction newDirection = Direction.compute(fromPosition, newPosition);
		
		return !newDirection.equals(currentDirection);
	}



}
