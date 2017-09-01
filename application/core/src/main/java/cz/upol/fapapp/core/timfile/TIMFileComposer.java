package cz.upol.fapapp.core.timfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Composer of {@link TIMFileData}'s files. Converts data to string and/or saves
 * to file.
 * 
 * @author martin
 *
 */
public class TIMFileComposer {

	private static final String DELIMITER_OF_LINE_ITEMS = " ";
	private static final String AFTER_KEY = ":";
	private static final String PADDING = "\t";
	private static final String ITEMS_SEPARATOR = "\t";

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Saves given data into specified file.
	 * 
	 * @param data
	 * @param file
	 * @throws IOException
	 */
	public void compose(TIMFileData data, File file) throws IOException {
		String content = compose(data);
		writeFileContent(content, file);
	}

	/**
	 * Generates string of given data.
	 * 
	 * @param data
	 * @return
	 */
	public String compose(TIMFileData data) {
		StringBuilder stb = new StringBuilder();

		for (String key : data.listItemsNames()) {
			List<LineElements> items = data.getElementsOf(key);
			processLines(stb, key, items);
		}

		return stb.toString();
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Processes (appends converted to String to stb) one item (with name
	 * itemName and content lines).
	 * 
	 * @param stb
	 * @param itemName
	 * @param lines
	 */
	private void processLines(StringBuilder stb, String itemName, List<LineElements> lines) {
		stb.append(itemName);
		stb.append(AFTER_KEY);
		stb.append('\n');

		for (LineElements line : lines) {
			stb.append(PADDING);

			processLineElements(stb, line);

			stb.append('\n');
		}

		stb.append('\n');
	}

	/**
	 * Processes (appends converted to String to stb) given line.
	 * 
	 * @param stb
	 * @param line
	 */
	private void processLineElements(StringBuilder stb, LineElements line) {
		Iterator<String> iter = line.iterator();
		while (iter.hasNext()) {
			String item = iter.next();
			stb.append(item);

			if (iter.hasNext()) {
				stb.append(ITEMS_SEPARATOR);
			}
		}
	}

	/**
	 * Joins given line elements into one string (using
	 * {@link #DELIMITER_OF_LINE_ITEMS}).
	 * 
	 * @param items
	 * @return
	 */
	public static String join(LineElements items) {
		return items.getElements().stream() //
				.collect(Collectors.joining(DELIMITER_OF_LINE_ITEMS));
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Saves given string into file.
	 * 
	 * @param content
	 * @param file
	 * @throws IOException
	 */
	public static void writeFileContent(String content, File file) throws IOException {
		Path path = file.toPath();
		byte[] bytes = content.getBytes();
		Files.write(path, bytes);
	}

}
