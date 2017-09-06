package cz.upol.fapapp.cfa.automata;

import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import cz.upol.fapapp.cfa.outers.CFAOuterCellSupplier;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

@Deprecated
public class CFATIMParser extends TIMObjectParser<CellularFuzzyAutomata> {

	public static final String TYPE = "Cellular Fuzzy Automata";

	public CFATIMParser() {
		super(TYPE);
	}

	@Override
	public CellularFuzzyAutomata process(TIMFileData data) {
		int size = processSize(data);
		CFATransitionFunction mu = processMu(data);
		CFAOuterCellSupplier supplier = processSupplier(data);

		return new CellularFuzzyAutomata(size, mu, supplier);
	}

	/**************************************************************************/

	private int processSize(TIMFileData data) {
		String value = TIMObjectParserComposerTools.findSingleItem(data, "size", "m");
		return TIMObjectParserComposerTools.parseInt(value);
	}

	private CFATransitionFunction processMu(TIMFileData data) {
		String value = TIMObjectParserComposerTools.findSingleItem(data, "transition function", "mu");
		return instantite(value);
	}

	private CFAOuterCellSupplier processSupplier(TIMFileData data) {
		String value = TIMObjectParserComposerTools.findSingleItem(data, "outer cell", "outer cell supllier", "outers");
		return instantite(value);
	}

	/**************************************************************************/

	private <T> T instantite(String value) {
		try {
			Class<?> clazz = Class.forName(value);
			Object instance = clazz.newInstance();

			@SuppressWarnings("unchecked")
			T typed = (T) instance;
			return typed;
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot load vlass: " + value, e);
		}
	}

}
