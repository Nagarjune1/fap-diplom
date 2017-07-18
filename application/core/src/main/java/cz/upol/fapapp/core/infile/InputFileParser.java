package cz.upol.fapapp.core.infile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class InputFileParser {

	private static final String COMMENT = "#";
	private static final String PADDING = "\t";
	private static final String PADDING_REGEX = "\t[ \t]*";
	private static final String OPTIONAL = ":";
	private static final String SEPARATOR_REGEX = "[,: \t][ \t]*";

	private String currentGroup;

	public InputFileParser() {
	}
	///////////////////////////////////////////////////////////////////////////

	public InputFileData parse(File file) throws IOException {
		String content = readFileContent(file);
		return parse(content);
	}

	public InputFileData parse(String content) {
		String[] lines = content.split("\n");

		InputFileData data = new InputFileData();
		for (String line : lines) {
			processLine(data, line);
		}

		return data;
	}

	private void processLine(InputFileData data, String line) {
		if (line.isEmpty() || line.matches(PADDING_REGEX) || line.startsWith(COMMENT)) {
			return;

		} else if (line.startsWith(PADDING)) {
			LineItems items = processPaddedLine(line);
			data.add(currentGroup, items);

		} else {
			String newGroup = processGroupOpeningLine(line);
			data.start(newGroup);

			this.currentGroup = newGroup;
		}

	}
	///////////////////////////////////////////////////////////////////////////

	protected static String processGroupOpeningLine(String line) {
		return removeOptionalFromEnd(line, OPTIONAL);
	}

	protected static LineItems processPaddedLine(String line) {
		String unpadded = removeRegexFromBegin(line, PADDING_REGEX);

		String parts[] = unpadded.split(SEPARATOR_REGEX);
		List<String> list = Arrays.asList(parts);
		return new LineItems(list);
	}

	protected static String removeFromBegin(String from, String what) {
		if (from.startsWith(what)) {
			int startIndex = what.length();
			return from.substring(startIndex);
		} else {
			return from;
		}
	}

	protected static String removeRegexFromBegin(String from, String regex) {
		return from.replaceFirst(regex, "");
	}

	protected static String removeOptionalFromEnd(String from, String what) {
		if (from.endsWith(what)) {
			int fromIndex = from.length() - what.length();
			return from.substring(0, fromIndex);
		} else {
			return from;
		}
	}

	///////////////////////////////////////////////////////////////////////////

	public static String readFileContent(File file) throws IOException {
		Path path = file.toPath();
		byte[] bytes = Files.readAllBytes(path);
		return new String(bytes);
	}

}
