package cz.upol.jfa.config;

import java.awt.Color;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.upol.jfa.utils.xml.XMLFileException;
import cz.upol.jfa.viewer.Position;

public class ConfigurationXMLUtils {

	public ConfigurationXMLUtils() {
	}

	public void addFormat(Element elem, Document document, String propName,
			String format) {

		Element child = createAndAdd(elem, document, "format", propName);

		child.setAttribute("format", format);
	}

	public void addPosition(Element elem, Document document, String propName,
			Position position) {

		Element child = createAndAdd(elem, document, "position", propName);

		child.setAttribute("x", Integer.toString(position.getIntX()));
		child.setAttribute("y", Integer.toString(position.getIntY()));

	}

	public Element createAndAdd(Element elem, Document document,
			String elemName, String propName) {

		Element child = document.createElement(elemName);
		elem.appendChild(child);
		child.setAttribute("prop-name", propName);

		return child;
	}

	public void addNumber(Element elem, Document document, String propName,
			int number) {

		Element child = createAndAdd(elem, document, "number", propName);

		child.setAttribute("value", Integer.toString(number));

	}

	public void addColor(Element elem, Document document, String propName,
			Color color) {
		Element child = createAndAdd(elem, document, "color", propName);

		child.setAttribute("rgba", colorToRGBA(color));

	}

	public String processFormat(Element child, Set<String> formats)
			throws XMLFileException {
		String suffix = child.getAttribute("suffix");

		if (!formats.contains(suffix)) {
			throw new XMLFileException("Unsupported format " + suffix
					+ ". Supported only: " + formats);
		} else {
			return suffix;
		}

	}

	public int processNumber(Element child) throws XMLFileException {
		String value = null;

		try {
			value = child.getAttribute("value");
			return Integer.parseInt(value);
		} catch (Exception e) {
			throw new XMLFileException(value + " is not a number", e);
		}
	}

	public Position processPosition(Element child) throws XMLFileException {
		String xVal = null, yVal = null;

		try {
			xVal = child.getAttribute("x");
			yVal = child.getAttribute("y");
			int x = Integer.parseInt(xVal);
			int y = Integer.parseInt(yVal);

			return new Position(x, y);
		} catch (Exception e) {
			throw new XMLFileException("(" + xVal + ", " + yVal
					+ ") is not a valid position", e);
		}
	}

	public Color processColor(Element child) throws XMLFileException {
		String rgb = child.getAttribute("rgb");
		if (rgb.isEmpty()) {
			rgb = child.getAttribute("rgba");
		}
		if (rgb.isEmpty()) {
			throw new XMLFileException("Missing color's rgb or rgba attribute");
		}
		Color color = colorFromRGBA(rgb);
		if (color == null) {
			throw new XMLFileException(rgb + " is wrong RGB(A) color.");
		} else {
			return color;
		}

	}

	public static Color colorFromRGBA(String str) {
		try {
			if (str.charAt(0) == '#') {
				str = str.substring(1);
			}
			if (str.length() < 8) {
				str = str + "FF";
			}

			int red = Integer.valueOf(str.substring(0, 2), 16);
			int green = Integer.valueOf(str.substring(2, 4), 16);
			int blue = Integer.valueOf(str.substring(4, 6), 16);
			int alpha = Integer.valueOf(str.substring(6, 8), 16);

			return new Color(red, green, blue, alpha);
		} catch (Exception e) {
			return null;
		}
	}

	public static String colorToRGBA(Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		int alpha = color.getAlpha();

		StringBuilder stb = new StringBuilder("#");
		stb.append(String.format("%02X", red));
		stb.append(String.format("%02X", green));
		stb.append(String.format("%02X", blue));

		if (alpha != 255) {
			stb.append(String.format("%02X", alpha));
		}

		return stb.toString();
	}

}
