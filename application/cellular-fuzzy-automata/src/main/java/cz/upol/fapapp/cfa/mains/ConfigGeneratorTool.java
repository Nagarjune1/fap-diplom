package cz.upol.fapapp.cfa.mains;

import java.io.File;
import java.util.List;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.comp.CFACompTIMComposer;
import cz.upol.fapapp.cfa.comp.CFAConfTIMComposer;
import cz.upol.fapapp.cfa.comp.CellularAutomataComputation;
import cz.upol.fapapp.cfa.comp.CommonConfiguration;
import cz.upol.fapapp.cfa.comp.ConfigGenerator;
import cz.upol.fapapp.core.misc.AppsMainsTools;

public class ConfigGeneratorTool {

	public static void main(String[] args) {
		List<String> argsList = AppsMainsTools.checkArgs(args, 3, 4, () -> printHelp());
		if (argsList == null) {
			return;
		}

		String fileStr = argsList.get(0);
		String sizeStr = argsList.get(1);
		String typeStr = argsList.get(2);
		String genStr = (argsList.size() > 3 ? argsList.get(3) : null);

		File file = new File(fileStr);
		int size = Integer.parseInt(sizeStr);
		Integer gen = (genStr != null ? Integer.parseInt(genStr) : null);

		generate(file, size, typeStr, gen);

	}

	private static void generate(File file, int size, String typeSpec, Integer generationOrNot) {
		CommonConfiguration configuration = generateConfig(size, typeSpec);

		if (generationOrNot == null) {
			CFAConfTIMComposer composer = new CFAConfTIMComposer("cells");
			AppsMainsTools.runComposer(file, configuration, composer);

		} else {
			CellularAutomataComputation computation = new CellularAutomataComputation(null, configuration);
			CFACompTIMComposer composer = new CFACompTIMComposer();

			AppsMainsTools.runComposer(file, computation, composer);
		}
	}

	private static CommonConfiguration generateConfig(int size, String typeSpec) {
		ConfigGenerator generator = new ConfigGenerator();
		int seed = (int) System.currentTimeMillis();

		switch (typeSpec) {
		case "zeros":
			return generator.generateFilled(size, new CellState(0.0));
		case "ones":
			return generator.generateFilled(size, new CellState(1.0));
		case "bival":
		case "bivalent":
		case "bival-1/2":
		case "bivalent-1/2":
			return generator.generateBival(size, seed, 0.5);
		case "bival-3/4":
		case "bivalent-3/4":
			return generator.generateBival(size, seed, 0.75);
		case "bival-1/4":
		case "bivalent-1/4":
			return generator.generateBival(size, seed, 0.25);
		case "random":
			return generator.generateDoubles(size, seed);
		default:
			throw new IllegalArgumentException("Invalid type spec (see help): " + typeSpec);
		}
	}

	private static void printHelp() {
		System.out.println("Config generator");
		System.out.println("Usage:");
		System.out.println("	ConfigGeneratorTool FILE.timf SIZE TYPE [GENERATION]");
		System.out.println("		where TYPE one of zeros, ones, bival, bival-3/4, bival-1/4 or random");
		System.out.println("		if GENERATION is specified, generates computation file");
		System.out.println("		if not generates configuration file");
	}

}
