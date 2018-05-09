package cz.upol.fapapp.feda.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.lingvar.LingVarValue;
import cz.upol.fapapp.core.lingvar.LingVarsTIMParser;
import cz.upol.fapapp.core.lingvar.LingvisticVariable;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} of {@link FuzzyEventsSequence}. Uses following format:
 * <pre>
 * variables:
 *  (see {@link LingVarsTIMParser})
 * values:
 *  foo is 42, bar is 99
 *  foo is 33, bar is -1.5
 * </pre>
 * @author martin
 *
 */
public class FESTIMParser extends TIMObjectParser<FuzzyEventsSequence> {

	public static final String TYPE = "fuzzy events sequence";

	private final LingVarsTIMParser lingVarsParser = new LingVarsTIMParser("whatever", "ling vars",
			"lingvistic variables", "variables");

	public FESTIMParser() {
		super(TYPE);
	}

	@Override
	public FuzzyEventsSequence process(TIMFileData data) {
		Set<LingvisticVariable> vars = processVars(data);
		List<FuzzyEvent> events = processEvents(data, vars);

		return new FuzzyEventsSequence(events);
	}

	private Set<LingvisticVariable> processVars(TIMFileData data) {
		return lingVarsParser.process(data);
	}

	private List<FuzzyEvent> processEvents(TIMFileData data, Set<LingvisticVariable> vars) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "events", "data", "values");

		List<FuzzyEvent> events = new ArrayList<>(lines.size());

		for (LineElements line : lines) {
			FuzzyEvent event = processEvent(line, vars);
			events.add(event);
		}

		return events;
	}

	private FuzzyEvent processEvent(LineElements line, Set<LingvisticVariable> vars) {
		Map<LingvisticVariable, LingVarValue> values = new HashMap<>(vars.size());

		for (int i = 0; i < line.getElements().size(); i += 3) {
			String varName = line.getIth(i + 0);
			TIMObjectParserComposerTools.is(line, i + 1, "is");
			String valueStr = line.getIth(i + 2);

			LingvisticVariable var = LingvisticVariable.getVariable(varName, vars);
			LingVarValue val = new LingVarValue(TIMObjectParserComposerTools.parseDouble(valueStr));
			// TODO migrate ling vars to core
			// TODO TIMObjectParserComposerTools.parseLingVarVal

			values.put(var, val);
		}

		return new FuzzyEvent(values);
	}

}
