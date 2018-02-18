package cz.upol.fapapp.core.misc;

import java.util.Set;

import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * Some methods to improove math stuff.
 * 
 * @author martin
 *
 */
public class MathUtils {
	/**
	 * Computer "big supremum", i.e. supremum for i = from to to of body.
	 * 
	 * @param from
	 * @param to
	 * @param body
	 * @return
	 */
	public static Degree bigSupremum(int from, int to, IndexedBigOperatorBody<Degree> body) {
		Degree result = Degree.ZERO;

		for (int i = from; i < to; i++) {
			Degree subresult = body.get(i);
			result = Degree.supremum(result, subresult);
		}

		return result;
	}

	/**
	 * Computer "big infimum", i.e. infimum for i = from to to of body.
	 * 
	 * @param from
	 * @param to
	 * @param body
	 * @return
	 */
	public static Degree bigInfimum(int from, int to, IndexedBigOperatorBody<Degree> body) {
		Degree result = Degree.ONE;

		for (int i = from; i < to; i++) {
			Degree subresult = body.get(i);
			result = Degree.infimum(result, subresult);
		}

		return result;
	}

	/**
	 * Computer "big supremum", i.e. supremum for each x of over of body.
	 * 
	 * @param over
	 * @param body
	 * @return
	 */
	public static <I> Degree bigSupremum(Set<I> over, IteratingBigOperatorBody<Degree, I> body) {
		Degree result = Degree.ZERO;

		for (I item : over) {
			Degree subresult = body.get(item);
			result = Degree.supremum(result, subresult);
		}

		return result;
	}

	/**
	 * Computer "big infimum", i.e. infimum for each x of over of body.
	 * 
	 * @param over
	 * @param body
	 * @return
	 */
	public static <I> Degree bigInfimum(Set<I> over, IteratingBigOperatorBody<Degree, I> body) {
		Degree result = Degree.ONE;

		for (I item : over) {
			Degree subresult = body.get(item);
			result = Degree.infimum(result, subresult);
		}

		return result;
	}

}
