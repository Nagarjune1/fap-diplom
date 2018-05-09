package cz.upol.fapapp.cfa.automata;

/**
 * State of cell of cellular fuzzy automata. Assuming only double from 0 to 1.
 * 
 * @author martin
 *
 */
public class CellState {
	private final double value;

	public CellState(double value) {
		super();

		if (value < 0.0 || value > 1.0) {
			throw new IllegalArgumentException("Invalid cell state " + value);
		}

		this.value = value;
	}

	/**************************************************************************/

	public double getValue() {
		return value;
	}

	/**************************************************************************/

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
		CellState other = (CellState) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CellState [value=" + value + "]";
	}

}
