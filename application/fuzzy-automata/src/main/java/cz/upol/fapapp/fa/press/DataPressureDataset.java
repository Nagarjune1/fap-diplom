package cz.upol.fapapp.fa.press;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.misc.Printable;

public class DataPressureDataset implements Printable {

	private double delta;
	private DatasetRow thresholds;
	private final List<DatasetRow> trainingData;
	private final List<DatasetRow> testData;

	public DataPressureDataset(double delta, DatasetRow thresholds, List<DatasetRow> trainingData,
			List<DatasetRow> testData) {
		super();
		this.delta = delta;
		this.thresholds = thresholds;
		this.trainingData = trainingData;
		this.testData = testData;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public DatasetRow getThresholds() {
		return thresholds;
	}

	public void setThresholds(DatasetRow thresholds) {
		this.thresholds = thresholds;
	}

	public List<DatasetRow> getTrainingData() {
		return trainingData;
	}

	public List<DatasetRow> getTestData() {
		return testData;
	}

	///////////////////////////////////////////////////////////////////////////

	protected List<Double> listColumn(int columnIndex) {
		return trainingData.stream() //
				.map((r) -> r.getValue(columnIndex)) //
				.collect(Collectors.toList());
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "DataPressureDataset [delta=" + delta + ", thresholds=" + thresholds.count() + ", trainingData="
				+ trainingData.size() + ", testData=" + testData.size() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(delta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((testData == null) ? 0 : testData.hashCode());
		result = prime * result + ((thresholds == null) ? 0 : thresholds.hashCode());
		result = prime * result + ((trainingData == null) ? 0 : trainingData.hashCode());
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
		DataPressureDataset other = (DataPressureDataset) obj;
		if (Double.doubleToLongBits(delta) != Double.doubleToLongBits(other.delta))
			return false;
		if (testData == null) {
			if (other.testData != null)
				return false;
		} else if (!testData.equals(other.testData))
			return false;
		if (thresholds == null) {
			if (other.thresholds != null)
				return false;
		} else if (!thresholds.equals(other.thresholds))
			return false;
		if (trainingData == null) {
			if (other.trainingData != null)
				return false;
		} else if (!trainingData.equals(other.trainingData))
			return false;
		return true;
	}

	@Override
	public void print(PrintStream to) {
		Printable.print(to, new TIMFDPDatesetComposer(), this);
	}

}
