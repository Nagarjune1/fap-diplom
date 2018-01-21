package cz.upol.fapapp.core.ling;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.CollectionsUtils;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class WordTIMParser extends TIMObjectParser<WordWithAlphabet> {

	public static final String TYPE = "word";
	
	public WordTIMParser() {
		super(TYPE);
	}

	@Override
	public WordWithAlphabet process(TIMFileData data) {
		Alphabet alphabet = processAlphabet(data);
		Word word = processWord(data);
		
		if (alphabet != null) {
			checkAlphabet(word, alphabet);
		} else {
			Logger.get().warning("Word " + word  + " has not specified alphabet");
		}
		
		return new WordWithAlphabet(alphabet, word);
	}
	

	private Alphabet processAlphabet(TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.findElementsMerged(data, "alphabet", "Sigma");
		return TIMObjectParserComposerTools.parseAlphabet(line);
	}

	private Word processWord(TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.findElementsMerged(data, "word", "w", "symbols");
		return TIMObjectParserComposerTools.parseWord(line);
	}

	private void checkAlphabet(Word word, Alphabet alphabet) {
		CollectionsUtils.checkWord(word, alphabet);
	}

}
