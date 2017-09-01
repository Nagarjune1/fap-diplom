package cz.upol.fapapp.cfa.gui.comp;

import java.io.File;
import java.io.IOException;

import cz.upol.fapapp.cfa.comp.CFAConfTIMComposer;
import cz.upol.fapapp.cfa.comp.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.misc.Imager;
import cz.upol.fapapp.core.misc.AppsFxTools;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FxConfigurationViewerComp extends BorderPane {

	@FXML
	private FxCFAConfigPanel configPane;
	@FXML
	private ComboBox<ColorModel> cmbColors;
	@FXML
	private Spinner<Integer> spinScale;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private VBox sideBar;

	public FxConfigurationViewerComp() {
		super();
		initialize();

	}

	private void initialize() {
		// load fxml
		AppsFxTools.loadFXML(this, "ConfigurationViewer");

		// sizes
		scrollPane.minWidthProperty().bind(this.widthProperty().subtract(sideBar.getPrefWidth()));
		scrollPane.minHeightProperty().bind(this.heightProperty());

		// colors
		cmbColors.setItems(FXCollections.observableArrayList(ColorModel.values()));
		cmbColors.setValue(FxCFAConfigPanel.DEFAULT_COLOR_MODEL);
		configPane.colorProperty().bind(cmbColors.valueProperty());

		// scale
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
		spinScale.setValueFactory(valueFactory);
		configPane.scaleProperty().bind(spinScale.valueProperty());

	}

	public ObjectProperty<CFAConfiguration> configProperty() {
		return configPane.configProperty();
	}

	/**************************************************************************/

	public void buttSaveAction() {
		CFAConfiguration object = configPane.configProperty().getValue();
		TIMObjectComposer<CFAConfiguration> composer = new CFAConfTIMComposer("cells");
		AppsFxTools.saveToTIMFile(this, object, composer);
	}

	public void buttImageAction() {
		CFAConfiguration config = configPane.configProperty().getValue();
		int scale = configPane.scaleProperty().intValue();
		ColorModel colors = configPane.colorProperty().getValue();
		File imageFile = AppsFxTools.promtPNGFile(this, false);
		if (imageFile == null) {
			return;
		}

		performSaveImage(config, scale, colors, imageFile);
	}

	private void performSaveImage(CFAConfiguration config, int scale, ColorModel colors, File imageFile) {
		Imager imager = new Imager();

		try {
			imager.configToImage(config, imageFile, colors, scale);
			AppsFxTools.showInfo("Saved", "Saved to " + imageFile);
		} catch (IOException e) {
			AppsFxTools.showError("Export failed", e.getMessage());
		}
	}

}
