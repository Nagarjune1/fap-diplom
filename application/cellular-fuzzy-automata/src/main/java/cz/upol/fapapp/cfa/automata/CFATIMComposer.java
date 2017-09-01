package cz.upol.fapapp.cfa.automata;

import cz.upol.fapapp.cfa.mu.CFAOuterCellSupplier;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;

public class CFATIMComposer extends TIMObjectComposer<CellularFuzzyAutomata> {

	public CFATIMComposer() {
		super(CFATIMParser.TYPE);
	}

	@Override
	protected void process(CellularFuzzyAutomata automata, TIMFileData data) {
		processSize(automata.getSize(), data);
		processMu(automata.getMu(), data);
		processOuters(automata.getOuters(), data);

	}

	/**************************************************************************/
	private void processSize(int size, TIMFileData data) {
		String value = Integer.toString(size);
		LineElements items = new LineElements(value);
		data.add("size", items);
	}

	private void processMu(CFATransitionFunction mu, TIMFileData data) {
		String value = mu.getClass().getName();
		LineElements items = new LineElements(value);
		data.add("mu", items);
	}

	private void processOuters(CFAOuterCellSupplier outers, TIMFileData data) {
		String value = outers.getClass().getName();
		LineElements items = new LineElements(value);
		data.add("outers", items);
	}

}
