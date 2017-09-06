package cz.upol.fapapp.core.misc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import javafx.application.Application.Parameters;

public class AppsMainsTools {

	/**
	 * Invokes {@link #checkArgs(List, Integer, Integer, Runnable)}.
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
	 * Invokes {@link #checkArgs(List, Integer, Integer, Runnable)}.
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
	 * If actual number of args does not match expected, prints error message,
	 * help and exists with error code 2.
	 * 
	 * Returns "modified" args list.
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

		if ((minArgs != null && argsList.size() < minArgs) || (maxArgs != null && argsList.size() > maxArgs)) {
			System.err.println("Expected " + minArgs + " up to " + maxArgs + " args, given " + argsList.size() + " ("
					+ argsList + "). Printing help");
			helpPrinter.run();
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
			helpPrinter.run();
			System.exit(0);
			return true;
		} else {
			return false;
		}
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

	public static <T> T runParser(File file, TIMObjectParser<T> parser) {
		try {
			return parser.parse(file);
		} catch (IOException e) {
			Logger.get().error("Parsing of file failed: " + e.getMessage());
			System.err.println(3);
			return null;
		}
	}

	/*************************************************************************/

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

	public static int toInt(List<String> args, int index, int dflt) {
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

	public static double toDouble(List<String> args, int index, double dflt) {
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

}
