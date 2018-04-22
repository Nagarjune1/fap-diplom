package cz.upol.feda.mains;

import java.util.List;
import java.util.Map;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.feda.power.data.DeviceState;
import cz.upol.feda.power.data.ElectricalDevice;
import cz.upol.feda.power.data.PCCDTIMFileParser;
import cz.upol.feda.power.data.PowerConsumptionComputaitionData;
import cz.upol.feda.power.impl.PowerConsumptionComputer;

/**
 * App running Power Consumption Computation.
 * 
 * @author martin
 *
 */
public class PowerConsumptionComputeApp {
	private static final boolean USE_GAUSSIANS = true;
	private static final Degree TRANSTION_THRESHOLD = new Degree(0.9);

	public static void main(String[] args) {
		// args = new String[] { "--verbose", "--tnorm", "product",
		// "test-data/powers/debug.timf" };
		// args = new String[] { "--verbose", "--tnorm", "product",
		// "test-data/powers/some.timf" };
		// args = new String[] {"--verbose", "--tnorm", "product",
		// "test-data/powers/better.timf" };

		List<String> argsList = AppsMainsTools.checkArgs(args, 1, 1, () -> printHelp());

		String pccdFileName = argsList.get(0);

		process(pccdFileName);
	}

	private static void process(String pccdFileName) {
		
		PowerConsumptionComputaitionData pccd = inferData(pccdFileName);

		Logger.get().info("Starting power consumptions computation ...");
		Map<ElectricalDevice, DeviceState> state = compute(pccd);

		printState(state);
	}

	///////////////////////////////////////////////////////////////////////////

	private static PowerConsumptionComputaitionData inferData(String pccdFile) {

		PowerConsumptionComputaitionData pccd = AppsMainsTools.runParser(pccdFile, new PCCDTIMFileParser());

		return pccd;
	}

	private static Map<ElectricalDevice, DeviceState> compute(PowerConsumptionComputaitionData pccd) {

		PowerConsumptionComputer computer = new PowerConsumptionComputer();

		Map<ElectricalDevice, DeviceState> state = computer.compute(pccd, USE_GAUSSIANS, TRANSTION_THRESHOLD);

		return state;
	}

	private static void printState(Map<ElectricalDevice, DeviceState> state) {
		for (ElectricalDevice device : state.keySet()) {
			DeviceState deviceState = state.get(device);

			System.out.println(device.getName() + " is " + deviceState.getName());
		}
	}

	private static void printHelp() {
		System.out.println("PowerConsumptionComputeApp PCCD.timf");
	}

}
