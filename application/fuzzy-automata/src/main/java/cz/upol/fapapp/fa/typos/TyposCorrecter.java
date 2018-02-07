package cz.upol.fapapp.fa.typos;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.CollectionsUtils;
import cz.upol.fapapp.core.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.fa.automata.AutomataCreator;
import cz.upol.fapapp.fa.automata.AutomataDeformer;
import cz.upol.fapapp.fa.automata.FuzzyAutomata;

public class TyposCorrecter {

	private final Map<Word, FuzzyAutomata> automata;

	public TyposCorrecter(List<Word> dictionary, KeyboardMap keymap, Degree typoRatio) {
		super();

		this.automata = generateAutomata(dictionary, keymap, typoRatio);
	}

	private static Map<Word, FuzzyAutomata> generateAutomata(List<Word> dictionary, KeyboardMap keymap,
			Degree typoRatio) {
		FuzzyBinaryRelation<Symbol, Symbol> similarity = keymap.toFuzzyRelation(typoRatio);

		return dictionary.stream() //
				.collect(Collectors.toMap((w) -> w, //
						(w) -> toAutomaton(w, similarity, typoRatio)));
	}

	private static FuzzyAutomata toAutomaton(Word word, FuzzyBinaryRelation<Symbol, Symbol> similarity,
			Degree typoRatio) {
		Alphabet alphabet = CollectionsUtils.createAlphabet('a', (char) ('z' + 1));
		FuzzyAutomata ofWord = AutomataCreator.automataOfWord(alphabet, word);

		FuzzyAutomata deformed = deform(ofWord, similarity, typoRatio);

		return deformed;
	}

	private static FuzzyAutomata deform(FuzzyAutomata automata, FuzzyBinaryRelation<Symbol, Symbol> similarity,
			Degree typoRatio) {

		automata = AutomataDeformer.deformInsertions(automata, typoRatio);
		automata = AutomataDeformer.deformReplace(automata, similarity);
		
		//automata.print(System.out);

		return automata;
	}

	///////////////////////////////////////////////////////////////////////////////

	public String correct(String word) {
		Word input = WordsTimFileParser.stringToWord(word);
		Word output = correct(input);

		return WordsTimFileComposer.wordToString(output);
	}

	public Word correct(Word word) {
		Word bestWord = word;
		Degree bestDegree = Degree.ZERO;
		
		for (Word pattern: automata.keySet()) {
			FuzzyAutomata automaton = automata.get(pattern);
			
			Degree degree = automaton.degreeOfWord(word);
			
			Logger.get().moreinfo("Word " + pattern + " matches " + pattern + " in " + degree);
			
			if (bestDegree.compareTo(degree) < 0) {
				bestWord = pattern;
				bestDegree = degree;
			}
		}
		
		if (Degree.ZERO.equals(bestDegree)) {
			Logger.get().moreinfo("Word " + word + " does not match to any of dictionary");
		} else {
			Logger.get().moreinfo("Word " + word + " will be corrected to " + bestWord + " in " + bestDegree);
		}
		
		return bestWord;
	}

}
