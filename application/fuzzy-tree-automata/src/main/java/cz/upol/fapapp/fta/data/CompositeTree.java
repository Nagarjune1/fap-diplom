package cz.upol.fapapp.fta.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.upol.fapapp.core.ling.Symbol;

public class CompositeTree extends BaseTree {
	private final List<BaseTree> children;

	public CompositeTree(Symbol inner, List<BaseTree> children) {
		super(inner);
		this.children = children;
	}

	public CompositeTree(Symbol inner, BaseTree... children) {
		super(inner);
		this.children = new ArrayList<>(Arrays.asList(children));
	}

	public List<BaseTree> getChildren() {
		return children;
	}

	public int getChildCount() {
		return children.size();
	}

	public BaseTree child(int i) {
		return children.get(i);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeTree other = (CompositeTree) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CompositeTree [inner=" + getLabel() + ", children=" + children + "]";
	}

}
