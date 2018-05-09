package cz.upol.fapapp.feda.power.data;

/**
 * State is some internal state describtion of some {@link ElectricalDevice}. It also contains power consumption when device is in this state.
 * @author martin
 *
 */
public class DeviceState {
	private final String name;
	private final double powerConsumption;

	public DeviceState(String name, double powerConsumption) {
		super();
		this.name = name;
		this.powerConsumption = powerConsumption;
	}
	public String getName() {
		return name;
	}
	public double getPowerConsumption() {
		return powerConsumption;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(powerConsumption);
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
		DeviceState other = (DeviceState) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(powerConsumption) != Double.doubleToLongBits(other.powerConsumption))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DeviceState [name=" + name + ", powerConsumption=" + powerConsumption + "]";
	}
	
	
}
