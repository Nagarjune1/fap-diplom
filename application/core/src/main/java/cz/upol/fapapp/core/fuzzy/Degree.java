package cz.upol.fapapp.core.fuzzy;

public class Degree implements Comparable<Degree> {

	public static final Degree ZERO = new Degree(0.0);
	public static final Degree ONE = new Degree(1.0);

	private final double value;

	public Degree(double value) {
		super();
		if (value < 0.0 || value > 1.0) {
			throw new IllegalArgumentException("Degree d must be 0 <= d <= 1!");
		}

		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public int compareTo(Degree o) {
		return Double.compare(this.value, o.value);
	}

	/**
	 * @see Degree#isSmallerThan(Degree, Degree, boolean)
	 * @param o
	 * @return
	 */
	public boolean isLessOrEqual(Degree o) {
		return this.compareTo(o) < 0;
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
		return "Deg(" + value + ")";
	}

	///////////////////////////////////////////////////////////////////////////

	public static Degree supremum(Degree first, Degree second) {
		double firstValue = first.getValue();
		double secondValue = second.getValue();

		double value = Math.max(firstValue, secondValue);
		return new Degree(value);
	}

	public static Degree infimum(Degree first, Degree second) {
		double firstValue = first.getValue();
		double secondValue = second.getValue();

		double value = Math.min(firstValue, secondValue);
		return new Degree(value);
	}

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

}
