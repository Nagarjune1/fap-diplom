package cz.upol.fapapp.core.misc;

import java.io.PrintStream;

import cz.upol.fapapp.core.timfile.TIMObjectComposer;

/**
 * Represents something which should be allowed to be printed. Each such object
 * must iplements {@link #print(PrintStream)}, but may call
 * {@link #print(PrintStream, TIMObjectComposer, Object)} within.
 * 
 * @author martin
 *
 */
public interface Printable {

	void print(PrintStream to);

	public static <T> void print(PrintStream to, TIMObjectComposer<T> composer, T object) {
		String string = composer.compose(object);
		to.println(string);
	}

}