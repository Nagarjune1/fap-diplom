package cz.upol.fapapp.fa.typos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.modifs.AutomataCreator;
import cz.upol.fapapp.fa.modifs.AutomatonDeformer;

/**
 * Class performing correction of typos.
 * 
 * @author martin
 *
 */
public class TyposCorrecter {

	private static final Integer PRECISION = 5;

	private final Map<Word, FuzzyAutomaton> automata;

	public TyposCorrecter(List<Word> dictionary, KeyboardMap keymap, Degree replacesDegree, Degree removesOnesDegree,
			Degree insertsOnesDegree, Degree insertsMoreDegree) {
		super();

		this.automata = generateAutomata(dictionary, keymap, replacesDegree, removesOnesDegree, insertsOnesDegree,
				insertsMoreDegree);
	}

	////////////////////////////////////////////////////////////////////////////////

	/**
	 * Generated automata for given words.
	 * 
	 * @param dictionary
	 * @param keymap
	 * @param replacesDegree
	 * @param removesDegree
	 * @param insertsOnesDegree
	 * @param insertsMoreDegree
	 * @return
	 */
	private static Map<Word, FuzzyAutomaton> generateAutomata(List<Word> dictionary, KeyboardMap keymap,
			Degree replacesDegree, Degree removesDegree, Degree insertsOnesDegree, Degree insertsMoreDegree) {

		Alphabet alphabet = CollectionsUtils.createAlphabet('a', (char) ('z' + 1));

		FuzzyBinaryRelation<Symbol, Symbol> similarity = keymap.toFuzzyRelation(replacesDegree);
		FuzzySet<Symbol> removesOnes = toInsertsAndRemoves(alphabet, removesDegree);
		FuzzySet<Symbol> insertsOnes = toInsertsAndRemoves(alphabet, insertsOnesDegree);
		FuzzySet<Symbol> insertsMore = toInsertsAndRemoves(alphabet, insertsMoreDegree);

		return dictionary.stream() //
				.collect(Collectors.toMap(//
						(w) -> w, //
						(w) -> toAutomaton(alphabet, w, similarity, removesOnes, insertsOnes, insertsMore), //
						(wOld, wNew) -> wOld)); //
	}

	/**
	 * Converts typos ratio to fuzzy set of theese.
	 * 
	 * @param alphabet
	 * @param typoRatio
	 * @return
	 */
	private static FuzzySet<Symbol> toInsertsAndRemoves(Alphabet alphabet, Degree typoRatio) {
		return new FuzzySet<>(alphabet.stream() //
				.collect(Collectors.toMap( //
						(s) -> s, //
						(s) -> typoRatio)));
	}

	/**
	 * Converts given word to automaton.
	 * 
	 * @param alphabet
	 * @param word
	 * @param similarity
	 * @param removesOnes
	 * @param insertsOnes
	 * @param insertsMore
	 * @return
	 */
	private static FuzzyAutomaton toAutomaton(Alphabet alphabet, Word word,
			FuzzyBinaryRelation<Symbol, Symbol> similarity, FuzzySet<Symbol> removesOnes, FuzzySet<Symbol> insertsOnes,
			FuzzySet<Symbol> insertsMore) {

		FuzzyAutomaton ofWord = AutomataCreator.automatonOfWord(alphabet, word);

		FuzzyAutomaton deformed = deform(ofWord, similarity, removesOnes, insertsOnes, insertsMore);
		
		if (Logger.get().isDebug()) {
			Logger.get().debug("Obtained deformed automata for word " + word + ":");
			deformed.print(Logger.META_STREAM);
		}

		return deformed;
	}

