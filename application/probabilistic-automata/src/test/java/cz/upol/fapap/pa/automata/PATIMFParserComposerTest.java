package cz.upol.fapap.pa.automata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PATIMFParserComposerTest {

	@Test
	public void testCompose() {
		ProbablisticAutomaton automaton = AutomataCreator.createSome();

		PATIMFComposer composer = new PATIMFComposer();
		String actual = composer.compose(automaton);

		String expected = createTIMF();

		assertEquals(actual, expected);
	}

	@Test
	public void testParse() {

		PATIMFParser parser = new PATIMFParser();
		String input = createTIMF();

		ProbablisticAutomaton actual = parser.parse(input);
		ProbablisticAutomaton expected = AutomataCreator.createSome();

		assertEquals(actual, expected);

	}

	private String createTIMF() {
		String expected = "" //
				+ "type:\n" //
				+ "	probabilistic automaton\n" //
				+ "\n" //
				+ "alphabet:\n" //
				+ "	a	b\n" //
				+ "\n" //
				+ "states:\n" //
				+ "	q_0	q_2	q_1\n" //
				+ "\n" //
				+ "transition function:\n" //
				+ "	q_1	b	q_2	0.9\n" //
				+ "	q_0	b	q_1	0.7\n" //
				+ "	q_0	a	q_1	1.0\n" //
				+ "	q_2	b	q_0	0.8\n" //
				+ "	q_1	b	q_0	0.3\n" //
				+ "\n" //
				+ "initial states:\n" //
				+ "	q_0	q_1\n" //
				+ "\n" //
				+ "final states:\n" //
				+ "	q_2	q_1\n" //
				+ "\n"; //
		return expected;
	}
}
