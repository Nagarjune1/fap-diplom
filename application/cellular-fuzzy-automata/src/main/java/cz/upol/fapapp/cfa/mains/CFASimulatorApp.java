package cz.upol.fapapp.cfa.mains;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.cfa.comp.CFAComputation;
import cz.upol.fapapp.cfa.conf.CFAConfTIMParser;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.frame.SimulationFrameController;
import cz.upol.fapapp.cfa.impls.common.BivalentGameOfLifeCFA;
import cz.upol.fapapp.cfa.impls.images.MyBEFilterAutomata;
import cz.upol.fapapp.cfa.impls.images.NoiseReductionAutomata;
import cz.upol.fapapp.cfa.impls.images.SimpleBlurFilterAutomata;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CFASimulatorApp extends Application {

	private static final double DEFAULT_PARAM_VALUE = 1.0;

	public static void main(String[] args) {
		args = new String[] { "100", "data/test/configs/random-config-100.timf", "be-filter", "1.2" };
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/cz/upol/fapapp/cfa/gui/frame/SimulationFrame.fxml"));

		CFAComputation computation = loadComputation(getParameters());
		if (computation == null) {
			return;
		}

		SimulationFrameController controller = new SimulationFrameController(computation);
		loader.setController(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Cellular fuzzy automata simulator");
		stage.show();
	}

	public static CFAComputation loadComputation(Parameters params) throws IOException {
		List<String> argsList = AppsMainsTools.checkArgs(params, 3, 4, () -> printHelp());

		int size = AppsMainsTools.toInt(argsList, 0);
		String configFileStr = argsList.get(1);
		String typeStr = argsList.get(2);
		double arg = AppsMainsTools.toDouble(argsList, 3, DEFAULT_PARAM_VALUE);

		CellularFuzzyAutomata automata = loadAutomata(size, typeStr, arg);
		CFAConfiguration config = loadConfig(configFileStr);

		if (automata == null || config == null) {
			return null;
		}

		return new CFAComputation(automata, config);
	}

	private static CFAConfiguration loadConfig(String configFileStr) {
		File configFile = new File(configFileStr);

		return AppsMainsTools.runParser(configFile, new CFAConfTIMParser());
	}

	private static CellularFuzzyAutomata loadAutomata(int size, String typeStr, double param) {
		switch (typeStr) {
		case "game-of-life":
			return new BivalentGameOfLifeCFA(size);
		case "BE-filter":
		case "be-filter":
			return new MyBEFilterAutomata(size, param);
		case "simple-blur":
			return new SimpleBlurFilterAutomata(size);
		case "noise-reduction":
			return new NoiseReductionAutomata(size);
		default:
			throw new IllegalArgumentException("Unknown automata type " + typeStr);
		}
	}

	private static void printHelp() {
		System.out.println("CFA Simulator App");
		System.out.println("Usage:");
		System.out.println("	CFASimulatorApp SIZE CONFIG.timf AUTOMATA [PARAM]");
		System.out.println();
		System.out.println("	where AUTOMATA can be one of:");
		System.out.println("		game-of-life, be-filter, simple-blur, noise-reduction");
		System.out.println("	automata be-filter requires PARAM to specified (double > 1)");
		System.err.println("	if not, is used default value, " + DEFAULT_PARAM_VALUE);
	}

}
