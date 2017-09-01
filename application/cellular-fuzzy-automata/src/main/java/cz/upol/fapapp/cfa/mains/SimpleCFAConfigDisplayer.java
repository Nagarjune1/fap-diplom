package cz.upol.fapapp.cfa.mains;

import cz.upol.fapapp.cfa.comp.CommonConfiguration;
import cz.upol.fapapp.cfa.comp.ConfigGenerator;
import cz.upol.fapapp.cfa.gui.comp.ColorModel;
import cz.upol.fapapp.cfa.gui.frame.JBasicCFAConfigFrame;
import cz.upol.fapapp.core.misc.Logger;

public class SimpleCFAConfigDisplayer {

	public static void main(String[] args) {
		// TODO load from file
		Logger.get().setVerbose(true);// XXX logging
		
		ConfigGenerator gen = new ConfigGenerator();
		CommonConfiguration config = gen.generateDoubles(100, 42);
		
		ColorModel colors = ColorModel.GRAY;
		JBasicCFAConfigFrame frame = new JBasicCFAConfigFrame(colors, config);
		frame.setVisible(true);
		
	}

}
