package cz.upol.fapapp.core.probability;

public class Probability {
	public static final Probability PROB_OF_CERTAIN_EVENT = new Probability(1.0);
	public static final Probability PROB_OF_IMPOSSIBLE_EVENT = new Probability(0.0);
	private final double value;

	public Probability(double value) {
		super();

		if (value < 0.0 || value > 1.0) {
			throw new IllegalArgumentException("Value " + value
					+ " is not probability!");
		}

		this.value = value;
	}

	public double getValue() {
		return value;
	}

	// //////////////////////////////////////////////////////////////////////

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
		Probability other = (Probability) obj;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prob(" + value + ")";
	}

	public static Probability intersect(Probability first,
			Probability second) {
		return new Probability(first.getValue() * second.getValue());
	}

	public static Probability join(Probability first, Probability second) {
		return new Probability(first.getValue() + second.getValue()); //TODO OR - first * second
	}

}
