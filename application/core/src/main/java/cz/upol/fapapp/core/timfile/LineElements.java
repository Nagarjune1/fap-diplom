package cz.upol.fapapp.core.timfile;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Data object representing line of strings.
 * 
 * @author martin
 *
 */
public class LineElements implements Iterable<String> {
	private final List<String> elements;

	/**
	 * Creates of given list.
	 * 
	 * @param elements
	 */
	public LineElements(List<String> elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Creates of given list.
	 * 
	 * @param elements
	 */
	public LineElements(String... elements) {
		super();
		this.elements = Arrays.asList(elements);
	}

	/**
	 * Returns i-th element (or null if no such).
	 * 
	 * @param index
	 * @return
	 */
	public String getIth(int index) {
		if (index < elements.size()) {
			return elements.get(index);
		} else {
			return null;
		}
	}

	/**
	 * Returns index of first occurance of given element (or -1 if no such).
	 * 
	 * @param element
	 * @return
	 */
	public int firstIndexOf(String element) {
		return elements.indexOf(element);
	}

	/**
	 * Returns line containing elements after given index. If index > length,
	 * returns null.
	 * 
	 * @param fromIndex
	 * @return
	 */
	public LineElements rest(int fromIndex) {
		if (fromIndex < elements.size()) {
			List<String> sublist = elements.subList(fromIndex, elements.size());
			return new LineElements(sublist);
		} else {
			return null;
		}
	}

	/**
	 * Lists elements.
	 * 
	 * @return
	 */
	public List<String> getElements() {
		return elements;
	}

	/**
	 * Returns count of elements.
	 * 
	 * @return
	 */
	public int count() {
		return elements.size();
	}

	@Override
	public Iterator<String> iterator() {
		return elements.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
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
		LineElements other = (LineElements) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LineElements[" + elements + "]";
	}

}
