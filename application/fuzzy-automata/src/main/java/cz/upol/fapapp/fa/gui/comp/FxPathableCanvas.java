package cz.upol.fapapp.fa.gui.comp;

import java.util.stream.Collectors;

import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.gui.data.Direction;
import cz.upol.fapapp.fa.gui.data.Multipath;
import cz.upol.fapapp.fa.gui.data.Path;
import cz.upol.fapapp.fa.gui.data.PathSegment;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FxPathableCanvas extends Canvas {
	public static final EventType<Event> NEW_POINT_EVENT = new EventType<>("new point");
	public static final EventType<Event> NEW_SEGMENT_EVENT = new EventType<>("new segment");
	public static final EventType<Event> NEW_PATH_EVENT = new EventType<>("new path");

	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final double LINE_WIDTH = 1;

	private final IntegerProperty shake;

	private Multipath paths;

	public FxPathableCanvas() {
		this(400, 300);

	}

	public FxPathableCanvas(double width, double height) {
		super(width, height);

		this.shake = new SimpleIntegerProperty();

		reset(true, true);
		initListeners();
	}

	private void initListeners() {
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> mousePressedHandler(e));
		this.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> mouseDraggedHandler(e));
		this.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> mouseReleasedHandler(e));
	}

	public IntegerProperty shakeProperty() {
		return shake;
	}

	///////////////////////////////////////////////////////////////////////////

	protected Multipath getPaths() {
		return paths;
	}

	private Path getCurrentPath() {
		return paths.getLastPath();
	}

	private PathSegment getCurrentSegment() {
		return paths.getLastPath().getLastSegment();
	}

	private Point2D getLastPosition() {
		return paths.getLastPath().getLastSegment().getLastPoint();
	}

	private boolean hasAtLeastOnePoint() {
		return paths.getLastPath() != null //
				&& paths.getLastPath().getLastSegment() != null //
				&& !paths.getLastPath().getLastSegment().getPoints().isEmpty();
	}

	/////////////////////////////////////////////////////////////////////////

	public void reset(boolean resetPath, boolean clear) {
		if (resetPath) {
			startNewMultipath();
		}
		
		if (clear) {
			clear();
		}
	}

	private void startNewMultipath() {
		paths = new Multipath();

		// startNewPath();
	}

	private void startNewPath() {
		getPaths().addPath(new Path());

		startNewSegment();
	}

	private void startNewSegment() {
		if (hasAtLeastOnePoint()) {
			Point2D lastPosition = getLastPosition();
			getCurrentPath().addSegment(new PathSegment());
			getCurrentSegment().addPoint(lastPosition);
		} else {
			getCurrentPath().addSegment(new PathSegment());
		}

	}

	/////////////////////////////////////////////////////////////////////////

	private void drawAddedPoint(Point2D lastPoint, Point2D newPoint) {
		if (lastPoint != null) {
			drawLine(lastPoint, newPoint);
		}

	}

	private void drawLine(Point2D from, Point2D to) {
		GraphicsContext ctx = getGraphicsContext2D();
		ctx.setLineWidth(LINE_WIDTH);
		ctx.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());

	}

	private void clear() {
		GraphicsContext ctx = getGraphicsContext2D();

		ctx.setFill(BACKGROUND_COLOR);
		ctx.fillRect(0, 0, getWidth(), getHeight());
	}

	/////////////////////////////////////////////////////////////////////////

	protected void appendNewPoint(Point2D newPosition) {
		Direction dirToNew;

		Point2D lastPoint = getLastPosition();
		if (lastPoint != null) {
			dirToNew = Direction.compute(lastPoint, newPosition);
		} else {
			dirToNew = null;
		}

		Direction currentDir = getCurrentSegment().directionOnScreen();

		if (dirToNew != null && currentDir != null && dirToNew != currentDir) {
			startNewSegment();
			newSegmentStarted();
		}

		getCurrentSegment().addPoint(newPosition);
		drawAddedPoint(lastPoint, newPosition);
		newPointAdded();
	}

	protected void startPathIn(Point2D newPosition) {
		startNewPath();
		appendNewPoint(newPosition);
	}

	protected void finishPathIn(Point2D newPosition) {
		// do nothing in fact
		newPathAdded();
	}

	/////////////////////////////////////////////////////////////////////////

	private void mousePressedHandler(MouseEvent event) {
		Point2D newPosition = new Point2D(event.getX(), event.getY());
		startPathIn(newPosition);
	}

	private void mouseDraggedHandler(MouseEvent event) {
		Point2D newPosition = new Point2D(event.getX(), event.getY());
		appendNewPoint(newPosition);
	}

	private void mouseReleasedHandler(MouseEvent event) {
		Point2D newPosition = new Point2D(event.getX(), event.getY());
		finishPathIn(newPosition);
	}

	private void newPathAdded() {
		this.fireEvent(new PathableCanvasEvent(this, this, NEW_PATH_EVENT));
	}

	private void newSegmentStarted() {
		this.fireEvent(new PathableCanvasEvent(this, this, NEW_SEGMENT_EVENT));

	}

	private void newPointAdded() {
		this.fireEvent(new PathableCanvasEvent(this, this, NEW_POINT_EVENT));
	}

	public static class PathableCanvasEvent extends Event {

		public PathableCanvasEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
			super(source, target, eventType);
		}

		private static final long serialVersionUID = -3480641326151415601L;

	}

	/////////////////////////////////////////////////////////////////////////

	public Word getWord() {
		Logger.get().moreinfo("Computing on multipath: " + paths);
		Multipath paths = getPaths();
		int shake = this.shake.get();
		return convertToWord(paths, shake);
	}

	private Word convertToWord(Multipath paths, int shake) {
		return new Word(paths.listSegments().stream() //
				.filter((s) -> !Direction.NOPE.equals(s.getDirection())) //
				.filter((s) -> s.getPoints().size() > shake) //
				.map((s) -> s.getDirection().toSymbol()) //
				.collect(Collectors.toList()));
	}

}
