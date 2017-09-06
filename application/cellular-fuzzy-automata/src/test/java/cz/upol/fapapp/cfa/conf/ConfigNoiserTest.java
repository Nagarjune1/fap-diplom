package cz.upol.fapapp.cfa.conf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.cfa.automata.CellState;

public class ConfigNoiserTest {

	@Test
	public void test() {
		CFAConfiguration config = new CFAConfiguration(10, new CellState(0.5));

		ConfigNoiser noiser = new ConfigNoiser();

		assertEquals(100, noiser.computeCount(config, 1.0));
		assertEquals(50, noiser.computeCount(config, 0.5));
		assertEquals(1, noiser.computeCount(config, 0.01));

		config.print(System.out);

		noiser.addNoiseSaltAndPepper(config, 42, 0.5, 0.1);

		config.print(System.out);
		
		noiser.addImpulseNoise(config, 42, 1.0);

		config.print(System.out);
	}

}
