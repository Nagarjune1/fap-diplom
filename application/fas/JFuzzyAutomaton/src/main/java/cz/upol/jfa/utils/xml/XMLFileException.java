package cz.upol.jfa.utils.xml;

public class XMLFileException extends Exception {

	private static final long serialVersionUID = 370354158165300244L;

	public XMLFileException(String message) {
		super(message);
	}

	public XMLFileException(Throwable cause) {
		super(cause);
	}

	public XMLFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLFileException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
