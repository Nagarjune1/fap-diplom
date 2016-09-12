package cz.upol.automaton.misc;

import cz.upol.automaton.sets.Externalisable;
import cz.upol.automaton.sets.Externisator;

public class State implements Comparable<State>, Externalisable<State> {

	public static final Externisator<State> EXTERNISATOR = new StatesExternisator();

	/**
	 * @uml.property  name="label"
	 */
	private final String label;

	public State(String label) {
		this.label = label;
	}

	public State(State other) {
		this.label = other.getLabel();
	}

	/**
	 * @return
	 * @uml.property  name="label"
	 */
	public String getLabel() {
		return label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "State [" + label + "]";
	}

	@Override
	public int compareTo(State o) {
		return this.getLabel().compareTo(o.getLabel());
	}

	@Override
	public Externisator<State> getExternisator() {
		return EXTERNISATOR;
	}

	public static class StatesExternisator extends Externisator<State> {

		@Override
		public State parseKnown(String string) {
			return new State(string);
		}

		@Override
		public String externalizeKnown(State externasiable) {
			return externasiable.getLabel();
		}

	}
}
