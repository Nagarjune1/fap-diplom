package cz.upol.automaton.fuzzyStructs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;



public class FuzzySet<U> implements Iterable<U> {
	/**
	 * @uml.property name="residuedLattice"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final ResiduedLattice residuedLattice;
	/**
	 * @uml.property name="universe"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     elementType="cz.upol.automaton.fuzzyStructs.Tuple"
	 */
	private final Set<U> universe;
	/**
	 * @uml.property name="mapping"
	 * @uml.associationEnd 
	 *                     qualifier="item:java.lang.Object cz.upol.automaton.fuzzyresiduedLattice.Degree"
	 */
	private final Map<U, Degree> mapping;

	public FuzzySet(ResiduedLattice residuedLattice, Set<U> universe) {
		this.residuedLattice = residuedLattice;
		this.universe = universe;
		this.mapping = new HashMap<U, Degree>();
	}

	public FuzzySet(FuzzySet<U> other) {
		this.residuedLattice = other.residuedLattice;
		this.universe = other.universe;
		this.mapping = new HashMap<>(other.mapping);
	}

	/**
	 * @return
	 * @uml.property name="residuedLattice"
	 */
	public ResiduedLattice getResiduedLattice() {
		return residuedLattice;
	}

	public Set<U> getUniverse() {
		return universe;
	}

	/**
	 * Vrací stupeň v jakém se položka item nachází v množině. Pokud se v
	 * množině nenachází, vrací 0.
	 * 
	 * @param item
	 * @return
	 */
	public Degree find(U item) {
		Degree degree = mapping.get(item);

		if (degree != null) {
			return degree;
		} else {
			return residuedLattice.getZero();
		}
	}
	
	/**
	 * Vrcí true, pokud se zadaná položka v množině nachází ve stupni větším než 0.
	 * 
	 * @param item
	 * @return
	 */
	public boolean contains(U item) {
		Degree degree = mapping.get(item);

		if (degree != null) {
			return residuedLattice.getZero().isLowerOrEqual(degree);
		} else {
			return false;
		}
	}

	/**
	 * Vrací true, pokud množina obsahuje alespoň jeden prvek se stupněm větším
	 * než 0.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		for (Entry<U, Degree> entry : mapping.entrySet()) {
			if (!entry.getValue().equals(residuedLattice.getZero())) {
				return false;
			}
		}
		return true;
	}

	public Degree insert(U item) {
		return insert(item, residuedLattice.getOne());
	}

	public Degree insert(U item, Degree degree) {
		if (degree.equals(residuedLattice.getZero())) {
			return degree;
		}

		Degree oldDegree = this.mapping.get(item);

		this.mapping.put(item, degree);

		return oldDegree;
	}

	public Degree remove(U item) {
		Degree degree = this.mapping.remove(item);

		if (degree != null) {
			return degree;
		} else {
			return residuedLattice.getZero();
		}
	}

	public FuzzySet<U> join(FuzzySet<U> set2) {
		FuzzySet<U> result = new FuzzySet<U>(residuedLattice, universe);

		for (U item : universe) {
			Degree domThis = this.find(item);
			Degree domSet2 = set2.find(item);
			Degree newDom = residuedLattice.supremum(domThis, domSet2);
			result.mapping.put(item, newDom);
		}

		return result;
	}

	public FuzzySet<U> intersect(FuzzySet<U> set2) {
		FuzzySet<U> result = new FuzzySet<U>(residuedLattice, universe);

		for (U item : universe) {
			Degree domThis = this.find(item);
			Degree domSet2 = set2.find(item);
			Degree newDom = residuedLattice.imfimum(domThis, domSet2);
			result.mapping.put(item, newDom);
		}

		return result;
	}

	public Iterator<U> iterator() {
		return mapping.keySet().iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((residuedLattice == null) ? 0 : residuedLattice.hashCode());
		result = prime * result + ((mapping == null) ? 0 : mapping.hashCode());
		result = prime * result
				+ ((universe == null) ? 0 : universe.hashCode());
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
		FuzzySet<?> other = (FuzzySet<?>) obj;
		if (residuedLattice == null) {
			if (other.residuedLattice != null)
				return false;
		} else if (!residuedLattice.equals(other.residuedLattice))
			return false;
		if (mapping == null) {
			if (other.mapping != null)
				return false;
		} else if (!mapping.equals(other.mapping))
			return false;
		if (universe == null) {
			if (other.universe != null)
				return false;
		} else if (!universe.equals(other.universe))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder("FuzzySet[");
		stb.append("residuedLattice=");
		stb.append(residuedLattice);
		stb.append(", ");

		addElements(stb);

		stb.append("]");

		return stb.toString();
	}

	protected void addElements(StringBuilder stb) {
		stb.append('{');

		for (U item : universe) {
			Degree degree = find(item);
			stb.append(item);
			stb.append('/');
			stb.append(degree);
			stb.append(',');
			stb.append(' ');
		}

		stb.append('}');
	}

}
