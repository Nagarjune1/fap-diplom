package cz.upol.jfa.config;

import java.io.File;

import cz.upol.jfa.utils.xml.XMLFileException;
import cz.upol.jfa.viewer.colors.ColorsSet;
import cz.upol.jfa.viewer.colors.DefaultColors;
import cz.upol.jfa.viewer.interactivity.PositioningConfig;
import cz.upol.jfa.viewer.painting.ViewerConfig;

public class Configuration {
	private static final File CONFIG_FILE = new File(".jfaconfig.xml");
	private static Configuration instance = null;

	private final ViewerConfig viewer;
	private final PositioningConfig positioning;

	private final ColorsSet normalViewColors;
	private final ColorsSet selectedViewColors;
	private final ColorsSet disabledViewColors;
	private final ColorsSet colourExportColors;
	private final ColorsSet blackWhiteExportColors;

	public PositioningConfig getPositioning() {
		return positioning;
	}

	public ViewerConfig getViewerParams() {
		return viewer;
	}

	public ColorsSet getNormalViewColors() {
		return normalViewColors;
	}

	public ColorsSet getSelectedViewColors() {
		return selectedViewColors;
	}

	public ColorsSet getDisabledViewColors() {
		return disabledViewColors;
	}

	public ColorsSet getColourExportColors() {
		return colourExportColors;
	}

	public ColorsSet getBlackWhiteExportColors() {
		return blackWhiteExportColors;
	}

	protected Configuration() {
		viewer = ViewerConfig.DEFAULT;
		positioning = PositioningConfig.DEFAULT;

		normalViewColors = DefaultColors.getNormalViewColors();
		selectedViewColors = DefaultColors.getSelectedViewColors();
		disabledViewColors = DefaultColors.getDisabledViewColors();
		colourExportColors = DefaultColors.getColourExportColors();
		blackWhiteExportColors = DefaultColors.getBlackWhiteExportColors();
	}

	public static Configuration get() {
		if (instance == null) {
			instance = load();
		}

		if (instance == null) {
			instance = new Configuration();
		}

		return instance;
	}

	private static Configuration load() {
		ConfigStorerLoader csl = new ConfigStorerLoader();
		Configuration configuration = new Configuration();

		try {
			csl.load(configuration, CONFIG_FILE);
			System.out.println("[INFO] Načten konfigurační soubor.");
		} catch (XMLFileException e) {
			System.out
					.println("[ERRR] Konfigurační soubor se nepodařilo načíst ("
							+ e + "). Ponechávám výchozí konfiguraci.");
		}

		return configuration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((blackWhiteExportColors == null) ? 0
						: blackWhiteExportColors.hashCode());
		result = prime
				* result
				+ ((colourExportColors == null) ? 0 : colourExportColors
						.hashCode());
		result = prime
				* result
				+ ((disabledViewColors == null) ? 0 : disabledViewColors
						.hashCode());
		result = prime
				* result
				+ ((normalViewColors == null) ? 0 : normalViewColors.hashCode());
		result = prime * result
				+ ((positioning == null) ? 0 : positioning.hashCode());
		result = prime
				* result
				+ ((selectedViewColors == null) ? 0 : selectedViewColors
						.hashCode());
		result = prime * result + ((viewer == null) ? 0 : viewer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		if (blackWhiteExportColors == null) {
			if (other.blackWhiteExportColors != null)
				return false;
		} else if (!blackWhiteExportColors.equals(other.blackWhiteExportColors))
			return false;
		if (colourExportColors == null) {
			if (other.colourExportColors != null)
				return false;
		} else if (!colourExportColors.equals(other.colourExportColors))
			return false;
		if (disabledViewColors == null) {
			if (other.disabledViewColors != null)
				return false;
		} else if (!disabledViewColors.equals(other.disabledViewColors))
			return false;
		if (normalViewColors == null) {
			if (other.normalViewColors != null)
				return false;
		} else if (!normalViewColors.equals(other.normalViewColors))
			return false;
		if (positioning == null) {
			if (other.positioning != null)
				return false;
		} else if (!positioning.equals(other.positioning))
			return false;
		if (selectedViewColors == null) {
			if (other.selectedViewColors != null)
				return false;
		} else if (!selectedViewColors.equals(other.selectedViewColors))
			return false;
		if (viewer == null) {
			if (other.viewer != null)
				return false;
		} else if (!viewer.equals(other.viewer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Configuration [viewer=" + viewer + ", positioning="
				+ positioning + ", normalViewColors=" + normalViewColors
				+ ", selectedViewColors=" + selectedViewColors
				+ ", disabledViewColors=" + disabledViewColors
				+ ", colourExportColors=" + colourExportColors
				+ ", blackWhiteExportColors=" + blackWhiteExportColors + "]";
	}

}
