package cz.upol.fapapp.core.fuzzy;

import java.text.DecimalFormat;

/**
 * Degree, the main term in the fuzzy theory. In this implementation is
 * represented as double number between 0 ({@link #ZERO}) and 1 ({@link #ONE})
 * with some aditional properties, like comparability, max and min.
 * 
 * @author martin
 *
 */
public class Degree implements Comparable<Degree> {

	public static final Degree ZERO = new Degree(0.0);
	public static final Degree ONE = new Degree(1.0);

	private final double value;

	public Degree(double value) throws IllegalArgumentException {
		super();
		if (value < 0.0 || value > 1.0) {
			throw new IllegalArgumentException("Degree d must be 0 <= d <= 1 but is " + value);
		}

		this.value = value;
	}

	public double getValue() {
		return value;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Degree other = (Degree) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Deg(" + new DecimalFormat("0.0#####").format(value) + ")";
	}

	@Override
	public int compareTo(Degree o) {
		return Double.compare(this.value, o.value);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Computes the negation, i.e. the {@value #ONE} - value.
	 * 
	 * @return
	 */
	public Degree negate() {
		return new Degree(Degree.ONE.getValue() - value);
	}

	/**
	 * Returns true if this degree is smaller or equal to given one.
	 * 
	 * @param o
	 * @return
	 */
	public boolean isLessOrEqual(Degree o) {
		return isSmallerThan(this, o, true);
	}

	/**
	 * Computes supremum (max) of given degrees.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static Degree supremum(Degree first, Degree second) {
		double firstValue = first.getValue();
		double secondValue = second.getValue();

		double value = Math.max(firstValue, secondValue);
		return new Degree(value);
	}

	/**
	 * Computes infimum (min) of given degrees.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static Degree infimum(Degree first, Degree second) {
		double firstValue = first.getValue();
		double secondValue = second.getValue();

		double value = Math.min(firstValue, secondValue);
		return new Degree(value);
	}

	/**
	 * Returns true if first degree is smaller than second (or equal if
	 * smallerOrEqual is set to true). If both are zeros, still assumes them as
	 * smaller.
	 * 
	 * @param first
	 * @param second
	 * @param smallerOrEqual
	 * @return
	 */
	public static boolean isSmallerThan(Degree first, Degree second, boolean smallerOrEqual) {
		if (ZERO.equals(first) && ZERO.equals(second)) {
			return true;
		}

		int cmp = first.compareTo(second);

		if (smallerOrEqual) {
			return cmp <= 0;
		} else {
			return cmp < 0;
		}
	}

	/**
	 * Computes algebraical difference between theese two degrees. If returned
	 * zero, they are completelly equal, if one they are completelly different.
	 * 
	 * @param first
	 * @param second
	 * @see Degree#equality(Degree, Degree)
	 * @return
	 */
	public static Degree difference(Degree first, Degree second) {
		double diff = Math.abs(first.getValue() - second.getValue());
		return new Degree(diff);
	}

	/**
	 * Computes quality of theese two. If returned one, they are completelly
	 * equal, if zero they are completelly different.
	 * 
	 * @param first
	 * @param second
	 * @see Degree#difference(Degree, Degree)
	 * @return
	 */
	public static Degree equality(Degree first, Degree second) {
		Degree difference = difference(first, second);
		return difference.negate();
	}

	///////////////////////////////////////////////////////////////////////////

}
