package cz.upol.feda.power.data;

import java.util.Set;

import cz.upol.fapapp.core.misc.CollectionsUtils;
 
/**
 * Electrical device here is something with name and some state. I.e. "fan" has states "off", "blowing-cold" and "blowing-hot". 
 * @author martin
 *
 */
public class ElectricalDevice {
	private final String name;
	private final Set<DeviceState> possibleStates;

	public ElectricalDevice(String name, Set<DeviceState> possibleStates) {
		super();
		this.name = name;
		this.possibleStates = possibleStates;
	}

	public ElectricalDevice(String name, DeviceState... possibleStates) {
		super();
		this.name = name;
		this.possibleStates = CollectionsUtils.toSet(possibleStates);
	}

	public String getName() {
		return name;
	}

	public Set<DeviceState> getPossibleStates() {
		return possibleStates;
	}

	public DeviceState getState(String stateName) {
		return possibleStates.stream() //
				.filter((s) -> s.getName().equals(stateName)) //
				.findAny().orElseThrow(//
						() -> new IllegalArgumentException("State " + stateName + " of " + name + " doesn't exist"));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((possibleStates == null) ? 0 : possibleStates.hashCode());
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
		ElectricalDevice other = (ElectricalDevice) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (possibleStates == null) {
			if (other.possibleStates != null)
				return false;
		} else if (!possibleStates.equals(other.possibleStates))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ElectricalDevice[name=" + name + ", states=" + possibleStates.size() + "]";
	}

}
