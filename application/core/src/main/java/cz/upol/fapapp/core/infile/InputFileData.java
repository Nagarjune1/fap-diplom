package cz.upol.fapapp.core.infile;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InputFileData {
	public static final String TYPE_KEY = "type";
	
	private final Map<String, List<LineItems>> data;

	public InputFileData() {
		super();
		this.data = new LinkedHashMap<>();
	}

	public String getType() {
		List<LineItems> types = data.get(TYPE_KEY);
		if (types.size() < 1) {
			throw new IllegalArgumentException("Type specifier is empty");
		}
		if (types.size() > 1) {
			System.err.println("Warning: more than one type specifier");
		}

		LineItems items = types.get(0);
		return InputFileComposer.join(items);
	}

	public Iterable<String> listKeys() {
		return data.keySet();
	}

	public List<LineItems> getItemsOf(String key) {
		return data.get(key);
	}

	public void start(String newGroupName) {
		this.data.put(newGroupName, new LinkedList<>());
	}

	public void add(String groupName, LineItems line) {
		List<LineItems> items = this.data.get(groupName);
		items.add(line);
	}

	@Deprecated
	public void add(String name, List<LineItems> data) {
		this.data.put(name, data);
	}

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
		InputFileData other = (InputFileData) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InputFileData [data=" + data + "]";
	}

}
