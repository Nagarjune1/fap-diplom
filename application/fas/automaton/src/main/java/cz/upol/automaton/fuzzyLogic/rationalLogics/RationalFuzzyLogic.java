package cz.upol.automaton.fuzzyLogic.rationalLogics;

import java.util.Set;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.sets.Externisator;

public abstract class RationalFuzzyLogic extends FuzzyLogic {

	protected static final Rational0to1Number ZERO = new Rational0to1Number(0.0);
	protected static final Rational0to1Number ONE = new Rational0to1Number(1.0);
	protected static final Rationals0to1Set ELEMENTS = new Rationals0to1Set();

	public RationalFuzzyLogic() {
	}

	@Override
	public Set<Degree> getElements() {
		return ELEMENTS;
	}

	/**
	 * Spočítá suprémum zadaných stupňů. Pokud to nejsou čísla, volá výjimku.
	 */
	@Override
	public Degree supremum(Degree element1, Degree element2) {

		if (element1 instanceof Rational0to1Number
				&& element2 instanceof Rational0to1Number) {

			return supremum((Rational0to1Number) element1,
					(Rational0to1Number) element2);
		} else {
			throw new IllegalArgumentException("Not a "
					+ Rational0to1Number.class.getName());
		}

	}

	/**
	 * Spočítá suprémum zadaných čísel.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	public Rational0to1Number supremum(Rational0to1Number element1,
			Rational0to1Number element2) {

		return Rational0to1Number.max(element1, element2);
	}

	/**
	 * Spočítá imfimum zadaných stupňů. Pokud to nejsou čísla, volá výjimku.
	 */
	public Degree imfimum(Degree element1, Degree element2) {

		if (element1 instanceof Rational0to1Number
				&& element2 instanceof Rational0to1Number) {

			return imfimum((Rational0to1Number) element1,
					(Rational0to1Number) element2);
		} else {
			throw new IllegalArgumentException("Not a "
					+ Rational0to1Number.class.getName());
		}

	}

	/**
	 * Spočítá imfimum zadaných čísel.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	public Rational0to1Number imfimum(Rational0to1Number element1,
			Rational0to1Number element2) {

		return Rational0to1Number.min(element1, element2);
	}

	/**
	 * Spočítá residuum zadaných stupňů. Pokud to nejsou čísla, volá výjimku.
	 */
	public Degree residuum(Degree element1, Degree element2) {

		if (element1 instanceof Rational0to1Number
				&& element2 instanceof Rational0to1Number) {

			return residuum((Rational0to1Number) element1,
					(Rational0to1Number) element2);
		} else {
			throw new IllegalArgumentException("Not a "
					+ Rational0to1Number.class.getName());
		}

	}

	/**
	 * Spočítá residuum zadaných stupňů.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	public abstract Rational0to1Number residuum(Rational0to1Number element1,
			Rational0to1Number element2);

	/**
	 * Spočítá součin zadaných stupňů. Pokud to nejsou čísla, volá výjimku.
	 */
	public Degree times(Degree element1, Degree element2) {

		if (element1 instanceof Rational0to1Number
				&& element2 instanceof Rational0to1Number) {

			return times((Rational0to1Number) element1,
					(Rational0to1Number) element2);
		} else {
			throw new IllegalArgumentException("Not a "
					+ Rational0to1Number.class.getName());
		}

	}

	/**
	 * Spočítá součin zadaných stupňů.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	public abstract Rational0to1Number times(Rational0to1Number element1,
			Rational0to1Number element2);

	@Override
	public Degree getZero() {
		return ZERO;
	}

	@Override
	public Degree getOne() {
		return ONE;
	}

	@Override
	public Externisator<Degree> getUniverseElementsExternisator() {
		return Rational0to1Number.EXTERNISATOR;
	}

	@Override
	public String toString() {
		return "Rational [0, 1] Fuzzy Logic";
	}

}
