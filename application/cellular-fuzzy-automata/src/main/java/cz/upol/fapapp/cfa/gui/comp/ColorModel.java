package cz.upol.fapapp.cfa.gui.comp;

import java.awt.Color;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

import cz.upol.fapapp.cfa.automata.CellState;

public enum ColorModel {
	GRAY(new GrayColorMapper()), //
	RED_TO_RED_HSV(new HSVColorMapper((float) 1.0, (float) 0.8)), //
	ORANGE_TO_PURPLE_HSV(new HSVColorMapper((float) 1.0, (float) 0.8, 0.1)), //
	RED_TO_GREEN_HSV(new HSVColorMapper((float) 1.0, (float) 0.8, 0.0, 1.0/3.0)), //
	GREEN_TO_BLUE_HSV(new HSVColorMapper((float) 1.0, (float) 0.8, 1/3.0, 2/3.0)), //
	RED(new RGBColorMapper(Color.RED)), //
	CYAN(new RGBColorMapper(Color.CYAN)); //

	/**************************************************************************/

	private final Function<CellState, Color> mapper;

	private ColorModel(Function<CellState, Color> mapper) {
		this.mapper = mapper;
	}

	public Color convert(CellState cell) {
		return mapper.apply(cell);
	}

	/**************************************************************************/

	public static class GrayColorMapper implements Function<CellState, Color> {

		@Override
		public Color apply(CellState cell) {
			double val = cell.getValue();
			float floatVal = (float) val;

			return new Color(floatVal, floatVal, floatVal);
		}

	}

	public static class HSVColorMapper implements Function<CellState, Color> {

		private final float brigtness;
		private final float saturation;
		private final DoubleUnaryOperator hueMapper;

		protected HSVColorMapper(float brigtness, float saturation, DoubleUnaryOperator hueMapper) {
			super();
			this.brigtness = brigtness;
			this.saturation = saturation;
			this.hueMapper = hueMapper;
		}

		public HSVColorMapper(float brigtness, float saturation) {
			this(brigtness, saturation, //
					(v) -> v);
		}

		public HSVColorMapper(float brigtness, float saturation, double padding) {
			this(brigtness, saturation, //
					(v) -> -2 * padding * v + v + padding);
		}

		public HSVColorMapper(float brigtness, float saturation, double hueOfZero, double hueOfOne) {
			this(brigtness, saturation, //
					(v) -> (hueOfOne - hueOfZero) * v + hueOfZero);
		}

		@Override
		public Color apply(CellState cell) {
			double cellVal = cell.getValue();
			double hueVal = hueMapper.applyAsDouble(cellVal);
			float floatVal = (float) hueVal;

			return Color.getHSBColor(floatVal, saturation, brigtness);
		}

	}

	public static class RGBColorMapper implements Function<CellState, Color> {

		private final Color color;

		public RGBColorMapper(Color color) {
			super();
			this.color = color;
		}

		@Override
		public Color apply(CellState cell) {
			double val = cell.getValue();
			float floatVal = (float) val;

			float r = 0, g = 0, b = 0;
			if (color == Color.RED || color == Color.MAGENTA || color == Color.YELLOW || color == Color.WHITE) {
				r = floatVal;
			}
			if (color == Color.GREEN || color == Color.YELLOW || color == Color.CYAN || color == Color.WHITE) {
				g = floatVal;
			}
			if (color == Color.BLUE || color == Color.CYAN || color == Color.MAGENTA || color == Color.WHITE) {
				b = floatVal;
			}

			return new Color(r, g, b);
		}

	}
}
