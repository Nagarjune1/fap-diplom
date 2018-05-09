package cz.upol.fapapp.core.timfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Implements parsing of {@link TIMFileData} from string and/or from file.
 * 
 * @author martin
 *
 */
public class TIMFileParser {

	private static final String COMMENT = "#";
	private static final String PADDING = "\t";
	private static final String PADDING_REGEX = "\t[ \t]*";
	private static final String OPTIONAL = ":";
	private static final String SEPARATOR_REGEX = "[,: \t][ \t]*";

	private String currentGroup;

	public TIMFileParser() {
	}
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Parses given file.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public TIMFileData parse(File file) throws IOException {
		String content = readFileContent(file);
		return parse(content);
	}

	/**
	 * Parses given string.
	 * 
	 * @param content
	 * @return
	 */
	public TIMFileData parse(String content) {
		String[] lines = content.split("\n");

		TIMFileData data = new TIMFileData();
		for (String line : lines) {
			processLine(data, line);
		}

		return data;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Processes given line of string (into given data).
	 * 
	 * @param data
	 * @param line
	 */
	private void processLine(TIMFileData data, String line) {
		if (isLineToIgnore(line)) {
			return;

		} else if (isLineWithElements(line)) {
			LineElements items = processItemElementsLine(line);
			data.add(currentGroup, items);

		} else if (isItemOpeningItem(line)) {
			String newGroup = processItemOpeningLine(line);
			this.currentGroup = newGroup;

		} else {
			throw new IllegalStateException("Invalid line: " + line);
		}
	}

	/**
	 * Should be this line ignored? (empty or comment)
	 * 
	 * @param line
	 * @return
	 */
	private boolean isLineToIgnore(String line) {
		return line.isEmpty() || line.matches(PADDING_REGEX) || line.startsWith(COMMENT);
	}

	/**
	 * Is this line with elements? (is padded)
	 * 
	 * @param line
	 * @return
	 */
	private boolean isLineWithElements(String line) {
		return line.startsWith(PADDING);
	}

	/**
	 * Is this line opening item? (not padded)
	 * 
	 * @param line
	 * @return
	 */
	private boolean isItemOpeningItem(String line) {
		return !line.startsWith(PADDING);
	}

	/**
	 * Processes given line as item opening.
	 * 
	 * @param line
	 * @return
	 */
	protected static String processItemOpeningLine(String line) {
		return removeOptionalFromEnd(line, OPTIONAL);
	}

	/**
	 * Processes given line as elements' line.
	 * 
	 * @param line
	 * @return
	 */
	protected static LineElements processItemElementsLine(String line) {
		String unpadded = removeRegexFromBegin(line, PADDING_REGEX);

		String parts[] = unpadded.split(SEPARATOR_REGEX);
		List<String> list = Arrays.asList(parts);
		return new LineElements(list);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Removes string what from the beggining of from (if so).
	 * 
	 * @param from
	 * @param what
	 * @return
	 */
	protected static String removeFromBegin(String from, String what) {
		if (from.startsWith(what)) {
			int startIndex = what.length();
			return from.substring(startIndex);
		} else {
			return from;
		}
	}

	/**
	 * Removes first occurence of given regex from string from.
	 * 
	 * @param from
	 * @param regex
	 * @return
	 */
	protected static String removeRegexFromBegin(String from, String regex) {
		return from.replaceFirst(regex, "");
	}

	/**
	 * Removes string what from end of string from (if so).
	 * 
	 * @param from
	 * @param what
	 * @return
	 */
	protected static String removeOptionalFromEnd(String from, String what) {
		if (from.endsWith(what)) {
			int fromIndex = from.length() - what.length();
			return from.substring(0, fromIndex);
		} else {
			return from;
		}
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Reads content of given file.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFileContent(File file) throws IOException {
		Path path = file.toPath();
		byte[] bytes = Files.readAllBytes(path);
		return new String(bytes);
	}

}
