package cz.upol.jfa.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import cz.upol.automaton.RandomAutomataCreator;
import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;
import cz.upol.automaton.fuzzyLogic.rationalLogics.RationalFuzzyLogic;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.automata.DetermFuzzyAutomatonToGUI;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;

public class RandomFATGCreator extends RandomAutomataCreator {

	public RandomFATGCreator() {
	}

	/**
	 * 
	 * @param logic
	 * @param alphabet
	 * @param statesCount
	 * @param transitionsCount
	 * @param degreeLength
	 * @param degreeCount
	 * @param symbolsToUse
	 * @return
	 */
	public BaseAutomatonToGUI createFATG(Boolean deterministic,
			RationalFuzzyLogic logic, Alphabet alphabet, int statesCount,
			int transitionsCount, int degreeLength, int degreeCount,
			String... symbolsToUse) {

		Set<Symbol> symbols = new HashSet<>();
		for (String symbolName : symbolsToUse) {
			Symbol symbol = new Symbol(symbolName);
			symbols.add(symbol);
		}

		Set<Degree> degrees = new HashSet<>();

		Random rand = new Random();
		double digits = Math.pow(10, degreeLength);

		for (int i = 0; i < degreeCount; i++) {
			double value = Math.floor(rand.nextDouble() * digits) / digits;
			Rational0to1Number num = new Rational0to1Number(value);
			degrees.add(num);
		}

		return generate(deterministic, logic, alphabet, statesCount,
				transitionsCount, symbols, degrees);
	}

	public BaseAutomatonToGUI generate(Boolean deterministic,
			RationalFuzzyLogic logic, Alphabet alphabet, int statesCount,
			int transitionsCount, Set<Symbol> symbols, Set<Degree> degrees) {

		return createFATG(deterministic, logic, alphabet, statesCount,
				transitionsCount, symbols, degrees);
	}

	/**
	 * 
	 * @param logic
	 * @param alphabet
	 * @param statesCount
	 * @param transitionsCount
	 * @param degreesList
	 * @param symbolsList
	 * @param comaRegex
	 * @return
	 */
	public BaseAutomatonToGUI createFATG(Boolean deterministic,
			FuzzyLogic logic, Alphabet alphabet, int statesCount,
			int transitionsCount, String symbolsList, String degreesList,
			String comaRegex) {

		Set<Symbol> symbols = tryToParseSymbols(alphabet, symbolsList,
				comaRegex);

		Set<Degree> degrees = tryToParseDegrees(logic, degreesList, comaRegex);

		return createFATG(deterministic, logic, alphabet, statesCount,
				transitionsCount, symbols, degrees);
	}

	/**
	 * 
	 * @param logic
	 * @param alphabet
	 * @param statesCount
	 * @param transitionsCount
	 * @param symbols
	 * @param degrees
	 * @return
	 */
	public BaseAutomatonToGUI createFATG(Boolean deterministic,
			FuzzyLogic logic, Alphabet alphabet, int statesCount,
			int transitionsCount, Set<Symbol> symbols, Set<Degree> degrees) {

		if (deterministic != null && deterministic) {
			DeterministicFuzzyAutomata dfa = super.generateDFA(logic, alphabet,
					statesCount, transitionsCount, symbols, degrees);
			return new DetermFuzzyAutomatonToGUI(dfa);

		} else {
			NondetermisticFuzzyAutomata nfa = super.generateNFA(logic,
					alphabet, statesCount, transitionsCount, symbols, degrees);
			return new NondetFuzzyAutomatonToGUI(nfa);
		}

	}

	/**
	 * 
	 * @param logic
	 * @param degreesList
	 * @param comaRegex
	 * @return
	 */
	public Set<Degree> tryToParseDegrees(FuzzyLogic logic, String degreesList,
			String comaRegex) {
		Set<Degree> degrees = new HashSet<>();
		if (!degreesList.isEmpty()) {
			String[] degreesArray = degreesList.split(comaRegex);
			for (String degreeExt : degreesArray) {
				Degree degree = logic.getUniverseElementsExternisator()
						.parseKnown(degreeExt);

				if (degree != null) {
					degrees.add(degree);
				} else {
					throw new IllegalArgumentException(degreeExt
							+ " is not a degree of " + logic.getDescription());
				}
			}
		}
		return degrees;
	}

	/**
	 * 
	 * @param alphabet
	 * @param symbolsList
	 * @param comaRegex
	 * @return
	 */
	public Set<Symbol> tryToParseSymbols(Alphabet alphabet, String symbolsList,
			String comaRegex) {

		Set<Symbol> symbols = new HashSet<>();
		if (!symbolsList.isEmpty()) {
			String[] symbolsArray = symbolsList.split(comaRegex);
			for (String symbolExt : symbolsArray) {
				Symbol symbol = alphabet.getSymbolsExternisator().parseKnown(
						symbolExt);

				if (symbol != null) {
					symbols.add(symbol);
				} else {
					throw new IllegalArgumentException(symbolExt
							+ " is not a symbol of "
							+ alphabet.getDescription());
				}
			}
		}
		return symbols;
	}

}
