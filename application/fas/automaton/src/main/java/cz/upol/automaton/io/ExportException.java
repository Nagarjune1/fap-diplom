package cz.upol.automaton.io;

import java.io.IOException;

public class ExportException extends IOException {

	private static final long serialVersionUID = -6593258453178906150L;

	public ExportException() {
		super();
	}

	public ExportException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExportException(String message) {
		super(message);
	}

	public ExportException(Throwable cause) {
		super(cause);
	}

}
