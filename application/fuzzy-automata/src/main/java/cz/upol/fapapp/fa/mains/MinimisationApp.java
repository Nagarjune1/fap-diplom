package cz.upol.fapapp.fa.mains;

import java.util.List;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.fa.automata.BaseFuzzyAutomaton;
import cz.upol.fapapp.fa.automata.FATIMComposer;
import cz.upol.fapapp.fa.automata.FATIMParser;

public class MinimisationApp {

	public static void main(String[] args) {
		args = new String[] { //
				"--verbose",
				"test-data/basic/det.timf", //
				"test-data/basic/min.timf",
				 "0.1" // 
		};
		
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 3, () -> printHelp());

		String inFile = argsList.get(0);
		String outFile = argsList.get(1);
		Degree delta = AppsMainsTools.toDegree(argsList, 2);

		perform(inFile, outFile, delta);

	}

	private static void perform(String inFile, String outFile, Degree delta) {
		BaseFuzzyAutomaton automaton = AppsMainsTools.runParser(inFile, new FATIMParser());

		BaseFuzzyAutomaton minimised;

		if (delta != null) {
			minimised = automaton.minimise(delta);
		} else {
			minimised = automaton.minimise();
		}

		AppsMainsTools.runComposer(outFile, minimised, new FATIMComposer());
	}

	private static void printHelp() {
		System.out.println("DeterminisationApp INFILE.timf OUTFILE.timf [DELTA]");
		System.out.println(" where INFILE.timf is input automata file name");
		System.out.println("       INFILE.timf is output automata file name");
		System.out.println("   and DELTA is degree value");

	}

}
