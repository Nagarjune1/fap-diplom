package cz.upol.jfa.utils.swing.cmbbxwe;

import java.util.Set;

import javax.swing.JComboBox;

import cz.upol.automaton.sets.Externalisable;
import cz.upol.automaton.sets.Externisator;
import cz.upol.automaton.sets.HugeSet;

public class JComboBoxWthExternalisable<E extends Externalisable<E>> extends
		JComboBox<E> {

	private static final long serialVersionUID = -2698776943275309494L;

	private final Set<E> set;

	public JComboBoxWthExternalisable(Set<E> set, Externisator<E> externisator) {
		super();

		this.set = set;

		setModel(new CbBxWtExtModel<E>(set));
		setRenderer(new CbBxWtExtRenderer<>(externisator));
		setEditor(new CbBxWtExtEditor<E>(externisator, this));

		setEditable(HugeSet.isSetHuge(set));
	}

	@Override
	public void setSelectedItem(Object anObject) {
		if (isEditable()) {
			@SuppressWarnings("unchecked")
			E anItem = (E) anObject;
			addItemToSet(anItem);

			getEditor().setItem(anObject);
		}

		super.setSelectedItem(anObject);
	}

	public void setSelectedItemFromEditor(Object object) {
		@SuppressWarnings("unchecked")
		E item = (E) object;

		addItemToSet(item);
	}

	private void addItemToSet(E anItem) {
		set.add(anItem);
		dataUpdated();
	}

	public void dataUpdated() {
		((CbBxWtExtModel<E>) this.getModel()).forceUpdate();
	}

	public void dataUpdatedTo(Set<E> newData) {
		((CbBxWtExtModel<E>) this.getModel()).updateTo(newData);
	}

}
