package cz.upol.fapapp.feda.power.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} for {@link PowerConsumptionComputaitionData}.
 * @author martin
 *
 */
public class PCCDTIMFileParser extends TIMObjectParser<PowerConsumptionComputaitionData> {

	public static final String TYPE = "power consumption computation data";

	public PCCDTIMFileParser() {
		super(TYPE);
	}

	@Override
	public PowerConsumptionComputaitionData process(TIMFileData data) {
		double melt = processMelt(data);

		Map<String, ElectricalDevice> devices = processDevices(data);
		Set<PowerConsumptionChange> changes = processChanges(data);

		Map<ElectricalDevice, DeviceState> initial = processInitial(data, devices);
		List<Double> consumptions = processConsumptions(data);

		Set<ElectricalDevice> devicesSet = new HashSet<>(devices.values());

		return new PowerConsumptionComputaitionData(melt, devicesSet, changes, initial, consumptions);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private double processMelt(TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.findElementsMerged(data, "melt");
		return TIMObjectParserComposerTools.parseDouble(line.getIth(0));
	}

	private Map<String, ElectricalDevice> processDevices(TIMFileData data) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "devices");

		Map<String, Map<String, Double>> map = new HashMap<>(lines.size());

		for (LineElements line : lines) {
			String devName = line.getIth(0);
			TIMObjectParserComposerTools.is(line, 1, "is");
			String stateName = line.getIth(2);
			TIMObjectParserComposerTools.is(line, 3, "if");
			TIMObjectParserComposerTools.is(line, 4, "consumes");
			double power = TIMObjectParserComposerTools.parseDouble(line.getIth(5));

			Map<String, Double> ofDev = map.get(devName);
			if (ofDev == null) {
				ofDev = new HashMap<>();
				map.put(devName, ofDev);
			}

			ofDev.put(stateName, power);
		}

		return mapToMap(map);
	}

	private Map<String, ElectricalDevice> mapToMap(Map<String, Map<String, Double>> map) {
		return map.entrySet().stream() //
				.collect(Collectors.toMap( //
						(e) -> e.getKey(), //
						(e) -> new ElectricalDevice(e.getKey(), //
								e.getValue().entrySet().stream() //
										.map((se) -> new DeviceState(se.getKey(), se.getValue())) //
										.collect(Collectors.toSet()))));
	}

	private Set<PowerConsumptionChange> processChanges(TIMFileData data) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "changes");

		Set<PowerConsumptionChange> set = new HashSet<>(lines.size());

		for (LineElements line : lines) {
			String name = line.getIth(0);
			TIMObjectParserComposerTools.is(line, 1, "if");
			double power = TIMObjectParserComposerTools.parseDouble(line.getIth(2));

			PowerConsumptionChange change = new PowerConsumptionChange(name, power);
			set.add(change);
		}

		return set;
	}

	private Map<ElectricalDevice, DeviceState> processInitial(TIMFileData data, Map<String, ElectricalDevice> devices) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "initial", "initial situation");

		Map<ElectricalDevice, DeviceState> map = new HashMap<>(lines.size());

		for (LineElements line : lines) {
			String devName = line.getIth(0);
			TIMObjectParserComposerTools.is(line, 1, "is");
			String stateName = line.getIth(2);

			ElectricalDevice device = devices.get(devName);
			if (device == null) {
				Logger.get().warning("Invalid device in " + line);
				continue;
			}
			DeviceState state = device.getState(stateName);
			if (state == null) {
				Logger.get().warning("Invalid device state in " + line);
				continue;
			}

			map.put(device, state);
		}

		return map;
	}

	private List<Double> processConsumptions(TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.findElementsMerged(data, "powers", "consumptions", "values");

		return line.getElements().stream() //
				.map((e) -> TIMObjectParserComposerTools.parseDouble(e)) //
				.collect(Collectors.toList());
	}

}
