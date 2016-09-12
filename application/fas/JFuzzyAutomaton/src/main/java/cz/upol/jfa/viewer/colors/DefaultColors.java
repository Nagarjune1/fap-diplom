package cz.upol.jfa.viewer.colors;

import java.awt.Color;

public class DefaultColors {
	public static ColorsSet getNormalViewColors() {
		return new ColorsSet(Color.WHITE, Color.YELLOW,
				ColorsSet.TRANSPARENT_WHITE, Color.GREEN, Color.GREEN,
				Color.GREEN);
	}

	public static ColorsSet getSelectedViewColors() {
		return new ColorsSet(Color.WHITE, Color.YELLOW,
				ColorsSet.TRANSPARENT_WHITE, Color.RED, Color.RED,
				Color.RED);
	}

	public static ColorsSet getDisabledViewColors() {
		return new ColorsSet(Color.LIGHT_GRAY, Color.DARK_GRAY);
	}

	public static ColorsSet getColourExportColors() {
		return new ColorsSet(ColorsSet.TRANSPARENT_WHITE,
				Color.YELLOW, ColorsSet.TRANSPARENT_WHITE, Color.GREEN,
				Color.GREEN, Color.GREEN);
	}

	public static ColorsSet getBlackWhiteExportColors() {
		return new ColorsSet(ColorsSet.TRANSPARENT_WHITE, Color.BLACK);
	}

}
