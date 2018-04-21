package cz.upol.fapapp.fa.press;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TIMFDPDatasetParserTest {

	@Test
	public void test() {
		DataPressureDataset dataset = createDataset();
		String file = createFile();

		TIMFDPDatesetComposer composer = new TIMFDPDatesetComposer();
		String composed = composer.compose(dataset);

		// System.out.println(composed);
		assertEquals(file, composed);

		TIMFDPDatasetParser parser = new TIMFDPDatasetParser();
		DataPressureDataset parsed = parser.parse(file);

		// System.out.println(parsed);
		assertEquals(dataset.toString(), parsed.toString());

		// FIXME ... assertEquals(dataset, parsed);

	}

	private DataPressureDataset createDataset() {
		double delta = 0.5;

		DatasetRow thresholds = new DatasetRow(Arrays.asList(0.1, 1.5, 0.7));

		List<DatasetRow> trainingData = Arrays.asList( //
				new DatasetRow(Arrays.asList(1.5, 0.99, 4.2)), //
				new DatasetRow(Arrays.asList(0.5, 1.99, 2.2)), //
				new DatasetRow(Arrays.asList(1.0, 0.11, 4.9)));

		List<DatasetRow> testData = Arrays.asList( //
				new DatasetRow(Arrays.asList(1.1, 1.3, 1.5)), //
				new DatasetRow(Arrays.asList(1.2, 1.4, 1.6)));

		return new DataPressureDataset(delta, thresholds, trainingData, testData);
	}

	private String createFile() {
		return "" //
				+ "type:\n" //
				+ "	data pressure dataset\n" //
				+ "\n" //
				+ "delta:\n" //
				+ "	0.5\n" //
				+ "\n" //
				+ "thresholds:\n" //
				+ "	0.1	1.5	0.7\n" //
				+ "\n" //
				+ "training data:\n" //
				+ "	1.5	0.99	4.2\n" //
				+ "	0.5	1.99	2.2\n" //
				+ "	1.0	0.11	4.9\n" //
				+ "\n" //
				+ "test data:\n" //
				+ "	1.1	1.3	1.5\n" //
				+ "	1.2	1.4	1.6\n" //
				+ "\n";
	}

}
