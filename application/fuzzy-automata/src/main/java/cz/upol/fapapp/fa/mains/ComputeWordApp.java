package cz.upol.fapapp.fa.mains;

import java.io.File;
import java.io.IOException;
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

	private static Degree run(String automatonFileName, String wordFileName) {
		File automatonFile = new File(automatonFileName);
		File wordFile = new File(wordFileName);

		return run(automatonFile, wordFile);
	}

	private static Degree run(File automatonFile, File wordFile) {
		FATIMParser automatonParser = new FATIMParser();
		FuzzyAutomaton automaton;
		try {
			automaton = (FuzzyAutomaton) automatonParser.parse(automatonFile);
		} catch (IOException e) {
			Logger.get().error("Cannot load automaton file: " + e);
			return null;
		}

		WordTIMParser wordParser = new WordTIMParser();
		Word word;
		try {
			word = wordParser.parse(wordFile);
		} catch (IOException e) {
			Logger.get().error("Cannot load word file: " + e);
			return null;
		}

		return run(automaton, word);
	}

	private static Degree run(FuzzyAutomaton automaton, Word word) {
		return automaton.degreeOfWord(word);
	}

	private static void printHelp() {
		System.out.println("ComputeWord");
		System.out.println("Usage: ComputeWord automaton.timf word.timf");
	}

}
