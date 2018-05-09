package cz.upol.fapapp.fa.typos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} of {@link List} of {@link Word}. Uses following
 * syntax:
 * 
 * <pre>
 * alphabet:
 *   a b c d e
 * words:
 *   abece
 *   debebe
 *   decaba
 * </pre>
 * 
 * @author martin
 *
 */
public class WordsTimFileParser extends TIMObjectParser<List<Word>> {

	public static final String TYPE = "words for typos correct";

	public WordsTimFileParser() {
		super(TYPE);
	}

	@Override
	public List<Word> process(TIMFileData data) {
		List<Word> words = processWords(data);

		return words;
	}

	private List<Word> processWords(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "words", "list");

		return elements.getElements().stream() //
				.map((e) -> stringToWord(e)) //
				.collect(Collectors.toList());
	}

	public static Word stringToWord(String string) {
		String lower = string.toLowerCase();

		List<Symbol> symbols = new ArrayList<>(lower.length());

		for (int i = 0; i < lower.length(); i++) {
			String str = Character.toString(lower.charAt(i));
			Symbol symbol = new Symbol(str);
			symbols.add(symbol);
		}

		return new Word(symbols);
	}

}
