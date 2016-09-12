package cz.upol.jfa.config;

import java.awt.Color;

import cz.upol.jfa.StorerLoaderAbstractTest;
import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.colors.ColorsSet;
import cz.upol.jfa.viewer.interactivity.PositioningConfig;
import cz.upol.jfa.viewer.painting.ViewerConfig;

public class ConfigStorerLoaderTest extends StorerLoaderAbstractTest<Configuration> {

	public ConfigStorerLoaderTest() {
		super(new ConfigStorerLoader());
	}

	@Override
	public Configuration createNewObject() {
		return new Configuration();
	}

	@Override
	public Configuration createTestingObject() {
		return createConfiguration();
	}

	private Configuration createConfiguration() {
		Configuration configuration = new Configuration();

		initPositioning(configuration.getPositioning());
		initViewParams(configuration.getViewerParams());

		initNormalColors(configuration.getNormalViewColors());
		initSelectedColors(configuration.getSelectedViewColors());
		initDisabledColors(configuration.getDisabledViewColors());

		initColourColors(configuration.getColourExportColors());
		initBlackWhiteColors(configuration.getBlackWhiteExportColors());

		return configuration;
	}

	private void initBlackWhiteColors(ColorsSet colors) {
		colors.setForegrounds(Color.MAGENTA);
		colors.setBackgrounds(Color.CYAN);
	}

	private void initDisabledColors(ColorsSet colors) {
		colors.setForegrounds(Color.MAGENTA);
		colors.setBackgrounds(Color.CYAN);
	}

	private void initColourColors(ColorsSet colors) {
		colors.setForegrounds(Color.MAGENTA);
		colors.setBackgrounds(Color.CYAN);
	}

	private void initSelectedColors(ColorsSet colors) {
		colors.setForegrounds(Color.MAGENTA);
		colors.setBackgrounds(Color.CYAN);
	}

	private void initNormalColors(ColorsSet colors) {
		colors.setForegrounds(Color.MAGENTA);
		colors.setBackgrounds(Color.CYAN);
	}

	private void initViewParams(ViewerConfig params) {
		params.setArrowLabelPadding(new Position(200, 400));
		params.setArrowPadding(199);
		params.setArrowSize(444);
		params.setExportFormat("TIFF");
		params.setLoopCenter(new Position(-99, -101));
		params.setLoopLabelPading(new Position(42, 69));
		params.setLoopRadius(9999);
		params.setStateMidcircDistance(128);
		params.setStateRadius(999);
	}

	private void initPositioning(PositioningConfig params) {
		params.setMinimalSize(new Position(-56, -83));
		params.setEdgeClickRange(-100);
		params.setFirstPosition(new Position(1000, 1000));
		params.setGeneratedPositionsDistance(999);
		params.setGeneratedPositionsOffset(new Position(444, 555));
		params.setMinStatesDistance(888);
		params.setNextPositionOffset(new Position(255, 511));
		params.setStateClickRange(1023);
	}

}
