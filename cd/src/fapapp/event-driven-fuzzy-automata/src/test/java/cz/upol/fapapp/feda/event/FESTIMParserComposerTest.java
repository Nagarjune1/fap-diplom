package cz.upol.fapapp.feda.event;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.feda.automata.AutomataCreator;
import cz.upol.fapapp.feda.event.FESTIMComposer;
import cz.upol.fapapp.feda.event.FESTIMParser;
import cz.upol.fapapp.feda.event.FuzzyEventsSequence;

public class FESTIMParserComposerTest {

	@Test
	public void testCompose() {
		FuzzyEventsSequence sequence = AutomataCreator.createSomeEvents(AutomataCreator.createSomeLingVars());

		String expected = createTIMF();
		

		FESTIMComposer composer = new FESTIMComposer();
		String actual = composer.compose(sequence);

		System.out.println(actual);
		
		assertEquals(expected, actual);
	}

	@Test
	public void testParse() {

		String input = createTIMF();

		FuzzyEventsSequence expected = AutomataCreator.createSomeEvents(AutomataCreator.createSomeLingVars());

		FESTIMParser parser = new FESTIMParser();
		FuzzyEventsSequence actual = parser.parse(input);

		assertEquals(expected, actual);
	}

	private String createTIMF() {
		return "" //
				+ "type:\n" //
				+ "	fuzzy events sequence\n" //
				+ "\n" //
				+ "variables:\n" //
				+ "	occured	is	pulse	if	unary\n" //
				+ "	the-X	is	hig	if	linear	40.0	60.0	100.0	100.0\n" //
				+ "	the-X	is	low	if	linear	0.0	0.0	40.0	60.0\n" //
				+ "\n" + //
				"events:\n" //
				+ "	the-X	is	1.0	occured	is	42.0\n" //
				+ "	the-X	is	45.0\n" //
				+ "	the-X	is	3.0	occured	is	42.0\n" //
				+ "	the-X	is	58.0	occured	is	42.0\n" //
				+ "\n"; //
	}

}
