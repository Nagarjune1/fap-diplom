package cz.upol.fapapp.fa.typos;

import cz.upol.fapapp.core.ling.Symbol;

/**
 * Default QWERTZ keymap, each key has as its neighbor keys which has at least a
 * piece of common edge at my Lenovo notebook ;-).
 * 
 * @author martin
 *
 */
public class DefaultKeymap extends KeyboardMap {

	public DefaultKeymap() {
		initialize();
	}

	private void initialize() {
		add('q', 'a', 'w');
		add('w', 'q', 'a', 's', 'e');
		add('e', 'w', 's', 'd', 'r');
		add('r', 'e', 'd', 'f', 't');
		add('t', 'r', 'f', 'g', 'z');
		add('z', 't', 'g', 'h', 'u');
		add('u', 'z', 'h', 'j', 'i');
		add('i', 'u', 'j', 'k', 'o');
		add('o', 'i', 'k', 'l', 'p');
		add('p', 'o', 'l');
		//
		add('a', 'q', 'w', 's', 'y');
		add('s', 'a', 'w', 'e', 'd', 'x', 'y');
		add('d', 's', 'e', 'r', 'f', 'c', 'x');
		add('f', 'd', 'r', 't', 'g', 'v', 'c');
		add('g', 'f', 't', 'z', 'h', 'b', 'v');
		add('h', 'g', 'z', 'u', 'j', 'n', 'b');
		add('j', 'h', 'u', 'i', 'k', 'm', 'n');
		add('k', 'j', 'i', 'o', 'l', 'm');
		add('l', 'k', 'o', 'p');
		//
		add('y', 'a', 's', 'x');
		add('x', 'y', 's', 'd', 'c');
		add('c', 'x', 'd', 'f', 'v');
		add('v', 'c', 'f', 'g', 'b');
		add('b', 'v', 'g', 'h', 'n');
		add('n', 'b', 'h', 'j', 'm');
		add('m', 'n', 'j', 'k');
	}

	private void add(char to, char... neighs) {
		for (char neigh : neighs) {
			Symbol toSymbol = new Symbol(Character.toString(to));
			Symbol neigSymbol = new Symbol(Character.toString(neigh));

			addNeighbor(toSymbol, neigSymbol);
		}
	}
}
