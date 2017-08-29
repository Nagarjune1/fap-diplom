package cz.upol.fapapp.cfa.automata;

import java.util.List;

import cz.upol.fapapp.cfa.config.CFAConfiguration;
import cz.upol.fapapp.cfa.misc.TwoDimArrParser;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;
import cz.upol.fapapp.core.infile.InputFileData;
import cz.upol.fapapp.core.infile.InputFileObjectParser;
import cz.upol.fapapp.core.infile.LineItems;
import cz.upol.fapapp.core.infile.ObjectParserTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.CollectionsUtils;

public class CFAFileParser extends InputFileObjectParser<BaseCellularFuzzyAutomata> {

	public static final String TYPE = "Cellular Fuzzy Automata";
	public static final String CONFIG_PARSER_TYPE = "Whatever, for instance 'Configuration'";

	private final TwoDimArrParser<CellState> arrPar;

	public CFAFileParser() {
		super(TYPE);

		arrPar = new TwoDimArrParser<>(CONFIG_PARSER_TYPE, //
				(s) -> new CellState(ObjectParserTools.parseDouble(s)));

	}

	@Override
	public BaseCellularFuzzyAutomata process(InputFileData data) {
		Integer size = null;
		CFATransitionFunction mu = null;
		CFAConfiguration config = null;

		for (String key : data.listKeys()) {
			List<LineItems> lines = data.getItemsOf(key);

			switch (key) {
			case InputFileData.TYPE_KEY:
				break;
			case "size":
				size = processSize(lines);
				break;
			case "mu":
			case "transition function":
				mu = processMu(lines);
				break;
			case "config":
			case "configuration":
			case "initial config":
			case "initial configuration":
				config = processConfig(lines);
				break;
			case "generation":
				break;
			default:
				Logger.get().warning("Unknown key " + key);
			}
		}

		CollectionsUtils.checkNotNull("size", size);
		CollectionsUtils.checkNotNull("mu", mu);
		CollectionsUtils.checkNotNull("config", config);

		return new CellularFuzzyAutomata(size, mu, config);
	}

	/**************************************************************************/

	private Integer processSize(List<LineItems> lines) {
		if (lines.size() != 1 || lines.get(0).count() != 1) {
			throw new IllegalArgumentException("Expected single one int number");
		}

		LineItems line = lines.get(0);
		String str = line.getIth(0);
		return ObjectParserTools.parseInt(str);
	}

	private CFATransitionFunction processMu(List<LineItems> lines) {
		if (lines.size() != 1 || lines.get(0).count() != 1) {
			throw new IllegalArgumentException("Expected single one class name");
		}

		LineItems line = lines.get(0);
		String str = line.getIth(0);
		
		try {
			Class<?> clazz = Class.forName(str);
			Object instance = clazz.newInstance();
			return (CFATransitionFunction) instance;
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot load mu: " + str, e);
		}
	}

	private CFAConfiguration processConfig(List<LineItems> lines) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("huhu");
	}

}
