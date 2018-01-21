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
import cz.upol.fapapp.fa.automata.FuzzyAutomata;

public class ComputeWordApp {

	public static void main(String[] args) {
		//args = new String[] { "test-data/basic/automata1.timf", "test-data/basic/word1.timf" };
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());
		if (argsList == null) {
			System.exit(1);
		}

		String automataFileName = argsList.get(0);
		String wordFileName = argsList.get(1);

		Degree degree = run(automataFileName, wordFileName);
		System.out.println(degree);
	}

	private static Degree run(String automataFileName, String wordFileName) {
		File automataFile = new File(automataFileName);
		File wordFile = new File(wordFileName);

		return run(automataFile, wordFile);
	}

	private static Degree run(File automataFile, File wordFile) {
		FATIMParser automataParser = new FATIMParser();
		FuzzyAutomata automata;
		try {
			automata = (FuzzyAutomata) automataParser.parse(automataFile);
		} catch (IOException e) {
			Logger.get().error("Cannot load automata file: " + e);
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

		return run(automata, word);
	}

	private static Degree run(FuzzyAutomata automata, Word word) {
		return automata.degreeOfWord(word);
	}

	private static void printHelp() {
		System.out.println("ComputeWord");
		System.out.println("Usage: ComputeWord automata.timf word.timf");
	}

}
