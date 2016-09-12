package cz.upol.fas.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.GodelLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.fuzzyLogic.rationalLogics.ProductLogic;
import cz.upol.automaton.ling.alphabets.AllStringsAlphabet;
import cz.upol.automaton.ling.alphabets.Alphabet;

public class Avaibles {
	private final static Avaibles INSTANCE = new Avaibles();

	private Avaibles() {
	}

	public List<FuzzyLogic> avaibleFuzzyLogics() {
		List<FuzzyLogic> result = new ArrayList<>();

		result.add(new LukasiewiczLogic());
		result.add(new GodelLogic());
		result.add(new ProductLogic());

		return result;
	}

	public List<Alphabet> avaibleAlphabets() {
		List<Alphabet> result = new ArrayList<>();

		result.add(new AllStringsAlphabet());

		return result;
	}

	public List<ExportFormat> avaibleExportFormats() {
		List<ExportFormat> result = new ArrayList<>();
		
		result.add(ExportFormat.COLOR_PNG);
		result.add(ExportFormat.BLACK_PNG);
		result.add(ExportFormat.PLAINTEXT);

		return result;
	}

	public List<AutomataType> avaibleAutomaton() {
		List<AutomataType> result = Arrays.asList(AutomataType.values());

		return result;
	}

	public static Avaibles get() {
		return INSTANCE;
	}

}
