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

	/**
	 * This method should look like:
	 * 
	 * <pre>
	 * public RetType process(InputFileData data) {
	 * 	Type1 value1 = null;
	 * 	Type2 value2 = null;
	 * 
	 * 	for (String key : data.listKeys()) {
	 * 		List<LineItems> lines = data.getItemsOf(key);
	 * 
	 * 		switch (key) {
	 * 		case InputFileData.TYPE_KEY:
	 * 			break;
	 * 		case "type1":
	 * 		case "values of type 1":
	 * 			value1 = processValue1(lines);
	 * 			break;
	 * 		case "type2":
	 * 		case "values of type 2":
	 * 			value2 = processValue2(lines);
	 * 			break;
	 * 		default:
	 * 			Logger.get().warning("Unknown key " + key);
	 * 		}
	 * 	}
	 * 
	 * 	CollectionsUtils.checkNotNull("type1", value1);
	 * 	// value2 is optional
	 * 
	 * 	return new RetType(value1, value2);
	 * }
	 * </pre>
	 * 
	 * @param data
	 * @return
	 */
	public abstract T process(InputFileData data);
}
