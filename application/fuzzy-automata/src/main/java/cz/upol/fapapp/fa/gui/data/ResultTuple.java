package cz.upol.fapapp.fa.gui.data;

import cz.upol.fapapp.core.fuzzy.Degree;

@Deprecated
public class ResultTuple {
	private final String name;
	private final Degree degree;
	
	public ResultTuple(String name, Degree degree) {
		super();
		this.name = name;
		this.degree = degree;
	}
	public String getName() {
		return name;
	}
	public Degree getDegree() {
		return degree;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ResultTuple other = (ResultTuple) obj;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ResultTuple [name=" + name + ", degree=" + degree + "]";
	}

}
