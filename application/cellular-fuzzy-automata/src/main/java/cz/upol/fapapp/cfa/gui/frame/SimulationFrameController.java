package cz.upol.fapapp.cfa.gui.frame;

import java.net.URL;
import java.util.ResourceBundle;

import cz.upol.fapapp.cfa.conf.CFAComputation;
import cz.upol.fapapp.cfa.gui.comp.FxConfigurationViewerComp;
import cz.upol.fapapp.cfa.gui.misc.CFaComputationService;
import cz.upol.fapapp.cfa.gui.misc.ObservableCFAComputation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Complate FxFrame of fuzzy cellular automata simulator.
 * 
 * @author martin
 *
 */
public class SimulationFrameController implements Initializable {

	@FXML
	private Button buttReset;
	@FXML
	private Button buttRun;
	@FXML
	private Button buttStop;
	@FXML
	private Button buttNext;

	@FXML
	private Slider sliSpeed;

	@FXML
	private Label lblGeneration;
	@FXML
	private FxConfigurationViewerComp configComp;

	private final ObservableCFAComputation observable;
	private final CFaComputationService service;

	/*************************************************************************/

	public SimulationFrameController(CFAComputation computation) {

		observable = new ObservableCFAComputation(computation);
		observable.addListener((n) -> updateForm((CFAComputation) n));

		service = new CFaComputationService(observable);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// binding
		service.speedProperty().bind(sliSpeed.valueProperty());

		// buttons
		buttReset.disableProperty().bind(service.runningProperty());
		buttRun.disableProperty().bind(service.runningProperty());
		buttStop.disableProperty().bind(service.runningProperty().not());
		buttNext.disableProperty().bind(service.runningProperty());
	}

	/**************************************************************************/

	private void updateForm(CFAComputation computation) {
		Platform.runLater(() -> {
			configComp.configProperty().setValue(computation.getConfig());
			lblGeneration.textProperty().setValue(Integer.toString(computation.getGeneration()));
		});
	}

	/**************************************************************************/

	public void buttResetAction() {
		observable.reset();
	}

	public void buttNextAction() {
		observable.toNextGeneration();
	}

	public void buttRunAction() {
		service.restart();
	}

	public void buttStopAction() {
		service.cancel();
	}

	/**************************************************************************/

}
