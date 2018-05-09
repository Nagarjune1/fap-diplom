package cz.upol.fapapp.fa.gui.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;

/**
 * Holds list of paths.
 * @author martin
 *
 */
public class Multipath {
	private final List<Path> paths;

	public Multipath() {
		paths = new ArrayList<>(5);
	}

	public void addPath(Path path) {
		paths.add(path);
	}

	public Path getLastPath() {
		if (paths.isEmpty()) {
			return null;
		} else {
			return paths.get(paths.size() - 1);
		}
	}

	public List<Path> getPaths() {
		return paths;
	}

	
	public List<PathSegment> listSegments() {
		return paths.stream() //
				.flatMap((p) -> p.getSegments().stream()).collect(Collectors.toList());
	}

	public List<Point2D> listPoint() {
		return paths.stream() //
				.flatMap((p) -> p.listPoints().stream()).collect(Collectors.toList());
	}

	///////////////////////////////////////////////////////////////////////////
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paths == null) ? 0 : paths.hashCode());
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
		Multipath other = (Multipath) obj;
		if (paths == null) {
			if (other.paths != null)
				return false;
		} else if (!paths.equals(other.paths))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Multipath [paths=" + paths + "]";
	}

}
