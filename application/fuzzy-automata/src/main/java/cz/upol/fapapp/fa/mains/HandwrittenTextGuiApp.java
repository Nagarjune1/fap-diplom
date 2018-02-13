package cz.upol.fapapp.fa.mains;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.automata.FATIMParser;
import cz.upol.fapapp.fa.automata.FuzzyAutomata;
import cz.upol.fapapp.fa.gui.frame.HandwrittenTextController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HandwrittenTextGuiApp extends Application {

	public static void main(String[] args) {
//		args = new String[] { // 
//				"test-data/handwritten/gen-word-1a.timf-automata.timf-deformed.timf",
//				"test-data/handwritten/gen-word-2a.timf-automata.timf-deformed.timf" };
//		
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/fapapp/fa/gui/frame/HandwrittenTextFrame.fxml"));

		Map<String, FuzzyAutomata> automata = loadAutomata(getParameters());
		HandwrittenTextController controller = new HandwrittenTextController(automata);
		loader.setController(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Handwritten text recognision");
		stage.show();
	}

	private Map<String, FuzzyAutomata> loadAutomata(Parameters parameters) {
		List<String> automataNames = AppsMainsTools.checkArgs(parameters, 1, null, () -> printHelp());

		Map<String, FuzzyAutomata> map = new TreeMap<>();

		for (String automatonName : automataNames) {
			FuzzyAutomata automaton = loadAutomaton(automatonName);

			if (automaton != null) {
				map.put(automatonName, automaton);
			}
		}

		return map;
	}

	private FuzzyAutomata loadAutomaton(String automatonName) {
		File automatonFile = new File(automatonName);

		FATIMParser parser = new FATIMParser();
		try {
			return (FuzzyAutomata) parser.parse(automatonFile);
		} catch (IOException e) {
			Logger.get().error("Cannot load automata file: " + e);
			return null;
		}
	}

	private static void printHelp() {
		System.out.println("HandwrittenTextGuiApp");
		System.out.println("Usage: HandwrittenTextGuiApp automata-1.timf [automata-2.timf ...]");
	}
}
