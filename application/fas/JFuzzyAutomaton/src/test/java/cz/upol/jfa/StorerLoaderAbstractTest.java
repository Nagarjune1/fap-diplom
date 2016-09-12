package cz.upol.jfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import cz.upol.jfa.utils.xml.XMLFileException;
import cz.upol.jfa.utils.xml.XMLStorerLoader;

public abstract class StorerLoaderAbstractTest<T> {

	private final XMLStorerLoader<T> storerLoader;

	public StorerLoaderAbstractTest(XMLStorerLoader<T> storerLoader) {
		super();
		this.storerLoader = storerLoader;
	}

	/**
	 * Vytvoří nový prázdný objekt, do nějž bude načten obsah z XML. Pokud je
	 * StorerLoader naimplementovaný tak, že si ho vytváří sám, měla by tato
	 * metoda vracet null.
	 * 
	 * @return
	 */
	public abstract T createNewObject();

	/**
	 * Vytvoří testovací objekt, který by měla být úplně jiný než nový (resp. vrácený
	 * metodou {@link #createNewObject()}). Atributy, které budou mít shodné
	 * hodnoty s výchozí nebudou moct být otestovány.
	 * 
	 * @return
	 */

	public abstract T createTestingObject();

	@Test
	public void test() {
		T oldObject = createTestingObject();

		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		try {
			storerLoader.save(oldObject, ous);
		} catch (XMLFileException e) {
			fail(e.toString());
		}

		byte[] bytes = ous.toByteArray();

		T newObject = createNewObject();
		ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
		try {
			if (newObject == null) {
				newObject = storerLoader.load(ins);
			} else {
				newObject = storerLoader.load(newObject, ins);
			}
		} catch (XMLFileException e) {
			fail(e.toString());
		}

		assertEquals(oldObject, newObject);

	}

}