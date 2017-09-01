package cz.upol.fapapp.cfa.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class TwoDimArrTIMParser<E> extends TIMObjectParser<TwoDimArray<E>> {

	private final Function<String, E> fromStringFunction;
	private final String[] dataItemNames;

	public TwoDimArrTIMParser(String type, Function<String, E> fromStringFunction, String... dataItemNames) {
		super(type);

		this.fromStringFunction = fromStringFunction;
		this.dataItemNames = dataItemNames;
	}

	@Override
	public TwoDimArray<E> process(TIMFileData data) {
		int minIndex = processSize(data, 0);
		int maxIndex = processSize(data, 1);

		Map<Integer, Map<Integer, E>> items = processItems(data, minIndex, maxIndex);

		return new TwoDimArray<>(minIndex, maxIndex, items);
	}

	public TwoDimArray<E> processWithKnownSize(TIMFileData data, int minIndex, int maxIndex) {
		Map<Integer, Map<Integer, E>> items = processItems(data, minIndex, maxIndex);

		return new TwoDimArray<>(minIndex, maxIndex, items);
	}

	/*************************************************************************/
	
	private int processSize(TIMFileData data, int indexAtLine) {
		LineElements line = TIMObjectParserComposerTools.findElementsMerged(data, "size");

		if (line.count() > 2) {
			Logger.get().warning("Expected 1 or 2 numbers, found: " + line);
		}
		if (line.count() == 2) {
			String val = line.getIth(indexAtLine);
			return TIMObjectParserComposerTools.parseInt(val);
		}
		if (line.count() == 1) {
			if (indexAtLine == 0) {
				return 0;
			} else {
				String val = line.getIth(0);
				return TIMObjectParserComposerTools.parseInt(val);
			}
		}

		throw new IllegalStateException("Invalid line length: " + line.count());
	}

	private Map<Integer, Map<Integer, E>> processItems(TIMFileData data, int minIndex, int maxIndex) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, dataItemNames);

		Map<Integer, Map<Integer, E>> elements = parseItemsElements(lines);

		return checkAndCorrectIndexes(elements, minIndex, maxIndex);
	}

	/*************************************************************************/
	
	private Map<Integer, Map<Integer, E>> parseItemsElements(List<LineElements> lines) {
		Map<Integer, Map<Integer, E>> result = new HashMap<>(lines.size());

		for (int i = 0; i < lines.size(); i++) {
			LineElements line = lines.get(i);
			Map<Integer, E> subMap = new HashMap<>(line.count());

			for (int j = 0; j < line.count(); j++) {
				String str = line.getIth(j);
				E item = fromStringFunction.apply(str);

				subMap.put(j, item);
			}

			result.put(i, subMap);
		}

		return result;
	}

	private Map<Integer, Map<Integer, E>> checkAndCorrectIndexes(Map<Integer, Map<Integer, E>> elements, int minIndex,
			int maxIndex) {
		int expectedSize = maxIndex - minIndex;
		Map<Integer, Map<Integer, E>> result = new HashMap<>(expectedSize);

		for (Integer i = 0; i < elements.size(); i++) {
			Map<Integer, E> line = elements.get(i);

			int row = i + minIndex;
			if (row >= maxIndex) {
				Logger.get()
						.warning("Skipping extra lines (expected " + expectedSize + ", found " + elements.size() + ")");
				break;
			}

			Map<Integer, E> resultLine = new HashMap<>(expectedSize);
			result.put(row, resultLine);

			for (Integer j = 0; j < line.size(); j++) {
				E atom = line.get(j);

				int col = j + minIndex;
				if (col >= maxIndex) {
					Logger.get().warning("Skipping extra elements at line " + i + " (expected " + expectedSize
							+ ", found " + line.size() + ")");
					break;
				}
				resultLine.put(col, atom);

			}

			if (resultLine.size() < expectedSize) {
				throw new IllegalArgumentException("Missing elements at line " + i + " (expected " + expectedSize
						+ ", found " + line.size() + ")");
			}
		}

		if (result.size() < expectedSize) {
			throw new IllegalArgumentException(
					"Missing lines (expected " + expectedSize + ", found " + elements.size() + ")");
		}

		return result;
	}

}
