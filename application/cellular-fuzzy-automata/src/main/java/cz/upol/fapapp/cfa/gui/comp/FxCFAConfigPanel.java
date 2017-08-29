package cz.upol.fapapp.cfa.gui.comp;

import java.awt.Color;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.config.CFAConfiguration;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class FxCFAConfigPanel extends Canvas {

	public static final ColorModel DEFAULT_COLOR_MODEL = ColorModel.GRAY;
	
	private final ObjectProperty<CFAConfiguration> configProperty;
	private final ObjectProperty<ColorModel> colorProperty;

	public FxCFAConfigPanel() {
		configProperty = new SimpleObjectProperty<>();
		colorProperty = new SimpleObjectProperty<>(DEFAULT_COLOR_MODEL);

		configProperty.addListener(//
				(o, oldVal, newVal) -> update());
		colorProperty.addListener(//
				(o, oldVal, newVal) -> update());
	}

	public ObjectProperty<CFAConfiguration> getConfig() {
		return configProperty;
	}

	public ObjectProperty<ColorModel> getColor() {
		return colorProperty;
	}

	public void update() {
		CFAConfiguration config = configProperty.get();
		if (config == null) {
			return;
		}
		
		setWidth(config.getSize());
		setHeight(config.getSize());

		redraw(config);
	}

	protected void redraw(CFAConfiguration config) {
		GraphicsContext ctx = getGraphicsContext2D();
		ColorModel colors = colorProperty.get();

		draw(config, ctx, colors);
	}

	private void draw(CFAConfiguration config, GraphicsContext ctx, ColorModel colors) {
		
		
		for (int i = 0; i < config.getSize(); i++) {
			for (int j = 0; j < config.getSize(); j++) {

				CellState cell = config.getCell(i, j);
				Color color = colors.convert(cell);

				Paint paint = colorToColor(color);
				ctx.setFill(paint);
				ctx.fillRect(i, j, 1, 1);
			}
		}
	}

	private static javafx.scene.paint.Color colorToColor(java.awt.Color color) {
		double red = (double) color.getRed() / 256.0;
		double green = (double) color.getGreen() / 256.0;
		double blue = (double) color.getBlue() / 256.0;
		double alpha = 1.0;

		return new javafx.scene.paint.Color(red, green, blue, alpha);
	}

}
