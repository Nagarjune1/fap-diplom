package cz.upol.fapapp.core.misc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.tnorm.BaseTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.GodelTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.LukasiewiczTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.ProductTNorm;
import cz.upol.fapapp.core.fuzzy.tnorm.TNorms;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import javafx.application.Application.Parameters;

/**
 * Some utilities to be fun within the console app.
 * 
 * @author martin
 *
 */
// TODO write test?
public class AppsMainsTools {

	/**
	 * Does the same as {@link #checkArgs(List, Integer, Integer, Runnable)}.
	 * 
	 * @param args
	 * @param minArgs
	 * @param maxArgs
	 * @param helpPrinter
	 * @return
	 */
	public static List<String> checkArgs(String[] args, Integer minArgs, Integer maxArgs, Runnable helpPrinter) {
		List<String> argsList = new ArrayList<>(Arrays.asList(args));
		return checkArgs(argsList, minArgs, maxArgs, helpPrinter);
	}

	/**
	 * Does the same as {@link #checkArgs(List, Integer, Integer, Runnable)}.
	 * 
	 * @param params
	 * @param minArgs
	 * @param maxArgs
	 * @param helpPrinter
	 * @return
	 */
	public static List<String> checkArgs(Parameters params, Integer minArgs, Integer maxArgs, Runnable helpPrinter) {
		List<String> argsList = new ArrayList<>(params.getRaw());
		return checkArgs(argsList, minArgs, maxArgs, helpPrinter);
	}

	/**
	 * Checks args.
	 * 
	 * If is help specifier ({@code -h} or {@code --help}), prints help and
	 * exits with 0.
	 * 
	 * If verbocity specifier is provided ({@code -v} or {@code --verbose}),
	 * sets the {@link Logger}'s flag.
	 * 
	 * I tnorm specifier is provided ({@code -t} or {@code --tnorm} SPEC), sets
	 * the tnorm ({@link TNorms}) to specified one.
	 * 
	 * Finally, check args count. If actual number of args does not match
	 * expected, prints error message, help and exists with error code 2.
	 * 
	 * Returns "modified" (without the flags) args list.
	 * 
	 * @param args
	 * @param minArgs
	 * @param maxArgs
	 * @param helpPrinter
	 * @return
	 */
	public static List<String> checkArgs(List<String> argsList, Integer minArgs, Integer maxArgs,
			Runnable helpPrinter) {

		if (checkHelp(argsList, helpPrinter)) {
			return null;
		}

		checkVerbocity(argsList);
		checkTNorm(argsList);

		if ((minArgs != null && argsList.size() < minArgs) || (maxArgs != null && argsList.size() > maxArgs)) {
			System.err.println("Expected " + minArgs + " up to " + maxArgs + " args, given " + argsList.size() + " ("
					+ argsList + "). Printing help");
			printHelp(helpPrinter);
			System.exit(2);
			return null;
		}

		return argsList;
	}

