package cz.upol.fapapp.cfa.automata;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.fapapp.cfa.mu.BivalGameOfLifeImpl;
import cz.upol.fapapp.cfa.mu.CFAOuterCellSupplier;
import cz.upol.fapapp.cfa.mu.CFATransitionFunction;

public class CFATIMFileTest {
	private final CFATIMParser parser = new CFATIMParser();
	private final CFATIMComposer composer = new CFATIMComposer();

	public CFATIMFileTest() {
	}

	/*************************************************************************/
	
	@Test
	public void testGameOfLifeBi() {
		int size = 100;
		CFATransitionFunction mu = new BivalGameOfLifeImpl();
		CFAOuterCellSupplier outers = new BivalGameOfLifeImpl();
		CellularFuzzyAutomata automata = new CellularFuzzyAutomata(size, mu, outers);
		
		String file = composer.compose(automata);
		CellularFuzzyAutomata parsed = parser.parse(file);
		
		assertEquals(automata.toString(), parsed.toString());
		assertEquals(automata, parsed);
	}
}
