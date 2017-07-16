package cz.upol.fapapp.core.misc;

import java.util.Set;

import cz.upol.fapapp.core.fuzzy.Degree;

public class MathUtils {
	public static Degree bigSupremum(int from, int to, IndexedBigOperatorBody<Degree> body) {
		Degree result = Degree.ZERO;

		for (int i = from; i < to; i++) {
			Degree subresult = body.get(i);
			result = Degree.supremum(result, subresult);
		}

		return result;
	}

	public static Degree bigInfimum(int from, int to, IndexedBigOperatorBody<Degree> body) {
		Degree result = Degree.ONE;

		for (int i = from; i < to; i++) {
			Degree subresult = body.get(i);
			result = Degree.infimum(result, subresult);
		}

		return result;
	}

	public static <I> Degree bigSupremum(Set<I> over, IteratingBigOperatorBody<Degree, I> body) {
		Degree result = Degree.ZERO;

		for (I item : over) {
			Degree subresult = body.get(item);
			result = Degree.supremum(result, subresult);
		}

		return result;
	}

	public static <I> Degree bigInfimum(Set<I> over, IteratingBigOperatorBody<Degree, I> body) {
		Degree result = Degree.ONE;

		for (I item : over) {
			Degree subresult = body.get(item);
			result = Degree.infimum(result, subresult);
		}

		return result;
	}

}
