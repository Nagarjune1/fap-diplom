package cz.upol.fapapp.fa.typos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;

public class KeyboardMap {
	private final Map<Symbol, Set<Symbol>> neighborhood;

	public KeyboardMap() {
		neighborhood = new HashMap<>();
	}

	protected void addNeighbor(Symbol to, Symbol who) {
		Set<Symbol> neighs = neighborhood.get(to);

		if (neighs == null) {
			neighs = new HashSet<>();
			neighborhood.put(to, neighs);
		}

		neighs.add(who);
	}

	public Set<Symbol> listKeys() {
		return neighborhood.keySet();
	}

	public Set<Symbol> neighborsOf(Symbol of) {
		return neighborhood.get(of);
	}

	public boolean isNeighbor(Symbol of, Symbol who) {
		return neighborhood.get(of).contains(who);
	}

	public FuzzyBinaryRelation<Symbol, Symbol> toFuzzyRelation(Degree similartyDegree) {
		Map<Couple<Symbol, Symbol>, Degree> map = new HashMap<>();

		for (Symbol from : listKeys()) {
			for (Symbol to : listKeys()) {
				boolean isSame = from.equals(to);
				boolean isNeigh = isNeighbor(from, to);

				Degree degree;
				if (isSame) {
					degree = Degree.ONE;
				} else if (isNeigh) {
					degree = similartyDegree;
				} else {
					degree = Degree.ZERO;
				}

				Couple<Symbol, Symbol> couple = new Couple<>(from, to);
				map.put(couple, degree);
			}
		}

		return new FuzzyBinaryRelation<>(map);
	}

	@Override
	public String toString() {
		return "KeyboardMap [neighborhood=" + neighborhood + "]";
	}
}
