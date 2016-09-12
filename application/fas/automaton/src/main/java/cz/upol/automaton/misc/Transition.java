package cz.upol.automaton.misc;

import cz.upol.automaton.fuzzyStructs.Tuple;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.sets.Externisator;
import cz.upol.automaton.sets.HasExterernalRepresentation;

public class Transition implements Tuple, HasExterernalRepresentation,
		Comparable<Transition> {

	/**
	 * @uml.property name="from"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final State from;
	/**
	 * @uml.property name="over"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final Symbol over;
	/**
	 * @uml.property name="to"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final State to;

	public Transition(State from, Symbol over, State to) {
		super();
		this.from = from;
		this.over = over;
		this.to = to;
	}

	/**
	 * @return
	 * @uml.property name="from"
	 */
	public State getFrom() {
		return from;
	}

	/**
	 * @return
	 * @uml.property name="over"
	 */
	public Symbol getOver() {
		return over;
	}

	/**
	 * @return
	 * @uml.property name="to"
	 */
	public State getTo() {
		return to;
	}

	/**
	 * Vrací true, pokud přechod vychází z nebo končí ve stavu state.
	 * 
	 * @param state
	 * @return
	 */
	public boolean incidesWithState(State state) {
		return this.from.equals(state) || this.to.equals(state);
	}

	/**
	 * Vrací true, pokud přechod vychází ze stavu from
	 * 
	 * @param from
	 * @return
	 */
	public boolean isFrom(State from) {
		return this.from.equals(from);
	}

	/**
	 * Vrací true, pokud je přechod přes symbol symbol.
	 * 
	 * @param symbol
	 * @return
	 */
	public boolean isOver(Symbol symbol) {
		return this.over.equals(symbol);
	}

	/**
	 * Vrací true, pokud je přechod do stavu to.
	 * 
	 * @param from
	 * @return
	 */
	public boolean isTo(State to) {
		return this.to.equals(to);
	}

	public Object getIthItem(int i) throws IllegalArgumentException {
		switch (i) {
		case 0:
			return from;
		case 1:
			return over;
		case 2:
			return to;
		default:
			throw new IllegalArgumentException("Index " + i + " is invalid in "
					+ this.getClass().getName());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((over == null) ? 0 : over.hashCode());
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
		Transition other = (Transition) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (over == null) {
			if (other.over != null)
				return false;
		} else if (!over.equals(other.over))
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
		return "Transition [" + from + " ==( " + over + " )=> " + to + "]";
	}

	@SuppressWarnings("unchecked")
	public static Class<Transition> getTransitionClass() {
		// TODO a nešlo by to udělat líp? :-/
		return (Class<Transition>) new Transition(null, null, null).getClass();
	}

	@Override
	public String externalize() {
		Externisator<State> statesExt = State.EXTERNISATOR;
		Externisator<Symbol> symbolsExt = Symbol.EXTERNISATOR;

		return statesExt.externalizeKnown(from) + " - "
				+ symbolsExt.externalizeKnown(over) + " - "
				+ statesExt.externalizeKnown(to);
	}

	@Override
	public int compareTo(Transition o) {
		if (this.from == null) {
			return -1;
		}
		int cmpFrom = this.from.compareTo(o.from);
		if (cmpFrom != 0) {
			return cmpFrom;
		}

		if (this.to == null) {
			return -1;
		}
		int cmpTo = this.to.compareTo(o.to);
		if (cmpTo != 0) {
			return cmpTo;
		}

		if (this.over == null) {
			return -1;
		}
		int cmpOver = this.over.compareTo(o.over);
		if (cmpOver != 0) {
			return cmpOver;
		}

		return 0;
	}

}
