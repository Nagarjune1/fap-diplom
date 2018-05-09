package cz.upol.fapapp.fa.min;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;

/**
 * Simple partitioner. It's so simple it does not work properly. And also
 * outdated (does not use SetsPartition as its primary data structure).
 * 
 * @author martin
 * @deprecated use {@link RightPartitioner} instead.
 */
@Deprecated
public class SimplePartitioner implements AutomataPartitioner {

	private final FuzzyAutomaton automaton;
	private final Degree delta;

	public SimplePartitioner(FuzzyAutomaton automaton, Degree delta) {
		this.automaton = automaton;
		this.delta = delta;
	}

	@Override
	public SetsPartition<State> compute() {
		Set<Set<State>> parts = computePartitionsClosure();
		return new SetsPartition<>(parts);
	}

	private Set<Set<State>> computePartitionsClosure() {
		Set<Set<State>> oldPartition = null;
		Set<Set<State>> newPartition = createInitialPartition();

		do {
			oldPartition = newPartition;
			newPartition = computePartition(oldPartition);
			Logger.get().debug("Computed partitions: " + newPartition);
		} while (!newPartition.equals(oldPartition));

		return newPartition;
	}

	private Set<Set<State>> createInitialPartition() {
		Set<State> part = new HashSet<>(automaton.getStates());

		Set<Set<State>> partitions = CollectionsUtils.toSet(part);
		return partitions;
	}

	///////////////////////////////////////////////////////////////////////////

	private Set<Set<State>> computePartition(Set<Set<State>> currentParts) {

		Map<Object, Set<State>> origToNews = toOldToNewsMaps(currentParts);

		for (State state : automaton.getStates()) {
			Set<State> oldPartOrig = findPartContaining(state, currentParts);
			Set<State> newPartOrig = findBestPartition(state, currentParts);

			Set<State> oldPart = origToNews.get(oldPartOrig);
			Set<State> newPart;

			if (newPartOrig != null) {
				newPart = origToNews.get(newPartOrig);
			} else {
				newPart = null;
			}

			if (newPart == null) {
				newPart = new HashSet<>();
				oldPart.remove(state);
				newPart.add(state);

				origToNews.put(new Object(), newPart);
			} else {
				if (newPart.equals(oldPart)) {
					// okay
				} else {
					oldPart.remove(state);
					newPart.add(state);
				}
			}
		}

		return origToNews.values().stream() //
				.filter((p) -> !p.isEmpty()) //
				.collect(Collectors.toSet()); //
	}

	private Map<Object, Set<State>> toOldToNewsMaps(Set<Set<State>> currentParts) {
		return currentParts.stream() //
				.collect(Collectors.toMap( //
						(p) -> p, //
						(p) -> new HashSet<>(p)));
	}

	private Set<State> findBestPartition(State state, Set<Set<State>> partitions) {
		Degree bestDifference = Degree.ONE;
		Set<State> bestPartition = null;

		for (Set<State> partition : partitions) {
			Degree difference = differenceOf(partitions, state, partition);

			if (difference.isLessOrEqual(delta)) {
				if (Degree.isSmallerThan(difference, bestDifference, false)) {
					bestDifference = difference;
					bestPartition = partition;
				}
			}
		}

		return bestPartition;
	}

	private Degree differenceOf(Set<Set<State>> parts, State state, Set<State> partition) {
		Degree equality = equalityOf(parts, state, partition);
		return equality.negate();
	}

	protected Degree equalityOf(Set<Set<State>> parts, State state, Set<State> partition) {
		AutomatonStatesWrapper statesWrapper = new AutomatonStatesWrapper(automaton);
		AutomatonPartsWrapper partsWrapper = new AutomatonPartsWrapper(automaton);

		return equalityOf(parts, state, partition, statesWrapper, partsWrapper);
	}

	private Degree equalityOf(Set<Set<State>> parts, //
			State state, Set<State> partition, //
			AutomatonStatesWrapper statesWrapper, AutomatonPartsWrapper partsWrapper) {

		Degree initials = equalityByInitials(state, partition, statesWrapper, partsWrapper);
		Degree finals = equalityByFinals(state, partition, statesWrapper, partsWrapper);
		Degree tos = equalityByTos(parts, state, partition, statesWrapper, partsWrapper);

		Degree result = tnorm(initials, finals, tos);
		return result;
	}

	private Degree equalityByInitials(State state, Set<State> partition, //
			AutomatonStatesWrapper statesWrapper, AutomatonPartsWrapper partsWrapper) {

		return computeEquality(statesWrapper.initalIn(state), partsWrapper.initalIn(partition));
	}

	private Degree equalityByFinals(State state, Set<State> partition, //
			AutomatonStatesWrapper statesWrapper, AutomatonPartsWrapper partsWrapper) {

		return computeEquality(statesWrapper.finalIn(state), partsWrapper.finalIn(partition));
	}

	private Degree equalityByTos(Set<Set<State>> parts, State fromState, Set<State> fromPart, //
			AutomatonStatesWrapper statesWrapper, AutomatonPartsWrapper partsWrapper) {

		Degree result = Degree.ONE;
		for (Symbol over : automaton.getAlphabet()) {
			for (State toState : automaton.getStates()) {
				Set<State> toPart = findPartContaining(toState, parts);

				Degree degree = computeEquality( //
						statesWrapper.transition(fromState, over, toState), //
						partsWrapper.transition(fromPart, over, toPart));

				result = tnorm(result, degree);
			}
		}

		return result;

	}

	private Degree computeEquality(Degree degreeOfState, Degree degreeOfPart) {
		return Degree.equality(degreeOfState, degreeOfPart);
	}

	///////////////////////////////////////////////////////////////////////////

	private static Set<State> findPartContaining(State state, Set<Set<State>> parts) {
		return parts.stream() //
				.filter((p) -> p.contains(state)) //
				.findAny().get();
	}

	private static Degree tnorm(Degree... degrees) {
		return Arrays.stream(degrees).reduce(Degree.ONE, //
				(x, y) -> TNorms.getTnorm().tnorm(x, y));
	}

}
