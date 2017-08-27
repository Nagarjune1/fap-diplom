package cz.upol.fapapp.cfa.misc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class TwoDimArray<E> implements Iterable<E> {

	private final int minIndex;
	private final int maxIndex;

	private final Map<Integer, Map<Integer, E>> items;

	public TwoDimArray(int minIndex, int maxIndex) {
		super();
		this.minIndex = minIndex;
		this.maxIndex = maxIndex;
		this.items = initEmpty(minIndex, maxIndex);
	}

	public TwoDimArray(int minIndex, int maxIndex, Map<Integer, Map<Integer, E>> items) {
		super();
		this.minIndex = minIndex;
		this.maxIndex = maxIndex;
		this.items = items;
	}

	public int getMinIndex() {
		return minIndex;
	}

	public int getMaxIndex() {
		return maxIndex;
	}

	public int getSize() {
		return maxIndex - minIndex;
	}

	/**************************************************************************/

	
	public E get(int i, int j) {
		checkBounds(i, j);

		Map<Integer, E> row = items.get(i);
		if (row == null) {
			throw new IllegalStateException("Missing row " + i);
		}
		E item = row.get(j);
		if (item == null) {
			throw new IllegalStateException("Missing value at (" + i + ", " + j + ")");
		}

		return item;
	}

	public void set(int i, int j, E item) {
		checkBounds(i, j);

		Map<Integer, E> row = items.get(i);
		row.put(j, item);
	}

	/**************************************************************************/

	private void checkBounds(int i, int j) {
		if (i < minIndex || i >= maxIndex) {
			throw new IllegalArgumentException("Out of bounds: i=" + i);
		}
		if (j < minIndex || j >= maxIndex) {
			throw new IllegalArgumentException("Out of bounds: j=" + j);
		}
	}

	private static <E> Map<Integer, Map<Integer, E>> initEmpty(int minIndex, int maxIndex) {
		Map<Integer, Map<Integer, E>> map = new HashMap<>();

		for (int i = minIndex; i < maxIndex; i++) {
			map.put(i, new HashMap<>());
		}

		return map;
	}

	/**************************************************************************/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + maxIndex;
		result = prime * result + minIndex;
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
		TwoDimArray<?> other = (TwoDimArray<?>) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (maxIndex != other.maxIndex)
			return false;
		if (minIndex != other.minIndex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TwoDimArray[(" + minIndex + " - " + maxIndex + "): " //
				+ items.values().stream() //
						.map((m) -> m.values().stream() //
								.map((e) -> e.toString()) //
								.collect(Collectors.joining(", "))) //

						.collect(Collectors.joining("; ")) //
				+ "]"; //
	}

	@Override
	public Iterator<E> iterator() {
		return new TwoDimArrIterator(this);
	}

	public class TwoDimArrIterator implements Iterator<E> {
		private Iterator<Map<Integer, E>> rowsIter;
		private Iterator<E> colsIter;

		public TwoDimArrIterator(TwoDimArray<E> arr) {
			super();
			rowsIter = items.values().iterator();
			colsIter = startNewRow();
		}

		@Override
		public boolean hasNext() {
			return rowsIter.hasNext() || colsIter.hasNext();
		}

		@Override
		public E next() {
			if (!colsIter.hasNext()) {
				colsIter = startNewRow();
			}

			E item = colsIter.next();
			return item;
		}

		private Iterator<E> startNewRow() {
			Map<Integer, E> nextRow = rowsIter.next();
			return nextRow.values().iterator();
		}
	}

}