package cz.upol.fapapp.cfa.mains;

import java.io.File;
import java.util.List;

import cz.upol.fapapp.cfa.conf.CFAConfTIMParser;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.frame.FxCFAConfigFrameController;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConfigDisplayerApp extends Application {

	public static void main(String[] args) {
		// args = new String[] { "--verbose", "data/test/configs/lenna.timf" };

		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		List<String> argsList = AppsMainsTools.checkArgs(getParameters(), 1, 1, () -> printHelp());

		String fileName = argsList.get(0);
		File file = new File(fileName);

		CFAConfiguration config = AppsMainsTools.runParser(file, new CFAConfTIMParser());
		if (config == null) {
			return;
		}
		// CFAConfiguration config = new ConfigGenerator().generateDoubles(100,
		// 42);

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/cz/upol/fapapp/cfa/gui/frame/ConfigurationFrame.fxml"));

		Parent root = loader.load();

		FxCFAConfigFrameController controller = loader.getController();
		controller.setConfig(config);

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Cellular fuzzy automata configuration displayer");
		stage.show();
	}

	private void printHelp() {
		System.out.println("Config displayer");
		System.out.println("Usage: ConfigDisplayer FILE.timf");
	}
}
