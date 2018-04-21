package cz.upol.fapapp.fa.press;

import java.util.ArrayList;
import java.util.List;

import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class TIMFDPDatesetComposer extends TIMObjectComposer<DataPressureDataset> {

	public TIMFDPDatesetComposer() {
		super(TIMFDPDatasetParser.TYPE);
	}

	@Override
	protected void process(DataPressureDataset dataset, TIMFileData data) {
		processDelta(dataset.getDelta(), data);
		processThresholds(dataset.getThresholds(), data);
		processTrainingData(dataset.getTrainingData(), data);
		processTestData(dataset.getTestData(), data);
	}

	private void processDelta(double delta, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.doublesToLine(delta);
		data.add("delta", line);
	}

	private void processThresholds(DatasetRow thresholds, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.doublesToLine(thresholds.getValues());
		data.add("thresholds", line);
	}

	private void processTrainingData(List<DatasetRow> trainingData, TIMFileData data) {
		processRows(trainingData, data, "training data");
	}

	private void processTestData(List<DatasetRow> testData, TIMFileData data) {
		processRows(testData, data, "test data");
	}

	
	private void processRows(List<DatasetRow> rows, TIMFileData data, String name) {
		for (DatasetRow row : rows) {

			List<Double> rowData = new ArrayList<>(row.getValues());
			if (row.getResult() != null) {
				rowData.add(row.getResult());
			}

			LineElements line = TIMObjectParserComposerTools.doublesToLine(rowData);
			data.add(name, line);
		}
	}

}
