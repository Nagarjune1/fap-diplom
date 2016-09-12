package cz.upol.automaton.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.automata.ingredients.HasStates;
import cz.upol.automaton.automata.ingredients.HasFuzzyDelta;
import cz.upol.automaton.automata.ingredients.HasFuzzyFiniteStates;
import cz.upol.automaton.automata.ingredients.HasFuzzyInitialStates;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;

public class PlainTextExporter {
	public PlainTextExporter() {
	}

	public String export(NondetermisticFuzzyAutomata automaton) {
		StringBuilder stb = new StringBuilder();

		stb.append("Let L be a residued lattice representing ");
		stb.append(automaton.getResiduedLattice()); // TODO .getDescription()
		stb.append(".\n");

		stb.append("Then we have non-deterministic LL-automaton A = < Q, Sigma, Qi, Qf, delta > where \n");

		stb.append("  Q = ");
		addSetOfStates(stb, automaton);
		stb.append("\n");

		stb.append("  Sigma = ");
		stb.append(automaton.getAlphabet().getDescription());
		stb.append("\n");

		stb.append("  Qi = ");
		addInitialStates(stb, automaton);
		stb.append("\n");

		stb.append("  Qf = ");
		addFiniteStates(stb, automaton);
		stb.append("\n");

		stb.append("  delta = ");
		addTransitions(stb, automaton);
		stb.append("\n");

		return stb.toString();
	}

	public String export(DeterministicFuzzyAutomata automaton) {
		StringBuilder stb = new StringBuilder();

		stb.append("Let L be a residued lattice representing ");
		stb.append(automaton.getResiduedLattice()); // TODO .getDescription()
		stb.append(".\n");

		stb.append("Then we have deterministic LL-automaton A = < Q, Sigma, qi, Qf, delta > where \n");

		stb.append("  Q = ");
		addSetOfStates(stb, automaton);
		stb.append("\n");

		stb.append("  Sigma = ");
		stb.append(automaton.getAlphabet().getDescription());
		stb.append("\n");

		stb.append("  qi = ");
		stb.append(automaton.getInitialState().getLabel());
		stb.append("\n");

		stb.append("  Qf = ");
		addFiniteStates(stb, automaton);
		stb.append("\n");

		stb.append("  delta = ");
		addTransitions(stb, automaton);
		stb.append("\n");

		return stb.toString();
	}

	private static void addSetOfStates(StringBuilder stb,
			HasStates automaton) {
		stb.append("{");

		for (State state : automaton.iterateOverStates()) {
			stb.append(state.getLabel());
			stb.append(", ");
		}

		stb.append("}");
	}

	private static void addInitialStates(StringBuilder stb,
			HasFuzzyInitialStates automaton) {
		stb.append("{");

		for (State state : automaton.iterateOverStates()) {
			stb.append(state.getLabel());
			stb.append("/");

			stb.append(automaton.degreeOfInitialState(state).toPrint());
			stb.append(", ");
		}

		stb.append("}");
	}

	private static void addFiniteStates(StringBuilder stb,
			HasFuzzyFiniteStates automaton) {
		stb.append("{");

		for (State state : automaton.iterateOverStates()) {
			stb.append(state.getLabel());
			stb.append("/");

			stb.append(automaton.degreeOfFiniteState(state).toPrint());
			stb.append(", ");
		}

		stb.append("}");
	}

	/*
	 * private static void addFuzzySetOfStates(StringBuilder stb, FuzzySet<?,
	 * State> states) { stb.append("{");
	 * 
	 * for (State state : states) { stb.append(state.getLabel());
	 * stb.append("/");
	 * 
	 * stb.append(states.find(state).toPrint()); stb.append(", "); }
	 * 
	 * stb.append("}"); }
	 */

	private void addTransitions(StringBuilder stb,
			DeterministicFuzzyAutomata automaton) {
		stb.append("{");

		for (Transition transition : automaton.iterateOverTransitions()) {
			stb.append("<");

			stb.append(transition.getFrom().getLabel());
			stb.append(", ");

			stb.append(transition.getOver().getValue());
			stb.append(", ");

			stb.append(transition.getTo().getLabel());
			stb.append(">");

			stb.append("; ");
		}

		stb.append("}");
	}

	private static void addTransitions(StringBuilder stb,
			HasFuzzyDelta automaton) {
		stb.append("{");

		for (Transition transition : automaton.iterateOverTransitions()) {
			stb.append("<");

			stb.append(transition.getFrom().getLabel());
			stb.append(", ");

			stb.append(transition.getOver().getValue());
			stb.append(", ");

			stb.append(transition.getTo().getLabel());
			stb.append(">/");

			stb.append(automaton.degreeOfTransition(transition).toPrint());
			stb.append("; ");
		}

		stb.append("}");
	}

	public void exportToFile(File file, NondetermisticFuzzyAutomata automaton)
			throws ExportException {

		String text = export(automaton);
		writeToFile(file, text);
	}

	public void exportToFile(File file, DeterministicFuzzyAutomata automaton)
			throws ExportException {

		String text = export(automaton);
		writeToFile(file, text);
	}

	public static void writeToFile(File file, String text)
			throws ExportException {

		Writer writer = null;
		try {
			writer = new FileWriter(file);

			writer.write(text);
		} catch (IOException e) {
			throw new ExportException("Error during plain text export", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException eIgorore) {
				}
			}
		}
	}
}
