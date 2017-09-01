package cz.upol.fapapp.cfa.comp;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class CFACompTIMParser extends TIMObjectParser<CellularAutomataComputation> {

	public static final String TYPE = "cellular fuzzy automata computation";
	private final CellularFuzzyAutomata automata;
	private final CFAConfTTIMParser confParser;

	public CFACompTIMParser(CellularFuzzyAutomata automata) {
		super(TYPE);
		this.automata = automata;
		this.confParser = new CFAConfTTIMParser("cells", "config", "configuration");

	}

	@Override
	public CellularAutomataComputation process(TIMFileData data) {
		CFAConfiguration config = processConfig(data);
		int generation = processGeneration(data);

		return new CellularAutomataComputation(automata, config, generation);
	}

	/**************************************************************************/

	private CFAConfiguration processConfig(TIMFileData data) {
		return confParser.process(data);
	}

	private int processGeneration(TIMFileData data) {
		String value = TIMObjectParserComposerTools.findSingleItem(data, "generation", "current generation");
		return TIMObjectParserComposerTools.parseInt(value);
	}

}
