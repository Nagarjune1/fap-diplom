package cz.upol.automaton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyStructs.FuzzyRelation;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;

public class RandomAutomataCreator {

	public RandomAutomataCreator() {
	}

	/**
	 * Vytvoří náhodný automat ze zadaných parametrů.
	 * 
	 * @param logic
	 *            fuzzy logika
	 * @param alphabet
	 *            abeceda
	 * @param statesCount
	 *            počet stavů, může být 0
	 * @param transitionsCount
	 *            počet přechodů, nesmí být > 0 když statesCount == 0
	 * @param degreeLength
	 *            délka (počet des. míst) stupňů pravdivosti
	 * @return
	 */
	public NondetermisticFuzzyAutomata generateNFA(FuzzyLogic logic,
			Alphabet alphabet, int statesCount, int transitionsCount,
			Set<Symbol> symbols, Set<Degree> degrees) {
		Random rand = new Random();

		List<State> statesList = new ArrayList<>(statesCount);
		Set<State> states = generateStates(statesCount, statesList);

		Set<Transition> transitionsSet = new HashSet<>();
		List<Symbol> symbolsList = new ArrayList<>(symbols);
		List<Degree> degreesList = new ArrayList<>(degrees);

		FuzzyRelation<Transition> delta = generateTransitions(logic,
				transitionsCount, rand, statesList, transitionsSet,
				symbolsList, degreesList);

		FuzzySet<State> initialStates = generateInitOrFinalStates(logic, rand,
				states, degreesList);

		FuzzySet<State> finiteStates = generateInitOrFinalStates(logic, rand,
				states, degreesList);

		NondetermisticFuzzyAutomata automaton = new NondetermisticFuzzyAutomata(
				logic, states, alphabet, initialStates, finiteStates, delta);

		return automaton;
	}

	/**
	 * Vytvoří náhodný automat ze zadaných parametrů.
	 * 
	 * @param logic
	 *            fuzzy logika
	 * @param alphabet
	 *            abeceda
	 * @param statesCount
	 *            počet stavů, může být 0
	 * @param transitionsCount
	 *            počet přechodů, nesmí být > 0 když statesCount == 0
	 * @param degreeLength
	 *            délka (počet des. míst) stupňů pravdivosti
	 * @return
	 */
	public DeterministicFuzzyAutomata generateDFA(FuzzyLogic logic,
			Alphabet alphabet, int statesCount, int transitionsCount,
			Set<Symbol> symbols, Set<Degree> degrees) {
		Random rand = new Random();

		List<State> statesList = new ArrayList<>(statesCount);
		Set<State> states = generateStates(statesCount, statesList);

		Set<Transition> transitionsSet = new HashSet<>();
		List<Symbol> symbolsList = new ArrayList<>(symbols);
		List<Degree> degreesList = new ArrayList<>(degrees);

		FuzzyRelation<Transition> fuzzyDelta = generateTransitions(logic,
				transitionsCount, rand, statesList, transitionsSet,
				symbolsList, degreesList);
		Set<Transition> delta = fuzzyDelta.getUniverse();

		State initialState = statesCount == 0 ? null : states.iterator().next();

		FuzzySet<State> finiteStates = generateInitOrFinalStates(logic, rand,
				states, degreesList);

		DeterministicFuzzyAutomata automaton = new DeterministicFuzzyAutomata(
				logic, states, alphabet, initialState, finiteStates, delta);

		return automaton;

	}

	public FuzzySet<State> generateInitOrFinalStates(FuzzyLogic logic,
			Random rand, Set<State> states, List<Degree> degreesList) {

		FuzzySet<State> initialStates = new FuzzySet<>(logic, states);
		for (State state : states) {
			Degree degree = degreesList.get(rand.nextInt(degreesList.size()));
			initialStates.insert(state, degree);
		}

		return initialStates;
	}

	public FuzzyRelation<Transition> generateTransitions(FuzzyLogic logic,
			int transitionsCount, Random rand, List<State> statesList,
			Set<Transition> transitionsSet, List<Symbol> symbolsList,
			List<Degree> degreesList) {

		FuzzyRelation<Transition> delta = new FuzzyRelation<>(logic,
				transitionsSet);
		if (transitionsCount > 0) {
			for (int i = 0; i < transitionsCount; i++) {
				State from = statesList.get(rand.nextInt(statesList.size()));
				State to = statesList.get(rand.nextInt(statesList.size()));
				Symbol over = symbolsList.get(rand.nextInt(symbolsList.size()));
				Degree degree = degreesList
						.get(rand.nextInt(degreesList.size()));

				Transition transition = new Transition(from, over, to);
				transitionsSet.add(transition);
				delta.insert(transition, degree);
			}
		}

		return delta;
	}

	public Set<State> generateStates(int statesCount, List<State> statesList) {
		Set<State> states = new HashSet<>(statesCount);

		for (int i = 0; i < statesCount; i++) {
			String label = "q" + i;
			State state = new State(label);

			statesList.add(state);
			states.add(state);
		}
		return states;
	}

}
