package cz.upol.fapapp.fta.automata;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.ling.Word;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.misc.MathUtils;
import cz.upol.fapapp.core.sets.BinaryRelation;
import cz.upol.fapapp.fta.tree.AtomicTree;
import cz.upol.fapapp.fta.tree.BaseTree;
import cz.upol.fapapp.fta.tree.CompositeTree;

public class FuzzyTreeAutomata extends BaseFuzzyTreeAutomata {

	public FuzzyTreeAutomata(Set<State> states, Alphabet nonterminals, Alphabet terminals,
			Map<Symbol, BinaryRelation<Word, FuzzyState>> transitionFunction, Set<State> finalStates) {
		super(states, nonterminals, terminals, transitionFunction, finalStates);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public Degree accept(BaseTree tree) {
		tree.validate(nonterminals, terminals);

		return MathUtils.bigSupremum(finalStates, (s) -> { //
			Degree degree = extendedMu(tree, s);
			Logger.get().moreinfo("Run of tree to " + s + " ended with degree " + degree);
			return degree;
		});
	}

	@Override
	protected Degree extendedMu(BaseTree tree, State state) {
		Symbol nodeLabel = tree.getLabel();

		if (tree instanceof AtomicTree) {
			BinaryRelation<Word, FuzzyState> muOf = transitionFunction.get(nodeLabel);
			FuzzyState to = muOf.get(Word.EMPTY);

			return to.degreeOf(state);
		} else {
			CompositeTree compositeTree = (CompositeTree) tree;
			BinaryRelation<Word, FuzzyState> muOf = transitionFunction.get(nodeLabel);

			int length = compositeTree.getChildCount();
			Set<Word> words = subsetsOfLength(length);

			return MathUtils.bigSupremum(words, (w) -> {
				FuzzyState to = muOf.get(w);
				Degree degreeOfX = to.degreeOf(state);

				Degree degreeOfChildren = MathUtils.bigInfimum(0, length, (i) -> {
					BaseTree subtree = compositeTree.child(i);
					Symbol subnodeSymbol = w.at(i);
					State subState = stateOfSymbol(subnodeSymbol);

					return extendedMu(subtree, subState);
				});

				return Degree.infimum(degreeOfX, degreeOfChildren);
			});
		}
	}

	///////////////////////////////////////////////////////////////////////////

	protected Set<Word> subsetsOfLength(int length) {
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

	///////////////////////////////////////////////////////////////////////////

}
