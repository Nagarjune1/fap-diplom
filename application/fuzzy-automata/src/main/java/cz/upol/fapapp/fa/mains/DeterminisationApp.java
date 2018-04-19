package cz.upol.fapapp.fa.mains;

import java.util.List;

import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.fa.automata.BaseFuzzyAutomaton;
import cz.upol.fapapp.fa.automata.FATIMComposer;
import cz.upol.fapapp.fa.automata.FATIMParser;

public class DeterminisationApp {

	public static void main(String[] args) {
		args = new String[] { //
				"--tnorm", "godel",
				"test-data/basic/nondet.timf", //
				"test-data/basic/det.timf" //
		};

		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());

		String inFile = argsList.get(0);
		String outFile = argsList.get(1);

		perform(inFile, outFile);

	}

	private static void perform(String inFile, String outFile) {
		BaseFuzzyAutomaton automaton = AppsMainsTools.runParser(inFile, new FATIMParser());

		BaseFuzzyAutomaton determinised = automaton.determinise();

		AppsMainsTools.runComposer(outFile, determinised, new FATIMComposer());
	}

	private static void printHelp() {
		System.out.println("DeterminisationApp INFILE.timf OUTFILE.timf");
		System.out.println(" where INFILE.timf is input automata file name");
		System.out.println("   and OUTFILE.timf is output automata file name");
	}

}
