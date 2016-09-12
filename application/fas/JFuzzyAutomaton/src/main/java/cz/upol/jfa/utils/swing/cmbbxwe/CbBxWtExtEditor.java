package cz.upol.jfa.utils.swing.cmbbxwe;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

import cz.upol.automaton.sets.Externalisable;
import cz.upol.automaton.sets.Externisator;

public class CbBxWtExtEditor<E extends Externalisable<E>> implements ComboBoxEditor {

	private final JTextField textField;
	private final Externisator<E> externisator;

	private Object lastItem;
	JComboBoxWthExternalisable<E> cbbx;

	public CbBxWtExtEditor(Externisator<E> externisator, JComboBoxWthExternalisable<E> cbbx) {
		this.externisator = externisator;
		this.textField = new JTextField();
		this.cbbx = cbbx;
	}

	@Override
	public Component getEditorComponent() {
		return textField;
	}

	@Override
	public void setItem(Object anObject) {
		setToObject(anObject);
		lastItem = anObject;
	}

	private void setToObject(Object anObject) {
		cbbx.setSelectedItemFromEditor(anObject);
		String text = externisator.externalize(anObject);
		textField.setText(text);
	}

	@Override
	public Object getItem() {
		String text = textField.getText();

		Object object = externisator.parse(text);

		if (object == null) {
			object = lastItem;
			setToObject(object);
		}

		return object;
	}

	@Override
	public void selectAll() {
		textField.selectAll();
	}

	@Override
	public void addActionListener(ActionListener l) {
		textField.addActionListener(l);
	}

	@Override
	public void removeActionListener(ActionListener l) {
		textField.removeActionListener(l);
	}

	@Override
	public String toString() {
		return "CbBxWtExtEditor [text=" + textField.getText() + "]";
	}

}
