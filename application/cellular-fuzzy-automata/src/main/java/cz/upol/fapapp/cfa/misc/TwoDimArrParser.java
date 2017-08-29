package cz.upol.fapapp.cfa.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import cz.upol.fapapp.core.infile.InputFileData;
import cz.upol.fapapp.core.infile.InputFileObjectParser;
import cz.upol.fapapp.core.infile.LineItems;
import cz.upol.fapapp.core.infile.ObjectParserTools;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.CollectionsUtils;

public class TwoDimArrParser<E> extends InputFileObjectParser<TwoDimArray<E>> {

	private final Function<String, E> fromStringFunction;

	public TwoDimArrParser(String type, Function<String, E> fromStringFunction) {
		super(type);

		this.fromStringFunction = fromStringFunction;
	}

	@Override
	public TwoDimArray<E> process(InputFileData data) {
		Integer minIndex = null;
		Integer maxIndex = null;
		Map<Integer, Map<Integer, E>> items = null;

		for (String key : data.listKeys()) {
			switch (key) {
			case InputFileData.TYPE_KEY:
				break;
			case "size":
				minIndex = processSize(data.getItemsOf(key), 0);
				maxIndex = processSize(data.getItemsOf(key), 1);
				break;
			case "data":
			case "items":
				items = processItems(data.getItemsOf(key));
				break;
			default:
				Logger.get().warning("Unknown key " + key);
			}
		}

		CollectionsUtils.checkNotNull("size (min) ", minIndex);
		CollectionsUtils.checkNotNull("size (max) ", maxIndex);
		CollectionsUtils.checkNotNull("data", items);

		return new TwoDimArray<>(minIndex, maxIndex, items);
	}

	private int processSize(List<LineItems> itemsOf, int indexAtLine) {
		if (itemsOf.size() != 1 || itemsOf.get(0).count() != 2) {
			throw new IllegalArgumentException("Required one line with exactly two int values");
		}

		LineItems line = itemsOf.get(0);
		String val = line.getIth(indexAtLine);
		return ObjectParserTools.parseInt(val);
	}

	public Map<Integer, Map<Integer, E>> processItems(List<LineItems> itemsOf) {
		Map<Integer, Map<Integer, E>> result = new HashMap<>(itemsOf.size());

		for (int i = 0; i < itemsOf.size(); i++) {
			LineItems line = itemsOf.get(i);
			Map<Integer, E> subMap = new HashMap<>(line.count());

			for (int j = 0; j < itemsOf.size(); j++) {
				String str = line.getIth(j);
				E item = fromStringFunction.apply(str);

				subMap.put(j, item);
			}

			result.put(i, subMap);
		}

		return result;
	}

}
