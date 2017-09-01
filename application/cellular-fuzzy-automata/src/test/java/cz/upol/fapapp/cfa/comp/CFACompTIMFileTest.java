package cz.upol.fapapp.cfa.comp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.automata.CellularFuzzyAutomata;
import cz.upol.fapapp.cfa.misc.TwoDimArray;

public class CFACompTIMFileTest {
	private final CellularFuzzyAutomata automata = new CellularFuzzyAutomata(4, null, null);
	private final CFACompTIMParser parser = new CFACompTIMParser(automata);
	private final CFACompTIMComposer composer = new CFACompTIMComposer();

	public CFACompTIMFileTest() {
	}

	/*************************************************************************/

	@Test
	public void testPrint() {
		CellularAutomataComputation comp = createCompA();
		comp.print(System.out);

	}

	@Test
	public void testParse() {
		String file = createSomeFile();
		CellularAutomataComputation expected = createCompA();

		CellularAutomataComputation actual = parser.parse(file);

		assertEquals(expected, actual);
	}

	@Test
	public void testCompose() {
		CellularAutomataComputation comp = createCompA();
		String expected = createGeneratedFile();

		String actual = composer.compose(comp);

		assertEquals(expected, actual);
	}

	@Test
	public void testBiParseAndCompose() {
		String file = createGeneratedFile();

		CellularAutomataComputation parsed = parser.parse(file);
		String composed = composer.compose(parsed);

		assertEquals(file, composed);
	}

	@Test
	public void testBiComposeAndParse() {
		CellularAutomataComputation comp = createCompA();

		String composed = composer.compose(comp);
		CellularAutomataComputation parsed = parser.parse(composed);

		assertEquals(comp, parsed);
	}

	/*************************************************************************/

	private String createGeneratedFile() {
		return "" //
				+ "type:\n" //
				+ "	cellular fuzzy automata computation\n" //
				+ "\n" //
				+ "size:\n" //
				+ "	4\n" //
				+ "\n" //
				+ "configuration:\n" //
				+ "	0.1	0.2	0.3	0.4\n" //
				+ "	0.01	0.02	0.03	0.04\n" //
				+ "	0.5	0.6	0.7	0.8\n" //
				+ "	0.05	0.06	0.07	0.08\n" //
				+ "\n" //
				+ "generation:\n" //
				+ "	42\n" //
				+ "\n"; //
	}

	private String createSomeFile() {
		return "" //
				+ "type:\n" //
				+ "	cellular fuzzy automata computation\n" //
				+ "size:\n" //
				+ "	4\n" //
				+ "config:\n" //
				+ "	0.1,	0.2,	0.3,	0.4\n" //
				+ "	0.01,	0.02,	0.03,	0.04\n" //
				+ "	0.5,	0.6,	0.7,	0.8\n" //
				+ "	0.05,	0.06,	0.07,	0.08\n" //
				+ "current generation:\n" //
				+ "	42\n"; //
	}

	private CellularAutomataComputation createCompA() {
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
		CellularAutomataComputation comp = new CellularAutomataComputation(automata, config, 42);
		return comp;
	}

}
