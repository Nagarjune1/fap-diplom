package cz.upol.fapapp.fa.modifs;

import cz.upol.fapapp.core.automata.State;

/**
 * Creator of states, each newly created state's label has following format: "q_
 * INFIX INDEX" (without spaces). Index increases after each next states have
 * been created.
 * 
 * @author martin
 *
 */
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

	/**
	 * Gimme next state.
	 * 
	 * @return
	 */
	public State next() {
		State state = create(infix, index);

		index++;

		return state;
	}

	/**
	 * Gimme lastly created state.
	 * 
	 * @return
	 */
	public State repeat() {
		State state = create(infix, index);

		return state;
	}

	/**
	 * Create state.
	 * 
	 * @param infix
	 * @param index
	 * @return
	 */
	public static State create(String infix, int index) {
		String label = "q" + infix + "_" + index;
		return new State(label);
	}
}
