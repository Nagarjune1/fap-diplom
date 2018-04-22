package cz.upol.fapapp.fa.gui.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;

/**
 * Path contains from segments.
 * @author martin
 *
 */
public class Path {
	private final List<PathSegment> segments;

	public Path() {
		segments = new ArrayList<>(10);
	}

	public void addSegment(PathSegment segment) {
		segments.add(segment);
	}

	public PathSegment getLastSegment() {
		if (segments.isEmpty()) {
			return null;
		} else {
			return segments.get(segments.size() - 1);
		}
	}

	public List<PathSegment> getSegments() {
		return segments;
	}

	public List<Point2D> listPoints() {
		return segments.stream() //
				.flatMap((p) -> p.getPoints().stream()).collect(Collectors.toList());
	}
	///////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((segments == null) ? 0 : segments.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (segments == null) {
			if (other.segments != null)
				return false;
		} else if (!segments.equals(other.segments))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Path [segments=" + segments + "]";
	}

}
