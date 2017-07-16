package cz.upol.fapapp.core.misc;

@FunctionalInterface
public interface IndexedBigOperatorBody<V> {
	public V get(int i);
}
