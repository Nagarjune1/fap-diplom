package cz.upol.fapapp.core.misc;

import java.io.PrintStream;

/**
 * Performs simple logging, singleton (use {@link #get()}).
 * 
 * @author martin
 *
 */
public class Logger {
	private static final Logger INSTANCE = new Logger(false, false);

	public static final PrintStream META_STREAM = System.err;

	private boolean verbose;
	private boolean debug;

	private Logger(boolean verbose, boolean debug) {
		super();
		this.verbose = verbose;
		this.debug = debug;
	}

	public static Logger get() {
		return INSTANCE;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	//////////////////////////////////////////////////////////////////

	/**
	 * Print debug message. Use to print some detailed data.
	 * 
	 * @param text
	 */
	public void debug(String text) {
		if (debug) {
			System.err.println(text);
		}
	}

	/**
	 * Print information. Use to print what's going on.
	 * 
	 * @param text
	 */
	public void info(String text) {
		if (verbose) {
			System.err.println(text);
		}
	}

	/**
	 * Print warining. Use to report something which is not corret, but also not
	 * critical.
	 * 
	 * @param text
	 */
	public void warning(String text) {
		System.err.println("Warning: " + text);
	}

	/**
	 * Print error. Use when something bad happens.
	 * 
	 * @param text
	 */
	public void error(String text) {
		System.err.println("Error: " + text);
	}
}
