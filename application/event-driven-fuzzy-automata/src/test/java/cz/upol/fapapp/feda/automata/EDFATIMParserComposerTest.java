package cz.upol.fapapp.feda.automata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.feda.automata.AutomataCreator;
import cz.upol.fapapp.feda.automata.EDFATIMComposer;
import cz.upol.fapapp.feda.automata.EDFATIMParser;
import cz.upol.fapapp.feda.automata.EventDrivenFuzzyAutomaton;

public class EDFATIMParserComposerTest {

	@Test
	public void testCompose() {
		EDFATIMComposer composer = new EDFATIMComposer();

		EventDrivenFuzzyAutomaton automaton = AutomataCreator.createSome();

		String expected = createTIMF();

		String actual = composer.compose(automaton);

		assertEquals(expected, actual);
	}

	@Test
	public void testParse() {
		EDFATIMParser parser = new EDFATIMParser();

		String input = createTIMF();

		EventDrivenFuzzyAutomaton expected = AutomataCreator.createSome();

		EventDrivenFuzzyAutomaton actual = parser.parse(input);

		assertEquals(expected, actual);
	}

	private String createTIMF() {
		return "" //
				+ "type:\n" //
				+ "	event driven fuzzy automaton\n" //
				+ "\n" //
				+ "states:\n" //
				+ "	q_0	q_1	q_2\n" //
				+ "\n" //
				+ "events:\n" //
				+ "	occured	is	pulse	if	unary\n" //
				+ "	the-X	is	hig	if	linear	40.0	60.0	100.0	100.0\n" //
				+ "	the-X	is	low	if	linear	0.0	0.0	40.0	60.0\n" //
				+ "\n" //
				+ "transition function:\n" //
				+ "	from	q_0	if	the-X	is	hig	to	q_2\n" //
				+ "	from	q_2	if	the-X	is	hig	to	q_2\n" //
				+ "	from	q_1	if	occured	is	pulse	to	q_2\n" //
				+ "	from	q_2	if	occured	is	pulse	to	q_1\n" //
				+ "	from	q_0	if	the-X	is	low	to	q_1\n" //
				+ "	from	q_1	if	the-X	is	low	to	q_0\n" //
				+ "\n" //
				+ "initial states:\n" //
				+ "	q_0/0.0	q_2/0.5	q_1/1.0\n" //
				+ "\n"; //
	}


}
