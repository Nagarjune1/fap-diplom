package cz.upol.jfa.io;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cz.upol.automaton.io.ExportException;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.config.Configuration;
import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.colors.AbstractColorsDirectory;
import cz.upol.jfa.viewer.colors.ExportColors;
import cz.upol.jfa.viewer.painting.ViewerPainter;

public class ImageExporter {

	public ImageExporter() {
	}

	public void exportBitmap(BaseAutomatonToGUI automaton, File file,
			boolean colorful) throws ExportException {

		AbstractColorsDirectory colors = new ExportColors(colorful);
		ViewerPainter painter = new ViewerPainter(automaton, colors);

		Position size = automaton.getProvider().getSize();
		int width = size.getIntX();
		int height = size.getIntY();

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D graphics = img.createGraphics();
		painter.paintJFA(graphics);

		String format = Configuration.get().getViewerParams().getExportFormat();

		try {
			ImageIO.write(img, format, file);
		} catch (IOException e) {
			throw new ExportException(e);
		}
	}
}
