package cz.upol.fapapp.fa.typos;

import java.util.List;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;

public class WordsTimFileComposer extends TIMObjectComposer<List<Word>> {

	public WordsTimFileComposer() {
		super(WordsTimFileParser.TYPE);
	}

	@Override
	protected void process(List<Word> object, TIMFileData data) {
		processWords(object, data);
	}

	private void processWords(List<Word> words, TIMFileData data) {
		words.stream() //
				.map((w) -> wordToString(w)) //
				.forEach((s) -> data.add("words", s));
	}

	public static String wordToString(Word word) {
		return word.getSymbols().stream() //
				.map((s) -> s.getValue()) //
				.collect(Collectors.joining());

	}

}
