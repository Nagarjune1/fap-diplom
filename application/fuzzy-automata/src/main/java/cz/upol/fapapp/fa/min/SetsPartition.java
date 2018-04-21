package cz.upol.fapapp.fa.min;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.misc.CollectionsUtils;

/**
 * ...
 * @author martin
 *
 * @param <E>
 */
public class SetsPartition<E> implements Comparable<SetsPartition<E>>, Cloneable {

	private final Set<Set<E>> partitions;

	protected SetsPartition(Set<Set<E>> partitions) {
		this.partitions = partitions;
	}

	public Set<Set<E>> getPartitions() {
		return partitions;
	}

	/////////////////////////////////////////////////////////////////

	public Set<E> findPartContaining(E elem) {
		for (Set<E> part : partitions) {
			boolean hasPartElem = part.contains(elem);
			if (hasPartElem) {
				return part;
			}
		}

		throw new IllegalArgumentException("No such elem " + elem + " in " + this);
	}

	public Set<E> merge(Set<E> first, Set<E> second) {
		boolean removed = true;
		removed &= partitions.remove(first);
		removed &= partitions.remove(second);

		if (!removed) {
			throw new IllegalArgumentException("Part " + first + " and/or " + second + " is not in " + this);
		}

		Set<E> merged = CollectionsUtils.join(first, second);

		partitions.add(merged);

		return merged;
	}

	public Set<E> split(Set<E> first, Set<E> second) {
		Set<E> merged = CollectionsUtils.join(first, second);

		boolean removed = partitions.remove(merged);
		if (!removed) {
			throw new IllegalArgumentException("Part (" + first + "+" + second + ") not in " + this);
		}

		partitions.add(first);
		partitions.add(second);

		return merged;
	}

	public Set<E> move(E elem, Set<E> newPart) {
		Set<E> currentPart = remove(elem);

		boolean removed = partitions.remove(newPart);
		if (!removed) {
			throw new IllegalArgumentException("Part " + newPart + " not in " + this);
		}

		if (!currentPart.isEmpty()) {
			partitions.add(currentPart);
		}

		newPart.add(elem);
		partitions.add(newPart);

		return newPart;
	}

	public Set<E> separate(E elem) {
		Set<E> currentPart = remove(elem);

		if (!currentPart.isEmpty()) {
			partitions.add(currentPart);
		}

		Set<E> newPart = CollectionsUtils.toSet(elem);
		partitions.add(newPart);

		return newPart;

	}

	private Set<E> remove(E elem) {
		Set<E> currentPart = findPartContaining(elem);

		partitions.remove(currentPart);
		currentPart.remove(elem);

		return currentPart;
	}

	/////////////////////////////////////////////////////////////////

	@Override
	public SetsPartition<E> clone() {
		Set<Set<E>> partitionsCopy = new HashSet<>(partitions);
		return new SetsPartition<>(partitionsCopy);
	}

	@Override
	public int compareTo(SetsPartition<E> o) {
		return this.partitions.size() - o.partitions.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partitions == null) ? 0 : partitions.hashCode());
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
		SetsPartition<?> other = (SetsPartition<?>) obj;
		if (partitions == null) {
			if (other.partitions != null)
				return false;
		} else if (!partitions.equals(other.partitions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SetsPartition" + partitions;
	}

	/////////////////////////////////////////////////////////////////

	public static <E> void prepareSplit(Set<E> firstPart, Set<E> secondPart, E first, E second) {
		boolean firstHasFirst = firstPart.contains(first);
		boolean secondHasFirst = secondPart.contains(first);
		boolean firstHasSecond = firstPart.contains(second);
		boolean secondHasSecond = secondPart.contains(second);

		if ((firstHasFirst && secondHasSecond) || (firstHasSecond && secondHasFirst)) {
			return; // they're already separed
		}

		if (firstHasFirst && firstHasSecond) {
			firstPart.remove(second);
			secondPart.add(second);
		}

		if (secondHasFirst && secondHasSecond) {
			secondPart.remove(second);
			firstPart.add(second);
		}
		
	}

	/////////////////////////////////////////////////////////////////

	public static <E> SetsPartition<E> createBulk(Set<E> set) {
		Set<E> copy = new HashSet<>(set);
		Set<Set<E>> parts = CollectionsUtils.toSet(copy);
		return new SetsPartition<>(parts);
	}

	public static <E> SetsPartition<E> createSinglies(Set<E> set) {
		Set<Set<E>> parts = set.stream() //
				.map((s) -> CollectionsUtils.toSet(s)) //
				.collect(Collectors.toSet());

		return new SetsPartition<>(parts);
	}

}
