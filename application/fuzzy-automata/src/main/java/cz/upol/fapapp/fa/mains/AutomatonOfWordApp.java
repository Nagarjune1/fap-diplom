package cz.upol.fapapp.fa.mains;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.ling.WordTIMParser;
import cz.upol.fapapp.core.ling.WordWithAlphabet;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.automata.FATIMComposer;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.modifs.AutomataCreator;

/**
 * Application generating automata recogniting word.
 * 
 * @author martin
 *
 */
public class AutomatonOfWordApp {

	public static void main(String[] args) {
		// args = new String[] { "test-data/basic/word1.timf",
		// "test-data/basic/automaton-of-word1.timf" };

		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());
		if (argsList == null) {
			System.exit(1);
		}

		String wordFileName = argsList.get(0);
		String automatonFileName = argsList.get(1);

		generate(wordFileName, automatonFileName);
	}

	private static void generate(String wordFileName, String automatonFileName) {
		File wordFile = new File(wordFileName);
		File automatonFile = new File(automatonFileName);

		generate(wordFile, automatonFile);
	}

	private static void generate(File wordFile, File automatonFile) {
		WordTIMParser wordParser = new WordTIMParser();
		WordWithAlphabet word;
		try {
			word = wordParser.parse(wordFile);

		} catch (IOException e) {
			Logger.get().error("Cannot load file with word: " + e);
			return;
		}

		FuzzyAutomaton automaton = generate(word.getAlphabet(), word);

		FATIMComposer automatonComposer = new FATIMComposer();
		try {
			automatonComposer.compose(automaton, automatonFile);
		} catch (IOException e) {
			Logger.get().error("Canno save file with automaton: " + e);
			return;
		}

	}

	private static FuzzyAutomaton generate(Alphabet alphabet, Word word) {
		return AutomataCreator.automatonOfWord(alphabet, word);
	}

	private static void printHelp() {
		System.out.println("AutomatonOfWordApp");
		System.out.println("Usage: AutomatonOfWordApp word.timf automaton.timf ");
	}

}
