package cz.upol.fapapp.core.ling;

import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectComposer} for {@link Word}.
 * 
 * @author martin
 *
 */
public class WordTIMComposer extends TIMObjectComposer<Word> {

	private final Alphabet alphabet;

	public WordTIMComposer(Alphabet alphabet) {
		super(WordTIMParser.TYPE);
		this.alphabet = alphabet;
	}

	@Override
	protected void process(Word word, TIMFileData data) {
		if (alphabet == null) {
			Alphabet inferred = CollectionsUtils.inferAlphabetOfWord(word);
			processAlphabet(inferred, data);
		} else {
			processAlphabet(alphabet, data);
		}

		processWord(word, data);
	}

	private void processAlphabet(Alphabet alphabet, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.alphabetToLine(alphabet);
		data.add("alphabet", line);
	}

	private void processWord(Word word, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.wordToLine(word);
		data.add("word", line);
	}

}
