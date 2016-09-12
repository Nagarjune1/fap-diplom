package cz.upol.automaton.ling;

public class EmptyStringSymbol extends Symbol {
	public static final String EMPTY_SYMBOL_LABEL = "€"; // TODO epsilon char
	public static final EmptyStringSymbol INSTANCE = new EmptyStringSymbol();

	public EmptyStringSymbol() {
		super(EMPTY_SYMBOL_LABEL);
	}

}
