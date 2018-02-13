package cz.upol.fapapp.fa.modifs;

import cz.upol.fapapp.core.automata.State;

public class StatesCreator {
	private int index;
	private final String infix;

	public StatesCreator() {
		this.index = 0;
		this.infix = "";
	}

	public StatesCreator(int initialIndex) {
		this.index = initialIndex;
		this.infix = "";
	}

	public StatesCreator(String infix) {
		this.index = 0;
		this.infix = infix;
	}

	public StatesCreator(String infix, int initialIndex) {
		this.index = initialIndex;
		this.infix = infix;
	}
	
	///////////////////////////////////////////////////////////////////////////

	public State next() {
		State state = create(infix, index);

		index++;

		return state;
	}

	public State repeat() {
		State state = create(infix, index);

		return state;
	}

	public static State create(String infix, int index) {
		String label = "q" + infix + "_" + index;
		return new State(label);
	}
}
