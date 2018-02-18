package cz.upol.fapapp.fa.mains;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.automata.FATIMParser;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.gui.frame.HandwrittenTextController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application for handwritten text recognition.
 * @author martin
 *
 */
public class HandwrittenTextGuiApp extends Application {

	public static void main(String[] args) {
//		args = new String[] { // 
//				"test-data/handwritten/gen-word-1a.timf-automaton.timf-deformed.timf",
//				"test-data/handwritten/gen-word-2a.timf-automaton.timf-deformed.timf" };
//		
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/fapapp/fa/gui/frame/HandwrittenTextFrame.fxml"));

		Map<String, FuzzyAutomaton> automaton = loadAutomaton(getParameters());
		HandwrittenTextController controller = new HandwrittenTextController(automaton);
		loader.setController(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Handwritten text recognision");
		stage.show();
	}

	private Map<String, FuzzyAutomaton> loadAutomaton(Parameters parameters) {
		List<String> automatonNames = AppsMainsTools.checkArgs(parameters, 1, null, () -> printHelp());

		Map<String, FuzzyAutomaton> map = new TreeMap<>();

		for (String automatonName : automatonNames) {
			FuzzyAutomaton automaton = loadAutomaton(automatonName);

			if (automaton != null) {
				map.put(automatonName, automaton);
			}
		}

		return map;
	}

	private FuzzyAutomaton loadAutomaton(String automatonName) {
		File automatonFile = new File(automatonName);

		FATIMParser parser = new FATIMParser();
		try {
			return (FuzzyAutomaton) parser.parse(automatonFile);
		} catch (IOException e) {
			Logger.get().error("Cannot load automaton file: " + e);
			return null;
		}
	}

	private static void printHelp() {
		System.out.println("HandwrittenTextGuiApp");
		System.out.println("Usage: HandwrittenTextGuiApp automaton-1.timf [automaton-2.timf ...]");
	}
}
