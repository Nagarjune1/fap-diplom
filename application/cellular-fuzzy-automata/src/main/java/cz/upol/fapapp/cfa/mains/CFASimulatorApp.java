package cz.upol.fapapp.cfa.mains;

import cz.upol.fapapp.core.misc.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CFASimulatorApp extends Application {

	public static void main(String[] args) {

		Logger.get().setVerbose(true);// XXX logging

		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/cz/upol/fapapp/cfa/gui/frame/simulation-frame.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Cellular fuzzy automata simulator");
		stage.show();
	}

}
