package cz.upol.fapapp.fa.gui.frame;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.gui.comp.FxPathableCanvas;
import cz.upol.fapapp.fa.gui.misc.AutomataComputationTask;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * Controller of app performing handwriitten text recognition.
 * 
 * @author martin
 *
 */
public class HandwrittenTextController implements Initializable {

	private final Map<String, FuzzyAutomaton> automata;

	@FXML
	private FxPathableCanvas canvas;
	@FXML
	private TableView<Entry<String, Degree>> resultsTable;
	@FXML
	private Slider sliShake;
	@FXML
	private CheckBox chkbImmediate;

	@FXML
	private Button buttReset;
	@FXML
	private Button buttCompute;
	@FXML
	private ProgressBar prgProgress;
	@FXML
	private Label lblWord;

	///////////////////////////////////////////////////////////////////////////

	public HandwrittenTextController(Map<String, FuzzyAutomaton> automata) {
		this.automata = automata;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCanvas();
		initResultsTable();

		initBindings();

	}

	private void initCanvas() {
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> handleNewDrag(e));
		canvas.addEventHandler(FxPathableCanvas.NEW_PATH_EVENT, (e) -> handleNewPath(e));
	}

	private void initResultsTable() {
		@SuppressWarnings("unchecked")
		TableColumn<Entry<String, Degree>, String> nameColumn = //
				(TableColumn<Entry<String, Degree>, String>) resultsTable.getColumns().get(0);

		@SuppressWarnings("unchecked")
		TableColumn<Entry<String, Degree>, Double> degreeColumn = //
				(TableColumn<Entry<String, Degree>, Double>) resultsTable.getColumns().get(1);

		nameColumn.setCellValueFactory( //
				(cell -> new SimpleObjectProperty<String>(cell.getValue().getKey())));

		degreeColumn.setCellValueFactory( //
				(cell -> new SimpleObjectProperty<Double>(cell.getValue().getValue().getValue())));

		resultsTable.setItems(FXCollections.observableArrayList());

	}

	private void initBindings() {
		buttReset.disableProperty().bind(chkbImmediate.selectedProperty());
		buttCompute.disableProperty().bind(chkbImmediate.selectedProperty());

		canvas.shakeProperty().bind(sliShake.valueProperty());
		sliShake.valueProperty().addListener((e) -> displayWordOnLabel());

		chkbImmediate.selectedProperty().addListener((e) -> performCompleteReset());
	}

	///////////////////////////////////////////////////////////////////////////
	private void handleNewDrag(Event e) {
		if (chkbImmediate.isSelected()) {
			canvas.reset(false, true);
		}
	}

	private void handleNewPath(Event e) {
		displayWordOnLabel();

		if (chkbImmediate.isSelected()) {
			runComputation();
			canvas.reset(true, false);
		}
	}

	public void resetButtAction() {
		performCompleteReset();
	}

	public void computeButtAction() {
		runComputation();
	}
	///////////////////////////////////////////////////////////////////////////

	protected void performCompleteReset() {
		canvas.reset(true, true);
		resetWordLabel();
		resultsTable.getItems().clear();
	}

	protected void runComputation() {
		Word word = canvas.getWord();
		AutomataComputationTask task = new AutomataComputationTask(automata, word);

		prgProgress.progressProperty().bind(task.progressProperty());
		prgProgress.visibleProperty().bind(task.runningProperty());

		task.setOnSucceeded((e) -> {
			Map<String, Degree> items = task.getValue();
			Set<Entry<String, Degree>> entries = items.entrySet();
			ObservableList<Entry<String, Degree>> list = FXCollections.observableArrayList(entries);
			resultsTable.setItems(list);
		});

		new Thread(task).start();
	}

	///////////////////////////////////////////////////////////////////////////

	private void displayWordOnLabel() {
		Word word = canvas.getWord();
		String text = word.toSimpleHumanString();
		lblWord.setText(text);
	}

	private void resetWordLabel() {
		lblWord.setText("");
	}
	///////////////////////////////////////////////////////////////////////////

}
