package cz.upol.fapapp.fta.mains;

import java.io.File;
import java.io.IOException;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fta.automata.FTAInputFileParser;
import cz.upol.fapapp.fta.automata.FuzzyTreeAutomata;
import cz.upol.fapapp.fta.data.AtomicTree;
import cz.upol.fapapp.fta.data.BaseTree;
import cz.upol.fapapp.fta.data.CompositeTree;

public class TreeOnFTaRunner {

	public static void main(String[] args) {
		Logger.get().setVerbose(true);
		
		String automataFile = "test/data/example-from-MorMal/fta-4.11.2.df";
		String treeFile = "";

		Degree degree = run(automataFile, treeFile);
		System.out.println(degree.getValue());
	}

	private static Degree run(String automataFilePath, String treeFilePath) {
		File automataFile = new File(automataFilePath);
		File treeFile = new File(treeFilePath);

		return run(automataFile, treeFile);
	}

	private static Degree run(File automataFile, File treeFile) {
		FTAInputFileParser automataParser = new FTAInputFileParser();
		FuzzyTreeAutomata automata;
		try {
			automata = automataParser.parse(automataFile);
		} catch (IOException e) {
			System.err.println("Cannot load automata file");
			return null;
		}

		BaseTree tree = new CompositeTree(new Symbol("A"), new AtomicTree(new Symbol("a")), new AtomicTree(new Symbol("a")), new AtomicTree(new Symbol("a"))); // TODO load from file
		return run(automata, tree);
	}

	private static Degree run(FuzzyTreeAutomata automata, BaseTree tree) {
		automata.print(System.out);
//		System.out.println(automata);
		return automata.accept(tree);
	}

}


