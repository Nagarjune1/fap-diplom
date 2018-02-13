package cz.upol.fapapp.fa.modifs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.core.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.fa.automata.FuzzyAutomata;

public class AutomataDeformer {
	private MutableAutomataStructure struct;

	public AutomataDeformer(FuzzyAutomata automata) {
		this.struct = new MutableAutomataStructure(automata);
	}

	public FuzzyAutomata getAutomata(Integer precisionOrNull) {
		return struct.toAutomata(precisionOrNull);
	}

	///////////////////////////////////////////////////////////////////////////

	public void addReplace(Degree degreeOf) {
		FuzzyBinaryRelation<Symbol, Symbol> similarity = degreeToFuzzyBinaryRelation(struct.getAlphabet(), degreeOf);
		addReplace(similarity);
	}

	public void addReplace(FuzzyBinaryRelation<Symbol, Symbol> similarity) {
		Logger.get().moreinfo("Adding replaces " + similarity);
		checkSimilarity(similarity);

		struct.doWithTransitionFunction((transition, from, over, to, degree) -> {
			if (Symbol.EMPTY.equals(over)) {
				return;
			}

			for (Symbol altOver : struct.getAlphabet()) {
				Degree similar = similarity.get(over, altOver);
				Degree newDegree = TNorms.getTnorm().tnorm(degree, similar);

				struct.addTransition(from, altOver, to, newDegree);
			}
		});
	}

	public void addInsertMore(Degree degreeOf) {
		FuzzySet<Symbol> degreesOf = degreeToFuzzySet(struct.getAlphabet(), degreeOf);
		addInsertMore(degreesOf);
	}

	public void addInsertMore(FuzzySet<Symbol> degreesOf) {
		Logger.get().moreinfo("Adding inserts more " + degreesOf);

		struct.getStates().stream().forEach((state) -> {
			struct.getAlphabet().stream().forEach((symbol) -> {
				Degree insert = degreesOf.degreeOf(symbol);

				struct.addTransition(state, symbol, state, insert);
			});
		});
	}

	public void addRemoveOne(Degree degreeOf) {
		FuzzySet<Symbol> degreesOf = degreeToFuzzySet(struct.getAlphabet(), degreeOf);
		addRemoveOne(degreesOf);
	}

	public void addRemoveOne(FuzzySet<Symbol> degreesOf) {
		Logger.get().moreinfo("Adding removes ones " + degreesOf);

		struct.doWithTransitionFunction((transition, from, over, to, degree) -> {
			if (Symbol.EMPTY.equals(over)) {
				return;
			}

			Degree similar = degreesOf.degreeOf(over);
			Degree newDegree = TNorms.getTnorm().tnorm(degree, similar);

			struct.addTransition(from, Symbol.EMPTY, to, newDegree);
		});
	}

	public void addInsertOne(Degree degreeOf) {
		FuzzySet<Symbol> degreesOf = degreeToFuzzySet(struct.getAlphabet(), degreeOf);
		addInsertOne(degreesOf);
	}

	public void addInsertOne(FuzzySet<Symbol> degreesOf) {
		Logger.get().moreinfo("Adding inserts ones " + degreesOf);
		StatesCreator creator = new StatesCreator("'", struct.getStates().size());

		struct.doWithTransitionFunction((transition, from, over, to, degree) -> {
			if (Symbol.EMPTY.equals(over)) {
				return;
			}

			Degree similar = degreesOf.degreeOf(over);
			Degree newDegree = TNorms.getTnorm().tnorm(degree, similar);

			State innerState = creator.next();
			Degree initialIn = Degree.ZERO;
			Degree finalIn = Degree.ZERO;

			struct.addState(innerState, initialIn, finalIn);
			struct.addTransition(from, over, innerState, newDegree);
			struct.addTransition(innerState, over, to, newDegree);
		});
	}

	// TODO add more deformations here?

	///////////////////////////////////////////////////////////////////////////

	private static FuzzySet<Symbol> degreeToFuzzySet(Set<Symbol> alphabet, Degree degreeOf) {
		Map<Symbol, Degree> tuples = new HashMap<>(alphabet.size());

		for (Symbol symbol : alphabet) {
			tuples.put(symbol, degreeOf);
		}

		return new FuzzySet<>(tuples);
	}

	private static FuzzyBinaryRelation<Symbol, Symbol> degreeToFuzzyBinaryRelation(Set<Symbol> alphabet,
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

	private static <E> void checkSimilarity(FuzzyBinaryRelation<E, E> similarity) {

		for (Couple<E, E> couple : similarity.domain()) {
			E first = couple.getDomain();
			E second = couple.getTarget();

			if (first.equals(second)) {
				Degree similar = similarity.get(first, second);
				if (!Degree.ONE.equals(similar)) {
					Logger.get().warning(first + " should be equal to " + second + " in degree 1, okay");
					continue;
				}
			}
		}

	}

}
