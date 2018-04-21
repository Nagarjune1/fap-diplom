package cz.upol.fapapp.fa.automata;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;

public class FuzzyAutomataBruteForceTester {

	private final PrintStream out;

	public FuzzyAutomataBruteForceTester(PrintStream out) {
		this.out = out;
	}

	public Set<Word> generateWords(Alphabet alphabet, int maxLenght) {
		Set<Word> result = new LinkedHashSet<>(alphabet.size() * alphabet.size() * maxLenght); // approx

		Word word = new Word();
		result.add(word);

		for (int i = 0; i <= maxLenght; i++) {
			Stack<Symbol> symbols = new Stack<>();
			Set<Word> subWords = generateWordsRec(alphabet, maxLenght, symbols);
			result.addAll(subWords);
		}

		return result;
	}

	private Set<Word> generateWordsRec(Alphabet alphabet, int remainingLenght, Stack<Symbol> symbols) {
		Set<Word> result = new LinkedHashSet<>(alphabet.size());

		if (remainingLenght >= 0) {
			Word word = new Word(symbols);
			result.add(word);

			for (Symbol symbol : alphabet) {
				symbols.push(symbol);

				Set<Word> subWords = generateWordsRec(alphabet, remainingLenght - 1, symbols);
				result.addAll(subWords);

				symbols.pop();
			}
		}

		return result;
	}

	///////////////////////////////////////////////////////////////////////////

	public void runWords(Collection<Word> words, Consumer<Word> tableCellRenderer) {

		tableCellRenderer.accept(null);
		out.println();

		for (Word word : words) {
			tableCellRenderer.accept(word);
			out.println();
		}

		out.println();
	}

	public void runWordsOverAutomata(Collection<Word> words, Map<String, FuzzyAutomaton> automata) {
		runWords(words, (w) -> { //
			putHeadingColumn(w);

			for (String name : automata.keySet()) {
				FuzzyAutomaton automaton = automata.get(name);

				putAutomatonColumn(w, name, automaton);
			}
		});
	}

	///////////////////////////////////////////////////////////////////////////

	public void putHeadingColumn(Word word) {
		if (word != null) {
			String wordStr = word.toSimpleHumanString();
			putCell(wordStr, true);

		} else {
			putCell("Word", true);
		}
	}

	public void putAutomatonColumn(Word word, String header, FuzzyAutomaton automaton) {
		String str;

		if (word != null) {
			Degree degree = automaton.degreeOfWord(word);
			if (Degree.ZERO.equals(degree)) {
				str = ".";
			} else {
				str = new DecimalFormat("0.0######").format(degree.getValue());
			}

		} else {
			str = header + "(" + automaton.getStates().size() + ")";

		}

		putCell(str, false);
	}

	private void putCell(String string, boolean isLeftColumn) {
		if (isLeftColumn) {
			out.print('|');
		}

		out.print(' ');

		out.printf("%-10s", string);

		out.print(' ');
		out.print('|');
	}

}
