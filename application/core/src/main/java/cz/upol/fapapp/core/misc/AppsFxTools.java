package cz.upol.fapapp.core.misc;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class AppsFxTools {

	public static void loadFXML(Pane control, String fileName) {
		FXMLLoader loader = new FXMLLoader(control.getClass().getResource(fileName + ".fxml"));
		loader.setController(control);
		try {
			Parent root = loader.load();
			control.getChildren().add(root);
		} catch (IOException e) {
			new IllegalStateException("FXML file not found", e);
		}
	}

	public static <T> void saveToTIMFile(Node caller, T object, TIMObjectComposer<T> composer) {
		File file = promtTIMFile(caller, false);
		if (file == null) {
			return;
		}

		try {
			composer.compose(object, file);
			showInfo("Saved", "Saved to " + file);
		} catch (IOException e) {
			showError("Cannot save TIM file", e.getMessage());
		}
	}

	/**************************************************************************/

	public static File promtTIMFile(Node caller, boolean isOpen) {
		return promtFile(caller, isOpen, "TIM files (*.timf)", "timf");
	}

	public static File promtPNGFile(Node caller, boolean isOpen) {
		return promtFile(caller, isOpen, "PNG image (*.png)", "png");
	}

	private static File promtFile(Node caller, boolean isOpen, String formatDesc, String extension) {
		FileChooser chooser = new FileChooser();

		ExtensionFilter filter = new ExtensionFilter(formatDesc, Arrays.asList(extension));

		chooser.getExtensionFilters().add(filter);
		chooser.setSelectedExtensionFilter(filter);

		Window owner = caller.getScene().getWindow();
		if (isOpen) {
			return chooser.showOpenDialog(owner);
		} else {
			return chooser.showSaveDialog(owner);
		}
	}

	/**************************************************************************/

	public static void showError(String title, String message) {
		// https://stackoverflow.com/questions/26341152/controlsfx-dialogs-deprecated-for-what/32618003#32618003

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}

	public static void showInfo(String title, String message) {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Info");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}

	@FunctionalInterface
	public static interface RunnableWithException<E extends Exception> {
		public void run() throws E;
	}

}
