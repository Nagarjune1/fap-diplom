package cz.upol.automaton.ling.alphabets;

import java.util.Set;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.sets.Externisator;

public class SmallAlphabet extends TreeSet<Symbol> implements Alphabet {

	private static final long serialVersionUID = 8116769484397401068L;

	public SmallAlphabet() {
		super();
	}

	public SmallAlphabet(Set<Symbol> symbols) {
		super(symbols);
	}

	@Override
	public Externisator<Symbol> getSymbolsExternisator() {
		return Symbol.EXTERNISATOR;
	}

	@Override
	public boolean shouldBeInSet(String stringReprezentation) {
		Externisator<Symbol> externisator = getSymbolsExternisator();
		Symbol symbol = externisator.parseKnown(stringReprezentation);

		if (symbol == null) {
			return false;
		} else {
			return contains(symbol);
		}
	}

	@Override
	public String getDescription() {
		return "Alphabet with " + size() + " symbols";
	}

	@Override
	public String externalize() {
		return getDescription();
	}

	/**
	 * Neimplementováno, vyvolá výjimku. TODO FIXME co s tím?
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Spliterator<Symbol> spliterator() {
		throw new UnsupportedOperationException("HugeSet#spliterator");
	}

	@Override
	public boolean removeIf(Predicate<? super Symbol> filter) {
		return super.removeIf(filter);
	}

	@Override
	public Stream<Symbol> stream() {
		return super.stream();
	}

	@Override
	public Stream<Symbol> parallelStream() {
		return super.parallelStream();
	}

	@Override
	public void forEach(Consumer<? super Symbol> action) {
		super.forEach(action);
	}
	
	

}
