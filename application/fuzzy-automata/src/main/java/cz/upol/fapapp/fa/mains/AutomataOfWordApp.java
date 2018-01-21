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
import cz.upol.fapapp.fa.automata.AutomataCreator;
import cz.upol.fapapp.fa.automata.FATIMComposer;
import cz.upol.fapapp.fa.automata.FuzzyAutomata;

public class AutomataOfWordApp {

	public static void main(String[] args) {
		//args = new String[] { "test-data/basic/word1.timf", "test-data/basic/automata-of-word1.timf" };

		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());
		if (argsList == null) {
			System.exit(1);
		}

		String wordFileName = argsList.get(0);
		String automataFileName = argsList.get(1);

		generate(wordFileName, automataFileName);
	}

	private static void generate(String wordFileName, String automataFileName) {
		File wordFile = new File(wordFileName);
		File automataFile = new File(automataFileName);

		generate(wordFile, automataFile);
	}

	private static void generate(File wordFile, File automataFile) {
		WordTIMParser wordParser = new WordTIMParser();
		WordWithAlphabet word;
		try {
			word = wordParser.parse(wordFile);

		} catch (IOException e) {
			Logger.get().error("Cannot load file with word: " + e);
			return;
		}

		FuzzyAutomata automata = generate(word.getAlphabet(), word);

		FATIMComposer automataComposer = new FATIMComposer();
		try {
			automataComposer.compose(automata, automataFile);
		} catch (IOException e) {
			Logger.get().error("Canno save file with automata: " + e);
			return;
		}

	}

	private static FuzzyAutomata generate(Alphabet alphabet, Word word) {
		return AutomataCreator.automataOfWord(alphabet, word);
	}

	private static void printHelp() {
		System.out.println("AutomataOfWordApp");
		System.out.println("Usage: AutomataOfWordApp word.timf automata.timf ");
	}

}
