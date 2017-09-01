package cz.upol.fapapp.core.misc;

import java.io.PrintStream;

import cz.upol.fapapp.core.timfile.TIMObjectComposer;

public interface Printable {

	void print(PrintStream to);

	public static <T> void print(PrintStream to, TIMObjectComposer<T> composer, T object) {
		String string = composer.compose(object);
		to.println(string);
	}

}