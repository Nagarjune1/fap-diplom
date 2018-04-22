package cz.upol.feda.mains;

import java.util.List;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.feda.automata.EDFATIMParser;
import cz.upol.feda.automata.EventDrivenFuzzyAutomaton;
import cz.upol.feda.event.FESTIMParser;
import cz.upol.feda.event.FuzzyEventsSequence;

public class RunAutomaton {

	public static void main(String[] args) {
//		args = new String[]{
//			"test-data/some/automaton.timf",
//			"test-data/some/sequence.timf",
//		};
		
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());

		String automatonFileName = argsList.get(0);
		String sequenceFileName = argsList.get(1);

		State state = run(automatonFileName, sequenceFileName);
		System.out.println(state);

	}


	private static State run(String automatonFile, String sequenceFile) {
		EventDrivenFuzzyAutomaton automaton = AppsMainsTools.runParser(automatonFile, new EDFATIMParser());
		FuzzyEventsSequence sequence = AppsMainsTools.runParser(sequenceFile, new FESTIMParser());

		if (automaton == null || sequence == null) {
			return null;
		}

		return run(automaton, sequence);
	}

	private static State run(EventDrivenFuzzyAutomaton automaton, FuzzyEventsSequence sequence) {
		Logger.get().info("Running automaton over sequence of " + sequence.getEvents().size() + " events");
		return automaton.run(sequence);
	}

	///////////////////////////////////////////////////////////////////////////

	private static void printHelp() {
		System.out.println("RunAutomaton AUTOMATA.timf SEQUENCE.timf");
	}

}
