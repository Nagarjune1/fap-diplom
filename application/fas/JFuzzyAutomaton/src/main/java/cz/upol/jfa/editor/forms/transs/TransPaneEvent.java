package cz.upol.jfa.editor.forms.transs;

import java.awt.event.ActionEvent;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.ling.Symbol;

public class TransPaneEvent extends ActionEvent {

	private static final long serialVersionUID = 8926071331154298331L;

	private final JAbstractTransPane pane;
	private final TransPaneEventType type;
	private final Symbol symbol;
	private final Degree degree;

	public TransPaneEvent(JAbstractTransPane pane,
			TransPaneEventType type, Symbol symbol, Degree degree, int id,
			String command, long when) {

		super(pane, id, command, when, 0);

		this.pane = pane;
		this.type = type;
		this.symbol = symbol;
		this.degree = degree;
	}

	public JAbstractTransPane getPane() {
		return pane;
	}

	public TransPaneEventType getType() {
		return type;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public Degree getDegree() {
		return degree;
	}
}
