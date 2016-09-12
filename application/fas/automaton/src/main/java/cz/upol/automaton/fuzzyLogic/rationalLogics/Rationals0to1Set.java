package cz.upol.automaton.fuzzyLogic.rationalLogics;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.ling.alphabets.SetOfExternisables;
import cz.upol.automaton.sets.SetOfAll;

public class Rationals0to1Set extends SetOfAll<Degree> implements
		SetOfExternisables<Degree> {

	public Rationals0to1Set() {
		super(Rational0to1Number.class);
	}

	@Override
	public boolean shouldBeInSet(String stringReprezentation) {
		return Rational0to1Number.isValid(stringReprezentation);
	}

}
