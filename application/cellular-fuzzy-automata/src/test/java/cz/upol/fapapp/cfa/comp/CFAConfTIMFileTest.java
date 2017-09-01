package cz.upol.fapapp.cfa.comp;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.misc.TwoDimArray;

public class CFAConfTIMFileTest {

	private final CFAConfTTIMParser parser = new CFAConfTTIMParser("cells");
	private final CFAConfTIMComposer composer = new CFAConfTIMComposer("cells");

	public CFAConfTIMFileTest() {
	}

	@Test
	public void testPrint() {
		CFAConfiguration config = createConfigA();
		config.print(System.out);

	}

	@Test
	public void testParse() {
		String file = createSomeFile();
		CFAConfiguration expected = createConfigA();

		CFAConfiguration actual = parser.parse(file);

		assertEquals(expected, actual);
	}

	@Test
	public void testCompose() {
		CFAConfiguration config = createConfigA();
		String expected = createGeneratedFile();

		String actual = composer.compose(config);

		assertEquals(expected, actual);
	}

	@Test
	public void testBiParseAndCompose() {
		String file = createGeneratedFile();

		CFAConfiguration parsed = parser.parse(file);
		String composed = composer.compose(parsed);

		assertEquals(file, composed);
	}

	@Test
	public void testBiComposeAndParse() {
		CFAConfiguration config = createConfigA();

		String composed = composer.compose(config);
		CFAConfiguration parsed = parser.parse(composed);

		assertEquals(config, parsed);
	}

	/*************************************************************************/

	private String createGeneratedFile() {
		return "" //
				+ "type:\n" //
				+ "	cellular fuzzy automata configuration\n" //
				+ "\n" //
				+ "size:\n" //
				+ "	4\n" //
				+ "\n" //
				+ "cells:\n" //
				+ "	0.1	0.2	0.3	0.4\n" //
				+ "	0.01	0.02	0.03	0.04\n" //
				+ "	0.5	0.6	0.7	0.8\n" //
				+ "	0.05	0.06	0.07	0.08\n" //
				+ "\n"; //
	}

	private String createSomeFile() {
		return "" //
				+ "type:\n" //
				+ "	cellular fuzzy automata configuration\n" //
				+ "size:\n" //
				+ "	4\n" //
				+ "cells:\n" //
				+ "	0.1,	0.2,	0.3,	0.4\n" //
				+ "	0.01,	0.02,	0.03,	0.04\n" //
				+ "	0.5,	0.6,	0.7,	0.8\n" //
				+ "	0.05,	0.06,	0.07,	0.08\n"; //
	}

	private CFAConfiguration createConfigA() {
		TwoDimArray<CellState> cells = new TwoDimArray<>(0, 4);
		cells.set(0, 0, new CellState(0.1));
		cells.set(0, 1, new CellState(0.2));
		cells.set(0, 2, new CellState(0.3));
		cells.set(0, 3, new CellState(0.4));

		cells.set(1, 0, new CellState(0.01));
		cells.set(1, 1, new CellState(0.02));
		cells.set(1, 2, new CellState(0.03));
		cells.set(1, 3, new CellState(0.04));

		cells.set(2, 0, new CellState(0.5));
		cells.set(2, 1, new CellState(0.6));
		cells.set(2, 2, new CellState(0.7));
		cells.set(2, 3, new CellState(0.8));

		cells.set(3, 0, new CellState(0.05));
		cells.set(3, 1, new CellState(0.06));
		cells.set(3, 2, new CellState(0.07));
		cells.set(3, 3, new CellState(0.08));

		CFAConfiguration config = new CommonConfiguration(4, cells);
		return config;
	}
}
