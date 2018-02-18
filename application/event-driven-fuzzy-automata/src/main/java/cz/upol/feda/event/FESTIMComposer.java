package cz.upol.feda.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.feda.lingvar.LingVarValue;
import cz.upol.feda.lingvar.LingVarsTIMComposer;
import cz.upol.feda.lingvar.LingvisticVariable;

public class FESTIMComposer extends TIMObjectComposer<FuzzyEventsSequence> {

	private final LingVarsTIMComposer lingVarsComposer = new LingVarsTIMComposer("whatever", "variables");

	public FESTIMComposer() {
		super(FESTIMParser.TYPE);
	}

	@Override
	protected void process(FuzzyEventsSequence sequence, TIMFileData data) {
		processLingVars(sequence.inferVariables(), data);

		for (FuzzyEvent event : sequence.getEvents()) {
			processEvent(event, data);
		}

	}

	private void processLingVars(Set<LingvisticVariable> vars, TIMFileData data) {
		lingVarsComposer.process(vars, data);
	}

	private void processEvent(FuzzyEvent event, TIMFileData data) {
		LineElements line = eventToLine(event);
		data.add("events", line);

	}

	private LineElements eventToLine(FuzzyEvent event) {
		List<String> elements = new ArrayList<>();

		for (LingvisticVariable var : event.getVariables()) {
			LingVarValue value = event.valueOf(var);

			elements.add(var.getName());
			elements.add("is");
			elements.add(Double.toString(value.getValue()));
		}

		LineElements line = new LineElements(elements);
		return line;
	}

}
