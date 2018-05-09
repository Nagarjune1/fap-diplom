package cz.upol.fapapp.fa.press;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} for {@link DataPressureDataset}.
 * 
 * @author martin
 *
 */
public class TIMFDPDatasetParser extends TIMObjectParser<DataPressureDataset> {

	public static final String TYPE = "data pressure dataset";

	public TIMFDPDatasetParser() {
		super(TYPE);
	}

	@Override
	public DataPressureDataset process(TIMFileData data) {
		double delta = parseDelta(data);

		DatasetRow thresholds = parseThresholds(data);
		int columnCount;
		if (thresholds != null) {
			columnCount = thresholds.count();
		} else {
			columnCount = 0;
		}

		List<DatasetRow> trainingData = parseTrainingData(data, columnCount);
		if (columnCount == 0) {
			columnCount = trainingData.get(0).count();
		}

		List<DatasetRow> testData = parseTestData(data, columnCount);

		return new DataPressureDataset(delta, thresholds, trainingData, testData);
	}

	private double parseDelta(TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.findElementsMerged(data, "delta");
		if (line.count() > 1) {
			Logger.get().warning("Extra data in delta element");
		}

		String deltaStr = line.getIth(0);
		return TIMObjectParserComposerTools.parseDouble(deltaStr);
	}

	private DatasetRow parseThresholds(TIMFileData data) {
		if (!TIMObjectParserComposerTools.has(data, "thresholds")) {
			return null;
		}

		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "thresholds");
		if (lines.size() > 1) {
			Logger.get().warning("Extra lines in threshold element");
		}

		LineElements line = lines.get(0);
		DatasetRow values = parseRow(line, false);
		return values;
	}

	private List<DatasetRow> parseTrainingData(TIMFileData data, int thresholdsCount) {
		return parseValues(data, thresholdsCount, true, "training data");
	}

	private List<DatasetRow> parseTestData(TIMFileData data, int thresholdsCount) {
		return parseValues(data, thresholdsCount, false, "test data", "testing data");
	}

	private List<DatasetRow> parseValues(TIMFileData data, int thresholdsCount, boolean lastColumnIsResult,
			String... names) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, names);

		List<DatasetRow> result = new ArrayList<>(lines.size());
		for (LineElements line : lines) {
			if (lastColumnIsResult) {
				TIMObjectParserComposerTools.lenthAtLeast(line, thresholdsCount - 1);
			} else {
				TIMObjectParserComposerTools.lenthAtLeast(line, thresholdsCount);

			}

			DatasetRow values = parseRow(line, lastColumnIsResult);
			result.add(values);
		}

		return result;
	}

	private DatasetRow parseRow(LineElements line, boolean lastColumnIsResult) {
		List<String> lineElems = line.getElements();

		List<Double> values = lineElems.stream() //
				.map((s) -> Double.parseDouble(s)) //
				.collect(Collectors.toList());

		DatasetRow row = new DatasetRow(values);

		if (lastColumnIsResult) {
			double result = values.remove(values.size() - 1);
			row.setResult(result);
		}

		return row;

	}

}
