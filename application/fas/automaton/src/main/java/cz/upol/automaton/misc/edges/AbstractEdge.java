package cz.upol.automaton.misc.edges;

import cz.upol.automaton.misc.State;

public abstract class AbstractEdge<TT> {

	private final State from;
	private final State to;
	private final TT transitions;

	public AbstractEdge(State from, State to, TT transitions) {
		this.from = from;
		this.to = to;
		this.transitions = transitions;
	}

	public AbstractEdge(AbstractEdge<TT> edge) {
		this.from = edge.getFrom();
		this.to = edge.getTo();
		this.transitions = edge.copyOfTransitions();
	}

	public State getFrom() {
		return from;
	}

	public State getTo() {
		return to;
	}

	public TT getTransitions() {
		return transitions;
	}

	public abstract TT copyOfTransitions();
	
	public abstract boolean hasSomeTransition();
	
	public abstract String getLabel();


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		AbstractEdge<?> other = (AbstractEdge<?>) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractEdge [from=" + from + ", to=" + to + ", transitions="
				+ transitions + "]";
	}




}
