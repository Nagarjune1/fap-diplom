package cz.upol.fapapp.fa.typos;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Word;

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

	public Word getBestMatch() {
		return bestMatch;
	}

	public boolean isNoTypo() {
		return input.equals(bestMatch);
	}

	public boolean isNoMatch() {
		return Degree.ZERO.equals(degree);
	}

	public boolean isCorrected() {
		return !isNoMatch() && !isNoTypo();
	}

	@Override
	public String toString() {
		return "CorrectionResult [isNoTypo=" + isNoTypo() + ", isNoMatch=" + isNoMatch() + ", isCorrected="
				+ isCorrected() + ", input=" + input + ", bestMatch=" + bestMatch + ", degree=" + degree + "]";
	}


	
	

}
