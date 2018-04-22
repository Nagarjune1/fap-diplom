package cz.upol.fapapp.fa.mains;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cz.upol.fapapp.core.misc.AppsFxTools;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.fa.automata.FATIMParser;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.gui.frame.HandwrittenTextController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application for handwritten text recognition.
 * 
 * @author martin
 *
 */
public class HandwrittenTextGuiApp extends Application {

	public static void main(String[] args) {
		// args = new String[] { //
		// "test-data/handwritten/gen-word-1a.timf-automaton.timf-deformed.timf",
		// "test-data/handwritten/gen-word-2a.timf-automaton.timf-deformed.timf"
		// };
		//

		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Map<String, FuzzyAutomaton> automaton = loadAutomaton(getParameters());
		HandwrittenTextController controller = new HandwrittenTextController(automaton);

		AppsFxTools.startFXMLApp(stage, controller, //
				"/cz/fapapp/fa/gui/frame/HandwrittenTextFrame.fxml", //
				"Handwritten text recognision");
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
		return (FuzzyAutomaton) AppsMainsTools.runParser(automatonName, new FATIMParser());
	}

	private static void printHelp() {
		System.out.println("HandwrittenTextGuiApp");
		System.out.println("Usage: HandwrittenTextGuiApp automaton-1.timf [automaton-2.timf ...]");
	}
}
