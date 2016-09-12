package cz.upol.jfa.viewer.interactivity;

import java.awt.event.ActionEvent;

import cz.upol.jfa.viewer.JFuzzyAutomatonViewer;
import cz.upol.jfa.viewer.Position;

public class ViewerActionEvent extends ActionEvent {

	private static final long serialVersionUID = -4301640885639313186L;

	private final ViewerEventType type;
	private final ViewerSelection on;
	private final Position position;

	public ViewerActionEvent(JFuzzyAutomatonViewer jfa, ViewerEventType type,
			ViewerSelection on, Position where, int id, long when) {

		super(jfa, id, createCommand(jfa, type, on, where));

		this.type = type;
		this.on = on;
		this.position = where;
	}

	public ViewerEventType getType() {
		return type;
	}

	public ViewerSelection getOn() {
		return on;
	}

	public Position getPosition() {
		return position;
	}

	public JFuzzyAutomatonViewer getJFuzzyAutomaton() {
		return (JFuzzyAutomatonViewer) source;
	}

	private static String createCommand(JFuzzyAutomatonViewer automaton,
			ViewerEventType type, ViewerSelection on, Position where) {

		StringBuilder stb = new StringBuilder();

		stb.append(type);
		stb.append(" on ");
		stb.append(on);
		stb.append(" (");
		stb.append(where);
		stb.append(") ");

		return stb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((on == null) ? 0 : on.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ViewerActionEvent other = (ViewerActionEvent) obj;
		if (on == null) {
			if (other.on != null)
				return false;
		} else if (!on.equals(other.on))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ViewerActionEvent [type=" + type + ", on=" + on + ", position="
				+ position + "]";
	}

}
