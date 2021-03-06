package cz.upol.fapapp.core.lingvar;

/**
 * Value of lingvistic variable. The value could be any double numer, no
 * restriction. This class is only naming for the general double.
 * 
 * @author martin
 *
 */
public class LingVarValue {
	private double value;

	public LingVarValue(double value) {
		super();
		this.value = value;
	}

	public double getValue() {
		return value;
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
		LingVarValue other = (LingVarValue) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "lvv" + value + "";
	}

}
