package cz.upol.jfa.config;

import java.awt.Color;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.upol.jfa.utils.xml.XMLChildrenIterable;
import cz.upol.jfa.utils.xml.XMLFileException;
import cz.upol.jfa.utils.xml.XMLStorerLoader;
import cz.upol.jfa.viewer.colors.ColorsSet;
import cz.upol.jfa.viewer.interactivity.PositioningConfig;
import cz.upol.jfa.viewer.painting.ViewerConfig;

public class ConfigStorerLoader extends XMLStorerLoader<Configuration> {

	private static final String VERSION = "0.1";

	private static final ConfigurationXMLUtils UTILS = new ConfigurationXMLUtils();

	@Override
	public String getRootElemName() {
		return "configuration";
	}

	@Override
	public String getVersion() {
		return VERSION;
	}

	@Override
	protected void processRootNodeChild(Element child, Configuration object)
			throws XMLFileException {

		switch (child.getNodeName()) {
		case "viewer-configuration":
			processViewerConfigNode(child, object.getViewerParams());
			break;
		case "positioning-configuration":
			processPositioningConfigNode(child, object.getPositioning());
			break;

		case "normal-viewer-colors":
			processColorsNode(child, object.getNormalViewColors());
			break;
		case "selected-viewer-colors":
			processColorsNode(child, object.getSelectedViewColors());
			break;
		case "disabled-viewer-colors":
			processColorsNode(child, object.getDisabledViewColors());
			break;

		case "colour-export-colors":
			processColorsNode(child, object.getColourExportColors());
			break;
		case "black-white-export-colors":
			processColorsNode(child, object.getBlackWhiteExportColors());
			break;

		}

	}

	private void processViewerConfigNode(Element elem, ViewerConfig params)
			throws XMLFileException {
		for (Element child : new XMLChildrenIterable(elem)) {
			switch (child.getAttribute("prop-name")) {
			case "arrow-label-padding":
				params.setArrowLabelPadding(UTILS.processPosition(child));
				break;
			case "arrow-padding":
				params.setArrowPadding(UTILS.processNumber(child));
				break;
			case "arrow-size":
				params.setArrowSize(UTILS.processNumber(child));
				break;
			case "export-format":
				params.setExportFormat(UTILS.processFormat(child,
						ViewerConfig.FORMATS));
				break;
			case "loop-center":
				params.setLoopCenter(UTILS.processPosition(child));
				break;
			case "loop-label-padding":
				params.setLoopLabelPading(UTILS.processPosition(child));
				break;
			case "loop-radius":
				params.setLoopRadius(UTILS.processNumber(child));
				break;
			case "state-midcirc-distance":
				params.setStateMidcircDistance(UTILS.processNumber(child));
				break;
			case "state-radius":
				params.setStateRadius(UTILS.processNumber(child));
				break;
			}
		}
	}

	private void processPositioningConfigNode(Element elem,
			PositioningConfig params) throws XMLFileException {
		for (Element child : new XMLChildrenIterable(elem)) {
			switch (child.getAttribute("prop-name")) {
			case "minimal-size":
				params.setMinimalSize(UTILS.processPosition(child));
				break;
			case "edge-click-range":
				params.setEdgeClickRange(UTILS.processNumber(child));
				break;
			case "first-position":
				params.setFirstPosition(UTILS.processPosition(child));
				break;
			case "generated-positions-distance":
				params.setGeneratedPositionsDistance(UTILS.processNumber(child));
				break;
			case "generated-positions-offset":
				params.setGeneratedPositionsOffset(UTILS.processPosition(child));
				break;
			case "min-states-distance":
				params.setMinStatesDistance(UTILS.processNumber(child));
				break;
			case "next-position-offset":
				params.setNextPositionOffset(UTILS.processPosition(child));
				break;
			case "state-click-range":
				params.setStateClickRange(UTILS.processNumber(child));
				break;
			}
		}

	}

