package cz.upol.fapapp.cfa.mains;

import java.util.List;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomaton;
import cz.upol.fapapp.cfa.conf.CFAComputation;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.frame.SimulationFrameController;
import cz.upol.fapapp.cfa.impls.droplets.CommonDropletsGenerator;
import cz.upol.fapapp.cfa.impls.droplets.DropletsAutomaton;
import cz.upol.fapapp.cfa.impls.droplets.DropletsConfigGenerator;
import cz.upol.fapapp.cfa.impls.droplets.DropletsGenerator;
import cz.upol.fapapp.core.misc.AppsFxTools;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application performing the droplets simulator.
 * 
 * @author martin
 *
 */
public class DropletsSimulatorApp extends Application {

	private static final int DEFAULT_INIT_COUNT = 1;
	private static final int DEFAULT_INIT_SEED = (int) System.currentTimeMillis();
	private static final double INIT_MIN_VALUE = 0.0;
	private static final double STEP_MIN_VALUE = 0.0;

	public static void main(String[] args) {
		// args = new String[] { "--verbose","--debug", "300", "1", "42"};
		// args = new String[] { "300", "1", "42"};
		Logger.get().info("Starting the GUI app ...");
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		CFAComputation computation = initComputation(getParameters());
		if (computation == null) {
			return;
		}

		SimulationFrameController controller = new SimulationFrameController(computation);

		AppsFxTools.startFXMLApp(stage, controller, //
				"/cz/upol/fapapp/cfa/gui/frame/SimulationFrame.fxml", //
				"Cellular fuzzy automaton simulator (droplets automaton)");
	}

	private CFAComputation initComputation(Parameters parameters) {
		List<String> args = AppsMainsTools.checkArgs(parameters, 1, 5, () -> printHelp());

		int size = AppsMainsTools.toInt(args, 0);
		int initCount = AppsMainsTools.toInt(args, 1, DEFAULT_INIT_COUNT);
		int initSeed = AppsMainsTools.toInt(args, 2, DEFAULT_INIT_SEED);

		int stepCount = AppsMainsTools.toInt(args, 3, initCount);
		int stepSeed = AppsMainsTools.toInt(args, 4, initCount);

		return createComputation(size, initCount, initSeed, INIT_MIN_VALUE, stepCount, stepSeed, STEP_MIN_VALUE);
	}

	private CFAComputation createComputation(int size, int initCount, int initSeed, double initMinValue, int stepCount,
			int stepSeed, double stepMinValue) {

		DropletsGenerator dropletsGenerator = new CommonDropletsGenerator(stepCount, stepSeed, stepMinValue);
		CellularFuzzyAutomaton automaton = new DropletsAutomaton(size, dropletsGenerator);

		DropletsConfigGenerator configsGenerator = new DropletsConfigGenerator();
		CFAConfiguration config = configsGenerator.generate(size, initCount, initSeed, initMinValue);

		return new CFAComputation(automaton, config);
	}

	private void printHelp() {
		System.out.println("Droplets simulator App");
		System.out.println("Usage: ");
		System.out.println("\tDropletsSimulatorApp SIZE ");
		System.out.println("\tDropletsSimulatorApp SIZE  COUNT SEED");
		System.out.println("\tDropletsSimulatorApp SIZE  INIT_COUNT INIT_SEED  STEP_COUNT STEP_SEED");
		System.out.println();
		System.out.println("INIT_* means droplets before first generation, STEP_* at each generation");
		System.out.println("If COUNT is not provided, is used " + DEFAULT_INIT_COUNT);
		System.out.println("If SEED is not provided, is used current timestamp");
		System.out.println("If only COUNT & SEED is provided, the values are used for both INIT_ and STEP_");

	}
}
