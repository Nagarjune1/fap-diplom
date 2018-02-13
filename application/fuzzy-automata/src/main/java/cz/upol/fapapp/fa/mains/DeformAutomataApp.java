package cz.upol.fapapp.fa.mains;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.FBRTIMObjectParser;
import cz.upol.fapapp.core.fuzzy.FuzzySet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.FSTIMObjectParser;
import cz.upol.fapapp.core.sets.FuzzyBinaryRelation;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;
import cz.upol.fapapp.fa.automata.FATIMComposer;
import cz.upol.fapapp.fa.automata.FATIMParser;
import cz.upol.fapapp.fa.automata.FuzzyAutomata;
import cz.upol.fapapp.fa.modifs.AutomataDeformer;

public class DeformAutomataApp {

	private static final String RELATION_FILE_TYPE = "symbols similarity relation";
	private static final String SET_FILE_TYPE = "insertion/deletions set";
	private static final Integer PRECISION = 5;

	public static void main(String[] args) {
		// args = new String[] { "--verbose", "test-data/basic/automata1.timf",
		// "test-data/basic/automata1-deformed.timf", //
		// "replace", "relation", "test-data/basic/relation-abc-1.timf", //
		// "insert", "set", "test-data/basic/set-abc-1.timf" //
		// };

		List<String> argsList = AppsMainsTools.checkArgs(args, 2, null, () -> printHelp());
		if (argsList == null) {
			System.exit(1);
		}

		String inputAutomataFileName = argsList.get(0);
		String outputAutomataFileName = argsList.get(1);
		List<String> deforms = argsList.subList(2, argsList.size());

		deform(inputAutomataFileName, outputAutomataFileName, deforms);
	}

	private static void deform(String inputAutomataFileName, String outputAutomataFileName, List<String> deforms) {
		File inputAutomataFile = new File(inputAutomataFileName);
		File outputAutomataFile = new File(outputAutomataFileName);

		deform(inputAutomataFile, outputAutomataFile, deforms);
	}

	private static void deform(File inputAutomataFile, File outputAutomataFile, List<String> deforms) {
		FATIMParser automataParser = new FATIMParser();
		FuzzyAutomata inputAutomata;
		try {
			inputAutomata = (FuzzyAutomata) automataParser.parse(inputAutomataFile);
		} catch (IOException e) {
			Logger.get().error("Cannot load input automata: " + e);
			return;
		}

		FuzzyAutomata outputAutomata = deform(inputAutomata, deforms);

		FATIMComposer automataComposer = new FATIMComposer();
		try {
			automataComposer.compose(outputAutomata, outputAutomataFile);
		} catch (IOException e) {
			Logger.get().error("Cannot save output automata: " + e);
			return;
		}
	}

	private static FuzzyAutomata deform(FuzzyAutomata inputAutomata, List<String> deforms) {
		if (deforms.size() % 3 != 0) {
			Logger.get().warning("It seems that " + (deforms.size() % 3) + " args are extra, doing nothing");
			return inputAutomata;
		}

		Iterator<String> deformsIters = deforms.iterator();
		AutomataDeformer deformer = new AutomataDeformer(inputAutomata);
		while (deformsIters.hasNext()) {
			String what = deformsIters.next();
			String how = deformsIters.next();
			String dataSpec = deformsIters.next();

			performDeform(deformer, what, how, dataSpec);
		}

		return deformer.getAutomata(PRECISION);
	}

	@SuppressWarnings("unchecked")
	private static void performDeform(AutomataDeformer deformer, String what, String how, String dataSpec) {

		Object data = inferData(what, how, dataSpec);
		if (data == null) {
			return;
		}

		switch (what) {
		case "replace":
			if (data instanceof Degree) {
				deformer.addReplace((Degree) data);
			}
			if (data instanceof FuzzyBinaryRelation) {
				deformer.addReplace((FuzzyBinaryRelation<Symbol, Symbol>) data);
			}
			break;
		case "insert":
		case "insert-more":
			if (data instanceof Degree) {
				deformer.addInsertMore((Degree) data);
			}
			if (data instanceof FuzzyBinaryRelation) {
				deformer.addInsertMore((FuzzySet<Symbol>) data);
			}
			break;
		case "insert-one":
			if (data instanceof Degree) {
				deformer.addInsertOne((Degree) data);
			}
			if (data instanceof FuzzyBinaryRelation) {
				deformer.addInsertOne((FuzzySet<Symbol>) data);
			}
			break;
		case "delete":
		case "remove":
			if (data instanceof Degree) {
				deformer.addRemoveOne((Degree) data);
			}
			if (data instanceof FuzzyBinaryRelation) {
				deformer.addRemoveOne((FuzzySet<Symbol>) data);
			}
			break;
		default:
			Logger.get().warning("Unknown deformation: " + what);
		}
	}

	private static Object inferData(String what, String how, String dataSpec) {
		switch (how) {
		case "degree":
			return TIMObjectParserComposerTools.parseDegree(dataSpec);
		case "relation":
			if (!"replace".equals(what)) {
				Logger.get().warning("'relation' must be used with 'replace' only");
				return null;
			}

			return loadRelationFromFile(dataSpec);

		case "set":
			if (!("insert".equals(what) || "delete".equals(what))) {
				Logger.get().warning("'set' must be used with 'insert' or 'delete' only");
				return null;
			}

			return loadSetFromFtile(dataSpec);
		default:
			Logger.get().warning("Unknown type: " + how);
			return null;
		}
	}

	private static FuzzyBinaryRelation<Symbol, Symbol> loadRelationFromFile(String dataSpec) {
		File relationFile = new File(dataSpec);

		FBRTIMObjectParser<Symbol, Symbol> parser = new FBRTIMObjectParser<>(RELATION_FILE_TYPE, //
				(s) -> new Symbol(s), //
				(s) -> new Symbol(s));

		try {
			return parser.parse(relationFile);
		} catch (IOException e) {
			Logger.get().error("Cannot load relation: " + e);
			return null;
		}
	}

	private static FuzzySet<Symbol> loadSetFromFtile(String dataSpec) {
		File setFile = new File(dataSpec);

		FSTIMObjectParser<Symbol> parser = new FSTIMObjectParser<>(SET_FILE_TYPE, //
				(s) -> new Symbol(s));

		try {
			return parser.parse(setFile);
		} catch (IOException e) {
			Logger.get().error("Cannot load set: " + e);
			return null;
		}
	}

	private static void printHelp() {
		System.out.println("DeformAutomata");
		System.out.println("Usage: DeformAutomata input-automata.timf output-automata.timf [<DEFORMATION_SPEC> ...]");
		System.out.println(
				"where <DEFORMATION_SPEC> = replace|insert-one|insert-more|delete degree|relation|set VALUE|FILE");
		System.out.println(" and VALUE is degree, and FILE file with fuzzy relation/fuzzy set");
	}

}
