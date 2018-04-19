package cz.upol.fapapp.core.ling;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} for {@link Word}.
 * 
 * @author martin
 *
 */
public class LangTIMParser extends TIMObjectParser<LangWithAlphabet> {

	public static final String TYPE = "language";

	public LangTIMParser() {
		super(TYPE);
	}

	@Override
	public LangWithAlphabet process(TIMFileData data) {
		Alphabet alphabet = processAlphabet(data);
		Set<Word> words = processWords(data);

		// TODO check

		return new LangWithAlphabet(alphabet, words);
	}

	private Alphabet processAlphabet(TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.findElementsMerged(data, "alphabet", "Sigma");
		return TIMObjectParserComposerTools.parseAlphabet(line);
	}

	private Set<Word> processWords(TIMFileData data) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "words", "w");

		return lines.stream()//
				.map((l) -> TIMObjectParserComposerTools.parseWord(l)) //
				.collect(Collectors.toSet()); //
	}

}
