package cz.upol.fapapp.fa.automata;

import cz.upol.fapapp.core.automata.State;

public class StatesCreator {
	private int index;

	public StatesCreator() {
	}

	public StatesCreator(int initialIndex) {
		this.index = initialIndex;
	}

	public State next() {
		State state = create(index);

		index++;

		return state;
	}
	
	public State repeat() {
		State state = create(index);

		
		return state;
	}
	
		

	public static State create(int index) {
		String label = "q_" + index;
		return new State(label);
	}
}
