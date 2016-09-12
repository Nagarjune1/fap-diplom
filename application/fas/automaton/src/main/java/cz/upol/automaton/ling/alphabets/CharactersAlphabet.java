package cz.upol.automaton.ling.alphabets;

import java.util.Set;
import java.util.TreeSet;

import cz.upol.automaton.ling.Symbol;

public class CharactersAlphabet extends SmallAlphabet {

	private static final long serialVersionUID = 1874396355980084269L;

	/**
	 * @uml.property  name="lowercase"
	 */
	private final boolean lowercase;
	/**
	 * @uml.property  name="uppercase"
	 */
	private final boolean uppercase;
	/**
	 * @uml.property  name="numbers"
	 */
	private final boolean numbers;
	/**
	 * @uml.property  name="othersChars" multiplicity="(0 -1)" dimension="1"
	 */
	private final char[] othersChars;

	public CharactersAlphabet(boolean lowercase, boolean uppercase,
			boolean numbers, char... othersChars) {

		super(initialize(lowercase, uppercase, numbers, othersChars));

		this.lowercase = lowercase;
		this.uppercase = uppercase;
		this.numbers = numbers;
		this.othersChars = othersChars;
	}

	protected static int calculateSize(boolean lowercase, boolean uppercase,
			boolean numbers, char... othersChars) {

		int size = 0;

		if (lowercase) {
			size += 'z' - 'a' + 1;
		}

		if (uppercase) {
			size += 'Z' - 'A' + 1;
		}

		if (numbers) {
			size += '9' - '0' + 1;
		}

		size += othersChars.length;
		return size;
	}

	protected static Set<Symbol> initialize(boolean lowercase,
			boolean uppercase, boolean numbers, char... othersChars) {

		Set<Symbol> symbols = new TreeSet<Symbol>();

		if (lowercase) {
			for (char c = 'a'; c <= 'z'; c++) {
				symbols.add(new Symbol(c));
			}
		}

		if (uppercase) {
			for (char c = 'A'; c <= 'Z'; c++) {
				symbols.add(new Symbol(c));
			}
		}

		if (numbers) {
			for (char c = '0'; c <= '9'; c++) {
				symbols.add(new Symbol(c));
			}
		}

		for (char c : othersChars) {
			symbols.add(new Symbol(c));
		}

		return symbols;
	}

	private void addContent(StringBuilder stb) {
		if (lowercase) {
			stb.append("lowercases, ");
		}
		if (uppercase) {
			stb.append("uppercases, ");
		}
		if (numbers) {
			stb.append("number digits, ");
		}

		for (char c : othersChars) {
			stb.append(c);
			stb.append(", ");
		}
	}

	@Override
	public String getDescription() {
		StringBuilder stb = new StringBuilder("Alphabet with ");

		addContent(stb);

		return stb.toString();
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder("Alphabet ");
		stb.append("[");

		addContent(stb);

		stb.append("]");
		return stb.toString();
	}

}
