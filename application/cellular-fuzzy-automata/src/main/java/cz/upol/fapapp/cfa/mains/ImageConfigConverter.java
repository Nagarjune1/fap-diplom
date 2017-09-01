package cz.upol.fapapp.cfa.mains;

import java.io.File;
import java.util.List;

import cz.upol.fapapp.cfa.comp.CFAConfTIMComposer;
import cz.upol.fapapp.cfa.comp.CFAConfTTIMParser;
import cz.upol.fapapp.cfa.comp.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.comp.ColorModel;
import cz.upol.fapapp.cfa.gui.misc.Imager;
import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.core.misc.Logger;
import javafx.scene.paint.Color;

public class ImageConfigConverter {

	private static final String IMAGE_EXTENSION = ".png";
	private static final String CONFIG_EXTENSION = ".timf";

	private static final Color CHANEL = Color.GRAY;
	private static final ColorModel COLORS = ColorModel.GRAY;
	private static final int SCALE = 1;

	public static void main(String[] args) {
		//args = new String[]{"--verbose", "data/test/images/random-100.png", "data/test/configs/random-config-100.timf"};//XXX
		
		
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());

		String firstFileName = argsList.get(0);
		String secondFileName = argsList.get(1);

		if (firstFileName.endsWith(IMAGE_EXTENSION) && secondFileName.endsWith(IMAGE_EXTENSION)) {
			Logger.get().error("Cannot convert image to image");
			System.exit(3);
			return;
		}
		if (firstFileName.endsWith(CONFIG_EXTENSION) && secondFileName.endsWith(CONFIG_EXTENSION)) {
			Logger.get().error("Cannot convert config to config");
			System.exit(3);
			return;
		}

		File firstFile = new File(firstFileName);
		File secondFile = new File(secondFileName);

		if (firstFileName.endsWith(IMAGE_EXTENSION) && secondFileName.endsWith(CONFIG_EXTENSION)) {
			convertImageToConfig(firstFile, secondFile);
		}
		if (firstFileName.endsWith(CONFIG_EXTENSION) && secondFileName.endsWith(IMAGE_EXTENSION)) {
			convertConfigToImage(firstFile, secondFile);
		}
		
		System.exit(0);
	}

	/**************************************************************************/

	private static void convertImageToConfig(File imageFile, File configFile) {
		Logger.get().moreinfo("Converting image " + imageFile + " to config " + configFile);
		try {
			Imager imager = new Imager();
			CFAConfiguration config = imager.imageToConfig(imageFile, CHANEL);

			CFAConfTIMComposer composer = new CFAConfTIMComposer("cells");
			composer.compose(config, configFile);
		} catch (Exception e) {
			Logger.get().error("Conversion failed: " + e.getMessage());
			System.exit(4);
		}
	}

	private static void convertConfigToImage(File configFile, File imageFile) {
		Logger.get().moreinfo("Converting config " + configFile + " to image " + imageFile);
		try {
			CFAConfTTIMParser parser = new CFAConfTTIMParser("cells");
			CFAConfiguration config = parser.parse(configFile);

			Imager imager = new Imager();
			imager.configToImage(config, imageFile, COLORS, SCALE);
		} catch (Exception e) {
			Logger.get().error("Conversion failed: " + e.getMessage());
			System.exit(4);
		}
	}

	/**************************************************************************/

	private static void printHelp() {
		System.out.println("Image Config Converter");
		System.out.println("Usage: ImageConfigConverter IMAGE.png CONFIG.timf");
		System.out.println("       ImageConfigConverter CONFIG.timf IMAGE.png");
	}

}
