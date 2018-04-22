package cz.upol.fapapp.fa.mains;

import java.util.List;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.ling.WordTIMParser;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.automata.FATIMParser;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

/**
 * Application for compiting degree of acceptance by automaton.
 * 
 * @author martin
 *
 */
public class ComputeWordApp {

	public static void main(String[] args) {
		// args = new String[] { "test-data/basic/automaton1.timf",
		// "test-data/basic/word1.timf" };
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());
		if (argsList == null) {
			System.exit(1);
		}

		String automatonFileName = argsList.get(0);
		String wordFileName = argsList.get(1);

		Degree degree = run(automatonFileName, wordFileName);
		System.out.println(degree);
	}

	private static Degree run(String automatonFile, String wordFile) {
		FuzzyAutomaton automaton = (FuzzyAutomaton) AppsMainsTools.runParser(automatonFile, new FATIMParser());
		Word word = AppsMainsTools.runParser(wordFile, new WordTIMParser());

		if (automaton == null || word == null) {
			return null;
		}

		return run(automaton, word);
	}

	private static Degree run(FuzzyAutomaton automaton, Word word) {
		Logger.get().info("Runs word " + word + " over automaton");
		return automaton.degreeOfWord(word);
	}

	private static void printHelp() {
		System.out.println("ComputeWord");
		System.out.println("Usage: ComputeWord automaton.timf word.timf");
	}

}
