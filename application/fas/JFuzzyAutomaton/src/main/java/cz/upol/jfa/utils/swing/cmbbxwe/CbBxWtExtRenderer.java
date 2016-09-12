package cz.upol.jfa.utils.swing.cmbbxwe;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cz.upol.automaton.sets.Externisator;

public class CbBxWtExtRenderer<E> implements ListCellRenderer<Object> {

	private final Externisator<E> externisator;
	private final JLabel label;

	public CbBxWtExtRenderer(Externisator<E> externisator) {
		super();
		this.externisator = externisator;
		this.label = new JLabel();

		setupLabel();
	}

	private void setupLabel() {
		label.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {

		String text = externisator.externalize(value);
		label.setText(text);

		if (isSelected) {
			label.setBackground(list.getSelectionBackground());
			label.setForeground(list.getSelectionForeground());
		} else {
			label.setBackground(list.getBackground());
			label.setForeground(list.getForeground());
		}

		return label;
	}

}
