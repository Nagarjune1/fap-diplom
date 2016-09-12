package cz.upol.automaton.sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;

public abstract class HugeSet<E> implements Set<E> {

	private final Set<E> items;

	public HugeSet() {
		super();
		this.items = new HashSet<>();
	}

	public HugeSet(Set<E> items) {
		super();
		this.items = new HashSet<>(items);
	}

	public int size() {
		return items.size();
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	/**
	 * Zjistí, jestli by objekt měl být v této množině (pomocí
	 * {@link #isInSet(Object)}) a pokud ano, tak jej přidá mezi známé objekty a
	 * vrací true.
	 * 
	 * @param o
	 *            objekt
	 * @return ...
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {
		E item;
		try {
			item = (E) o;
		} catch (ClassCastException e) {
			return false;
		}

		boolean isInItems = items.contains(item);
		if (isInItems) {
			return true;
		}

		boolean isInSet = isInSet(item);
		if (isInSet) {
			items.add(item);
			return true;
		}

		return false;
	}

	/**
	 * Vrátí true, pokud objekt item měl být v této množině.
	 * 
	 * @param item
	 *            objekt
	 * @return ...
	 */
	public abstract boolean isInSet(E item);

	public Iterator<E> iterator() {
		return items.iterator();
	}

	public Object[] toArray() {
		return items.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return items.toArray(a);
	}

	public boolean add(E e) {
		return items.add(e);
	}

	public boolean remove(Object o) {
		return items.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return items.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return items.addAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return items.retainAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return items.removeAll(c);
	}

	public void clear() {
		items.clear();
	}

	/**
	 * Neimplementováno, vyvolá výjimku. TODO FIXME co s tím?
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException("HugeSet#spliterator");
	}

	@Override
	public String toString() {
		return "HugeSet [some items=" + items + "]";
	}

	/**
	 * Vrací, zda-li je zadaná množina "velkou" množinou.
	 * 
	 * @param set
	 *            množina
	 * @return ...
	 */
	public static boolean isSetHuge(Set<?> set) {
		return (set instanceof HugeSet);
	}
}
