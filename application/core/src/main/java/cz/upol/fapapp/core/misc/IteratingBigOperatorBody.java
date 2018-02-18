package cz.upol.fapapp.core.misc;

/**
 * Lambda speficier for
 * {@link MathUtils#bigInfimum(java.util.Set, IteratingBigOperatorBody)} and or
 * {@link MathUtils#bigSupremum(java.util.Set, IteratingBigOperatorBody)}
 * 
 * @author martin.
 *
 * @param <V>
 * @param <I>
 */
@FunctionalInterface
public interface IteratingBigOperatorBody<V, I> {
	public V get(I item);
}
