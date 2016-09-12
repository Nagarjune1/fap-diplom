package cz.upol.automaton.automata.definition;

import java.util.Set;
import java.util.TreeSet;

import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;

public class DetermFuzzyAutomataDefinition {
	private final ResiduedLattice residuedLattice;

	private final Set<State> states;

	private final Alphabet alphabet;

	private State initialState;

	private final FuzzySet<State> finiteStates;

	private final Set<Transition> delta;

	public DetermFuzzyAutomataDefinition(ResiduedLattice residuedLattice,
			Alphabet alphabet) {
		super();

		this.residuedLattice = residuedLattice;

		this.alphabet = alphabet;
		this.states = new TreeSet<>();

		this.initialState = null;
		this.finiteStates = new FuzzySet<State>(residuedLattice, states);

		this.delta = new TreeSet<>();
	}

	public DetermFuzzyAutomataDefinition(ResiduedLattice residuedLattice,
			Set<State> states, Alphabet alphabet, State initialState,
			FuzzySet<State> finiteStates, Set<Transition> delta) {
		super();
		this.residuedLattice = residuedLattice;
		this.states = states;
		this.alphabet = alphabet;
		this.initialState = initialState;
		this.finiteStates = finiteStates;
		this.delta = delta;
	}

	public DetermFuzzyAutomataDefinition(DetermFuzzyAutomataDefinition other) {
		super();
		this.residuedLattice = other.getResiduedLattice();
		this.states = new TreeSet<>(other.getStates());
		this.alphabet = other.getAlphabet();
		this.initialState = other.getInitialState();
		this.finiteStates = new FuzzySet<State>(other.getFiniteStates());
		this.delta = new TreeSet<Transition>(other.getDelta());
	}

	public ResiduedLattice getResiduedLattice() {
		return residuedLattice;
	}

	public Set<State> getStates() {
		return states;
	}

	public Alphabet getAlphabet() {
		return alphabet;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public FuzzySet<State> getFiniteStates() {
		return finiteStates;
	}

	public Set<Transition> getDelta() {
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
				+ ((initialState == null) ? 0 : initialState.hashCode());
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
		DetermFuzzyAutomataDefinition other = (DetermFuzzyAutomataDefinition) obj;
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
		if (initialState == null) {
			if (other.initialState != null)
				return false;
		} else if (!initialState.equals(other.initialState))
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
		return "DetermFuzzyAutomataDefinition [residuedLattice="
				+ residuedLattice + ", states=" + states + ", alphabet="
				+ alphabet + ", initialState=" + initialState
				+ ", finiteStates=" + finiteStates + ", delta=" + delta + "]";
	}

}
