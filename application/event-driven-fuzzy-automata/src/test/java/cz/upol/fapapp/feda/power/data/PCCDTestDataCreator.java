package cz.upol.fapapp.feda.power.data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.feda.power.data.DeviceState;
import cz.upol.fapapp.feda.power.data.ElectricalDevice;
import cz.upol.fapapp.feda.power.data.PowerConsumptionChange;
import cz.upol.fapapp.feda.power.data.PowerConsumptionComputaitionData;

public class PCCDTestDataCreator {

	public static Set<ElectricalDevice> listDevices() {

		DeviceState tvOff = new DeviceState("off", 0.0);
		DeviceState tvStby = new DeviceState("standby", 20);
		DeviceState tvOn = new DeviceState("on", 300);
		ElectricalDevice tv = new ElectricalDevice("tv", tvOff, tvOn, tvStby);

		DeviceState fanOff = new DeviceState("off", 0);
		DeviceState fanCold = new DeviceState("cold", 100);
		DeviceState fanWarm = new DeviceState("warm", 600);
		DeviceState fanHot = new DeviceState("hot", 900);
		ElectricalDevice fan = new ElectricalDevice("fan", fanOff, fanCold, fanWarm, fanHot);

		DeviceState lampOff = new DeviceState("off", 0);
		DeviceState lampOn = new DeviceState("on", 50);
		ElectricalDevice lamp = new ElectricalDevice("lamp", lampOff, lampOn);

		return CollectionsUtils.toSet(tv, fan, lamp);
	}

	public static Set<PowerConsumptionChange> listChanges() {

		PowerConsumptionChange dec = new PowerConsumptionChange("decrease", -600);
		PowerConsumptionChange noc = new PowerConsumptionChange("no-change", 0);
		PowerConsumptionChange inc = new PowerConsumptionChange("increase", +600);

		return CollectionsUtils.toSet(dec, noc, inc);
	}

	public static Map<ElectricalDevice, DeviceState> createInitial() {
		return listDevices().stream() //
				.collect(Collectors.toMap( //
						(d) -> d, //
						(d) -> d.getState("off"))); //
	}

	public static PowerConsumptionComputaitionData createData() {
		double melt = 400;

		Map<ElectricalDevice, DeviceState> initial = createInitial();

		List<Double> consumptions = Arrays.asList(10.0, 230.0, 480.0);
		Set<ElectricalDevice> devices = listDevices();
		Set<PowerConsumptionChange> changes = listChanges();

		return new PowerConsumptionComputaitionData(melt, devices, changes, initial, consumptions);
	}

}
