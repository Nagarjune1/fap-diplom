package cz.upol.fapapp.cfa.gui.frame;

import java.net.URL;
import java.util.ResourceBundle;

import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.comp.FxConfigurationViewerComp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * Simple FxFrame displaing only the specified
 * ({@link #setConfig(CFAConfiguration)}) configuration.
 * 
 * @author martin
 *
 */
public class FxCFAConfigFrameController implements Initializable {

	@FXML
	private FxConfigurationViewerComp configViewer;

	public FxCFAConfigFrameController() {
		super();
	}

	public CFAConfiguration getConfig() {
		return configViewer.configProperty().getValue();
	}

	public void setConfig(CFAConfiguration config) {
		configViewer.configProperty().setValue(config);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// nothing needed

	}

	/**************************************************************************/
}
