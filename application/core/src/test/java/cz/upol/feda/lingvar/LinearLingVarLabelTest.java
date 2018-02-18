package cz.upol.feda.lingvar;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinearLingVarLabelTest {
	private static final double EPSILON = 0.001; 

	@Test
	public void testComputeLinear() {
		assertEquals(0.25, LinearLingVarLabel.compute(0.0, 4.0, 1.0).getValue(), EPSILON);
		assertEquals(0.50, LinearLingVarLabel.compute(0.0, 4.0, 2.0).getValue(), EPSILON);
		assertEquals(0.75, LinearLingVarLabel.compute(0.0, 4.0, 3.0).getValue(), EPSILON);
	
		assertEquals(0.2, LinearLingVarLabel.compute(5.0, 10.0, 6.0).getValue(), EPSILON);
		assertEquals(0.5, LinearLingVarLabel.compute(5.0, 10.0, 7.5).getValue(), EPSILON);
		assertEquals(0.75, LinearLingVarLabel.compute(5.0, 10.0, 8.75).getValue(), EPSILON);
		
		
		assertEquals(0.45, LinearLingVarLabel.compute(0.0, 1.0, 0.45).getValue(), EPSILON);
		assertEquals(0.55, LinearLingVarLabel.compute(0.0, 1.0, 0.55).getValue(), EPSILON);
		
		assertEquals(0.55, LinearLingVarLabel.compute(1.0, 0.0, 0.45).getValue(), EPSILON);
		assertEquals(0.45, LinearLingVarLabel.compute(1.0, 0.0, 0.55).getValue(), EPSILON);
	
	}
	
	
	
	
	@Test
	public void testLabels() {
		LinearLingVarLabel low = new LinearLingVarLabel("low", // 
				new LingVarValue(Double.NEGATIVE_INFINITY), new LingVarValue(0.0), new LingVarValue(20.0), new LingVarValue(40.0));
		LinearLingVarLabel mid = new LinearLingVarLabel("middle", // 
				new LingVarValue(20.0), new LingVarValue(40.0), new LingVarValue(60.0), new LingVarValue(80.0));
		LinearLingVarLabel hig = new LinearLingVarLabel("high",  //
				new LingVarValue(60.0), new LingVarValue(80.0), new LingVarValue(110.0), new LingVarValue(110.0));
		
		
		checkDegrees(low, mid, hig, 1.0, 0.0, 0.0, 00.0);
		checkDegrees(low, mid, hig, 1.0, 0.0, 0.0, 10.0);
		checkDegrees(low, mid, hig, 1.0, 0.0, 0.0, 20.0);
		checkDegrees(low, mid, hig, 0.5, 0.5, 0.0, 30.0);
		checkDegrees(low, mid, hig, 0.0, 1.0, 0.0, 40.0);
		checkDegrees(low, mid, hig, 0.0, 1.0, 0.0, 50.0);
		checkDegrees(low, mid, hig, 0.0, 1.0, 0.0, 60.0);
		checkDegrees(low, mid, hig, 0.0, 0.5, 0.5, 70.0);
		checkDegrees(low, mid, hig, 0.0, 0.0, 1.0, 80.0);
		checkDegrees(low, mid, hig, 0.0, 0.0, 1.0, 90.0);
		checkDegrees(low, mid, hig, 0.0, 0.0, 1.0, 100.0);

		
		
		
	}

	private void checkDegrees(LinearLingVarLabel low, LinearLingVarLabel mid,
			LinearLingVarLabel hig, double degreeOfLow, double degreeOfMid, double degreeOfHig, double value) {
		
		assertEquals("at low", degreeOfLow, low.compute(new LingVarValue(value)).getValue(), EPSILON);
		assertEquals("at mid", degreeOfMid, mid.compute(new LingVarValue(value)).getValue(), EPSILON);
		assertEquals("at hig", degreeOfHig, hig.compute(new LingVarValue(value)).getValue(), EPSILON);
	
	}
	

}
