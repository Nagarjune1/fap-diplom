package cz.upol.fapapp.fta.automata;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.BaseAutomata;
import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.core.misc.MathUtils;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.fta.data.AtomicTree;
import cz.upol.fapapp.fta.data.BaseTree;
import cz.upol.fapapp.fta.data.CompositeTree;

public class FuzzyTreeAutomata implements BaseAutomata {
	private final Set<State> states;
	private final Alphabet nonterminals;
	private final Alphabet terminals;
	private final Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction;
	private final Set<State> finalStates;

	public FuzzyTreeAutomata(Set<State> states, Alphabet nonterminals, Alphabet terminals,
			Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction, Set<State> finalStates) {
		super();

		CollectionsUtils.checkDisjoint(nonterminals, terminals);
		// TODO check transition function
		CollectionsUtils.checkSubset(finalStates, states);

		this.states = states;
		this.nonterminals = nonterminals;
		this.terminals = terminals;
		this.transitionFunction = transitionFunction;
		this.finalStates = finalStates;
	}

	public Degree accept(BaseTree tree) {
		return MathUtils.bigSupremum(finalStates, (s) -> //
		extendedMu(tree, s) //
		);
	}

	private Degree extendedMu(BaseTree tree, State state) {
		Symbol label = tree.getLabel();

		if (tree instanceof AtomicTree) {
			BinaryRelation<Word, FuzzyState> muOf = transitionFunction.get(label);
			FuzzyState to = muOf.get(Word.EMPTY);

			return to.degreeOf(state);
		} else {
			CompositeTree compositeTree = (CompositeTree) tree;
			BinaryRelation<Word, FuzzyState> muOf = transitionFunction.get(label);

			int length = compositeTree.getChildCount();
			Set<Word> words = subsetsOfLength(length);
			return MathUtils.bigSupremum(words, (w) -> {
				FuzzyState to = muOf.get(w);
				Degree degreeOfX = to.degreeOf(state);

				Degree degreeOfChildren = MathUtils.bigInfimum(0, length, (i) -> {
					BaseTree subtree = compositeTree.child(i);
					Symbol subsymbol = w.at(i);
					State subState = stateOfSymbol(subsymbol);

					return extendedMu(subtree, subState);
				});

				return Degree.infimum(degreeOfX, degreeOfChildren);
			});
		}
	}

	private Set<Word> subsetsOfLength(int length) {
		Set<Word> words = new HashSet<>();

		transitionFunction.forEach((s, r) -> {
			r.getTuples().forEach((t) -> {
				Word word = t.getDomain();
				if (word.getLength() == length) {
					words.add(word);
				}
			});
		});

		return words;
	}

	protected State stateOfSymbol(Symbol symbol) {
		return new State(symbol.getValue());
	}

	protected Symbol symbolOfState(State state) {
		return new Symbol(state.getLabel());
	}

	@Override
	public String toString() {
		return "FuzzyTreeAutomata [states=" + states + ", nonterminals=" + nonterminals + ", terminals=" + terminals
				+ ", transitionFunction=" + transitionFunction + ", finalStates=" + finalStates + "]";
	}

	public static class FTAmuTuple extends Couple<Word, FuzzyState> {

		public FTAmuTuple(Word over, FuzzyState to) {
			super(over, to);
		}

		public Word getOver() {
			return getDomain();
		}

		public FuzzyState getTo() {
			return getTarget();
		}

		@Override
		public String toString() {
			return "FTAmuTuple [over=" + getOver() + ", to=" + getTo() + "]";
		}

	}
}
