package cz.upol.fapapp.core.misc;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * Some utilities for JavaFX apps.
 * 
 * @author martin
 *
 */
public class AppsFxTools {

	/**
	 * Runs FX app. Invoke from {@link Application#start(Stage)} method, pass
	 * stage, create controller, specify path to FXML file and frame title.
	 * 
	 * @param stage
	 *            the stage passed from start method
	 * @param controller
	 *            controller object
	 * @param pathToFXMLFile
	 *            absolute path, i.e. "/cz/upol/fapapp/foo/Bar.fxml"
	 * @param title
	 *            the title of frame
	 */
	public static void startFXMLApp(Stage stage, Object controller, String pathToFXMLFile, String title) {
		Logger.get().info("Starting GUI app ...");

		FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(pathToFXMLFile));

		loader.setController(controller);

		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			Logger.get().error("Cannot load FXML: " + e.toString());
			return;
		}
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();

	}

	/**
	 * Loads given fxml name (note: not the path, but only name (in the same
	 * directory hopefully) and without the suffix).
	 * 
	 * @param control
	 * @param fileName
	 */
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

	/**
	 * Performs save to TIMFile with feedback (both positive and negative).
	 * 
	 * @param caller
	 * @param object
	 * @param composer
	 */
	public static <T> void saveToTIMFile(Node caller, T object, TIMObjectComposer<T> composer) {
		File file = promtTIMFile(caller, false);
		if (file == null) {
			return;
		}

		try {
			composer.compose(object, file);
			showInfo("Saved", "Saved to " + file.getName());
		} catch (IOException e) {
			showError("Cannot save TIM file", e.getMessage());
		}
	}

	/**************************************************************************/

	/**
	 * Requests open of TIM file from user.
	 * 
	 * @param caller
	 * @param isOpen
	 *            true if to read, false if to write
	 * @return
	 */
	public static File promtTIMFile(Node caller, boolean isOpen) {
		return promtFile(caller, isOpen, "TIM files (*.timf)", "*.timf");
	}

	/**
	 * Requests open of PNG file from user.
	 * 
	 * @param caller
	 * @param isOpen
	 *            true if to read, false if to write
	 * @return
	 */
	public static File promtPNGFile(Node caller, boolean isOpen) {
		return promtFile(caller, isOpen, "PNG image (*.png)", "*.png");
	}

	/**
	 * Requests open of file.
	 * 
	 * @param caller
	 * @param isOpen
	 *            true if to read, false if to write
	 * @param formatDesc
	 *            in-human-language description of required format (i.e.
	 *            {@code "Text file (*.txt)"}
	 * @param extension
	 *            allowed extension, with wildcard (i.e. {@code "*.txt"})
	 * @return
	 */
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

	/**
	 * Shows error message.
	 * 
	 * @param title
	 * @param message
	 */
	public static void showError(String title, String message) {
		// https://stackoverflow.com/questions/26341152/controlsfx-dialogs-deprecated-for-what/32618003#32618003

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}

	/**
	 * Shows info message.
	 * 
	 * @param title
	 * @param message
	 */
	public static void showInfo(String title, String message) {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Info");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}

	/**
	 * Simple class (similary to {@link Runnable}) but allowing to throw
	 * exception of type E during the runtime.
	 * 
	 * @author martin
	 *
	 * @param <E>
	 */
	@FunctionalInterface
	public static interface RunnableWithException<E extends Exception> {
		public void run() throws E;
	}

}
