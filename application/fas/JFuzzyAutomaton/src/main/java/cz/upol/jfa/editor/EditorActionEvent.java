package cz.upol.jfa.editor;

import java.awt.event.ActionEvent;

public class EditorActionEvent extends ActionEvent {

	private static final long serialVersionUID = 5253085251715898017L;

	private final EditorStatus status;

	public EditorActionEvent(Object source, EditorStatus status, int id,
			String command, int modifiers) {
		super(source, id, command, modifiers);

		this.status = status;
	}

	public EditorActionEvent(Object source, EditorStatus status, int id,
			String command, long when, int modifiers) {

		super(source, id, command, when, modifiers);

		this.status = status;
	}

	public EditorActionEvent(Object source, EditorStatus status, int id,
			String command) {

		super(source, id, command);

		this.status = status;
	}

	public EditorStatus getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		EditorActionEvent other = (EditorActionEvent) obj;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EditorActionEvent [status=" + status + "]";
	}

}
