package cz.upol.feda.power.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectComposer} for {@link PowerConsumptionComputaitionData}.
 * 
 * @author martin
 *
 */
public class PCCDTIMFileComposer extends TIMObjectComposer<PowerConsumptionComputaitionData> {

	public PCCDTIMFileComposer() {
		super(PCCDTIMFileParser.TYPE);
	}

	@Override
	protected void process(PowerConsumptionComputaitionData pccd, TIMFileData data) {
		processMelt(pccd.getMelt(), data);
		processDevices(pccd.getDevices(), data);
		processChanges(pccd.getChanges(), data);
		processInitial(pccd.getInitial(), data);
		processConsumptions(pccd.getConsumptions(), data);
	}

	private void processMelt(double melt, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.doublesToLine(melt);
		data.add("melt", line);
	}

	private void processDevices(Set<ElectricalDevice> devices, TIMFileData data) {
		for (ElectricalDevice device : devices) {
			for (DeviceState state : device.getPossibleStates()) {
				String devName = device.getName();
				String stateName = state.getName();
				String power = Double.toString(state.getPowerConsumption());

				LineElements line = new LineElements(devName, "is", stateName, "if", "consumes", power);
				data.add("devices", line);
			}
		}
	}

	private void processChanges(Set<PowerConsumptionChange> changes, TIMFileData data) {
		for (PowerConsumptionChange change : changes) {
			String changeName = change.getName();
			String power = Double.toString(change.getPowerChange());

			LineElements line = new LineElements(changeName, "if", power);
			data.add("changes", line);
		}
	}

	private void processInitial(Map<ElectricalDevice, DeviceState> initial, TIMFileData data) {
		for (ElectricalDevice device : initial.keySet()) {
			DeviceState state = initial.get(device);

			String devName = device.getName();
			String stateName = state.getName();

			LineElements line = new LineElements(devName, "is", stateName);
			data.add("initial", line);
		}
	}

	private void processConsumptions(List<Double> consumptions, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.doublesToLine(consumptions);
		data.add("consumptions", line);
	}

}
