package cz.upol.fapapp.cfa.automata;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import cz.upol.fapapp.cfa.impls.common.BivalentGameOfLifeCFA;

@Deprecated
public class CFATIMFileTest {
	private final CFATIMParser parser = new CFATIMParser();
	private final CFATIMComposer composer = new CFATIMComposer();

	public CFATIMFileTest() {
	}

	/*************************************************************************/

	@Test
	@Ignore
	public void testGameOfLifeBi() {
		int size = 100;
		CellularFuzzyAutomata automata = new BivalentGameOfLifeCFA(size);

		String file = composer.compose(automata);
		CellularFuzzyAutomata parsed = parser.parse(file);

		assertEquals(automata.toString(), parsed.toString());
		assertEquals(automata, parsed);
	}
}
