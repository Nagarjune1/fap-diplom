package cz.upol.fapapp.fa.typos;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Word;

/**
 * Result of correction. Beside the only corrected word this contains more
 * informtaions, like the degree of similarity or whether even the typo occured.
 * 
 * @author martin
 *
 */
public class CorrectionResult {
	private final Word input;
	private final Word bestMatch;
	private final Degree degree;

	public CorrectionResult(Word input, Word bestMatch, Degree degree) {
		super();
		this.input = input;
		this.bestMatch = bestMatch;
		this.degree = degree;
	}

	/**
	 * Pattern word which best matched to input. If no such, returns original word.
	 * 
	 * @return
	 */
	public Word getBestMatch() {
		if (isNoMatch()) {
			return input;
		} else {
			return bestMatch;
		}
	}

	/**
	 * Returns true if no typo occured (the input was equal to some of
	 * patterns).
	 * 
	 * @return
	 */
	public boolean isNoTypo() {
		return input.equals(bestMatch);
	}

	/**
	 * Returns true if no match with the pattern occured (the degree of
	 * similarity was no higer than zero).
	 * 
	 * @return
	 */
	public boolean isNoMatch() {
		return Degree.ZERO.equals(degree);
	}

	/**
	 * Returns true if correction occured (found some match with the pattern AND
	 * the input was not equal pattern).
	 * 
	 * @return
	 */
	public boolean isCorrected() {
		return !isNoMatch() && !isNoTypo();
	}

	@Override
	public String toString() {
		return "CorrectionResult [isNoTypo=" + isNoTypo() + ", isNoMatch=" + isNoMatch() + ", isCorrected="
				+ isCorrected() + ", input=" + input + ", bestMatch=" + bestMatch + ", degree=" + degree + "]";
	}

}
