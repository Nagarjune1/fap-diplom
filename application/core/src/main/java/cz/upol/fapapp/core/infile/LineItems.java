package cz.upol.fapapp.core.infile;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LineItems implements Iterable<String> {
	private final List<String> items;

	public LineItems(List<String> items) {
		super();
		this.items = items;
	}

	public LineItems(String... items) {
		super();
		this.items = Arrays.asList(items);
	}

	public String getIth(int index) {
		if (index < items.size()) {
			return items.get(index);
		} else {
			return null;
		}
	}

	public LineItems rest(int fromIndex) {
		if (fromIndex < items.size()) {
			List<String> sublist = items.subList(fromIndex, items.size());
			return new LineItems(sublist);
		} else {
			return null;
		}
	}

	public List<String> getItems() {
		return items;
	}

	@Override
	public Iterator<String> iterator() {
		return items.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		LineItems other = (LineItems) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InputFileLineItems [items=" + items + "]";
	}

}
