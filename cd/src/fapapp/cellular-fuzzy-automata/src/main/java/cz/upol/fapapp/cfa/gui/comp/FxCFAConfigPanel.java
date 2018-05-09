package cz.upol.fapapp.cfa.gui.comp;

import java.awt.Color;

import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.ConfigGenerator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * JavaFX component displaing {@link CFAConfiguration}.
 * 
 * @author martin
 *
 */
public class FxCFAConfigPanel extends Canvas {

	public static final ColorModel DEFAULT_COLOR_MODEL = ColorModel.GRAY;

	private static final CFAConfiguration DEFAULT_CONFIG = createDefaultConfig();

	private final ObjectProperty<CFAConfiguration> configProperty;
	private final ObjectProperty<ColorModel> colorProperty;
	private final IntegerProperty scaleProperty;

	public FxCFAConfigPanel() {
		configProperty = new SimpleObjectProperty<>(DEFAULT_CONFIG);
		colorProperty = new SimpleObjectProperty<>(DEFAULT_COLOR_MODEL);
		scaleProperty = new SimpleIntegerProperty(1);

		configProperty.addListener(//
				(o) -> update());
		colorProperty.addListener(//
				(o) -> update());
		scaleProperty.addListener(//
				(o) -> update());

		update();
	}

	public ObjectProperty<CFAConfiguration> configProperty() {
		return configProperty;
	}

	public ObjectProperty<ColorModel> colorProperty() {
		return colorProperty;
	}

	public IntegerProperty scaleProperty() {
		return scaleProperty;
	}

	/**************************************************************************/

	public void update() {
		CFAConfiguration config = configProperty.get();
		int scale = scaleProperty.intValue();

		setWidth(config.getSize() * scale);
		setHeight(config.getSize() * scale);

		redraw(config);
	}

	protected void redraw(CFAConfiguration config) {
		GraphicsContext ctx = getGraphicsContext2D();
		ColorModel colors = colorProperty.getValue();
		int scale = scaleProperty.intValue();

		draw(config, ctx, colors, scale);
	}

	private void draw(CFAConfiguration config, GraphicsContext ctx, ColorModel colors, int scale) {

		config.forEach((i, j, cell) -> {

			Color color = colors.convert(cell);

			Paint paint = colorToColor(color);
			ctx.setFill(paint);

			ctx.fillRect(i * scale, j * scale, 1 * scale, 1 * scale);
		});

	}

	/**************************************************************************/

	private static CFAConfiguration createDefaultConfig() {
		ConfigGenerator generator = new ConfigGenerator();

		return generator.generateBidiag(100);
	}

	private static javafx.scene.paint.Color colorToColor(java.awt.Color color) {
		double red = (double) color.getRed() / 256.0;
		double green = (double) color.getGreen() / 256.0;
		double blue = (double) color.getBlue() / 256.0;
		double alpha = 1.0;

		return new javafx.scene.paint.Color(red, green, blue, alpha);
	}

}
