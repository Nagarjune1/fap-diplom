package cz.upol.feda.lingvar;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParser;

public class LingVarsTIMParserComposerTest {

	@Test
	public void testCompose() {
		TIMObjectComposer<Set<LingvisticVariable>> composer = new LingVarsTIMComposer("lingvistic variables",
				"variables");

		Set<LingvisticVariable> vars = createVars();
		String expected = createTIMF();

		String actual = composer.compose(vars);

		assertEquals(expected, actual);

	}

	@Test
	public void testParse() {
		TIMObjectParser<Set<LingvisticVariable>> parser = new LingVarsTIMParser("lingvistic variables", "variables",
				"vars");

		String timf = createTIMF();
		Set<LingvisticVariable> expected = createVars();

		Set<LingvisticVariable> actual = parser.parse(timf);
		
		assertEquals(expected, actual);
	}

	private Set<LingvisticVariable> createVars() {
		BaseLingVarLabel labelXL = new LinearLingVarLabel("low", //
				new LingVarValue(0.0), new LingVarValue(0.0), new LingVarValue(40.0), new LingVarValue(60.0));
		BaseLingVarLabel labelXH = new LinearLingVarLabel("hig", //
				new LingVarValue(40.0), new LingVarValue(60.0), new LingVarValue(100.0), new LingVarValue(100.0));
		BaseLingVarLabel labelPO = new UnaryVarLabel("pulse");

		LingvisticVariable varX = new LingvisticVariable("the-X", labelXL, labelXH);
		LingvisticVariable varO = new LingvisticVariable("occured", labelPO);

		return new HashSet<>(Arrays.asList(varX, varO));

	}

	private String createTIMF() {
		return "" //
				+ "type:\n" //
				+ "	lingvistic variables\n" //
				+ "\n" //
				+ "variables:\n" //
				+ "	occured	is	pulse	if	unary\n" //
				+ "	the-X	is	hig	if	linear	40.0	60.0	100.0	100.0\n" //
				+ "	the-X	is	low	if	linear	0.0	0.0	40.0	60.0\n" //
				+ "\n"; //

	}

}
