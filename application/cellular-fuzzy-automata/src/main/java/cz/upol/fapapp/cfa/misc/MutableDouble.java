package cz.upol.fapapp.cfa.misc;

public class MutableDouble {
	private double value;

	public MutableDouble(double value) {
		super();
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void add(double value) {
		this.value += value;
	}

	public void sub(double value) {
		this.value -= value;
	}

	public void mul(double value) {
		this.value *= value;
	}

	public void div(double value) {
		this.value /= value;
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
		MutableDouble other = (MutableDouble) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MutableDouble [value=" + value + "]";
	}

}
