package cz.upol.fapapp.cfa.mains;

import java.util.List;

import cz.upol.fapapp.cfa.conf.CFAConfTIMComposer;
import cz.upol.fapapp.cfa.conf.CFAConfTIMParser;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.misc.ConfigNoiser;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;

/**
 * Application performing the noising.
 * 
 * @author martin
 *
 */
public class ConfigNoiserTool {

	private static final double DEFAULT_RATIO = 0.5;
	private static final int DEFAULT_SEED = (int) System.currentTimeMillis();
	private static final double DEFAULT_WHITES_RATIO = 0.5;

	public static void main(String[] args) {
		// args = new String[] { "--verbose", "data/test/configs/lenna.timf",
		// "data/test/configs/lenna-sp-noise-0.5.timf",
		// "salt-and-pepper", "1.0" }; 

		List<String> argsList = AppsMainsTools.checkArgs(args, 3, 5, () -> printHelp());
		if (argsList == null) {
			return;
		}

		String infile = argsList.get(0);
		String outfile = argsList.get(1);
		String typeStr = argsList.get(2);
		double ratio = AppsMainsTools.toDouble(argsList, 3, DEFAULT_RATIO);
		int seed = AppsMainsTools.toInt(argsList, 4, DEFAULT_SEED);


		genenerateByFiles(infile, outfile, typeStr, ratio, seed);
	}

	private static void genenerateByFiles(String infile, String outfile, String type, double ratio, int seed) {
		
		CFAConfiguration config = AppsMainsTools.runParser(infile, new CFAConfTIMParser());
		if (config == null) {
			return;
		}
		
		generate(config, type, ratio, seed);

		AppsMainsTools.runComposer(outfile, config, new CFAConfTIMComposer());
	}

	private static void generate(CFAConfiguration config, String type, double ratio, int seed) {
		Logger.get().info("Generating noise of type " + type);
		
		ConfigNoiser noiser = new ConfigNoiser();

		switch (type) {
		case "salt and pepper":
		case "salt-and-pepper":
			noiser.addNoiseSaltAndPepper(config, seed, ratio, DEFAULT_WHITES_RATIO);
			return;
		case "impulse":
			noiser.addImpulseNoise(config, seed, ratio);
			return;
		default:
			throw new IllegalArgumentException("Unknown noise type specifier: " + type);
		}
	}

	private static void printHelp() {
		System.out.println("Config noiser tool");
		System.out.println("Usage: ");
		System.out.println("	ConfigNoiserTool INFILE.timf OTFILE.timf TYPE [RATIO] [SEED]");
		System.out.println("	where TYPE can be salt-and-pepper or impulse");
		System.out.println("	if no RATIO is specified is used " + DEFAULT_RATIO);
		System.out.println("	if no SEED is specified is used current timestamp");
	}

}
