package cz.upol.jfa.utils.swing.cmbbxwe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class CbBxWtExtModel<E> extends AbstractListModel<E> implements
		ComboBoxModel<E> {

	private static final long serialVersionUID = 5188892701999135164L;

	private Set<E> set;
	private List<E> items;

	private Object selectedItem;

	public CbBxWtExtModel(Set<E> set) {
		super();

		this.set = set;
		this.items = new ArrayList<>(0);
	}

	@Override
	public int getSize() {
		return toList(false).size();
	}

	@Override
	public E getElementAt(int index) {
		return toList(false).get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		this.selectedItem = anItem;
		fireContentsChanged(this, 0, items.size() - 1);
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	protected List<E> toList(boolean skipCheck) {
		int setSize = set.size();

		int listSize;
		if (items != null) {
			listSize = items.size();
		} else {
			listSize = 0;
		}

		boolean needsUpdate;
		if (!skipCheck) {
			needsUpdate = listSize != setSize;
		} else {
			needsUpdate = true;
		}

		if (needsUpdate) {
			items = new ArrayList<>(set);

			if (selectedItem != null && !set.contains(selectedItem)) {
				setSelectedItem(null);
			}

			fireChange(setSize, listSize);
		}

		return items;
	}

	private void fireChange(int toSize, int fromSize) {

		if (fromSize > toSize) {
			fireIntervalRemoved(this, toSize, fromSize - 1);
			fireContentsChanged(this, 0, toSize - 1);
		}

		if (fromSize < toSize) {
			fireIntervalAdded(this, fromSize, toSize - 1);
			fireContentsChanged(this, 0, fromSize - 1);
		}
	}

	public void forceUpdate() {
		toList(true);
	}

	public void updateTo(Set<E> newSet) {
		this.set = newSet;
		toList(true);
	}
}