	private void processColorsNode(Element elem, ColorsSet colors)
			throws XMLFileException {
		for (Element child : new XMLChildrenIterable(elem)) {
			Color color = UTILS.processColor(child);

			switch (child.getAttribute("prop-name")) {
			case "background":
				colors.setBackground(color);
				break;
			case "edge-arrow":
				colors.setEdgeArrow(color);
				break;
			case "edge-label":
				colors.setEdgeLabel(color);
				break;
			case "finite-text":
				colors.setFiniteText(color);
				break;
			case "initial-text":
				colors.setInitialText(color);
				break;
			case "label-background":
				colors.setLabelBg(color);
				break;
			case "state-background":
				colors.setStateBg(color);
				break;
			case "state-circle":
				colors.setStateCircle(color);
				break;
			case "state-label":
				colors.setStateLabel(color);
				break;
			}
		}

	}

	@Override
	protected void putToRootNode(Configuration object, Document document,
			Element rootNode) {
		putViewerConfig(rootNode, document, "viewer-configuration",
				object.getViewerParams());
		putPositioningConfig(rootNode, document, "positioning-configuration",
				object.getPositioning());
		putColors(rootNode, document, "normal-viewer-colors",
				object.getNormalViewColors());
		putColors(rootNode, document, "selected-viewer-colors",
				object.getSelectedViewColors());
		putColors(rootNode, document, "disabled-viewer-colors",
				object.getDisabledViewColors());
		putColors(rootNode, document, "colour-export-colors",
				object.getColourExportColors());
		putColors(rootNode, document, "black-white-export-colors",
				object.getBlackWhiteExportColors());

	}

	private void putViewerConfig(Element elem, Document document,
			String tagName, ViewerConfig params) {
		Element child = document.createElement(tagName);
		elem.appendChild(child);

		UTILS.addPosition(child, document, "arrow-label-padding",
				params.getArrowLabelPadding());
		UTILS.addNumber(child, document, "arrow-padding",
				params.getArrowPadding());
		UTILS.addNumber(child, document, "arrow-size", params.getArrowSize());
		UTILS.addFormat(child, document, "format", params.getExportFormat());
		UTILS.addPosition(child, document, "loop-center",
				params.getLoopCenter());
		UTILS.addPosition(child, document, "loop-label-padding",
				params.getLoopLabelPading());
		UTILS.addNumber(child, document, "loop-radius", params.getLoopRadius());
		UTILS.addNumber(child, document, "state-midcirc-distance",
				params.getStateMidcircDistance());
		UTILS.addNumber(child, document, "state-radius",
				params.getStateRadius());

	}

	private void putPositioningConfig(Element elem, Document document,
			String tagName, PositioningConfig params) {
		Element child = document.createElement(tagName);
		elem.appendChild(child);

		UTILS.addPosition(child, document, "minimal-size",
				params.getMinimalSize());
		UTILS.addPosition(child, document, "first-position",
				params.getFirstPosition());
		UTILS.addPosition(child, document, "generated-positions-offset",
				params.getGeneratedPositionsOffset());
		UTILS.addPosition(child, document, "next-position-offset",
				params.getNextPositionOffset());
		UTILS.addNumber(child, document, "edge-click-range",
				params.getEdgeClickRange());
		UTILS.addNumber(child, document, "generated-positions-distance",
				params.getGeneratedPositionsDistance());
		UTILS.addNumber(child, document, "min-states-distance",
				params.getMinStatesDistance());
		UTILS.addNumber(child, document, "state-click-range",
				params.getStateClickRange());
	}

	private void putColors(Element elem, Document document, String tagName,
			ColorsSet colors) {
		Element child = document.createElement(tagName);
		elem.appendChild(child);

		UTILS.addColor(child, document, "background", colors.getBackground());
		UTILS.addColor(child, document, "edge-arrow", colors.getEdgeArrow());
		UTILS.addColor(child, document, "edge-label", colors.getEdgeLabel());
		UTILS.addColor(child, document, "finite-text", colors.getFiniteText());
		UTILS.addColor(child, document, "initial-text", colors.getInitialText());
		UTILS.addColor(child, document, "label-background", colors.getLabelBg());
		UTILS.addColor(child, document, "state-background", colors.getStateBg());
		UTILS.addColor(child, document, "state-circle", colors.getStateCircle());
		UTILS.addColor(child, document, "state-label", colors.getStateLabel());
	}

}
