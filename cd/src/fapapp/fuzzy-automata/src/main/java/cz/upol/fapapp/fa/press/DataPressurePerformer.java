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
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.modifs.AutomataCreator;

/**
 * Performs the data pressure technique.
 * 
 * In short, for given training data table constructs words, for theese words
 * creates minimised automaton, which is then runned over the test data. The
 * results are set back into the dataset's test data results.
 * 
 * @author martin
 *
 */
public class DataPressurePerformer {
	private static final Symbol SYMBOL_ZERO = new Symbol("0");
	private static final Symbol SYMBOL_ONE = new Symbol("1");

	// something between 0 and 1
	private static final double RESULT_THRESHOLD = 0.5;

	public DataPressurePerformer() {
	}

	/**
	 * Runs data pressure technique as described before. If dataset does not
	 * have specified thresholds, generates one using medians of all columns.
	 * 
	 * @param dataset
	 */
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
	/**
	 * Generates (and sets into the dataset!) thresholds using the
	 * {@link #computeThresholds(DataPressureDataset)} method.
	 * 
	 * @param dataset
	 */
	private void generateThresholds(DataPressureDataset dataset) {
		Logger.get().info("Generating thresholds");

		DatasetRow thresholds = computeThresholds(dataset);
		dataset.setThresholds(thresholds);

		Logger.get().debug("Generated thresholds: " + thresholds);
	}

	/**
	 * Computes thresholds as medians of all columns' values.
	 * 
	 * @param dataset
	 * @return
	 */
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

	/**
	 * Generates automaton for given dataset's training data minimised with
	 * delta.
	 * 
	 * @param dataset
	 * @return
	 */
	protected FuzzyAutomaton prepareToAutomaton(DataPressureDataset dataset) {
		Logger.get().debug("Preparing automaton");

		List<Word> words = prepareTrainingWords(dataset);
		Set<Word> wordsSet = new HashSet<>(words);
		Alphabet alphabet = CollectionsUtils.toAlphabet(SYMBOL_ONE, SYMBOL_ZERO);
		Language language = new Language(wordsSet);

		FuzzyAutomaton automaton = AutomataCreator.automatonOfLanguage(alphabet, language);
		FuzzyAutomaton determinised = (FuzzyAutomaton) automaton.determinise();
		Degree delta = new Degree(dataset.getDelta());
		FuzzyAutomaton minimised = (FuzzyAutomaton) determinised.minimise(delta);

		if (Logger.get().isDebug()) {
			Logger.get().debug("Created automata:");

			Logger.get().debug("- initial automaton:");
			automaton.print(Logger.META_STREAM);

			Logger.get().debug("- determinised automaton:");
			determinised.print(Logger.META_STREAM);

			Logger.get().debug("- minimised automaton:");
			minimised.print(Logger.META_STREAM);
		}

		return minimised;
	}

	/**
	 * Converts dataset's training data into words.
	 * 
	 * @param dataset
	 * @return
	 */
	protected List<Word> prepareTrainingWords(DataPressureDataset dataset) {
		List<DatasetRow> values = dataset.getTrainingData();
		List<DatasetRow> filtered = filterAccepted(values);
		List<Word> words = convertToWords(filtered, dataset);

		return words;
	}

	/**
	 * Filters rowd which might be accepted by this model (their result is equal
	 * to 1).
	 * 
	 * @param values
	 * @return
	 */
	private List<DatasetRow> filterAccepted(List<DatasetRow> values) {
		return values.stream() //
				.filter((r) -> r.getResult() > RESULT_THRESHOLD) //
				.collect(Collectors.toList());
	}

	/**
	 * Runs computation of one row. Converts row to word, runs over automaton
	 * and degree converts back to result value.
	 * 
	 * @param dataset
	 * @param automaton
	 * @param row
	 * @return
	 */
	protected double computeOneRow(DataPressureDataset dataset, FuzzyAutomaton automaton, DatasetRow row) {
		Logger.get().info("Computing testing row " + row);

		Word word = toWord(row, dataset);

		Degree degree = automaton.degreeOfWord(word);
		Logger.get().debug("Run of word " + word + " resulted in " + degree);

		double result = toResult(degree);
		return result;
	}

	/**
	 * Converts degree to value. In fact just returns degree's value.
	 * 
	 * @param degree
	 * @return
	 */
	private double toResult(Degree degree) {
		return degree.getValue();
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Converts given rows to words.
	 * 
	 * @param values
	 * @param dataset
	 * @return
	 */
	protected List<Word> convertToWords(List<DatasetRow> values, DataPressureDataset dataset) {
		return values.stream() //
				.map((l) -> toWord(l, dataset)) //
				.collect(Collectors.toList());
	}

	/**
	 * Converts given row to the word.
	 * 
	 * @param line
	 * @param dataset
	 * @return
	 */
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

	/**
	 * Returns true if given value at given index (of column) is over
	 * corresponding threshold value or not.
	 * 
	 * @param value
	 * @param index
	 * @param dataset
	 * @return
	 */
	private boolean isOverThreshold(double value, int index, DataPressureDataset dataset) {
		double thresholdValue = dataset.getThresholds().getValue(index);
		return value > thresholdValue;
	}

	/**
	 * Computes median of given list of values.
	 * 
	 * @param column
	 * @return
	 */
	protected static double computeMedian(List<Double> column) {
		Collections.sort(column);
		int size = column.size();

		if (size % 2 == 0) {
			return column.get(size / 2);
		} else {
			return (column.get(size / 2) + column.get(size / 2 + 1)) / 2;
		}
	}

}
