package cz.upol.fapapp.fa.press;

import java.util.List;

/**
 * Row of {@link DataPressureDataset}. Contains list of values and resulting
 * attribute value, both doubles.
 * 
 * @author martin
 *
 */
public class DatasetRow {
	private final List<Double> values;
	private Double result;

	public DatasetRow(List<Double> values) {
		super();
		this.values = values;
	}

	public List<Double> getValues() {
		return values;
	}

	public int count() {
		return values.size();
	}

	public Double getValue(int columnIndex) {
		return values.get(columnIndex);
	}

	public Double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		DatasetRow other = (DatasetRow) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DatasetRow" + values + ";" + result + "";
	}

}
