package cz.upol.fapapp.fa.gui.misc;

import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.fa.gui.data.Multipath;
import cz.upol.fapapp.fa.gui.data.Path;
import cz.upol.fapapp.fa.gui.data.PathSegment;
import javafx.geometry.Point2D;

public interface PathSegmentationStrategy {

	public void newPathStarted();

	public boolean isNewSegment(Path currentPath, PathSegment currentSegment, Point2D newPosition, int shaking);

	public Word convertToWord(Multipath paths, int shakeReductionLevel);

	
}
