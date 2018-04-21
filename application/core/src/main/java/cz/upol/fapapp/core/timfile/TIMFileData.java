package cz.upol.fapapp.core.timfile;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.misc.Printable;

/**
 * An 'Table-In-Map' data object. Cotains data parsed from file/ready to compose
 * into file.
 * 
 * @author martin
 *
 */
public class TIMFileData implements Printable {
	public static final String TYPE_KEY = "type";

	private final Map<String, List<LineElements>> data;

	public TIMFileData() {
		super();
		this.data = new LinkedHashMap<>();
	}

	/*************************************************************************/

	/**
	 * Returns type specifier of given data. TODO make it private final field
	 * 
	 * @return
	 */
	public String getType() {
		List<LineElements> types = data.get(TYPE_KEY);
		if (types == null) {
			throw new IllegalArgumentException("Type not specified");
		}
		if (types.size() < 1) {
			throw new IllegalArgumentException("Type specifier is empty");
		}
		if (types.size() > 1) {
			Logger.get().warning("More than one type specifier");
		}

		LineElements items = types.get(0);
		return TIMFileComposer.join(items);
	}

	/**
	 * Lists names of items.
	 * 
	 * @return
	 */
	public Iterable<String> listItemsNames() {
		return data.keySet();
	}

	/**
	 * Returns whether this data contains item of given name.
	 * 
	 * @param itemName
	 * @return
	 */
	public boolean hasItem(String itemName) {
		return data.containsKey(itemName);
	}

	/**
	 * Returns elements of given name (as list of lines).
	 * 
	 * @param itemName
	 * @return
	 */
	public List<LineElements> getElementsOf(String itemName) {
		return data.get(itemName);
	}

	/**
	 * Returns elements of given name (merged into one line).
	 * 
	 * @param itemName
	 * @return
	 */
	public LineElements getElementsMerged(String itemName) {
		return new LineElements(data.get(itemName).stream() //
				.flatMap((l) -> l.getElements().stream()) //
				.collect(Collectors.toList()));
	}

	/**
	 * Returns elements of given name (as original string)
	 * 
	 * @param itemName
	 * @return
	 */
	public String getElementsRaw(String itemName) {
		return data.get(itemName).stream() //
				.map((l) -> TIMFileComposer.join(l)) //
				.collect(Collectors.joining("\n"));
	}

	/*************************************************************************/

	@Deprecated
	public void start(String newGroupName) {
		this.data.put(newGroupName, new LinkedList<>());
	}

	/**
	 * Adds given line to specified item. If specified item does not yet exist,
	 * creates it.
	 * 
	 * @param itemName
	 * @param line
	 */
	public void add(String itemName, LineElements line) {
		List<LineElements> items = getAndCreateIfNeeded(itemName);

		items.add(line);
	}

	/**
	 * Adds given single element to specified item. If specified item does not
	 * yet exist, creates it.
	 * 
	 * @param itemName
	 * @param singleElement
	 */
	public void add(String itemName, String singleElement) {
		LineElements line = new LineElements(singleElement);
		add(itemName, line);
	}

	public void add(String itemName, List<LineElements> lines) {
		List<LineElements> items = getAndCreateIfNeeded(itemName);

		items.addAll(lines);
	}

	/*************************************************************************/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TIMFileData other = (TIMFileData) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TIMFileData [data=" + data + "]";
	}

	/*************************************************************************/

	/**
	 * Returns list of lines of given name. If does not exist, creates and
	 * returns it.
	 * 
	 * @param itemName
	 * @return
	 */
	private List<LineElements> getAndCreateIfNeeded(String itemName) {
		List<LineElements> items = this.data.get(itemName);

		if (items == null) {
			items = new LinkedList<>();
			this.data.put(itemName, items);
		}
		return items;
	}

	@Override
	public void print(PrintStream to) {
		TIMFileComposer composer = new TIMFileComposer();
		String value = composer.compose(this);
		to.println(value);
	}
}
