package cz.upol.feda.power.data;

/**
 * Named change of power, i.e. "very-high" (with power 600) or "not-too-much" (e.g. -20)
 * 
 * @author martin
 *
 */
public class PowerConsumptionChange {

	private final String name;
	private final double powerChange;

	public PowerConsumptionChange(String name, double powerChange) {
		super();
		this.name = name;
		this.powerChange = powerChange;
	}

	public String getName() {
		return name;
	}

	public double getPowerChange() {
		return powerChange;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(powerChange);
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
		PowerConsumptionChange other = (PowerConsumptionChange) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(powerChange) != Double.doubleToLongBits(other.powerChange))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerConsumptionChange [name=" + name + ", powerChange=" + powerChange + "]";
	}

}
