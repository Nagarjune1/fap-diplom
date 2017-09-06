package cz.upol.fapapp.cfa.comp;

import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.cfa.conf.CFAConfTIMParser;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

@Deprecated
public class CFACompTIMParser extends TIMObjectParser<CFAComputation> {

	public static final String TYPE = "cellular fuzzy automata computation";
	private final CellularFuzzyAutomata automata;
	private final CFAConfTIMParser confParser;

	public CFACompTIMParser(CellularFuzzyAutomata automata) {
		super(TYPE);
		this.automata = automata;
		this.confParser = new CFAConfTIMParser();

	}

	@Override
	public CFAComputation process(TIMFileData data) {
		CFAConfiguration config = processConfig(data);
		int generation = processGeneration(data);

		return new CFAComputation(automata, config, generation);
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
