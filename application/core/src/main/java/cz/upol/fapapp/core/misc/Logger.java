package cz.upol.fapapp.core.misc;

import java.io.PrintStream;

/**
 * Performs simple logging, singleton (use {@link #get()}).
 * 
 * @author martin
 *
 */
public class Logger {
	private static final Logger INSTANCE = new Logger(false);

	public static final PrintStream META_STREAM = System.err;

	private boolean verbose;

	private Logger(boolean verbose) {
		super();
		this.verbose = verbose;
	}

	public static Logger get() {
		return INSTANCE;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	//////////////////////////////////////////////////////////////////

	public void moreinfo(String text) {
		if (verbose) {
			System.err.println(text);
		}
	}

	public void output(String text) {
		System.out.println(text);
	}

	public void warning(String text) {
		System.err.println("Warning: " + text);
	}

	public void error(String text) {
		System.err.println("Error: " + text);
	}
}
