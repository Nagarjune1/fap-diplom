package cz.upol.fapapp.feda.power.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.feda.power.impl.PowerConsumptionComputer;

/**
 * Complete dataset holdin all requred data to construct automaton and compute
 * power consumptions.
 * 
 * @author martin
 * @see PowerConsumptionComputer
 *
 */
public class PowerConsumptionComputaitionData {
	private final double melt;
	private final Set<ElectricalDevice> devices;
	private final Set<PowerConsumptionChange> changes;
	private final Map<ElectricalDevice, DeviceState> initial;
	private final List<Double> consumptions;

	public PowerConsumptionComputaitionData(double melt, Set<ElectricalDevice> devices,
			Set<PowerConsumptionChange> changes, Map<ElectricalDevice, DeviceState> initial,
			List<Double> consumptions) {
		super();
		this.melt = melt;
		this.devices = devices;
		this.changes = changes;
		this.initial = initial;
		this.consumptions = consumptions;
	}

	public double getMelt() {
		return melt;
	}

	public Set<ElectricalDevice> getDevices() {
		return devices;
	}

	public Set<PowerConsumptionChange> getChanges() {
		return changes;
	}

	public Map<ElectricalDevice, DeviceState> getInitial() {
		return initial;
	}

	public List<Double> getConsumptions() {
		return consumptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changes == null) ? 0 : changes.hashCode());
		result = prime * result + ((consumptions == null) ? 0 : consumptions.hashCode());
		result = prime * result + ((devices == null) ? 0 : devices.hashCode());
		result = prime * result + ((initial == null) ? 0 : initial.hashCode());
		long temp;
		temp = Double.doubleToLongBits(melt);
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
		PowerConsumptionComputaitionData other = (PowerConsumptionComputaitionData) obj;
		if (changes == null) {
			if (other.changes != null)
				return false;
		} else if (!changes.equals(other.changes))
			return false;
		if (consumptions == null) {
			if (other.consumptions != null)
				return false;
		} else if (!consumptions.equals(other.consumptions))
			return false;
		if (devices == null) {
			if (other.devices != null)
				return false;
		} else if (!devices.equals(other.devices))
			return false;
		if (initial == null) {
			if (other.initial != null)
				return false;
		} else if (!initial.equals(other.initial))
			return false;
		if (Double.doubleToLongBits(melt) != Double.doubleToLongBits(other.melt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerConsumptionComputaitionData [melt=" + melt + ", devices=" + devices + ", changes=" + changes
				+ ", initial=" + initial + ", consumptions=" + consumptions + "]";
	}

}
