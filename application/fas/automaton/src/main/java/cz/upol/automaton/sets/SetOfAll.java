package cz.upol.automaton.sets;

public class SetOfAll<E> extends HugeSet<E> {

	private final Class<? extends E> clazz;

	public SetOfAll(Class<? extends E> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean isInSet(E item) {
		return item.getClass().equals(clazz);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetOfAll<?> other = (SetOfAll<?>) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		return true;
	}

}
