package cz.upol.automaton.fuzzyLogic.rationalLogics;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.sets.Externisator;

public class Rational0to1Number implements Degree {
	public static final Externisator<Degree> EXTERNISATOR = new Rat01NumExternisator();

	/**
	 * @uml.property  name="value"
	 */
	private final double value;

	public Rational0to1Number(double value) {
		if (value < 0.0 || value > 1.0) {
			throw new IllegalArgumentException("Value " + value
					+ " is out of range [0, 0]");
		}

		this.value = value;
	}

	public Rational0to1Number(Rational0to1Number other) {
		this.value = other.getValue();
	}

	/**
	 * @return
	 * @uml.property  name="value"
	 */
	public double getValue() {
		return value;
	}

	public static Rational0to1Number max(Rational0to1Number num1,
			Rational0to1Number num2) {
		if (num1.compareTo(num2) > 0) {
			return new Rational0to1Number(num1);
		} else {
			return new Rational0to1Number(num2);
		}
	}

	public static Rational0to1Number min(Rational0to1Number num1,
			Rational0to1Number num2) {
		if (num1.compareTo(num2) > 0) {
			return new Rational0to1Number(num2);
		} else {
			return new Rational0to1Number(num1);
		}
	}

	public static boolean isValid(String string) {
		double value;
		try {
			value = Double.parseDouble(string);
		} catch (Exception e) {
			return false;
		}

		return value >= 0.0 && value <= 1.0;
	}

	public static Rational0to1Number fromString(String string) {
		try {
			double value = Double.parseDouble(string);
			return new Rational0to1Number(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <ul>
	 * <li><strong>0.1</strong>.compareTo(<strong>0.9</strong>) =&gt; 1</li>
	 * <li><strong>0.8</strong>.compareTo(<strong>0.2</strong>) =&gt; -1</li>
	 * <li><strong>0.5</strong>.compareTo(<strong>0.5</strong>) =&gt; 0</li>
	 * </ul>
	 */
	public int compareTo(Degree o) {
		if (o instanceof Rational0to1Number) {
			Rational0to1Number other = (Rational0to1Number) o;
			Double thisDouble = new Double(this.getValue());
			Double otherDouble = new Double(other.getValue());

			return thisDouble.compareTo(otherDouble);
		} else {
			throw new IllegalArgumentException("Not a "
					+ this.getClass().getName());
		}
	}

	@Override
	public boolean isLowerOrEqual(Degree other) {
		if (other instanceof Rational0to1Number) {
			Rational0to1Number number = (Rational0to1Number) other;
			return this.getValue() <= number.getValue();
		} else {
			throw new IllegalArgumentException("Not a "
					+ this.getClass().getName());
		}
	}

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
		Rational0to1Number other = (Rational0to1Number) obj;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}

	public String toPrint() {
		return Double.toString(value); // TODO String.format("%f...");
	}

	@Override
	public String toString() {
		return "RatNum=" + value;
	}

	@Override
	public Externisator<Degree> getExternisator() {
		return EXTERNISATOR;
	}

	public static class Rat01NumExternisator extends Externisator<Degree> {

		@Override
		public Degree parseKnown(String string) {
			return Rational0to1Number.fromString(string);
		}

		@Override
		public String externalizeKnown(Degree externasiable) {
			if (externasiable == null) {
				return null;
			} else {
				return externasiable.toPrint();
			}
		}
	}

}
