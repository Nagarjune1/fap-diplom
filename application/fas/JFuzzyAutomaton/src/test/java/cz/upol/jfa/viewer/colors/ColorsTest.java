package cz.upol.jfa.viewer.colors;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class ColorsTest {

	@Test
	public void test() {
		testNotNullColors(new ColorsSet(//
				Color.BLACK, Color.WHITE), //
				"background, foreground");//

		testNotNullColors(new ColorsSet(
				//
				Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
				Color.GRAY, Color.GREEN), //
				"background, stateBg, labelBg, stateFg, externLabelsFg, edgeFg");//
	}

	private void testNotNullColors(ColorsSet colors, String name) {
		testNotNullColor(colors.getBackground(), name, "getBackground");
		testNotNullColor(colors.getEdgeArrow(), name, "getEdgeAdrrow");
		testNotNullColor(colors.getEdgeLabel(), name, "getEdgeLabel");
		testNotNullColor(colors.getFiniteText(), name, "getFiniteText");
		testNotNullColor(colors.getInitialText(), name, "getInitialText");
		testNotNullColor(colors.getLabelBg(), name, "getLabelBg");
		testNotNullColor(colors.getStateBg(), name, "getStateBg");
		testNotNullColor(colors.getStateCircle(), name, "getStateCircle");
		testNotNullColor(colors.getStateLabel(), name, "getStateLabel");
	}

	private void testNotNullColor(Color color, String objName, String fieldName) {
		String message = fieldName + " on " + objName + " is null";
		assertFalse(message, color == null);
	}

}
