package cz.upol.fapapp.fa.typos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.CollectionsUtils;
import cz.upol.fapapp.core.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.fa.automata.FuzzyAutomata;
import cz.upol.fapapp.fa.modifs.AutomataCreator;
import cz.upol.fapapp.fa.modifs.AutomataDeformer;

public class TyposCorrecter {

	private static final Integer PRECISION = 5;

	private final Map<Word, FuzzyAutomata> automata;

	public TyposCorrecter(List<Word> dictionary, KeyboardMap keymap, Degree replacesDegree, Degree removesOnesDegree,
			Degree insertsOnesDegree, Degree insertsMoreDegree) {
		super();

		this.automata = generateAutomata(dictionary, keymap, replacesDegree, removesOnesDegree, insertsOnesDegree,
				insertsMoreDegree);
	}

	////////////////////////////////////////////////////////////////////////////////

	private static Map<Word, FuzzyAutomata> generateAutomata(List<Word> dictionary, KeyboardMap keymap,
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

	private static FuzzySet<Symbol> toInsertsAndRemoves(Alphabet alphabet, Degree typoRatio) {
		return new FuzzySet<>(alphabet.stream() //
				.collect(Collectors.toMap( //
						(s) -> s, //
						(s) -> typoRatio)));
	}

	private static FuzzyAutomata toAutomaton(Alphabet alphabet, Word word,
			FuzzyBinaryRelation<Symbol, Symbol> similarity, FuzzySet<Symbol> removesOnes, FuzzySet<Symbol> insertsOnes,
			FuzzySet<Symbol> insertsMore) {

		FuzzyAutomata ofWord = AutomataCreator.automataOfWord(alphabet, word);

		FuzzyAutomata deformed = deform(ofWord, similarity, removesOnes, insertsOnes, insertsMore);

		return deformed;
	}

	private static FuzzyAutomata deform(FuzzyAutomata automata, FuzzyBinaryRelation<Symbol, Symbol> similarity,
			FuzzySet<Symbol> removesOnes, FuzzySet<Symbol> insertsOnes, FuzzySet<Symbol> insertsMore) {

		AutomataDeformer deformer = new AutomataDeformer(automata);

		deformer.addRemoveOne(removesOnes);
		deformer.addInsertOne(insertsOnes);
		deformer.addReplace(similarity);
		deformer.addInsertMore(insertsMore);

		// automata.print(System.out);

		return deformer.getAutomata(PRECISION);
	}

	///////////////////////////////////////////////////////////////////////////////

	public String correct(String word) {
		return correct(word, false, true, false);
	}

	public String correct(String word, boolean upperNoTypo, boolean upperReplaced, boolean upperNoMatch) {
		Word input = WordsTimFileParser.stringToWord(word);
		CorrectionResult output = correct(input);

		return toString(output, upperNoTypo, upperReplaced, upperNoMatch);
	}

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

	public CorrectionResult correct(Word word) {
		Word bestWord = word;
		Degree bestDegree = Degree.ZERO;

		for (Word pattern : automata.keySet()) {			
			FuzzyAutomata automaton = automata.get(pattern);

			Degree degree = automaton.degreeOfWord(word);

			Logger.get().moreinfo("Word " + pattern + " matches " + pattern + " in " + degree);
			
			if (bestDegree.isLessOrEqual(degree)) {
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

	private CorrectionResult processBestMatched(Word word, Word bestWord, Degree bestDegree) {
		CorrectionResult result = new CorrectionResult(word, bestWord, bestDegree);

		if (result.isNoTypo()) {
			Logger.get().moreinfo("Word " + word + " is in dictionary");
		} else if (result.isNoMatch()) {
			Logger.get().moreinfo("Word " + word + " does not match to any of dictionary");
		} else if (result.isCorrected()) {
			Logger.get().moreinfo("Word " + word + " will be corrected to " + bestWord + " in " + bestDegree);
		} else {
			Logger.get().moreinfo("Something strange happened to word " + word);
		}

		return result;
	}

}
