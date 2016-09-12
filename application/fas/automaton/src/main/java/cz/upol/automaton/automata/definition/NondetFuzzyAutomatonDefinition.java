package cz.upol.automaton.automata.definition;

import java.util.Set;
import java.util.TreeSet;

import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.fuzzyStructs.FuzzyRelation;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;

public class NondetFuzzyAutomatonDefinition {

	private final ResiduedLattice residuedLattice;

	private final Set<State> states;

	private final Alphabet alphabet;

	private final FuzzySet<State> initialStates;

	private final FuzzySet<State> finiteStates;

	private final FuzzyRelation<Transition> delta;

	public NondetFuzzyAutomatonDefinition(ResiduedLattice residuedLattice,
			Alphabet alphabet) {
		super();

		this.residuedLattice = residuedLattice;
		this.alphabet = alphabet;
		this.states = new TreeSet<>();

		this.initialStates = new FuzzySet<State>(residuedLattice, states);
		this.finiteStates = new FuzzySet<State>(residuedLattice, states);

		this.delta = new FuzzyRelation<Transition>(residuedLattice,
				Transition.getTransitionClass());
	}

	public NondetFuzzyAutomatonDefinition(ResiduedLattice residuedLattice,
			Set<State> states, Alphabet alphabet, FuzzySet<State> intialStates,
			FuzzySet<State> finiteStates, FuzzyRelation<Transition> delta) {

		super();
		this.residuedLattice = residuedLattice;
		this.states = states;
		this.alphabet = alphabet;
		this.initialStates = intialStates;
		this.finiteStates = finiteStates;
		this.delta = delta;
	}

	public NondetFuzzyAutomatonDefinition(NondetFuzzyAutomatonDefinition other) {
		super();
		this.residuedLattice = other.getResiduedLattice();
		this.states = new TreeSet<>(other.getStates());
		this.alphabet = other.getAlphabet();
		this.initialStates = new FuzzySet<State>(other.getInitialStates());
		this.finiteStates = new FuzzySet<State>(other.getFiniteStates());
		this.delta = new FuzzyRelation<Transition>(other.getDelta());
	}

	/**
	 * @return
	 * @uml.property name="logic"
	 */
	public ResiduedLattice getResiduedLattice() {
		return residuedLattice;
	}

	/**
	 * @return
	 * @uml.property name="alphabet"
	 */
	public Alphabet getAlphabet() {
		return alphabet;
	}

	public Set<State> getStates() {
		return states;
	}

	public FuzzySet<State> getInitialStates() {
		return initialStates;
	}

	public FuzzySet<State> getFiniteStates() {
		return finiteStates;
	}

	public FuzzyRelation<Transition> getDelta() {
		return delta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alphabet == null) ? 0 : alphabet.hashCode());
		result = prime * result + ((delta == null) ? 0 : delta.hashCode());
		result = prime * result
				+ ((finiteStates == null) ? 0 : finiteStates.hashCode());
		result = prime * result
				+ ((initialStates == null) ? 0 : initialStates.hashCode());
		result = prime * result
				+ ((residuedLattice == null) ? 0 : residuedLattice.hashCode());
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NondetFuzzyAutomatonDefinition other = (NondetFuzzyAutomatonDefinition) obj;
		if (alphabet == null) {
			if (other.alphabet != null)
				return false;
		} else if (!alphabet.equals(other.alphabet))
			return false;
		if (delta == null) {
			if (other.delta != null)
				return false;
		} else if (!delta.equals(other.delta))
			return false;
		if (finiteStates == null) {
			if (other.finiteStates != null)
				return false;
		} else if (!finiteStates.equals(other.finiteStates))
			return false;
		if (initialStates == null) {
			if (other.initialStates != null)
				return false;
		} else if (!initialStates.equals(other.initialStates))
			return false;
		if (residuedLattice == null) {
			if (other.residuedLattice != null)
				return false;
		} else if (!residuedLattice.equals(other.residuedLattice))
			return false;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FuzzyAutomatonDefinition [\n" + " residuedLattice="
				+ residuedLattice + ",\n"//
				+ " states=" + states + ",\n" //
				+ " alphabet=" + alphabet + ",\n"//
				+ " initialStates=" + initialStates + ",\n"//
				+ " finiteStates=" + finiteStates + ",\n"//
				+ " delta=" + delta + "]";
	}

}
