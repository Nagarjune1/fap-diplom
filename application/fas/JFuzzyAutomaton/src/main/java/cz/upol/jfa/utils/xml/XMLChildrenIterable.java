package cz.upol.jfa.utils.xml;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.w3c.dom.Element;

public class XMLChildrenIterable implements Iterable<Element> {

	public static class XMLChildrenIterator implements Iterator<Element> {

		private final Element element;
		private int currentIndex;
		private int nextIndex;

		public XMLChildrenIterator(Element element) {
			this.element = element;
			this.currentIndex = findNextElement(-1);
			this.nextIndex = findNextElement(currentIndex);
		}

		private int findNextElement(int index) {
			int i;
			for (i = index + 1; //
			!(element.getChildNodes().item(i) instanceof Element)//
					&& (i < element.getChildNodes().getLength()); //
			i++) {
			}

			return i;
		}

		@Override
		public boolean hasNext() {
			return currentIndex < element.getChildNodes().getLength();
		}

		@Override
		public Element next() {
			Element elem = (Element) element.getChildNodes().item(currentIndex);

			currentIndex = nextIndex;
			nextIndex = findNextElement(currentIndex);

			return elem;
		}

		@Override
		public void remove() {
			currentIndex = nextIndex;
			nextIndex = findNextElement(currentIndex);
		}

		@Override
		public void forEachRemaining(Consumer<? super Element> action) {
			throw new UnsupportedOperationException("forEachRemaining");
		}

	}

	private final Element element;

	public XMLChildrenIterable(Element element) {
		this.element = element;
	}

	@Override
	public Iterator<Element> iterator() {
		return new XMLChildrenIterator(element);
	}

	@Override
	public void forEach(Consumer<? super Element> action) {
		throw new UnsupportedOperationException("forEach");
	}

	@Override
	public Spliterator<Element> spliterator() {
		throw new UnsupportedOperationException("spliterator");
	}

}
