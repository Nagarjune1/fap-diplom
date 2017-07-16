package cz.upol.fapapp.core.misc;

@FunctionalInterface
public interface IteratingBigOperatorBody<V, I> {
	public V get(I item);
}
