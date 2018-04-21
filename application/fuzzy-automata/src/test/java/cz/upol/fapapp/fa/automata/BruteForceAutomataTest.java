package cz.upol.fapapp.fa.automata;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.tnorm.LukasiewiczTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.ProductTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.CollectionsUtils;

public class BruteForceAutomataTest {

	private final FuzzyAutomataBruteForceTester tester = new FuzzyAutomataBruteForceTester(System.out);

	@Test
	public void testWordsGen() {
		Alphabet alphabet = CollectionsUtils.toAlphabet(new Symbol("a"), new Symbol("b"), new Symbol("c"));

		Set<Word> words0 = tester.generateWords(alphabet, 0);
		// System.out.println(words0);
		assertEquals(1, words0.size());

		Set<Word> words1 = tester.generateWords(alphabet, 1);
		// System.out.println(words1);
		assertEquals(1 + 3, words1.size());

		Set<Word> words2 = tester.generateWords(alphabet, 2);
		// System.out.println(words2);
		assertEquals(1 + 3 + 9, words2.size());

		Set<Word> words3 = tester.generateWords(alphabet, 3);
		// System.out.println(words3);
		assertEquals(1 + 3 + 9 + 27, words3.size());
	}

	@Test
	public void testItUp() {
		Alphabet alphabet = CollectionsUtils.toAlphabet(new Symbol("a"), new Symbol("b"), new Symbol("c"));

		Set<Word> words3 = tester.generateWords(alphabet, 3);

		TNorms.setTnorm(new ProductTNorm());
		FuzzyAutomaton automaton1 = TestAutomataCreator.createAutomatonOfLang();
		runSomeTestOnAutomaton(words3, automaton1);
		
		TNorms.setTnorm(new LukasiewiczTNorm());
		FuzzyAutomaton automaton2 = TestAutomataCreator.createAutomaton1();
		runSomeTestOnAutomaton(words3, automaton2);
	}

	
	

	private void runSomeTestOnAutomaton(Set<Word> words3, FuzzyAutomaton automaton) {
		Map<String, FuzzyAutomaton> automata = new LinkedHashMap<>();
		automata.put("Aut", automaton);
		
		FuzzyAutomaton determinised = (FuzzyAutomaton) automaton.determinise();
		automata.put("Det", determinised);
		
		for (double d = 0.0; d < 1.0; d+= 0.25) {
			Degree delta = new Degree(d);
			FuzzyAutomaton minimised = (FuzzyAutomaton) determinised.minimise(delta);
			
			String label = "Min" + d;
			automata.put(label, minimised);
		}
		
		tester.runWordsOverAutomata(words3, automata);
	}

}
