package cz.upol.fas.config;

import cz.upol.automaton.sets.HasExterernalRepresentation;

public enum ExportFormat implements HasExterernalRepresentation {
	COLOR_PNG("Barevné PNG"), //
	BLACK_PNG("Černobílé PNG"), //
	COLOR_SVG("Barevné SVG"), //
	BLACK_SVG("Černobílé SVG"), //
	PLAINTEXT("Text");

	private final String text;

	private ExportFormat(String text) {
		this.text = text;
	}

	@Override
	public String externalize() {
		return text;
	}

}
