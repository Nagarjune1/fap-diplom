package cz.upol.fapap.pa.automata;

import java.io.PrintStream;
import java.util.Set;

import cz.upol.fapap.core.QuadriaryRelation;
import cz.upol.fapapp.core.automata.BaseAutomaton;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Printable;
import cz.upol.fapapp.core.probability.ProbabilisticEnvironment;
import cz.upol.fapapp.core.probability.Probability;

public abstract class BaseProbabilisticAutomaton implements BaseAutomaton {

	protected final Set<State> states;
	protected final Alphabet alphabet;
	protected final QuadriaryRelation<State, Symbol, State, Probability> transitionFunction;
	protected final Set<State> initialStates;
	protected final Set<State> finalStates;

	public BaseProbabilisticAutomaton(Set<State> states, Alphabet alphabet,
			QuadriaryRelation<State, Symbol, State, Probability> transitionFunction, Set<State> initialStates,
			Set<State> finalStates) {
		super();
		this.states = states;
		this.alphabet = alphabet;
		this.transitionFunction = transitionFunction;
		this.initialStates = initialStates;
		this.finalStates = finalStates;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Set<State> getStates() {
		return states;
	}

	public Alphabet getAlphabet() {
		return alphabet;
	}

	public QuadriaryRelation<State, Symbol, State, Probability> getTransitionFunction() {
		return transitionFunction;
	}

	public Set<State> getInitialStates() {
		return initialStates;
	}

	public Set<State> getFinalStates() {
		return finalStates;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public abstract boolean accepts(Word word, ProbabilisticEnvironment env);

	public abstract Probability probabilityOf(Word word);

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alphabet == null) ? 0 : alphabet.hashCode());
		result = prime * result + ((finalStates == null) ? 0 : finalStates.hashCode());
		result = prime * result + ((initialStates == null) ? 0 : initialStates.hashCode());
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		result = prime * result + ((transitionFunction == null) ? 0 : transitionFunction.hashCode());
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
		BaseProbabilisticAutomaton other = (BaseProbabilisticAutomaton) obj;
		if (alphabet == null) {
			if (other.alphabet != null)
				return false;
		} else if (!alphabet.equals(other.alphabet))
			return false;
		if (finalStates == null) {
			if (other.finalStates != null)
				return false;
		} else if (!finalStates.equals(other.finalStates))
			return false;
		if (initialStates == null) {
			if (other.initialStates != null)
				return false;
		} else if (!initialStates.equals(other.initialStates))
			return false;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		if (transitionFunction == null) {
			if (other.transitionFunction != null)
				return false;
		} else if (!transitionFunction.equals(other.transitionFunction))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseProbabilisticAutomaton [states=" + states + ", alphabet=" + alphabet + ", transitionFunction="
				+ transitionFunction + ", initialStates=" + initialStates + ", finalStates=" + finalStates + "]";
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void print(PrintStream to) {
		Printable.print(to, new PATIMFComposer(), (ProbablisticAutomaton) this);
	}

}
