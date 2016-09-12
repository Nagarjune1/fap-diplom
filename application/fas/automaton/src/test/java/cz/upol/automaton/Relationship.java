package cz.upol.automaton;

import cz.upol.automaton.fuzzyStructs.Tuple;

public class Relationship implements Tuple {
	/**
	 * @uml.property  name="boy"
	 */
	private final String boy;
	/**
	 * @uml.property  name="girl"
	 */
	private final String girl;

	public Relationship(String boy, String girl) {
		super();
		this.boy = boy;
		this.girl = girl;
	}

	public Object getIthItem(int i) throws IllegalArgumentException {
		switch (i) {
		case 0:
			return boy;
		case 1:
			return girl;
		default:
			throw new IllegalArgumentException("Invalid index");
		}
	}

	@Override
	public String toString() {
		return "Relationship [boy=" + boy + ", girl=" + girl + "]";
	}

}
