package cz.upol.fapapp.fa.press;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Language;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.modifs.AutomataCreator;

public class DataPressurePerformer {
	private static final Symbol SYMBOL_ZERO = new Symbol("0");
	private static final Symbol SYMBOL_ONE = new Symbol("1");

	// something between 0 and 1
	private static final double RESULT_THRESHOLD = 0.5;

	public DataPressurePerformer() {
	}

	public void run(DataPressureDataset dataset) {
		if (dataset.getThresholds() == null) {
			generateThresholds(dataset);
		}

		FuzzyAutomaton automaton = prepareToAutomaton(dataset);

		for (DatasetRow row : dataset.getTestData()) {
			double result = computeOneRow(dataset, automaton, row);
			row.setResult(result);
		}

	}

	///////////////////////////////////////////////////////////////////////////
	private void generateThresholds(DataPressureDataset dataset) {
		DatasetRow thresholds = computeThresholds(dataset);
		dataset.setThresholds(thresholds);

	}

	protected DatasetRow computeThresholds(DataPressureDataset dataset) {
		DatasetRow firstRow = dataset.getTrainingData().get(0);

		List<Double> thresholds = new ArrayList<>(firstRow.count());
		for (int columnIndex = 0; columnIndex < firstRow.count(); columnIndex++) {
			List<Double> column = dataset.listColumn(columnIndex);
			double median = computeMedian(column);
			thresholds.add(median);
		}

		return new DatasetRow(thresholds);
	}

	////////////////////////////////////////////////////////////////////////////

	protected FuzzyAutomaton prepareToAutomaton(DataPressureDataset dataset) {
		List<Word> words = prepareTrainingWords(dataset);
		Set<Word> wordsSet = new HashSet<>(words);
		Alphabet alphabet = CollectionsUtils.toAlphabet(SYMBOL_ONE, SYMBOL_ZERO);
		Language language = new Language(wordsSet);

		FuzzyAutomaton automaton = AutomataCreator.automatonOfLanguage(alphabet, language);
		FuzzyAutomaton determinised = (FuzzyAutomaton) automaton.determinise();
		Degree delta = new Degree(dataset.getDelta());
		FuzzyAutomaton minimised = (FuzzyAutomaton) determinised.minimise(delta);

		return minimised;
	}

	protected List<Word> prepareTrainingWords(DataPressureDataset dataset) {
		List<DatasetRow> values = dataset.getTrainingData();
		List<DatasetRow> filtered = filterAccepted(values);
		List<Word> words = convertToWords(filtered, dataset);

		return words;
	}

	private List<DatasetRow> filterAccepted(List<DatasetRow> values) {
		return values.stream() //
				.filter((r) -> r.getResult() > RESULT_THRESHOLD) //
				.collect(Collectors.toList());
	}

	protected double computeOneRow(DataPressureDataset dataset, FuzzyAutomaton automaton, DatasetRow row) {
		Word word = toWord(row, dataset);

		Degree degree = automaton.degreeOfWord(word);

		double result = toResult(degree);
		return result;
	}

	private double toResult(Degree degree) {
		return degree.getValue();
	}

	///////////////////////////////////////////////////////////////////////////

	protected List<Word> convertToWords(List<DatasetRow> values, DataPressureDataset dataset) {
		return values.stream() //
				.map((l) -> toWord(l, dataset)) //
				.collect(Collectors.toList());
	}

	private Word toWord(DatasetRow line, DataPressureDataset dataset) {
		List<Symbol> result = new ArrayList<>(line.count());

		for (int i = 0; i < line.count(); i++) {
			double value = line.getValue(i);
			boolean isOver = isOverThreshold(value, i, dataset);
			Symbol symbol;
			if (isOver) {
				symbol = SYMBOL_ONE;
			} else {
				symbol = SYMBOL_ZERO;
			}

			result.add(symbol);
		}

		return new Word(result);
	}

	private boolean isOverThreshold(double value, int index, DataPressureDataset dataset) {
		double thresholdValue = dataset.getThresholds().getValue(index);
		return value > thresholdValue;
	}

	protected double computeMedian(List<Double> column) {
		Collections.sort(column);
		int size = column.size();

		if (size % 2 == 0) {
			return column.get(size / 2);
		} else {
			return (column.get(size / 2) + column.get(size / 2 + 1)) / 2;
		}
	}

}
