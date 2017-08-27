package cz.upol.fapapp.cfa.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import cz.upol.fapapp.core.infile.InputFileData;
import cz.upol.fapapp.core.infile.InputFileObjectComposer;
import cz.upol.fapapp.core.infile.LineItems;

public class TwoDimArrComposer<E> extends InputFileObjectComposer<TwoDimArray<E>> {

	private final Function<E, String> toStringFunction;

	public TwoDimArrComposer(String type, Function<E, String> toStringFunction) {
		super(type);

		this.toStringFunction = toStringFunction;
	}

	@Override
	protected void process(TwoDimArray<E> arr, InputFileData data) {
		data.start("data");
		putItems("data", arr, data);
	}

	/**************************************************************************/

	public void putItems(String groupName, TwoDimArray<E> arr, InputFileData data) {

		for (int i = arr.getMinIndex(); i < arr.getMaxIndex(); i++) {
			List<String> line = new ArrayList<>(arr.getSize());

			for (int j = arr.getMinIndex(); j < arr.getMaxIndex(); j++) {
				E item = arr.get(i, j);
				String str = toStringFunction.apply(item);
				line.add(str);
			}

			LineItems items = new LineItems(line);
			data.add(groupName, items);
		}

	}

}
