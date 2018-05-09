package cz.upol.fapapp.fa.gui.comp;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.gui.data.Direction;
import cz.upol.fapapp.fa.gui.data.Multipath;
import javafx.geometry.Point2D;

public class FxPathableCanvasTest {

	@Test
	@Ignore
	public void test() {
		Logger.get().warning("Skipped test");
		
		FxPathableCanvas canvas = new FxPathableCanvas();
		check(canvas, 0, null, null, null, null, null);

		canvas.startPathIn(new Point2D(-1, 0));
		check(canvas, 1, 1, 1, null, null, null);
		
		canvas.appendNewPoint(new Point2D(0, 0));
		check(canvas, 1, 1, 2, null, null, null);

		canvas.appendNewPoint(new Point2D(1, 0));
		check(canvas, 1, 1, 3, null, null, Direction.RIGHT);

		canvas.appendNewPoint(new Point2D(3, 0));
		check(canvas, 1, 1, 4, null, null, Direction.RIGHT);
		
		canvas.appendNewPoint(new Point2D(5, 2));
		check(canvas, 1, 2, 4, null, 2, Direction.UP_RIGHT);
		
		canvas.appendNewPoint(new Point2D(6, 3));
		check(canvas, 1, 2, 4, null, 3, Direction.UP_RIGHT);
		
		canvas.appendNewPoint(new Point2D(0, 3));
		check(canvas, 1, 3, 4, null, 3, Direction.LEFT);
		
		canvas.appendNewPoint(new Point2D(-1, 3));
		check(canvas, 1, 3, 4, null, 3, Direction.LEFT);
		
		canvas.finishPathIn(new Point2D(-2, 3));
		check(canvas, 1, 3, 4, null, 3, Direction.LEFT);
		
		
		canvas.startPathIn(new Point2D(10, 10));
		check(canvas, 2, null, null, null, null, null);
		
		canvas.appendNewPoint(new Point2D(9, 9));
		check(canvas, 2, null, null, null, null, Direction.DOWN_LEFT);
	
	}

	private void check(FxPathableCanvas canvas, Integer pathCount, //
			Integer firstPathSegmentCount, Integer firstSegmentPointsCount, //
			Integer secondPathSegmentCount, Integer secondSegmentPointsCount, //
			Direction lastSegmentDirection) {

		Multipath paths = canvas.getPaths();

		if (pathCount != null) {
			assertEquals("Different paths count", //
					pathCount.intValue(), paths.getPaths().size());
		}

		if (firstPathSegmentCount != null) {
			assertEquals("Different segments count in first path", //
					firstPathSegmentCount.intValue(), paths.getPaths().get(0).getSegments().size());
		}

		if (secondPathSegmentCount != null) {
			assertEquals("Different segments count in second path", //
					secondPathSegmentCount.intValue(), paths.getPaths().get(1).getSegments().size());
		}

		if (firstSegmentPointsCount != null) {
			assertEquals("Different first segment's points count in first path", //
					firstSegmentPointsCount.intValue(),
					paths.getPaths().get(0).getSegments().get(0).getPoints().size());
		}

		if (secondSegmentPointsCount != null) {
			assertEquals("Different second segment's points count in first path", //
					secondSegmentPointsCount.intValue(),
					paths.getPaths().get(0).getSegments().get(1).getPoints().size());
		}

		if (lastSegmentDirection != null) {
			assertEquals("Different last segment direction", //
					lastSegmentDirection, paths.getLastPath().getLastSegment().directionOnScreen());
		}

	}

}
