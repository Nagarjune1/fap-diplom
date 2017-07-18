package cz.upol.fapapp.fta.automata;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.automata.BaseAutomata;
import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.CollectionsUtils;
import cz.upol.fapapp.fta.tree.BaseTree;

public abstract class BaseFuzzyTreeAutomata implements BaseAutomata {

	protected final Set<State> states;
	protected final Alphabet nonterminals;
	protected final Alphabet terminals;
	protected final Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction;
	protected final Set<State> finalStates;

	public BaseFuzzyTreeAutomata(Set<State> states, Alphabet nonterminals, Alphabet terminals,
			Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction, Set<State> finalStates) {
		super();

		validate(states, nonterminals, terminals, transitionFunction, finalStates);

		this.states = states;
		this.nonterminals = nonterminals;
		this.terminals = terminals;
		this.transitionFunction = transitionFunction;
		this.finalStates = finalStates;
	}

	public Set<State> getStates() {
		return states;
	}

	public Alphabet getNonterminals() {
		return nonterminals;
	}

	public Alphabet getTerminals() {
		return terminals;
	}

	public Map<Symbol, BinaryRelation<Word, FuzzyState>> getTransitionFunction() {
		return transitionFunction;
	}

	public Set<State> getFinalStates() {
		return finalStates;
	}

	///////////////////////////////////////////////////////////////////////////

	protected void validate(Set<State> states, Alphabet nonterminals, Alphabet terminals,
			Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction, Set<State> finalStates) {

		CollectionsUtils.checkDisjoint(nonterminals, terminals);
		checkTransitionFunction(states, nonterminals, terminals, transitionFunction);
		CollectionsUtils.checkSubset(finalStates, states);

	}

	private static void checkTransitionFunction(Set<State> states, Alphabet nonterminals, Alphabet terminals,
			Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction) {

		Alphabet statesAlphabet = alphabetOfStateSymbols(states);

		transitionFunction.forEach((s, t) -> {
			CollectionsUtils.checkInSetsJoin(s, nonterminals, terminals);
			
			t.getTuples().forEach((c) -> {
				Word word = c.getDomain();
				CollectionsUtils.checkWord(word, statesAlphabet);

				if (nonterminals.contains(s)) {
					CollectionsUtils.checkIsNotEmptyWord(word);
				} else {
					CollectionsUtils.checkIsEmptyWord(word);
				}

				FuzzyState fuzzyState = c.getTarget();
				CollectionsUtils.checkFuzzyState(fuzzyState, states);
			});
		});
	}

	///////////////////////////////////////////////////////////////////////////

	protected static State stateOfSymbol(Symbol symbol) {
		return new State(symbol.getValue());
	}

	protected static Symbol symbolOfState(State state) {
		return new Symbol(state.getLabel());
	}

	protected static Alphabet alphabetOfStateSymbols(Set<State> states) {
		return new Alphabet(states.stream() //
				.map((s) -> symbolOfState(s)) //
				.collect(Collectors.toSet())); //
	}

	///////////////////////////////////////////////////////////////////////////

	protected abstract Degree extendedMu(BaseTree tree, State state);

	public abstract Degree accept(BaseTree tree);

	///////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finalStates == null) ? 0 : finalStates.hashCode());
		result = prime * result + ((nonterminals == null) ? 0 : nonterminals.hashCode());
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		result = prime * result + ((terminals == null) ? 0 : terminals.hashCode());
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
		BaseFuzzyTreeAutomata other = (BaseFuzzyTreeAutomata) obj;
		if (finalStates == null) {
			if (other.finalStates != null)
				return false;
		} else if (!finalStates.equals(other.finalStates))
			return false;
		if (nonterminals == null) {
			if (other.nonterminals != null)
				return false;
		} else if (!nonterminals.equals(other.nonterminals))
			return false;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		if (terminals == null) {
			if (other.terminals != null)
				return false;
		} else if (!terminals.equals(other.terminals))
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
		return "FuzzyTreeAutomata [states=" + states + ", nonterminals=" + nonterminals + ", terminals=" + terminals
				+ ", transitionFunction=" + transitionFunction + ", finalStates=" + finalStates + "]";
	}

	@Override
	public void print(PrintStream to) {
		FTAFileComposer composer = new FTAFileComposer();
		String string = composer.compose((FuzzyTreeAutomata) this);
		to.println(string);
	}

}