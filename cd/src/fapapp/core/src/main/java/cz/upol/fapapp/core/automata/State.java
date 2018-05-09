package cz.upol.fapapp.core.automata;

/**
 * State, base element in (fuzzy) automata theory.
 * @author martin
 *
 */
public class State implements Comparable<State> {
	private final String label;

	public State(String label) {
		super();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	///////////////////////////////////////////////////////////////////////////
	
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
		return "State(" + label + ")";
	}
	
	@Override
	public int compareTo(State o) {
		return this.label.compareTo(o.label);
	}
}
