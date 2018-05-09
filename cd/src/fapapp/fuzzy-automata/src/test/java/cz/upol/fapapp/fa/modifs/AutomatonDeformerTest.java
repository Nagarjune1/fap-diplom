package cz.upol.fapapp.fa.modifs;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.tnorm.GodelTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

public class AutomatonDeformerTest {
	private static final Integer PRECISION = 5;

	private static final FuzzyBinaryRelation<Symbol, Symbol> REPLACES = createReplaces();
	private static final FuzzySet<Symbol> ADDS = createAdds();

	private AutomatonDeformer deformer;

	@BeforeClass
	public static void setUp() {
		TNorms.setTnorm(new GodelTNorm());
	}

	@Before
	public void init() {
		FuzzyAutomaton automaton = AutomataCreator.createAutomaton1();
		deformer = new AutomatonDeformer(automaton);
	}

	private static FuzzyBinaryRelation<Symbol, Symbol> createReplaces() {
		Map<Couple<Symbol, Symbol>, Degree> map = new HashMap<>();

		map.put(new Couple<>(new Symbol("a"), new Symbol("a")), Degree.ONE);
		map.put(new Couple<>(new Symbol("b"), new Symbol("b")), Degree.ONE);

		map.put(new Couple<>(new Symbol("a"), new Symbol("b")), new Degree(0.3));
		map.put(new Couple<>(new Symbol("b"), new Symbol("a")), new Degree(0.9));

		return new FuzzyBinaryRelation<>(map);
	}

	private static FuzzySet<Symbol> createAdds() {
		Map<Symbol, Degree> map = new HashMap<>();

		map.put(new Symbol("a"), new Degree(0.3));
		map.put(new Symbol("b"), new Degree(0.6));

		return new FuzzySet<>(map);
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testNoDeform() {
		checkWord(0.4);
		checkWord(0.5, "a");
		checkWord(0.1, "b");
		checkWord(0.2, "a", "a");
		checkWord(0.4, "a", "b");
	}

	@Test
	public void testAddReplace() {
		deformer.addReplace(REPLACES);

		checkWord(0.4);
		checkWord(0.5, "a");
		checkWord(0.3, "b");
		checkWord(0.3, "b", "b");
		checkWord(0.3, "b", "a");
	}

	@Test
	public void testAddInsertMore() {
		deformer.addInsertMore(ADDS);

		checkWord(0.4);
		checkWord(0.5, "a");
		checkWord(0.4, "b");
		checkWord(0.4, "b", "b");
		checkWord(0.4, "b", "b", "b");
		checkWord(0.3, "a", "a", "a");
		checkWord(0.3, "a", "a", "a", "b");
		checkWord(0.5, "b", "a");
	}

	@Test
	public void testAddRemoveOne() {
		deformer.addRemoveOne(ADDS);

		checkWord(0.4);
		checkWord(0.1, "b");
		checkWord(0.5, "a", "a");
		checkWord(0.5, "a", "a", "b", "a");
		checkWord(0.3, "a", "b", "b");
	}

	@Test
	public void testAddInsertOne() {
		deformer.addInsertOne(ADDS);

		checkWord(0.4);
		checkWord(0.5, "a");
		checkWord(0.1, "b");
		checkWord(0.3, "a", "a");
		checkWord(0.4, "a", "b", "b");
	}

	///////////////////////////////////////////////////////////////////////////

	private void checkWord(double degreeVal, String... symbolsLbls) {
		Degree expectedDegree = new Degree(degreeVal);
		Word word = CollectionsUtils.toWord(symbolsLbls);
		FuzzyAutomaton automaton = deformer.getAutomaton(PRECISION);

		Degree actualDegree = automaton.degreeOfWord(word);

		assertEquals("For \"" + word.toSimpleHumanString() + "\"", //
				expectedDegree, actualDegree);

	}

}
