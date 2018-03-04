package cz.upol.fapap.pa.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapap.core.QuadriaryRelation;
import cz.upol.fapap.core.QuadriaryRelation.Quadrituple;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.probability.ProbabilisticEnvironment;
import cz.upol.fapapp.core.probability.Probability;

public class ProbablisticAutomaton extends BaseProbabilisticAutomaton {

	public ProbablisticAutomaton(
			Set<State> states,
			Alphabet alphabet,
			QuadriaryRelation<State, Symbol, State, Probability> transitionFunction,
			Set<State> initialStates, Set<State> finalStates) {

		super(states, alphabet, transitionFunction, initialStates, finalStates);
	}

	@Override
	public boolean accepts(Word word, ProbabilisticEnvironment env) {
		Set<State> currentStates = new HashSet<>(initialStates);

		for (Symbol symbol : word.getSymbols()) {
			currentStates = stepOver(currentStates, symbol, env);
		}

		return intersects(currentStates, finalStates);
	}

	private Set<State> stepOver(Set<State> fromStates, Symbol over,
			ProbabilisticEnvironment env) {
		Set<State> toStates = new HashSet<>(states.size());

		for (State from : fromStates) {
			Set<Quadrituple<State, Symbol, State, Probability>> transitions = transitionFunction
					.get(from, over);

			for (Quadrituple<State, Symbol, State, Probability> transition : transitions) {
				State to = transition.getThird();
				Probability prob = transition.getFourth();

				boolean occurs = env.isProbable(prob);
				if (occurs) {
					toStates.add(to);
				}
			}
		}

		return toStates;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public Probability probabilityOf(Word word) {
		Map<State, Probability> currentStates = CollectionsUtils.createMap(
				initialStates, Probability.PROB_OF_CERTAIN_EVENT);

		for (Symbol symbol : word.getSymbols()) {
			currentStates = stepOver(currentStates, symbol);
		}

		Map<State, Probability> endedInFinals = reduceProbabilityBy(
				currentStates, finalStates);
		return join(endedInFinals);
	}

	private Map<State, Probability> stepOver(
			Map<State, Probability> fromStates, Symbol over) {

		Map<State, Probability> toStates = new HashMap<>(states.size());

		for (State fromState : fromStates.keySet()) {
			Probability fromProb = fromStates.get(fromState);
			Set<Quadrituple<State, Symbol, State, Probability>> transitions = transitionFunction
					.get(fromState, over);

			for (Quadrituple<State, Symbol, State, Probability> transition : transitions) {
				State toState = transition.getThird();
				Probability overProb = transition.getFourth();

				Probability toProb = toStates.get(toState);
				if (toProb == null) {
					toProb = Probability.PROB_OF_IMPOSSIBLE_EVENT;
				}

				Probability transProb = Probability.intersect(fromProb,
						overProb);
				toProb = Probability.join(toProb, transProb);

				toStates.put(toState, toProb);
			}
		}

		return toStates;
	}

	private Map<State, Probability> reduceProbabilityBy(
			Map<State, Probability> states, Set<State> finalStates) {

		return states.keySet().stream() //
				.filter((s) -> finalStates.contains(s)) //
				.collect(Collectors.toMap( //
						(s) -> s, //
						(s) -> states.get(s))); //
	}

	private Probability join(Map<State, Probability> states) {
		return states.values().stream() //
				.reduce(Probability.PROB_OF_IMPOSSIBLE_EVENT, //
						(p1, p2) -> Probability.join(p1, p2));
	}

	// TODO move to collections utils !!!
	private <E> boolean intersects(Set<E> first, Set<E> second) {
		for (E elem : first) {
			if (second.contains(elem)) {
				return true;
			}
		}

		return false;
	}

}
