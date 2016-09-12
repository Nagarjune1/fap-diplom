package cz.upol.automaton.sets;

public interface Externalisable<T extends Externalisable<T>> {
	public Externisator<T> getExternisator();
}
