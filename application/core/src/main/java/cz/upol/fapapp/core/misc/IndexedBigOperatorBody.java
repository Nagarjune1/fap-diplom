package cz.upol.fapapp.core.misc;

/**
 * Lambda speficier for
 * {@link MathUtils#bigInfimum(java.util.Set, IndexedBigOperatorBody)} and or
 * {@link MathUtils#bigSupremum(java.util.Set, IndexedBigOperatorBody)}
 * 
 * @author martin
 *
 * @param <V>
 */
@FunctionalInterface
public interface IndexedBigOperatorBody<V> {
	public V get(int i);
}
