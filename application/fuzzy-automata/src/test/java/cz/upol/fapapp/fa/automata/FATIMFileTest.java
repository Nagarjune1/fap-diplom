package cz.upol.fapapp.fa.automata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FATIMFileTest {

	@Test
	public void testCompose() {
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();
		
		FATIMComposer composer = new FATIMComposer();
		String actual = composer.compose(automaton);
		
		String expected = createComposedOf1();
		
		assertEquals(actual, expected);	
	}
	
	@Test
	public void testParse() {
		
		FATIMParser parser = new FATIMParser();
		String input = createComposedOf1();
		
		
		BaseFuzzyAutomaton actual = parser.parse(input);  
		FuzzyAutomaton expected = TestAutomataCreator.createAutomaton1();
	
		assertEquals(actual, expected);
		
	}
	
	//TODO test parse and compose bi-combinations?
	
	

	private String createComposedOf1() {
		String expected = ""
				+ "type:\n"
				+ "	fuzzy automaton\n"
				+ "\n"
				+ "alphabet:\n"
				+ "	a	b\n"
				+ "\n"
				+ "states:\n"
				+ "	q_0	q_1\n"
				+ "\n"
				+ "transition function:\n"
				+ "	q_0	a	q_1	0.5\n"
				+ "	q_0	a	q_0	0.2\n"
				+ "	q_1	b	q_0	1.0\n"
				+ "\n"
				+ "initial states:\n"
				+ "	q_0/1.0	q_1/0.1\n"
				+ "\n"
				+ "final states:\n"
				+ "	q_0/0.4	q_1/0.8\n"
				+ "\n";
		return expected;
	}

}