	/**
	 * Deforms given automaton.
	 * 
	 * @param automaton
	 * @param similarity
	 * @param removesOnes
	 * @param insertsOnes
	 * @param insertsMore
	 * @return
	 */
	private static FuzzyAutomaton deform(FuzzyAutomaton automaton, FuzzyBinaryRelation<Symbol, Symbol> similarity,
			FuzzySet<Symbol> removesOnes, FuzzySet<Symbol> insertsOnes, FuzzySet<Symbol> insertsMore) {

		AutomatonDeformer deformer = new AutomatonDeformer(automaton);

		deformer.addInsertMore(insertsMore); //TODO XXX do not use?!
		
		deformer.addRemoveOne(removesOnes);
		deformer.addInsertOne(insertsOnes);
		deformer.addReplace(similarity);
		

		// automaton.print(System.out);

		return deformer.getAutomaton(PRECISION);
	}

	///////////////////////////////////////////////////////////////////////////////

	/**
	 * Corrects given word. Uppercases if corrected.
	 * 
	 * @param word
	 * @return
	 */
	public String correct(String word) {
		return correct(word, false, true, false);
	}

	/**
	 * Corrects given word. If specified, uppercases the resulting word.
	 * 
	 * @param word
	 * @param upperNoTypo
	 * @param upperReplaced
	 * @param upperNoMatch
	 * @return
	 */
	public String correct(String word, boolean upperNoTypo, boolean upperReplaced, boolean upperNoMatch) {
		Word input = WordsTimFileParser.stringToWord(word);
		CorrectionResult output = correct(input);

		return toString(output, upperNoTypo, upperReplaced, upperNoMatch);
	}

	/**
	 * Converts given result to string (and if matches flags, uppercases).
	 * 
	 * @param result
	 * @param upperNoTypo
	 * @param upperReplaced
	 * @param upperNoMatch
	 * @return
	 */
	private String toString(CorrectionResult result, boolean upperNoTypo, boolean upperReplaced, boolean upperNoMatch) {
		Word word = result.getBestMatch();
		String string = WordsTimFileComposer.wordToString(word);

		boolean toUpper = upperNoTypo && result.isNoTypo() //
				|| upperReplaced && result.isCorrected() //
				|| upperNoMatch && result.isNoMatch();

		if (toUpper) {
			string = string.toUpperCase();
		}

		return string;
	}

	///////////////////////////////////////////////////////////////////////////////

	/**
	 * Runs conversion of given word.
	 * 
	 * @param word
	 * @return
	 */
	public CorrectionResult correct(Word word) {
		Word bestWord = word;
		Degree bestDegree = Degree.ZERO;

		Logger.get().info("Correcting word " + word + " ... ");

		for (Word pattern : automata.keySet()) {
			FuzzyAutomaton automaton = automata.get(pattern);

			Degree degree = automaton.degreeOfWord(word);

			Logger.get().debug("Word " + word + " matches " + pattern + " in " + degree);
			//System.out.println(" in " + degree + "\t  matches " + pattern );

			
			if (bestDegree.isLessOrEqual(degree)) {
				//TODO if found two of equal degree ...
				bestWord = pattern;
				bestDegree = degree;
			}

			if (pattern.equals(word)) {
				// no need to continue ...
				break;
			}
		}

		return processBestMatched(word, bestWord, bestDegree);
	}

	/**
	 * Creates {@link CorrectionResult} based on match of pattern's automata.
	 * 
	 * @param word
	 * @param bestWord
	 * @param bestDegree
	 * @return
	 */
	private CorrectionResult processBestMatched(Word word, Word bestWord, Degree bestDegree) {
		CorrectionResult result = new CorrectionResult(word, bestWord, bestDegree);

		if (result.isNoTypo()) {
			Logger.get().info("Word " + word + " is in dictionary");
		} else if (result.isNoMatch()) {
			Logger.get().info("Word " + word + " does not match to any of dictionary");
		} else if (result.isCorrected()) {
			Logger.get().info("Word " + word + " will be corrected to " + bestWord + " in " + bestDegree);
		} else {
			Logger.get().info("Something strange happened to word " + word);
		}

		return result;
	}

}
