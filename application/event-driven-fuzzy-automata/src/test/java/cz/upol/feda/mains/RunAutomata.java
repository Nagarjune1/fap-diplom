package cz.upol.feda.mains;

import java.io.File;
import java.util.List;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.feda.automata.EDFATIMParser;
import cz.upol.feda.automata.EventDrivenFuzzyAutomata;
import cz.upol.feda.event.FESTIMParser;
import cz.upol.feda.event.FuzzyEventsSequence;

public class RunAutomata {

	public static void main(String[] args) {
		args = new String[]{
			"test-data/some/automaton.timf",
			"test-data/some/sequence.timf",
		};
		
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());

		String automataFileName = argsList.get(0);
		String sequenceFileName = argsList.get(1);

		State state = run(automataFileName, sequenceFileName);
		System.out.println(state);

	}

	private static State run(String automataFileName, String sequenceFileName) {
		File automataFile = new File(automataFileName);
		File sequenceFile = new File(sequenceFileName);

		return run(automataFile, sequenceFile);
	}

	private static State run(File automataFile, File sequenceFile) {
		EventDrivenFuzzyAutomata automata = AppsMainsTools.runParser(automataFile, new EDFATIMParser());
		FuzzyEventsSequence sequence = AppsMainsTools.runParser(sequenceFile, new FESTIMParser());

		if (automata == null || sequence == null) {
			return null;
		}

		return run(automata, sequence);
	}

	private static State run(EventDrivenFuzzyAutomata automata, FuzzyEventsSequence sequence) {
		return automata.run(sequence);
	}

	///////////////////////////////////////////////////////////////////////////

	private static void printHelp() {
		System.out.println("RunAutomata AUTOMATA.timf SEQUENCE.timf");
	}

}
