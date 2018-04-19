package cz.upol.fapapp.fa.mains;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.fa.typos.DefaultKeymap;
import cz.upol.fapapp.fa.typos.KeyboardMap;
import cz.upol.fapapp.fa.typos.TyposCorrecter;
import cz.upol.fapapp.fa.typos.WordsTimFileParser;

/**
 * Application for typos correcting. Can be used both with input or iteractivelly (words read from stdin).
 * @author martin
 *
 */
public class TyposCorrecterApp {

	private static final String SPACE = " ";
	private static final String SPACE_REGEX = "\\s+";
	private static final KeyboardMap KEYMAP = new DefaultKeymap();

	private static final Degree REPLACES_DEGREE = new Degree(0.8);
	private static final Degree REMOVES_DEGREE = new Degree(0.1);
	private static final Degree INSERTS_ONES_DEGREE = new Degree(0.5);
	private static final Degree INSERTS_MORE_DEGREE = new Degree(0.01);

	public static void main(String[] args) {
		args = new String[] { /* "--verbose", */ "--tnorm", "product", "test-data/typos/months.timf", };
//		
		//		args = new String[] { /* "--verbose", */ "--tnorm", "product", "test-data/typos/animals-dictionary.timf", // };
//				"dg", "doberman" };
//		
//		args = new String[] {   "--tnorm", "product", "test-data/typos/numbers.timf"}; //XXX debug
//		
//		args = new String[] {  "--tnorm", "product", "test-data/typos/100-words.timf", // };
//				//"peqple" };
//				"nan use yout peqple sou thus thibng i thinl" };
//		

		List<String> argsList = AppsMainsTools.checkArgs(args, 1, null, () -> printHelp());
		if (argsList == null) {
			System.exit(1);
		}

		String dictionaryFileName = argsList.get(0);
		List<String> wordsList = argsList.subList(1, argsList.size());

		correct(dictionaryFileName, wordsList);
	}

	///////////////////////////////////////////////////////////////////////////

	private static void correct(String dictionaryFileName, List<String> wordsList) {
		File dictionaryFile = new File(dictionaryFileName);

		correct(dictionaryFile, wordsList);
	}

	private static void correct(File dictionaryFile, List<String> wordsList) {
		List<Word> dictionary = AppsMainsTools.runParser(dictionaryFile, new WordsTimFileParser());
		if (dictionary == null) {
			return;
		}

		KeyboardMap keymap = KEYMAP;

		correct(dictionary, keymap, wordsList);

	}

	private static void correct(List<Word> dictionary, KeyboardMap keymap, List<String> wordsList) {
		TyposCorrecter correcter = new TyposCorrecter(dictionary, keymap, //
				REPLACES_DEGREE, REMOVES_DEGREE, INSERTS_ONES_DEGREE, INSERTS_MORE_DEGREE);

		if (wordsList.isEmpty()) {
			correctStream(correcter, System.in);
		} else {
			correctList(correcter, wordsList);
		}

	}

	private static void correctList(TyposCorrecter correcter, List<String> wordsList) {
		List<String> words = mergeAndSplit(wordsList);

		for (String word : words) {
			String corrected = correcter.correct(word);
			System.out.print(corrected);
			System.out.print(SPACE);
		}
		System.out.println();
	}

	private static void correctStream(TyposCorrecter correcter, InputStream in) {
		Scanner scanner = new Scanner(in);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			List<String> wordsList = split(line);

			correctList(correcter, wordsList);
		}

		scanner.close();
	}

	///////////////////////////////////////////////////////////////////////////

	private static List<String> mergeAndSplit(List<String> wordsList) {
		String one = wordsList.stream().collect(Collectors.joining(SPACE));

		return Arrays.asList(one.split(SPACE_REGEX));
	}

	private static List<String> split(String word) {

		return Arrays.asList(word.split(SPACE_REGEX));
	}

	///////////////////////////////////////////////////////////////////////////

	private static void printHelp() {
		System.out.println("TyposCorrectedApp DICTIONARY_FILE.timf TYPOS_RATIO [WORD1 WORD2 ...]");
		System.out.println("where: DICTIONARY_FILE.timf is timf file");
		System.out.println("       TYPOS_RATIO is from 0.0 to 1.0");
		System.out.println("   and WORD1 WORD2 ... are words to correct (if none, sdtin is used)");
	}

}
