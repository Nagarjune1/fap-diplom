package cz.upol.fapapp.cfa.gui.frame;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.cfa.config.CFAConfiguration;
import cz.upol.fapapp.cfa.config.ConfigGenerator;
import cz.upol.fapapp.cfa.gui.comp.ColorModel;
import cz.upol.fapapp.cfa.gui.comp.FxCFAConfigPanel;
import cz.upol.fapapp.cfa.gui.comp.ZoomPane;
import cz.upol.fapapp.cfa.gui.task.CFAComputationTask;
import cz.upol.fapapp.cfa.mu.BivalGameOfLifeMu;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class SimulationFrameController implements Initializable {

	private static final int DEFAULT_SIZE = 500;

	@FXML
	private Button buttRun;
	@FXML
	private Button buttStop;
	@FXML
	private Button buttNext;

	@FXML
	private Spinner<Double> spinZoom;
	@FXML
	private ComboBox<ColorModel> cmbColors;
	@FXML
	private Slider sliSpeed;

	@FXML
	private Label lblGeneration;

	@FXML
	private ZoomPane zoomPane;

	@FXML
	private FxCFAConfigPanel configPane;

	private final CellularFuzzyAutomata automata;
	private CFAComputationTask task;

	/**************************************************************************/

	public SimulationFrameController() {
		// TODO specify by constructor
		CFATransitionFunction mu = new BivalGameOfLifeMu();
		CFAConfiguration initialConfig = new ConfigGenerator().generateBival(DEFAULT_SIZE, BivalGameOfLifeMu.ALIVE, 42);
		automata = new CellularFuzzyAutomata(DEFAULT_SIZE, mu, initialConfig);
		

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// zoom
		SpinnerValueFactory<Double> valueFactory = new ZoomSpinnerValueFactory();
		spinZoom.setValueFactory(valueFactory);
		zoomPane.getZoom().bind(spinZoom.valueProperty());

		// colors
		ObservableList<ColorModel> colors = FXCollections.observableArrayList(ColorModel.values());
		cmbColors.setItems(colors);
		cmbColors.setValue(FxCFAConfigPanel.DEFAULT_COLOR_MODEL);
		configPane.getColor().bind(cmbColors.valueProperty());

		updateAutomataInFrame();
	}

	/**************************************************************************/

	public void buttRunAction() {

		task = new CFAComputationTask(automata);
		// misc
		task.getSpeed().bind(sliSpeed.valueProperty());
		configPane.getConfig().bind(task.getConfig());
		lblGeneration.textProperty().bind(task.getGeneration().asString());

		// buttons
		buttRun.disableProperty().bind(task.runningProperty());
		buttStop.disableProperty().bind(task.runningProperty().not());
		buttNext.disableProperty().bind(task.runningProperty());

		new Thread(task).start();

	}

	public void buttStopAction() {
		Platform.runLater(() -> { //
				task.cancel();
				configPane.getConfig().unbind();
				lblGeneration.textProperty().unbind();
	});//

	}

	public void buttNextAction() {
		automata.toNextGeneration();
		updateAutomataInFrame();
	}

	private void updateAutomataInFrame() {
		configPane.getConfig().set(automata.getCurrentConfig());
		lblGeneration.textProperty().set(Integer.toString(automata.getCurrentGeneration()));
	}

	/**************************************************************************/

	private final class ZoomSpinnerValueFactory extends SpinnerValueFactory<Double> {
		private static final double MIN_VAL = 1.0 / 1024.0;
		private static final double MAX_VAL = 1024.0;

		public ZoomSpinnerValueFactory() {
			setValue(1.0);
		}

		@Override
		public void decrement(int steps) {
			perform(steps, //
					(v) -> (v / 2.0));
		}

		@Override
		public void increment(int steps) {
			perform(steps, //
					(v) -> (v * 2.0));
		}

		private void perform(int steps, Function<Double, Double> fn) {
			double val = getValue();
			if (val < MIN_VAL || val > MAX_VAL) {
				return;
			}

			for (int i = 0; i < steps; i++) {
				val = fn.apply(val);
			}

			setValue(val);
		}
	}

}
