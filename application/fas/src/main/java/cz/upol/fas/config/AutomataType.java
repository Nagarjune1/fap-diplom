package cz.upol.fas.config;

import cz.upol.automaton.sets.HasExterernalRepresentation;

public enum AutomataType implements HasExterernalRepresentation {
	NONDETERMINISTIC_FUZZY("Nedeterministický fuzzy automat"), //
	DETERMINISTIC_FUZZY("Deterministický fuzzy automat");//

	private final String name;

	private AutomataType(String name) {
		this.name = name;
	}

	@Override
	public String externalize() {
		return name;
	}

}
