package cz.upol.fapapp.core.ling;

import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectComposer} for {@link Language}.
 * 
 * @author martin
 *
 */
public class LangTIMComposer extends TIMObjectComposer<Language> {

	private final Alphabet alphabet;

	public LangTIMComposer(Alphabet alphabet) {
		super(LangTIMParser.TYPE);
		this.alphabet = alphabet;
	}

	@Override
	protected void process(Language language, TIMFileData data) {
		if (alphabet == null) {
			Alphabet inferred = CollectionsUtils.inferAlphabetOfLanguage(language);
			processAlphabet(inferred, data);
		} else {
			processAlphabet(alphabet, data);
		}

		processLanguage(language, data);
	}

	private void processAlphabet(Alphabet alphabet, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.alphabetToLine(alphabet);
		data.add("alphabet", line);
	}

	private void processLanguage(Language language, TIMFileData data) {
		for (Word word : language.getWords()) {
			LineElements line = TIMObjectParserComposerTools.wordToLine(word);
			data.add("word", line);
		}
	}

}