	/**
	 * If args are {@code -h} or {@code --help}, prints help, exists with 0 and
	 * returns true. Else returns false.
	 * 
	 * @param argsList
	 * @param helpPrinter
	 * @return
	 */
	private static boolean checkHelp(List<String> argsList, Runnable helpPrinter) {
		if (argsList.size() == 1 && ("--help".equals(argsList.get(0)) || "-h".equals(argsList.get(0)))) {

			printHelp(helpPrinter);
			System.exit(0);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Performs help print.
	 * 
	 * @param helpPrinter
	 */
	private static void printHelp(Runnable helpPrinter) {
		System.out.println("General usage: PROGRAM FLAGS PROGRAM_ARGS");
		helpPrinter.run();
		System.out.println("FLAGS: [-v|--verbose] [-t|--tnorm <TNORM>]");
		System.out.println("	where <TNROM> is Godel|product|Lukasiewicz");

	}

	/**
	 * If first arg is {@code -v} or {@code --verbose}, sets verbocity of
	 * {@link Logger} to true and removes flag from args.
	 * 
	 * @param argsList
	 */
	private static void checkVerbocity(List<String> argsList) {
		if (argsList.size() >= 1 && ("--verbose".equals(argsList.get(0)) || "-v".equals(argsList.get(0)))) {
			Logger.get().setVerbose(true);
			argsList.remove(0);
		}
	}

	/**
	 * If first arg is {@code -t} or {@code --tnorm}, parses next one. If
	 * succeeds, sets appropiate instance into {@link TNorms}.
	 * 
	 * @param argsList
	 */
	private static void checkTNorm(List<String> argsList) {
		if (argsList.size() >= 1 && ("--tnorm".equals(argsList.get(0)) || "-t".equals(argsList.get(0)))) {
			argsList.remove(0);

			if (argsList.isEmpty()) {
				Logger.get().warning("Missing t-norm spec");
				return;
			}

			BaseTNorm tnorm = parseTnorm(argsList.get(0));
			if (tnorm == null) {
				Logger.get().warning("Invalid t-norm spec, see help");
				return;
			}

			argsList.remove(0);

			TNorms.setTnorm(tnorm);
			Logger.get().moreinfo("Will use " + tnorm.getClass().getSimpleName());
		}
	}

	/**
	 * Parses spec to {@link BaseTNorm} instance, or returns null if unknown.
	 * 
	 * @param spec
	 * @return
	 */
	private static BaseTNorm parseTnorm(String spec) {
		switch (spec) {
		case "minimum":
		case "Godel":
		case "godel":
			return new GodelTNorm();
		case "product":
			return new ProductTNorm();
		case "Lukasiewicz":
		case "lukasiewicz":
			return new LukasiewiczTNorm();
		default:
			Logger.get().warning("Unknown tnorm specifier: " + spec + ", ignoring");
			return null;
		}
	}

	/**
	 * Using given composer composes given object into given file. Prints quite
	 * pretty error message and exits with 3 if failed. Returns whether or not
	 * suceeded.
	 * 
	 * @param file
	 * @param object
	 * @param composer
	 * @return
	 */
	public static <T> boolean runComposer(File file, T object, TIMObjectComposer<T> composer) {
		try {
			composer.compose(object, file);
			return true;
		} catch (IOException e) {
			Logger.get().error("Composition into file failed: " + e.getMessage());
			System.err.println(3);
			return false;
		}
	}

	/**
	 * Using given parser composes given object into given file. Prints quite
	 * pretty error message and exits with 4 if failed. Returns the parsed file
	 * or null if failed.
	 * 
	 * @param file
	 * @param parser
	 * @deprecated
	 * @see #runParser(String, FATIMParser)
	 * @return
	 */
	@Deprecated
	public static <T> T runParser(File file, TIMObjectParser<T> parser) {
		try {
			return parser.parse(file);
		} catch (IOException e) {
			Logger.get().error("Parsing of file failed: " + e.getMessage());
			System.err.println(4);
			return null;
		}
	}

	public static <T> T runParser(String file, TIMObjectParser<T> parser) {
		return runParser(new File(file), parser);
	}

	public static <T> boolean runComposer(String file, T object, TIMObjectComposer<T> composer) {
		return runComposer(new File(file), object, composer);
	}

	/*************************************************************************/

	/**
	 * Item of args at given index converts to int. Throws exception if fails.
	 * 
	 * @param args
	 * @param index
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static int toInt(List<String> args, int index) throws IllegalArgumentException {
		String value = null;
		try {
			value = args.get(index);
			return Integer.parseInt(value);

		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Missing argument at index " + index, e);

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Not int " + value + " at index " + index, e);
		}
	}

	/**
	 * Item of args at given index converts to int. If not specified, returns
	 * dflt, if fails (invalid number) throws.
	 * 
	 * @param args
	 * @param index
	 * @param dflt
	 * @return
	 */
	public static int toInt(List<String> args, int index, int dflt) throws IllegalArgumentException {
		String value = null;
		try {
			value = args.get(index);
			return Integer.parseInt(value);

		} catch (IndexOutOfBoundsException e) {
			return dflt;

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Not int " + value + " at index " + index, e);
		}
	}

	/**
	 * Item of args at given index converts to double. If not specified, returns
	 * dflt, if fails (invalid number) throws.
	 * 
	 * @param args
	 * @param index
	 * @param dflt
	 * @return
	 */
	public static Double toDouble(List<String> args, int index, Double dflt) throws IllegalArgumentException {
		String value = null;
		try {
			value = args.get(index);
			return Double.parseDouble(value);

		} catch (IndexOutOfBoundsException e) {
			return dflt;

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Not double " + value + " at index " + index, e);
		}
	}

	public static Degree toDegree(List<String> args, int index) {
		Double val = toDouble(args, index, null);
		if (val != null) {
			return new Degree(val);
		} else {
			return null;
		}
	}

}
