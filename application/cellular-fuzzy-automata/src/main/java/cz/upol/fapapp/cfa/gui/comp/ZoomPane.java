package cz.upol.fapapp.cfa.gui.comp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ZoomPane extends Pane {

	private final DoubleProperty zoomProp;

	public ZoomPane() {
		super();

		this.zoomProp = createZoomProp();
	}

	public ZoomPane(Node... children) {
		super(children);

		this.zoomProp = createZoomProp();
	}

	private DoubleProperty createZoomProp() {
		DoubleProperty prop = new SimpleDoubleProperty(1.0);

		this.scaleXProperty().bind(prop);
		this.scaleYProperty().bind(prop);
		this.scaleZProperty().bind(prop);

		return prop;
	}

	
	public DoubleProperty getZoom() {
		return zoomProp;
	}

	public void changeZoomTo(double zoom) {
		zoomProp.set(zoom);
	}

}
