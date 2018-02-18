package cz.upol.fapapp.cfa.gui.misc;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.ConfigGenerator;
import cz.upol.fapapp.cfa.gui.comp.ColorModel;
import javafx.scene.paint.Color;

public class ImagerTest {

	private static final double EPSILON = 0.01;

	private final Imager imager = new Imager();

	public ImagerTest() {
	}

	/**************************************************************************/

	@Test
	@Ignore("Due the performance test skipped")//TODO wtf
	public void testBi() throws IOException {
		ConfigGenerator gen = new ConfigGenerator();
		CFAConfiguration config = gen.generateDoubles(250, 42);

		File file;
		try {
			file = File.createTempFile("image", ".png");
			System.out.println("Will use tmp file: " + file);
		} catch (IOException e) {
			System.err.println("Warining: Cannot create test file, test skipped.");
			return;
		}

		ColorModel colors = ColorModel.GRAY;
		int scale = 1;
		Color chanel = Color.GRAY;

		imager.configToImage(config, file, colors, scale);
		CFAConfiguration done = imager.imageToConfig(file, chanel);

		// assertEquals(done.toString(), config.toString());
		compare(done, config);
	}

	/**************************************************************************/

	private void compare(CFAConfiguration expected, CFAConfiguration actual) {
		Iterator<CellState> expIt = expected.toArray().iterator();
		Iterator<CellState> actIt = actual.toArray().iterator();

		while (expIt.hasNext() && actIt.hasNext()) {
			CellState expCell = expIt.next();
			CellState actCell = actIt.next();

			double expVal = expCell.getValue();
			double actVal = actCell.getValue();

			assertEquals(expVal, actVal, EPSILON);
		}
	}

}
