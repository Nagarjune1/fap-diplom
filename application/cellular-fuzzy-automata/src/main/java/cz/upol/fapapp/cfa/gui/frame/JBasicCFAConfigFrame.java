package cz.upol.fapapp.cfa.gui.frame;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import cz.upol.fapapp.cfa.comp.CFAConfiguration;
import cz.upol.fapapp.cfa.gui.comp.ColorModel;
import cz.upol.fapapp.cfa.gui.comp.JCFAConfigPanel;

public class JBasicCFAConfigFrame extends JFrame {

	private static final long serialVersionUID = 7746530314763833818L;

	private final JCFAConfigPanel pane;

	public JBasicCFAConfigFrame(ColorModel colors, CFAConfiguration config) throws HeadlessException {
		super("Basic config frame");
		pane = new JCFAConfigPanel(colors, config);

		initializeComponents();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	private void initializeComponents() {
		getContentPane().add(pane, BorderLayout.CENTER);
	}

}
