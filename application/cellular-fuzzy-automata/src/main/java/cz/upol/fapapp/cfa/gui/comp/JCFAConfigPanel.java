package cz.upol.fapapp.cfa.gui.comp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.comp.CFAConfiguration;

public class JCFAConfigPanel extends JComponent {

	private static final long serialVersionUID = 2152556181110631253L;

	private final ColorModel colors;
	private final CFAConfiguration config;

	public JCFAConfigPanel(ColorModel colors, CFAConfiguration config) {
		super();
		this.colors = colors;
		this.config = config;

		Dimension size = new Dimension(config.getSize(), config.getSize());
		this.setPreferredSize(size);
	}

	/**************************************************************************/

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics = (Graphics2D) g;

		drawAsPixels(graphics, colors, config);
	}

	private void drawAsPixels(Graphics2D graphics, ColorModel colors, CFAConfiguration config) {
		for (int i = 0; i < config.getSize(); i++) {
			for (int j = 0; j < config.getSize(); j++) {

				CellState cell = config.getCell(i, j);
				Color color = colors.convert(cell);

				graphics.setColor(color);
				graphics.drawRect(i, j, 1, 1);
			}
		}
	}

};