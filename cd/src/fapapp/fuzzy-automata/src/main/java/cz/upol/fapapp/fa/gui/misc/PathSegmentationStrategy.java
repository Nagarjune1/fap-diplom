package cz.upol.fapapp.fa.gui.misc;

import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.fa.gui.data.Multipath;
import cz.upol.fapapp.fa.gui.data.Path;
import cz.upol.fapapp.fa.gui.data.PathSegment;
import javafx.geometry.Point2D;

/**
 * Strategy to identify when to start new path segment and which direction the
 * path goes to.
 * 
 * @author martin
 *
 */
public interface PathSegmentationStrategy {

	/**
	 * Informs this strategy that new path started.
	 */
	public void newPathStarted();

	/**
	 * Is the newPosition part of the current segment or it should be part of
	 * the new one?
	 * 
	 * @param currentPath
	 * @param currentSegment
	 * @param newPosition
	 * @param shaking
	 * @return
	 */
	public boolean isNewSegment(Path currentPath, PathSegment currentSegment, Point2D newPosition, int shaking);

	/**
	 * Does this-strategy-specific things and converts given paths to word.
	 * 
	 * @param paths
	 * @param shakeReductionLevel
	 * @return
	 */
	public Word convertToWord(Multipath paths, int shakeReductionLevel);

}
