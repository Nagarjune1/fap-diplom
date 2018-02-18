package cz.upol.fapapp.fa.gui.misc;

import java.util.stream.Collectors;

import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.fa.gui.data.Direction;
import cz.upol.fapapp.fa.gui.data.Multipath;
import cz.upol.fapapp.fa.gui.data.Path;
import cz.upol.fapapp.fa.gui.data.PathSegment;
import javafx.geometry.Point2D;

/**
 * Base strategy. Does nothing when new path started and converts to word
 * ignoring {@link Direction.NOPE}s and short segments.
 * 
 * @author martin
 *
 */
public abstract class BaseSimpleSegmentStrategy implements PathSegmentationStrategy {

	public BaseSimpleSegmentStrategy() {
		super();
	}

	@Override
	public boolean isNewSegment(Path currentPath, PathSegment currentSegment, Point2D newPosition, int shaking) {
		return isNewSegmentBySegment(currentSegment, newPosition, shaking);
	}

	protected abstract boolean isNewSegmentBySegment(PathSegment currentSegment, Point2D newPosition, int shaking);

	@Override
	public Word convertToWord(Multipath paths, int shake) {
		return new Word(paths.listSegments().stream() //
				.filter((s) -> !Direction.NOPE.equals(s.getDirection())) //
				.filter((s) -> s.getPoints().size() > shake) //
				.map((s) -> s.getDirection().toSymbol()) //
				.collect(Collectors.toList()));

	}

	@Override
	public void newPathStarted() {
		// do nothing here ...
	}

}