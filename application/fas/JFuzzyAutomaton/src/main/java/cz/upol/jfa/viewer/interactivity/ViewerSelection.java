package cz.upol.jfa.viewer.interactivity;

import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.automaton.misc.edges.NFAedge;

public class ViewerSelection {
	private State selectedState;
	private AbstractEdge<?> selectedEdge;

	public ViewerSelection() {
	}

	public ViewerSelection(State selectedState) {
		super();
		this.selectedState = selectedState;
		this.selectedEdge = null;
	}

	public ViewerSelection(AbstractEdge<?> selectedEdge) {
		super();
		this.selectedState = null;
		this.selectedEdge = selectedEdge;
	}

	public ViewerSelection(ViewerSelection other) {
		this.selectedState = other.selectedState;
		this.selectedEdge = other.selectedEdge;
	}

	public void selectState(State state) {
		if (isSelectedEdge()) {
			unselect();
		}
		this.selectedState = state;
	}

	public void selectEdge(NFAedge edge) {
		if (isSelectedState()) {
			unselect();
		}
		this.selectedEdge = edge;
	}

	public void select(ViewerSelection other) {
		this.selectedEdge = other.getSelectedEdge();
		this.selectedState = other.getSelectedState();
	}

	public void unselect() {
		this.selectedState = null;
		this.selectedEdge = null;
	}

	public State getSelectedState() {
		return selectedState;
	}

	public AbstractEdge<?> getSelectedEdge() {
		return selectedEdge;
	}

	public boolean isSelected() {
		return isSelectedState() || isSelectedEdge();
	}

	public boolean isSelectedState() {
		return selectedState != null;
	}

	public boolean isSelectedEdge() {
		return selectedEdge != null;
	}

	public boolean isSelectedState(State state) {
		return state.equals(selectedState);
	}

	public boolean isSelectedEdge(AbstractEdge<?> edge) {
		return edge.equals(selectedEdge);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((selectedState == null) ? 0 : selectedState.hashCode());
		result = prime * result
				+ ((selectedEdge == null) ? 0 : selectedEdge.hashCode());
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
		ViewerSelection other = (ViewerSelection) obj;
		if (selectedState == null) {
			if (other.selectedState != null)
				return false;
		} else if (!selectedState.equals(other.selectedState))
			return false;
		if (selectedEdge == null) {
			if (other.selectedEdge != null)
				return false;
		} else if (!selectedEdge.equals(other.selectedEdge))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ViewerSelection [selectedState=" + selectedState
				+ ", selectedEdge=" + selectedEdge + "]";
	}

}
