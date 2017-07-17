package cz.upol.fapapp.core.infile;

import java.io.File;
import java.io.IOException;

public abstract class InputFileObjectParser<T> {

	private final String type;

	public InputFileObjectParser(String type) {
		super();
		this.type = type;
	}

	public T parse(File file) throws IOException {
		InputFileParser parser = new InputFileParser();
		InputFileData data = parser.parse(file);
		return checkAndProcess(data);
	}

	public T parse(String content) {
		InputFileParser parser = new InputFileParser();
		InputFileData data = parser.parse(content);
		return checkAndProcess(data);
	}

	public T checkAndProcess(InputFileData data) {
		String type = data.getType();
		if (!this.type.equalsIgnoreCase(type)) {
			throw new IllegalArgumentException("Required file of type " + this.type + " but found " + type);
		}

		return process(data);
	}

	public abstract T process(InputFileData data);
}
