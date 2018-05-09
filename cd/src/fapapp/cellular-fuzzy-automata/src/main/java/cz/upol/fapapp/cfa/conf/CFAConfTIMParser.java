package cz.upol.fapapp.cfa.conf;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArrTIMParser;
import cz.upol.fapapp.cfa.misc.TwoDimArray;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} for {@link CFAConfiguration}. Uses following format:
 * 
 * <pre>
 * size:
 * 	3
 * cells:
 * 	0.1 0.2 0.3
 *  0.4 0.5 0.6
 *  0.7 0.8 0.9
 * </pre>
 * 
 * @author martin
 *
 */
public class CFAConfTIMParser extends TIMObjectParser<CFAConfiguration> {

	public static final String TYPE = "cellular fuzzy automaton configuration";

	private final TwoDimArrTIMParser<CellState> arrParser;

	public CFAConfTIMParser() {
		super(TYPE);

		this.arrParser = new TwoDimArrTIMParser<>("whatever", //
				(s) -> new CellState(TIMObjectParserComposerTools.parseDouble(s)), //
				"cells");
	}

	@Override
	public CFAConfiguration process(TIMFileData data) {
		int m = processSize(data);
		TwoDimArray<CellState> cells = processCells(data, m);

		return new CFAConfiguration(m, cells);
	}

	/*************************************************************************/

	private int processSize(TIMFileData data) {
		String value = TIMObjectParserComposerTools.findSingleItem(data, "size", "m");
		return TIMObjectParserComposerTools.parseInt(value);
	}

	private TwoDimArray<CellState> processCells(TIMFileData data, int size) {
		return arrParser.processWithKnownSize(data, 0, size);
	}

}
