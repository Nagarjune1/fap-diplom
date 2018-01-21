package cz.upol.fapapp.fa.automata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.core.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.core.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;

public class AutomataDeformer {

	///////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param automata
	 * @param degreeOfOthers
	 * @return
	 */
	public static FuzzyAutomata deformReplace(FuzzyAutomata automata, Degree degreeOfOthers) {
		FuzzyBinaryRelation<Symbol, Symbol> similarity = degreeToFuzzyBinaryRelation(automata.getAlphabet(),
				degreeOfOthers);
		return deformReplace(automata, similarity);
	}

	/**
	 * 
	 * @param automata
	 * @param similarity
	 * @return
	 */
	public static FuzzyAutomata deformReplace(FuzzyAutomata automata, FuzzyBinaryRelation<Symbol, Symbol> similarity) {
		Logger.get().moreinfo("Deforming automata by replace: " + similarity);
		
		Alphabet alphabet = automata.getAlphabet();
		Set<State> states = automata.getStates();

		Map<Triple<State, Symbol, State>, Degree> transitions = automata.getTransitionFunction().getTuplesWithDegree();
		for (Triple<State, Symbol, State> triple : automata.getTransitionFunction().domain()) {
			State from = triple.getFirst();
			Symbol over = triple.getSecond();
			State to = triple.getThird();

			for (Symbol altOver : alphabet) {
				Degree similar = similarity.get(over, altOver);

				if (over.equals(altOver)) {
					if (!Degree.ONE.equals(similar)) {
						Logger.get().warning(over + " should be equal to " + altOver + " in degree 1, okay");
						continue;
					}
				}

				Triple<State, Symbol, State> newTriple = new Triple<>(from, altOver, to);
				putWithHihgerDegree(transitions, newTriple, similar);
			}
		}

		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = new FuzzyTernaryRelation<>(transitions);

		FuzzySet<State> initialStates = automata.getInitialStates();
		FuzzySet<State> finalStates = automata.getFinalStates();

		return new FuzzyAutomata(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param automata
	 * @param degreeOf
	 * @return
	 */
	public static FuzzyAutomata deformInsertions(FuzzyAutomata automata, Degree degreeOf) {
		FuzzySet<Symbol> insertions = degreeToFuzzySet(automata.getAlphabet(), degreeOf);
		return deformInsertions(automata, insertions);
	}

	/**
	 * 
	 * @param automata
	 * @param insertions
	 * @return
	 */
	public static FuzzyAutomata deformInsertions(FuzzyAutomata automata, FuzzySet<Symbol> insertions) {
		Logger.get().moreinfo("Deforming automata by insertions: " + insertions);
		
		Alphabet alphabet = automata.getAlphabet();
		Set<State> states = automata.getStates();

		Map<Triple<State, Symbol, State>, Degree> transitions = automata.getTransitionFunction().getTuplesWithDegree();
		for (State state : states) {
			for (Symbol over : insertions.domain()) {
				Degree degree = insertions.degreeOf(over);

				Triple<State, Symbol, State> newTriple = new Triple<>(state, over, state);
				putWithHihgerDegree(transitions, newTriple, degree);
			}
		}

		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = new FuzzyTernaryRelation<>(transitions);

		FuzzySet<State> initialStates = automata.getInitialStates();
		FuzzySet<State> finalStates = automata.getFinalStates();

		return new FuzzyAutomata(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param automata
	 * @param degreeOf
	 * @return
	 */
	public static FuzzyAutomata deformDeletions(FuzzyAutomata automata, Degree degreeOf) {
		FuzzySet<Symbol> deletions = degreeToFuzzySet(automata.getAlphabet(), degreeOf);
		return deformDeletions(automata, deletions);
	}

	/**
	 * 
	 * @param automata
	 * @param deletions
	 * @return
	 */
	public static FuzzyAutomata deformDeletions(FuzzyAutomata automata, FuzzySet<Symbol> deletions) {
		Logger.get().moreinfo("Deforming automata by deletions: " + deletions);
		
		Alphabet alphabet = automata.getAlphabet();
		Set<State> states = automata.getStates(); 
		// TODO will need to add new final state, won't?
		// TODO and transitive jumps

		final FuzzyTernaryRelation<State, Symbol, State> originalTransitions = automata.getTransitionFunction();
		Map<Triple<State, Symbol, State>, Degree> triples = originalTransitions.getTuplesWithDegree();

		for (Triple<State, Symbol, State> transition : originalTransitions.getTuplesWithDegree().keySet()) {
			State from = transition.getFirst();
			Symbol over = transition.getSecond();
			State to = transition.getThird();

			Map<Triple<State, Symbol, State>, Degree> transitionsAfter = originalTransitions.get(to);
			for (Triple<State, Symbol, State> transitionAfter : transitionsAfter.keySet()) {
				Symbol afterOver = transitionAfter.getSecond();
				State afterTo = transitionAfter.getThird();
				Degree newDegree = deletions.degreeOf(afterOver);

				Triple<State, Symbol, State> newTriple = new Triple<State, Symbol, State>(from, over, afterTo);
				putWithHihgerDegree(triples, newTriple, newDegree);
			}
		}

		FuzzyTernaryRelation<State, Symbol, State> transitionFunction = new FuzzyTernaryRelation<>(triples);

		FuzzySet<State> initialStates = automata.getInitialStates();
		FuzzySet<State> finalStates = automata.getFinalStates();

		return new FuzzyAutomata(alphabet, states, transitionFunction, initialStates, finalStates);
	}

	///////////////////////////////////////////////////////////////////////////

	private static <TT> void putWithHihgerDegree(Map<TT, Degree> tuples, TT tuple, Degree newDegree) {

		Degree oldDegree = tuples.get(tuple);

		Degree degree;
		if (oldDegree != null) {
			degree = Degree.supremum(oldDegree, newDegree);
		} else {
			degree = newDegree;
		}

		tuples.put(tuple, degree);
	}

	private static FuzzySet<Symbol> degreeToFuzzySet(Alphabet alphabet, Degree degreeOf) {
		Map<Symbol, Degree> tuples = new HashMap<>(alphabet.size());

		for (Symbol symbol : alphabet) {
			tuples.put(symbol, degreeOf);
		}

		return new FuzzySet<>(tuples);
	}

	private static FuzzyBinaryRelation<Symbol, Symbol> degreeToFuzzyBinaryRelation(Alphabet alphabet,
			Degree degreeOfOthers) {

		Map<Couple<Symbol, Symbol>, Degree> tuples = //
				new HashMap<>(alphabet.size() * alphabet.size());

		for (Symbol source : alphabet) {
			for (Symbol target : alphabet) {
				Degree degree;
				if (source.equals(target)) {
					degree = Degree.ONE;
				} else {
					degree = degreeOfOthers;
				}

				Couple<Symbol, Symbol> couple = new Couple<Symbol, Symbol>(source, target);
				tuples.put(couple, degree);
			}
		}

		return new FuzzyBinaryRelation<>(tuples);
	}

}
