package cz.upol.jfa.automata;

import cz.upol.automaton.automata.ingredients.HasAlphabet;
import cz.upol.automaton.automata.ingredients.HasBivalentDeterministicDelta;
import cz.upol.automaton.automata.ingredients.HasFuzzyDelta;
import cz.upol.automaton.automata.ingredients.HasFuzzyFiniteStates;
import cz.upol.automaton.automata.ingredients.HasFuzzyInitialStates;
import cz.upol.automaton.automata.ingredients.HasOneInitialState;
import cz.upol.automaton.automata.ingredients.IsFuzzy;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.jfa.viewer.Position;

public class AutomatonDesc {

	private AutomatonDesc() {
	}

	public static boolean hasDegreeOfTransition(BaseAutomatonToGUI automaton) {
		return automaton instanceof HasFuzzyDelta;
	}

	public static boolean hasInitialAndFiniteDegree(BaseAutomatonToGUI automaton) {
		return hasInitialDegree(automaton) && hasFiniteDegree(automaton);
	}

	public static boolean hasInitialDegree(BaseAutomatonToGUI automaton) {
		return automaton instanceof HasFuzzyInitialStates;
	}

	public static boolean hasFiniteDegree(BaseAutomatonToGUI automaton) {
		return automaton instanceof HasFuzzyFiniteStates;
	}

	public static boolean hasOnlyFiniteDegree(BaseAutomatonToGUI automaton) {
		return hasFiniteDegree(automaton);
	}

	public static Degree initialDegree(BaseAutomatonToGUI automaton, State state) {
		return ((HasFuzzyInitialStates) automaton).degreeOfInitialState(state);
	}

	public static Degree finiteDegree(BaseAutomatonToGUI automaton, State state) {
		return ((HasFuzzyFiniteStates) automaton).degreeOfFiniteState(state);
	}

	public static boolean isInitial(BaseAutomatonToGUI automaton, State state) {
		return ((HasOneInitialState) automaton).isInitial(state);
	}

	public static void moveStateTo(BaseAutomatonToGUI automaton, State state,
			Position newPosition) {
		((BaseAutomatonToGUI) automaton).getProvider().moveStateTo(state,
				newPosition);

	}

	public static Degree getDegreeOfTransition(BaseAutomatonToGUI automaton,
			Transition transition) {
		return ((HasFuzzyDelta) automaton).degreeOfTransition(transition);
	}

	public static void setInitialDegree(BaseAutomatonToGUI automaton,
			State newState, Degree initial) {
		((HasFuzzyInitialStates) automaton).setInitialState(newState, initial);
	}

	public static void setFiniteDegree(BaseAutomatonToGUI automaton,
			State newState, Degree finite) {
		((HasFuzzyFiniteStates) automaton).setFiniteState(newState, finite);
	}

	public static void setInitial(BaseAutomatonToGUI automaton, State newState,
			boolean initial) {
		((HasOneInitialState) automaton).setInitialState(newState);

	}

	public static Alphabet alphabet(BaseAutomatonToGUI automaton) {
		return ((HasAlphabet) automaton).getAlphabet();
	}

	public static ResiduedLattice residuedLattice(BaseAutomatonToGUI automaton) {
		return ((IsFuzzy) automaton).getResiduedLattice();
	}

	public static boolean hasIncidentingState(BaseAutomatonToGUI automaton,
			Transition transition) {
		return ((HasBivalentDeterministicDelta) automaton)
				.hasIncidentingState(transition);
	}

}
