package cz.upol.fapapp.cfa.automata;

import cz.upol.fapapp.cfa.config.CFAConfiguration;
import cz.upol.fapapp.cfa.misc.TwoDimArrComposer;
import cz.upol.fapapp.cfa.misc.TwoDimArray;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import cz.upol.fapapp.core.infile.InputFileData;
import cz.upol.fapapp.core.infile.InputFileObjectComposer;
import cz.upol.fapapp.core.infile.LineItems;

public class CFAFileComposer extends InputFileObjectComposer<CellularFuzzyAutomata> {




	private final TwoDimArrComposer<CellState> arrComp;

	public CFAFileComposer() {
		super(CFAFileParser.TYPE);

		arrComp = new TwoDimArrComposer<>("Configuration", //
				(c) -> Double.toString(c.getValue()));

	}

	@Override
	protected void process(CellularFuzzyAutomata automata, InputFileData data) {
		processSize(automata.getSize(), data);
		processMu(automata.getMu(), data);
		processGeneration(automata.getCurrentGeneration(), data);
		processConfig(automata.getCurrentConfig(), data);
	}

	/**************************************************************************/
	private void processSize(int size, InputFileData data) {
		data.start("size");
		String value = Integer.toString(size);
		LineItems items = new LineItems(value);
		data.add("size", items);
	}

	private void processMu(CFATransitionFunction mu, InputFileData data) {
		data.start("mu");
		String value = mu.getClass().getName();
		LineItems items = new LineItems(value);
		data.add("mu", items);
	}

	private void processGeneration(int currentGeneration, InputFileData data) {
		data.start("generation");
		String value = Integer.toString(currentGeneration);
		LineItems items = new LineItems(value);
		data.add("generation", items);
	}

	private void processConfig(CFAConfiguration currentConfig, InputFileData data) {
		data.start("default cell");
		String value = Double.toString(currentConfig.defaultCell().getValue());
		LineItems items = new LineItems(value);
		data.add("default cell", items);

		data.start("config");
		TwoDimArray<CellState> arr = currentConfig.toArray();
		arrComp.putItems("config", arr, data);
	}

}
