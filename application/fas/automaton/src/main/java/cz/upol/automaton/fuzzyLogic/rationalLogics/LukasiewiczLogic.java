package cz.upol.automaton.fuzzyLogic.rationalLogics;

public class LukasiewiczLogic extends RationalFuzzyLogic {
	public LukasiewiczLogic() {
		super();
	}

	public Rational0to1Number residuum(Rational0to1Number element1,
			Rational0to1Number element2) {

		double val = 1.0 - element1.getValue() + element2.getValue();

		return new Rational0to1Number(Math.min(1.0, val));
	}

	public Rational0to1Number times(Rational0to1Number element1,
			Rational0to1Number element2) {

		double val = element1.getValue() + element2.getValue() - 1.0;

		return new Rational0to1Number(Math.max(0.0, val));
	}

	@Override
	public String getDescription() {
		return "Łukasiewicz Logic";
	}

	@Override
	public String toString() {
		return "Lukasiewicz Logic";
	}

}
