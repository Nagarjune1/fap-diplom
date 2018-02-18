package cz.upol.fapapp.fa.gui.misc;

import java.util.Map;
import java.util.TreeMap;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import javafx.concurrent.Task;

/**
 * JavaFx task performing computation of automata. Updates states (number of
 * computed automata).
 * 
 * @author martin
 *
 */
public class AutomataComputationTask extends Task<Map<String, Degree>> {

	private final Map<String, FuzzyAutomaton> automata;
	private final Word word;

	public AutomataComputationTask(Map<String, FuzzyAutomaton> automata, Word word) {
		super();
		this.automata = automata;
		this.word = word;
	}

	@Override
	protected Map<String, Degree> call() throws Exception {
		Map<String, Degree> result = new TreeMap<>();

		int done = 0;
		for (String name : automata.keySet()) {
			this.updateMessage(name + " ...");

			FuzzyAutomaton automaton = automata.get(name);

			Degree degree = automaton.degreeOfWord(word);

			result.put(name, degree);

			if (isCancelled()) {
				break;
			}

			this.updateProgress(done, automata.size());
			done++;
		}

		return result;
	}

}
