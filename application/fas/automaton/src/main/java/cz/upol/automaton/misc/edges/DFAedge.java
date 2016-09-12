package cz.upol.automaton.misc.edges;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;

public class DFAedge extends AbstractEdge<Set<Transition>> {

	public DFAedge(State from, State to, Set<Transition> transitions) {
		super(from, to, transitions);
	}

	public DFAedge(State from, State to) {
		super(from, to, new TreeSet<Transition>());
	}

	public DFAedge(DFAedge edge) {
		super(edge);
	}

	@Override
	public Set<Transition> copyOfTransitions() {
		return new TreeSet<>(getTransitions());
	}

	@Override
	public String getLabel() {
		StringBuilder stb = new StringBuilder();

		for (Iterator<Transition> iterator = getTransitions().iterator(); iterator
				.hasNext();) {

			Transition transition = iterator.next();

			Symbol over = transition.getOver();

			stb.append(over.getExternisator().externalizeKnown(over));

			if (iterator.hasNext()) {
				stb.append(", ");
			}
		}

		return stb.toString();
	}

	@Override
	public boolean hasSomeTransition() {
		return !getTransitions().isEmpty();
	}

}
