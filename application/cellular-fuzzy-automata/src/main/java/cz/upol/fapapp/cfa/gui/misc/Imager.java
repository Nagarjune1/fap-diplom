package cz.upol.fapapp.cfa.gui.misc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.comp.ColorModel;
import cz.upol.fapapp.cfa.gui.comp.FxCFAConfigPanel;
import cz.upol.fapapp.core.misc.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * Utility class performing interaction of {@link CFAConfiguration}s with the
 * images in {@value #WRITE_FORMAT}.
 * 
 * @author martin
 *
 */
public class Imager {

	private static final String WRITE_FORMAT = "png";

	public Imager() {
	}

	/**
	 * Read image from given file and convert to configuration.
	 * 
	 * @param imageFile
	 * @param chanel
	 * @return
	 * @throws IOException
	 */
	public CFAConfiguration imageToConfig(File imageFile, Color chanel) throws IOException {

		Logger.get().moreinfo("Loading config from image " + imageFile);
		try {
			Image image = readImage(imageFile);
			return convert(image, chanel);
		} catch (Exception e) {
			throw new IOException("Image load failed", e);
		}
	}

	/**
	 * Convert configuration to image file.
	 * 
	 * @param config
	 * @param imageFile
	 * @param colors
	 * @param scale
	 * @throws IOException
	 */
	public void configToImage(CFAConfiguration config, File imageFile, ColorModel colors, int scale)
			throws IOException {

		Logger.get().moreinfo("Rendering config to image " + imageFile);

		Image image = convert(config, colors, scale);
		saveImage(image, imageFile);
	}

	/*************************************************************************/

	private Image readImage(File imageFile) throws IOException {
		BufferedImage swimage = ImageIO.read(imageFile);
		return SwingFXUtils.toFXImage(swimage, null);
	}

	private void saveImage(Image image, File imageFile) throws IOException {
		BufferedImage swimage = SwingFXUtils.fromFXImage(image, null);
		ImageIO.write(swimage, WRITE_FORMAT, imageFile);
	}

	/**************************************************************************/

	private CFAConfiguration convert(Image image, Color chanel) {
		int size = inferImageSize(image);
		PixelReader r = image.getPixelReader();

		CFAConfiguration config = new CFAConfiguration(size);

		config.forEach((i, j, xVal) -> {
			Color color = r.getColor(i, j);
			double value = applyChanel(chanel, color);
			CellState cell = new CellState(value);
			config.setCell(i, j, cell);
		});

		return config;
	}

	private Image convert(CFAConfiguration config, ColorModel colors, int scale) {
		// int size = config.getSize() * scale;

		FxCFAConfigPanel pane = new FxCFAConfigPanel();
		pane.configProperty().set(config);
		pane.colorProperty().setValue(colors);
		pane.scaleProperty().setValue(scale);

		// Parent parent = new VBox(pane);
		// Scene scene = new Scene(parent, size, size);

		SnapshotParameters params = new SnapshotParameters();
		return pane.snapshot(params, null);
	}

	/**************************************************************************/

	private double applyChanel(Color chanel, Color color) {
		if (Color.RED.equals(chanel)) {
			return color.getRed();
		}
		if (Color.GREEN.equals(chanel)) {
			return color.getGreen();
		}
		if (Color.BLUE.equals(chanel)) {
			return color.getBlue();
		}
		if (Color.GRAY.equals(chanel)) {
			return color.getBrightness();
		}

		throw new IllegalArgumentException("Unknown chanel (allowed red, green, blue and gray) " + chanel);
	}

	private int inferImageSize(Image image) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();

		if (width < height) {
			Logger.get().warning("Image is in portrait format. Will be cropped to square");
		}
		if (width > height) {
			Logger.get().warning("Image is in landscape format. Will be cropped to square");
		}

		return width;
	}

	/**************************************************************************/

}
