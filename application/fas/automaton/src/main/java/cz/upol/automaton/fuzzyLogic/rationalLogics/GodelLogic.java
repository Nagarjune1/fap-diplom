package cz.upol.automaton.fuzzyLogic.rationalLogics;

public class GodelLogic extends RationalFuzzyLogic {

	public GodelLogic() {
		super();
	}

	public Rational0to1Number residuum(Rational0to1Number element1,
			Rational0to1Number element2) {

		if (element1.getValue() <= element2.getValue()) {
			return ONE;
		} else {
			double val = element2.getValue();
			return new Rational0to1Number(val);
		}
	}

	public Rational0to1Number times(Rational0to1Number element1,
			Rational0to1Number element2) {

		double val = Math.min(element1.getValue(), element2.getValue());

		return new Rational0to1Number(val);
	}

	@Override
	public String getDescription() {
		return "Gödel Logic";
	}

	@Override
	public String toString() {
		return "Godel Logic";
	}

}
