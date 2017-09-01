package cz.upol.fapapp.fta.mains;

import java.io.File;
import java.io.IOException;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.fta.automata.FTATIMParser;
import cz.upol.fapapp.fta.automata.FuzzyTreeAutomata;
import cz.upol.fapapp.fta.tree.BaseTree;
import cz.upol.fapapp.fta.tree.TreeTIMParser;

public class TreeOnFTaRunner {

	public static void main(String[] args) {
		Logger.get().setVerbose(true);

		// String automataFile = "test/data/example-from-MorMal/fta-4.11.2.df";
		// String treeFile = "test/data/example-from-MorMal/tree-4.11.2.df";

		// String automataFile = "test/data/example-from-thesis/fta.df";
		// String treeFile = "test/data/example-from-thesis/tree-1.df";

		// String automataFile =
		// "test/data/m-ary-complete-trees/fta-of-3-ary.df";
		// String treeFile =
		// "test/data/m-ary-complete-trees/2-ary-notcomplete-tree.df";

		if (args.length != 2) {
			Logger.get()
					.error("Usage: " + TreeOnFTaRunner.class.getSimpleName() + " [automata_file.df] [tree_file.df]");
			System.exit(1);
		}

		String automataFile = args[0];
		String treeFile = args[1];

		Degree degree = run(automataFile, treeFile);
		System.out.println(degree.getValue());
	}

	private static Degree run(String automataFilePath, String treeFilePath) {
		File automataFile = new File(automataFilePath);
		File treeFile = new File(treeFilePath);

		return run(automataFile, treeFile);
	}

	private static Degree run(File automataFile, File treeFile) {
		FTATIMParser automataParser = new FTATIMParser();
		FuzzyTreeAutomata automata;
		try {
			automata = automataParser.parse(automataFile);
		} catch (IOException e) {
			System.err.println("Cannot load automata file");
			return null;
		}

		TreeTIMParser treeParser = new TreeTIMParser();
		BaseTree tree;
		try {
			tree = treeParser.parse(treeFile);
		} catch (IOException e) {
			System.err.println("Cannot load tree file");
			return null;
		}

		return run(automata, tree);
	}

	private static Degree run(FuzzyTreeAutomata automata, BaseTree tree) {
		// automata.print(System.out);
		// System.out.println(automata);

		return automata.accept(tree);
	}

}
