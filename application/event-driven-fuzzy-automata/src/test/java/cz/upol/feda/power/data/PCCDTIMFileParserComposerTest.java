package cz.upol.feda.power.data;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.feda.power.data.PCCDTIMFileComposer;
import cz.upol.feda.power.data.PCCDTIMFileParser;
import cz.upol.feda.power.data.PowerConsumptionComputaitionData;

public class PCCDTIMFileParserComposerTest {

	@Test
	public void testParser() {

		String input = createTIMFile();

		PCCDTIMFileParser parser = new PCCDTIMFileParser();
		PowerConsumptionComputaitionData actual = parser.parse(input);

		PowerConsumptionComputaitionData expected = PCCDTestDataCreator.createData();

		System.out.println(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testComposer() {

		PowerConsumptionComputaitionData input = PCCDTestDataCreator.createData();

		PCCDTIMFileComposer composer = new PCCDTIMFileComposer();
		String actual = composer.compose(input);

		String expected = createTIMFile();

		System.out.println(actual);
		assertEquals(expected, actual);
		
	}


	private String createTIMFile() {
		return "" //
				+ "type:\n" //
				+ "	power consumption computation data\n" //
				+ "\n" //
				+ "melt:\n" //
				+ "	400.0\n" //
				+ "\n" //
				+ "devices:\n" //
				+ "	fan	is	warm	if	consumes	600.0\n" //
				+ "	fan	is	off	if	consumes	0.0\n" //
				+ "	fan	is	hot	if	consumes	900.0\n" //
				+ "	fan	is	cold	if	consumes	100.0\n" //
				+ "	lamp	is	off	if	consumes	0.0\n" //
				+ "	lamp	is	on	if	consumes	50.0\n" //
				+ "	tv	is	standby	if	consumes	20.0\n" //
				+ "	tv	is	off	if	consumes	0.0\n" //
				+ "	tv	is	on	if	consumes	300.0\n" //
				+ "\n" //
				+ "changes:\n" //
				+ "	decrease	if	-600.0\n" //
				+ "	increase	if	600.0\n" //
				+ "	no-change	if	0.0\n" //
				+ "\n" //
				+ "initial:\n" //
				+ "	lamp	is	off\n" //
				+ "	fan	is	off\n" //
				+ "	tv	is	off\n" //
				+ "\n" //
				+ "consumptions:\n" //
				+ "	10.0	230.0	480.0\n" //
				+ "\n"; //
	}

}
