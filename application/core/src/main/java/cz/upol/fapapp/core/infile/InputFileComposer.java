package cz.upol.fapapp.core.infile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class InputFileComposer {

	private static final CharSequence DELIMITER_OF_LINE_ITEMS = " ";
	private static final Object AFTER_KEY = ":";
	private static final Object PADDING = "\t";
	private static final Object ITEMS_SEPARATOR = ",\t";

	///////////////////////////////////////////////////////////////////////////

	public void compose(InputFileData data, File file) throws IOException {
		String content = compose(data);
		writeFileContent(content, file);
	}

	public String compose(InputFileData data) {
		StringBuilder stb = new StringBuilder();

		for (String key : data.listKeys()) {
			List<LineItems> items = data.getItemsOf(key);
			processLines(stb, key, items);
		}

		return stb.toString();
	}

	///////////////////////////////////////////////////////////////////////////

	private void processLines(StringBuilder stb, String key, List<LineItems> lines) {
		stb.append(key);
		stb.append(AFTER_KEY);
		stb.append('\n');

		for (LineItems line : lines) {
			stb.append(PADDING);

			processLineItems(stb, line);

			stb.append('\n');
		}

		stb.append('\n');
	}

	private void processLineItems(StringBuilder stb, LineItems line) {
		Iterator<String> iter = line.iterator();
		while (iter.hasNext()) {
			String item = iter.next();
			stb.append(item);

			if (iter.hasNext()) {
				stb.append(ITEMS_SEPARATOR);
			}
		}
	}

	public static String join(LineItems items) {
		return items.getItems().stream() //
				.collect(Collectors.joining(DELIMITER_OF_LINE_ITEMS));
	}

	///////////////////////////////////////////////////////////////////////////

	public static void writeFileContent(String content, File file) throws IOException {
		Path path = file.toPath();
		byte[] bytes = content.getBytes();
		Files.write(path, bytes);
	}

}
