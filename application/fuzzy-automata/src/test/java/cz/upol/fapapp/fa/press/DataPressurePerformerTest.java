package cz.upol.fapapp.fa.press;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.tnorm.ProductTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.automata.FuzzyAutomataBruteForceTester;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

public class DataPressurePerformerTest {

	@Test
	@Ignore
	public void test() throws IOException {
		Logger.get().warning("Test " + getClass().getName() + " skipped");
		TNorms.setTnorm(new ProductTNorm());
		
		//String filePath = "test-data/press/iris-setosa-1.timf";
		//String filePath = "test-data/press/iris-virginica-1.timf";
		//String filePath = "test-data/press/iris-versicolor-1.timf";
		String filePath = "test-data/press/flag-data-europe-v2.timf";
		
		File file = new File(filePath);
		TIMFDPDatasetParser parser = new TIMFDPDatasetParser();
		DataPressureDataset dataset = parser.parse(file);

		DataPressurePerformer performer = new DataPressurePerformer();

		DatasetRow thresholds = performer.computeThresholds(dataset);
		dataset.setThresholds(thresholds);
		
		Map<String, FuzzyAutomaton> automata = new LinkedHashMap<>();
		for (double delta = 0.0; delta < 1.0; delta += 0.05) {
			dataset.setDelta(delta);
			FuzzyAutomaton automaton = performer.prepareToAutomaton(dataset);
			// automaton.print(System.out);
			String label = "Aut" + delta;
			automata.put(label, automaton);
		}

		FuzzyAutomataBruteForceTester tester = new FuzzyAutomataBruteForceTester(System.out);

		List<Word> testWords = performer.convertToWords(dataset.getTestData(), dataset);
		tester.runWordsOverAutomata(testWords, automata);

		List<Word> trainingWords = performer.prepareTrainingWords(dataset);
		tester.runWordsOverAutomata(trainingWords, automata);

	}

}
