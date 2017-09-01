package cz.upol.fapapp.cfa.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class TwoDimArrTIMComposer<E> extends TIMObjectComposer<TwoDimArray<E>> {

	private final Function<E, String> toStringFunction;
	private final String dataItemName;

	public TwoDimArrTIMComposer(String type, Function<E, String> toStringFunction, String dataItemName) {
		super(type);

		this.toStringFunction = toStringFunction;
		this.dataItemName = dataItemName;
	}

	@Override
	protected void process(TwoDimArray<E> arr, TIMFileData data) {
		processSize(arr.getMinIndex(), arr.getMaxIndex(), data);
		processData(arr, data);
	}

	public void processWithoutSize(TwoDimArray<E> arr, TIMFileData data) {
		processData(arr, data);
	}

	/**************************************************************************/

	private void processSize(int minIndex, int maxIndex, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.intsToLine(minIndex, maxIndex);
		data.add("size", line);
	}

	private void processData(TwoDimArray<E> arr, TIMFileData data) {
		putItems(dataItemName, arr, data);
	}

	private void putItems(String groupName, TwoDimArray<E> arr, TIMFileData data) {
		List<LineElements> lines = composeLinesElements(arr);
		data.add(groupName, lines);
	}

	/**************************************************************************/

	private List<LineElements> composeLinesElements(TwoDimArray<E> arr) {
		List<LineElements> lines = new ArrayList<>(arr.getSize());

		for (int i = arr.getMinIndex(); i < arr.getMaxIndex(); i++) {
			List<String> line = new ArrayList<>(arr.getSize());

			for (int j = arr.getMinIndex(); j < arr.getMaxIndex(); j++) {
				E item = arr.get(i, j);
				String str = toStringFunction.apply(item);
				line.add(str);
			}

			LineElements items = new LineElements(line);

			lines.add(items);
		}
		return lines;
	}

}
