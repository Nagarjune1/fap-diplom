package cz.upol.fapapp.cfa.mains;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.config.CommonConfiguration;
import cz.upol.fapapp.cfa.config.ConfigGenerator;
import cz.upol.fapapp.cfa.gui.comp.ColorModel;
import cz.upol.fapapp.cfa.gui.frame.JBasicCFAConfigFrame;
import cz.upol.fapapp.core.misc.Logger;

public class SimpleCFAConfigDisplayer {

	public static void main(String[] args) {
		// TODO load from file
		Logger.get().setVerbose(true);// XXX logging
		
		ConfigGenerator gen = new ConfigGenerator();
		CommonConfiguration config = gen.generateDoubles(100, new CellState(0.5), 42);
		
		ColorModel colors = ColorModel.GRAY;
		JBasicCFAConfigFrame frame = new JBasicCFAConfigFrame(colors, config);
		frame.setVisible(true);
		
	}

}
