package cz.upol.fapapp.cfa.comp;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArrTIMComposer;
import cz.upol.fapapp.cfa.misc.TwoDimArray;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;

public class CFAConfTIMComposer extends TIMObjectComposer<CFAConfiguration> {

	private final TwoDimArrTIMComposer<CellState> arrComp;

	public CFAConfTIMComposer(String cellsItemName) {
		super(CFAConfTTIMParser.TYPE);

		arrComp = new TwoDimArrTIMComposer<>("whatever", //
				(c) -> Double.toString(c.getValue()), //
				cellsItemName);

	}

	@Override
	protected void process(CFAConfiguration config, TIMFileData data) {
		processSize(config.getSize(), data);
		processCells(config.toArray(), data);

	}

	public void processWithoutSize(CFAConfiguration config, TIMFileData data) {
		processCells(config.toArray(), data);
	}

	/*************************************************************************/

	private void processSize(int size, TIMFileData data) {
		String value = Integer.toString(size);
		data.add("size", value);
	}

	private void processCells(TwoDimArray<CellState> array, TIMFileData data) {
		arrComp.processWithoutSize(array, data);

	}

}
