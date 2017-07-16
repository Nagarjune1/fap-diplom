package cz.upol.fapapp.core.sets;

import java.util.Set;

import cz.upol.fapapp.core.sets.Relation.Tuple;

public class Relation<T extends Tuple> {

	protected final Set<T> tuples;

	public Relation(Set<T> tuples) {
		super();
		this.tuples = tuples;
	}

	public Set<T> getTuples() {
		return tuples;
	}

	public T getOne() {
		return tuples.stream().findAny().get();
	}

	@Override
	public String toString() {
		return "Relation:" + tuples + "";
	}

	public static abstract class Tuple {
		
		
	}

}
