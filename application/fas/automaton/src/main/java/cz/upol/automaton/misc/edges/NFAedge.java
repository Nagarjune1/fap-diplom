package cz.upol.automaton.misc.edges;

import java.util.Iterator;
import java.util.Set;

import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.fuzzyStructs.FuzzySet;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.sets.SetOfAll;

/**
 * Pomocná třída reprezentující hranu v diagramu automatu, tedy množinu přechodů
 * mezi dvojící stavů.
 * 
 * @author martin
 * 
 * @param
 */
public class NFAedge extends AbstractEdge<FuzzySet<Transition>> {

	public NFAedge(State from, State to, FuzzySet<Transition> transitions) {
		super(from, to, transitions);
	}

	public NFAedge(State from, State to, NondetermisticFuzzyAutomata automaton) {
		super(from, to, initializeTransitions(automaton));
	}

	public NFAedge(NFAedge edge) {
		super(edge);
	}

	/**
	 * 
	 * @param automaton
	 * @return
	 */
	private static FuzzySet<Transition> initializeTransitions(
			NondetermisticFuzzyAutomata automaton) {

		if (automaton == null) {
			System.err.println("Warning: vytváření hrany bez automatu");
			return new FuzzySet<>(new LukasiewiczLogic(), new SetOfAll<>(
					Transition.class));
		} else {
			return new FuzzySet<Transition>(automaton.getResiduedLattice(),
					automaton.getDelta().getUniverse());
		}
	}

	@Override
	public FuzzySet<Transition> copyOfTransitions() {
		ResiduedLattice residuedLattice = getTransitions().getResiduedLattice();
		Set<Transition> universe = getTransitions().getUniverse();

		return new FuzzySet<Transition>(residuedLattice, universe);
	}

	@Override
	public boolean hasSomeTransition() {
		return !getTransitions().isEmpty();
	}

	@Override
	public String getLabel() {
		StringBuilder stb = new StringBuilder();

		for (Iterator<Transition> iterator = getTransitions().iterator(); iterator
				.hasNext();) {

			Transition transition = iterator.next();

			Symbol over = transition.getOver();
			Degree degree = getTransitions().find(transition);

			stb.append(over.getExternisator().externalizeKnown(over));
			stb.append("/");
			stb.append(degree.getExternisator().externalizeKnown(degree));

			if (iterator.hasNext()) {
				stb.append(", ");
			}
		}

		return stb.toString();
	}

}
