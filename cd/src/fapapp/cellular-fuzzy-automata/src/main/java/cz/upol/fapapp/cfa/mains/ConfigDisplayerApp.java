package cz.upol.fapapp.cfa.mains;

import java.util.List;

import cz.upol.fapapp.cfa.conf.CFAConfTIMParser;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.frame.FxCFAConfigFrameController;
import cz.upol.fapapp.core.misc.AppsFxTools;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Basic config displaying app (in frame). No interactivity.
 * @author martin
 *
 */
public class ConfigDisplayerApp extends Application {

	public static void main(String[] args) {
		// args = new String[] { "--verbose", "data/test/configs/lenna.timf" };

		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		CFAConfiguration config = inferConfig();
		// CFAConfiguration config = new ConfigGenerator().generateDoubles(100,
		// 42);
		
		FxCFAConfigFrameController controller = new FxCFAConfigFrameController();
		controller.setConfig(config);;
		
		AppsFxTools.startFXMLApp(stage, controller, //
				"/cz/upol/fapapp/cfa/gui/frame/ConfigurationFrame.fxml", //
				"Cellular fuzzy automaton configuration displayer");			
		}

	protected CFAConfiguration inferConfig() {
		List<String> argsList = AppsMainsTools.checkArgs(getParameters(), 1, 1, () -> printHelp());

		String fileName = argsList.get(0);

		Logger.get().info("Loading configuration from " + fileName);
		CFAConfiguration config = AppsMainsTools.runParser(fileName, new CFAConfTIMParser());

		return config;
	}

	private void printHelp() {
		System.out.println("Config displayer");
		System.out.println("Usage: ConfigDisplayer FILE.timf");
	}
}
