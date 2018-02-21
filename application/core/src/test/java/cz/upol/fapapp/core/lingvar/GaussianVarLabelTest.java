package cz.upol.fapapp.core.lingvar;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.fapapp.core.lingvar.GaussianLingVarLabel;
import cz.upol.fapapp.core.lingvar.LingVarValue;

public class GaussianVarLabelTest {

	private final static double DELTA = 0.0001;

	@Test
	public void testComputeJustPositiveWithDoubles() {
		assertEquals(1.0, GaussianLingVarLabel.computePositiveGaussHalf(0.0, 10.0), DELTA);
		assertEquals(1.0, GaussianLingVarLabel.computePositiveGaussHalf(0.0, 20.0), DELTA);

		assertEquals(0.99501, GaussianLingVarLabel.computePositiveGaussHalf(1.0, 10.0), DELTA);
		assertEquals(0.98020, GaussianLingVarLabel.computePositiveGaussHalf(2.0, 10.0), DELTA);
		assertEquals(0.88250, GaussianLingVarLabel.computePositiveGaussHalf(5.0, 10.0), DELTA);
		assertEquals(0.60653, GaussianLingVarLabel.computePositiveGaussHalf(10.0, 10.0), DELTA);
		assertEquals(0.13534, GaussianLingVarLabel.computePositiveGaussHalf(20.0, 10.0), DELTA);
		assertEquals(0.00000, GaussianLingVarLabel.computePositiveGaussHalf(50.0, 10.0), DELTA);
		assertEquals(0.00000, GaussianLingVarLabel.computePositiveGaussHalf(100.0, 10.0), DELTA);
	}

	@Test
	public void test() {
		GaussianLingVarLabel label = new GaussianLingVarLabel("some", //
				new LingVarValue(100), new LingVarValue(90), new LingVarValue(200));

		assertEquals(0.29823, label.compute(new LingVarValue(-40.0)).getValue(), DELTA);
		assertEquals(0.41111, label.compute(new LingVarValue(-20.0)).getValue(), DELTA);
		assertEquals(0.53940, label.compute(new LingVarValue(0.0)).getValue(), DELTA);
		assertEquals(0.60653, label.compute(new LingVarValue(10.0)).getValue(), DELTA);
		assertEquals(0.85699, label.compute(new LingVarValue(50.0)).getValue(), DELTA);
		assertEquals(0.99384, label.compute(new LingVarValue(90.0)).getValue(), DELTA);
		assertEquals(1.00000, label.compute(new LingVarValue(100.0)).getValue(), DELTA);
		assertEquals(0.99875, label.compute(new LingVarValue(110.0)).getValue(), DELTA);
		assertEquals(0.96923, label.compute(new LingVarValue(150.0)).getValue(), DELTA);
		assertEquals(0.90370, label.compute(new LingVarValue(190.0)).getValue(), DELTA);
		assertEquals(0.75483, label.compute(new LingVarValue(250.0)).getValue(), DELTA);
		assertEquals(0.60653, label.compute(new LingVarValue(300.0)).getValue(), DELTA);
		assertEquals(0.32465, label.compute(new LingVarValue(400.0)).getValue(), DELTA);
		assertEquals(0.13533, label.compute(new LingVarValue(500.0)).getValue(), DELTA);

	}

}
