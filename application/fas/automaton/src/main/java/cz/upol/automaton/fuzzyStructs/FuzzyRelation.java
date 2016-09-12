package cz.upol.automaton.fuzzyStructs;

import java.util.Set;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.sets.SetOfAll;

public class FuzzyRelation<S extends Tuple> extends FuzzySet<S> {

	public FuzzyRelation(ResiduedLattice residuedLattice, Class<S> tupleClass) {
		super(residuedLattice, new SetOfAll<S>(tupleClass));
	}

	public FuzzyRelation(FuzzyLogic logic, Set<S> universe) {
		super(logic, universe);
	}

	public FuzzyRelation(FuzzyRelation<S> other) {
		super(other);
	}

	@Override
	public Degree insert(S item, Degree degree) {
		getUniverse().add(item);
		return super.insert(item, degree);
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder("FuzzyRelation[");
		stb.append("logic=");
		stb.append(getResiduedLattice());
		stb.append(", ");

		addElements(stb);

		stb.append("]");

		return stb.toString();
	}
}
