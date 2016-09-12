package cz.upol.automaton.fuzzyLogic.rationalLogics;

public class ProductLogic extends RationalFuzzyLogic {

	public ProductLogic() {
		super();
	}

	public Rational0to1Number residuum(Rational0to1Number element1,
			Rational0to1Number element2) {

		if (element1.getValue() <= element2.getValue()) {
			return ONE;
		} else {
			double val = element2.getValue() / element1.getValue();
			return new Rational0to1Number(val);
		}
	}

	public Rational0to1Number times(Rational0to1Number element1,
			Rational0to1Number element2) {

		double val = element1.getValue() * element2.getValue();

		return new Rational0to1Number(val);
	}

	@Override
	public String getDescription() {
		return "Product logic";
	}

	@Override
	public String toString() {
		return "Product Logic";
	}
}
