package cz.upol.fapapp.fa.min;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

public class RightPartitioner implements AutomataPartitioner {

	private final Set<State> states;
	private final Alphabet alphabet;

	private final Degree delta;
	private final AutomatonStatesWrapper statesWrapper;
	private final AutomatonPartsWrapper partsWrapper;

	public RightPartitioner(FuzzyAutomaton automaton, Degree delta) {
		this.states = automaton.getStates();
		this.alphabet = automaton.getAlphabet();
		this.delta = delta;

		this.statesWrapper = new AutomatonStatesWrapper(automaton);
		this.partsWrapper = new AutomatonPartsWrapper(automaton);
	}

	@Override
	public SetsPartition<State> compute() {
		//System.out.println("-------");
		SetsPartition<State> oldPartition = null;
		SetsPartition<State> newPartition = createInitialPartition();

		do {
			oldPartition = newPartition.clone();
			newPartition = recomputePartition(newPartition);
			Logger.get().moreinfo("Computed partitions: " + newPartition);
		} while (!newPartition.equals(oldPartition));

		return newPartition;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private SetsPartition<State> recomputePartition(SetsPartition<State> partition) {

		for (Set<State> part : new HashSet<>(partition.getPartitions())) {
			Couple<Set<State>, Set<State>> partDifferents = checkPart(part, partition);

			Set<State> firstSub = partDifferents.getDomain();
			Set<State> secondSub = partDifferents.getTarget();
			
			if (!firstSub.isEmpty() && !secondSub.isEmpty()) {
				partition.split(firstSub, secondSub);
			}
		}

		return partition;

	}

	/////////////////////////////////////////////////////////////////////////////////////


	private Couple<Set<State>, Set<State>> checkPart(Set<State> part, SetsPartition<State> partition) {

		Set<State> keepInPart = new HashSet<>(part);
		Set<State> removeFromPart = new HashSet<>(part.size());
		
		//for (State first : part) {
		State first = part.iterator().next();
			for (State second : part) {
				if (first.equals(second)) {
					continue;
				}

				Degree match = matchOfPart(first, second, partition);

				boolean isInDelta = isMatchInDelta(match);
				if (!isInDelta) {
					//System.out.println("NoMach " + first + " x " + second + " in " + " -> \t" + match);
					
					SetsPartition.prepareSplit(keepInPart, removeFromPart, first, second);
				} else {
					//System.out.println("Machtc " + first + " x " + second + " in " + " -> \t" + match);
				}

				//System.out.println("State " + first + " x " + second + " in " + " -> \t" + match);
				
			}
		//}

		//System.out.println("+++" + keepInPart + " / "+ removeFromPart);

		return new Couple<>(keepInPart, removeFromPart);
	}

	@Deprecated
	private boolean fitsToItsPartition(State state, SetsPartition<State> partition) {
		Set<State> part = partition.findPartContaining(state);
		Degree matchOfPart = matchOfPart(state, null, partition);
		Degree diff = matchToDiff(matchOfPart);

		return isInDelta(diff);
	}

	private void removeFromCurrentPartition(State state, SetsPartition<State> partition) {
		Set<State> currentPart = partition.findPartContaining(state);
		Set<State> betterPart = computeBetterPart(state, currentPart, partition);

		if (betterPart == null) {
			separate(state, partition);
		} else {
			move(state, betterPart, partition);
		}
	}

	@Deprecated
	private Set<State> computeBetterPart(State state, Set<State> currentPart, SetsPartition<State> partition) {
		Set<State> betterPart = currentPart;
		Degree betterMatch = matchOfPart(state, null, partition);

		for (Set<State> part : partition.getPartitions()) {
			Degree match = matchOfPart(state, null, partition);
			System.err.println("BTR " + state + " -> " + part + "\t ==> " + match);
			Degree diff = match.negate();
			if (!isInDelta(diff)) {
				continue;
			}
			if (Degree.isSmallerThan(betterMatch, match, false)) {
				betterPart = part;
				betterMatch = match;
			}
		}

		System.err.println("BBB " + state + " ==>" + betterPart + " \t :: " + betterMatch + " ? "
				+ betterPart.equals(currentPart));
		if (betterPart.equals(currentPart)) {
			return null;
		} else {
			return betterPart;
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	private void move(State state, Set<State> betterPart, SetsPartition<State> partition) {
		partition.move(state, betterPart);
	}

	private void separate(State state, SetsPartition<State> partition) {
		partition.separate(state);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	protected Degree matchOfPart(State first, State second, SetsPartition<State> partition) {
		Degree result = Degree.ONE;

		for (Symbol over : alphabet) {
			for (State to : states) {

				Set<State> toPart = partition.findPartContaining(to);

				Degree degreeOfState = transitionToPart(first, over, toPart);
				Degree degreeOfAnother = transitionToPart(second, over, toPart);

				Degree equality = equality(degreeOfState, degreeOfAnother);
				result = tnorm(result, equality);

				// if (state.getLabel().equals("q_q_1_5") &&
				// anotherFrom.getLabel().equals("q_q_1_1")) {
				// System.out.println("- over " + over + " to " + to + " -> " +
				// degreeOfState + " / " + degreeOfAnother + " ==> " + equality
				// + ", " + result);
				// }
			}
		}

		return result;
	}

	private Degree transitionToPart(State state, Symbol over, Set<State> toPart) {
		Degree result = Degree.ZERO;
		for (State to : toPart) {
			Degree degree = statesWrapper.transition(state, over, to);
			result = conorm(result, degree);
		}
		return result;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

	protected SetsPartition<State> createInitialPartition() {
		SetsPartition<State> part = SetsPartition.createSinglies(states);

		part = computeSimpleClosureOf(part, //
				(s) -> partsWrapper.finalIn(s));

		return part;
	}

	@Deprecated
	private SetsPartition<State> computeSimpleClosureOf(SetsPartition<State> partition, //
			Function<Set<State>, Degree> degreeOfPartComputer) {

		while (true) {
			Couple<Set<State>, Set<State>> bestPair = findClosestFeasiblePair(partition, degreeOfPartComputer);

			if (bestPair == null) {
				break;
			}

			partition.merge(bestPair.getDomain(), bestPair.getTarget());
		}

		return partition;
	}

	@Deprecated
	private Couple<Set<State>, Set<State>> findClosestFeasiblePair(SetsPartition<State> partition, //
			Function<Set<State>, Degree> degreeOfPartComputer) {

		Set<State> bestFirst = null;
		Set<State> bestSecond = null;
		Degree bestDiff = null;

		for (Set<State> first : partition.getPartitions()) {
			for (Set<State> second : partition.getPartitions()) {
				if (first.equals(second)) {
					continue;
				}
				Degree diff = differenceBy(first, second, degreeOfPartComputer);
				boolean isDiffInDelta = isInDelta(diff);
				if (!isDiffInDelta) {
					continue;
				}

				if (bestDiff == null || Degree.isSmallerThan(diff, bestDiff, false)) {
					bestFirst = first;
					bestSecond = second;
					bestDiff = diff;
				}
			}
		}

		if (bestDiff == null) {
			return null;
		} else {
			return new Couple<>(bestFirst, bestSecond);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////

	private Degree differenceBy(Set<State> first, Set<State> second,
			Function<Set<State>, Degree> degreeOfPartComputer) {
		Degree firstFinalIn = degreeOfPartComputer.apply(first);
		Degree secondFinalIn = degreeOfPartComputer.apply(second);

		return difference(firstFinalIn, secondFinalIn);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private Degree matchToDiff(Degree degree) {
		return degree.negate();
	}

	private Degree equality(Degree first, Degree second) {
		return difference(first, second).negate();
	}

	private Degree difference(Degree first, Degree second) {
		double firstDegree = first.getValue();
		double secondDegree = second.getValue();
		double differenceDegree = Math.abs(firstDegree - secondDegree);
		return new Degree(differenceDegree);
	}

	private boolean isInDelta(Degree diff) {
		double diffValue = diff.getValue();
		double deltaValue = delta.getValue();

		return diffValue <= deltaValue;
	}
	
	private boolean isMatchInDelta(Degree match) {
		double matchValue = match.getValue();
		double deltaValue = delta.getValue();

		return matchValue >= deltaValue;
	}

	private static Degree tnorm(Degree... degrees) {
		return Arrays.stream(degrees).reduce(Degree.ONE, //
				(x, y) -> TNorms.getTnorm().tnorm(x, y));
	}

	private Degree conorm(Degree first, Degree second) {
		return TNorms.getTnorm().tconorm(first, second);
	}

}
