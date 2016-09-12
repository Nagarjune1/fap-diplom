package cz.upol.automaton;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;
import cz.upol.automaton.fuzzyStructs.FuzzyRelation;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.io.PlainTextExporter;
import cz.upol.automaton.ling.alphabets.CharactersAlphabet;

public class _Tests {
	public static void main(String[] args) {
		playingWithFuzzySets();
		System.out.println("--------------------------------");
		playingWithFuzzyRelations();
		System.out.println("--------------------------------");
		playingWithalphabets();
		System.out.println("--------------------------------");
		playingWithAutomatonPrinting();
		System.out.println("--------------------------------");

		System.out.println("That's all folks!");
	}

	private static void playingWithalphabets() {
		CharactersAlphabet alphabet = new CharactersAlphabet(true, false, true,
				'@', '.', '-', '_');
		System.out.println("E-mail address: " + alphabet);

	}

	private static void playingWithAutomatonPrinting() {
		PlainTextExporter exporter = new PlainTextExporter();
		
		DeterministicFuzzyAutomata a1 = TestingAutomata.createOneStateAutomaton();
		System.out.println(exporter.export(a1));
		
		NondetermisticFuzzyAutomata a2 = TestingAutomata.createTriangleAutomaton();
		System.out.println(exporter.export(a2));

	}

	public static void playingWithFuzzySets() {
		FuzzyLogic logic = new LukasiewiczLogic();
		Set<String> universe = new HashSet<String>(Arrays.asList("Dog", "Cat",
				"Snake", "Stick insect", "Bear", "Tiger", "Weasel"));

		FuzzySet<String> petsByMartin = new FuzzySet<>(
				logic, universe);

		petsByMartin.insert("Dog", logic.getOne());
		petsByMartin.insert("Cat", logic.getOne());
		petsByMartin.insert("Snake", new Rational0to1Number(0.5));
		petsByMartin.insert("Snake", new Rational0to1Number(0.7));
		petsByMartin.insert("Stick insect", new Rational0to1Number(0.1));
		petsByMartin.insert("Bear", logic.getZero());
		petsByMartin.insert("Weasel", new Rational0to1Number(0.2));

		System.out.println("Pets by Martin: " + petsByMartin);

		FuzzySet<String> petsByPetr = new FuzzySet<>(
				logic, universe);

		petsByPetr.insert("Dog", logic.getOne());
		petsByPetr.insert("Cat", new Rational0to1Number(0.9));
		petsByPetr.insert("Snake", new Rational0to1Number(0.8));
		petsByPetr.insert("Stick insect", new Rational0to1Number(0.6));
		petsByPetr.insert("Tiger", new Rational0to1Number(0.1));
		petsByPetr.insert("Weasel", new Rational0to1Number(0.3));

		System.out.println("Pets by Petr:   " + petsByPetr);

		System.out.println("Pets by M && P: "
				+ petsByMartin.intersect(petsByPetr));
		System.out.println("Pets by M || P: " + petsByMartin.join(petsByPetr));

	}

	public static void playingWithFuzzyRelations() {
		FuzzyLogic logic = new LukasiewiczLogic();
		FuzzyRelation<Relationship> gossips = new FuzzyRelation<Relationship>(
				logic, Relationship.class);

		gossips.insert(new Relationship("John", "Johana"),
				new Rational0to1Number(0.6));
		gossips.insert(new Relationship("Romeo", "Juliet"),
				new Rational0to1Number(0.9));
		gossips.insert(new Relationship("Lorem", "Ipsum"));
		gossips.insert(new Relationship("Homer", "Marge"),
				new Rational0to1Number(0.9));

		System.out.println("Gossips credibility: " + gossips);
	}

}
