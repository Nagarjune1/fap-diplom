package cz.upol.jfa.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.xml.sax.SAXException;

/**
 * Abstraktní třída pro načítání a ukládání objektů do XMDegree souboru.
 * Předepisuje metody pro uložení/načtení dat do/z dokumentu XML.
 * 
 * @author martin
 * 
 * @param <T>
 *            Typ ukládaného/načítaného objektu
 * @se XMLFileException
 */
public abstract class XMLStorerLoader<T> {

	private static final String VERSION_ATTR_NAME = "version";

	public XMLStorerLoader() {
		super();
	}

	/**
	 * Vrátí název kořenováho uzlu.
	 * 
	 * @return
	 */
	public abstract String getRootElemName();

	/**
	 * Vrátí verzi souboru (pro případ změny ve struktuktuře schématu).
	 * 
	 * @return
	 */
	public abstract String getVersion();

	/**
	 * Zpracuje potomka kořenového elementu - data z něj uloží do objektu.
	 * 
	 * @param child
	 * @param object
	 * @throws XMLFileException
	 */
	protected void processRootNodeChild(Element child, T object)
			throws XMLFileException {
		throw new UnsupportedOperationException(
				"If you want use this, you should override and implement it!");
	}

	/**
	 * Zpracuje kořenový element a vytvoří z něj požadovaný objekt, který vrátí.
	 * 
	 * @param root
	 * @return
	 * @throws XMLFileException
	 */
	protected T processRootNodeChild(Element root) throws XMLFileException {
		throw new UnsupportedOperationException(
				"If you want use this, you should override and implement it!");
	}

	/**
	 * Uloží objekt do kořenového elementu.
	 * 
	 * @param rootElem
	 * @param object
	 * @throws XMLFileException
	 */
	protected abstract void putToRootNode(T object, Document document,
			Element rootNode);

	/*************************************************************************/

	/**
	 * Načte data ze souboru do existujícího objektu. Bylo by vhodné, aby byl
	 * nový.
	 * 
	 * @param intoObject
	 * @param file
	 * @return
	 * @throws XMLFileException
	 */
	public T load(T intoObject, File file) throws XMLFileException {

		InputStream ins;
		try {
			ins = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new XMLFileException("Soubor nenalezen", e);
		}

		return load(intoObject, ins);
	}

	/**
	 * Načte data ze souboru do objektu, který si vytvoří.
	 * 
	 * @param file
	 * @return
	 * @throws XMLFileException
	 */
	public T load(File file) throws XMLFileException {

		InputStream ins;
		try {
			ins = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new XMLFileException("Soubor nenalezen", e);
		}

		return load(ins);
	}

	/**
	 * Načte data z input streamu do existujícího objetku.
	 * 
	 * @param intoObject
	 * @param ins
	 * @return
	 * @throws XMLFileException
	 */
	public T load(T intoObject, InputStream ins) throws XMLFileException {
		Document document = readDocumentFrom(ins);

		return setFromDocument(document, intoObject);
	}

	/**
	 * Načte data z input streamu a vytvoří z nich nový objekt, který vrátí.
	 * 
	 * @param ins
	 * @return
	 * @throws XMLFileException
	 */
	public T load(InputStream ins) throws XMLFileException {
		Document document = readDocumentFrom(ins);

		return setFromDocument(document);
	}

	/**
	 * Uloží objekt do souboru.
	 * 
	 * @param object
	 * @param file
	 * @throws XMLFileException
	 */
	public void save(T object, File file) throws XMLFileException {
		OutputStream ous;
		try {
			ous = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new XMLFileException("Soubor nenalezen", e);
		}

		save(object, ous);
	}

	public void save(T object, OutputStream data) throws XMLFileException {
		Document document = createEmptyDocument();

		putToDocument(object, document);

		writeDocumentTo(document, data);
	}

	/*************************************************************************/
	/**
	 * Načte dokument ze zadaného streamu.
	 * 
	 * @return
	 * @throws XMLFileException
	 */
	private Document readDocumentFrom(InputStream data) throws XMLFileException {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			Document document = docBuilder.parse(data);

			return document;
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new XMLFileException(
					"Chyba při načítání kořene konfiguračního souboru.", e);
		}
	}

	/**
	 * Vloží data z xml dokumentu do objektu
	 * 
	 * @param document
	 * @param object
	 * @throws XMLFileException
	 */
	protected T setFromDocument(Document document, T object)
			throws XMLFileException {

		Element rootElem = checkAndGetRootElement(document);

		for (Element elem : new XMLChildrenIterable(rootElem)) {
			processRootNodeChild(elem, object);
		}

		return object;
	}

	/**
	 * Z dat v xml dokumentu vytvoří nový objekt a ten vrátí.
	 * 
	 * @param document
	 * @return
	 * @throws XMLFileException
	 */
	protected T setFromDocument(Document document) throws XMLFileException {
		Element rootElem = checkAndGetRootElement(document);

		return processRootNodeChild(rootElem);
	}

	/**
	 * Načte kořenový element z dokumentu a ověří název a typ.
	 * 
	 * @param document
	 * @return
	 * @throws XMLFileException
	 */
	protected Element checkAndGetRootElement(Document document)
			throws XMLFileException {
		Element rootElem = document.getDocumentElement();

		if (rootElem.getNodeName() == null
				|| !rootElem.getNodeName().equals(getRootElemName())) {
			throw new XMLFileException("Dokument \"" + rootElem.getNodeName()
					+ "\" není elementem " + getRootElemName());
		}

		if (!rootElem.hasAttribute(VERSION_ATTR_NAME)
				|| !rootElem.getAttribute(VERSION_ATTR_NAME).equals(
						getVersion())) {
			throw new XMLFileException("Je vyžadována verze dokumentu "
					+ getVersion());
		}

		return rootElem;
	}

	/*************************************************************************/
	/**
	 * Vytvoří prázdný dokument.
	 * 
	 * @return
	 * @throws XMLFileException
	 */
	private Document createEmptyDocument() throws XMLFileException {

		Document document;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			document = docBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			throw new XMLFileException("Chyba při vytváření nového dokumentu",
					e);
		}

		return document;
	}

	/**
	 * Vytvoří kořenový uzel, vloží do něj obsah objektu dle implementace a ten
	 * poté uloží do dokumentu.
	 * 
	 * @param object
	 * @param document
	 */
	private void putToDocument(T object, Document document) {
		Element rootNode = createRootNode(document);

		putToRootNode(object, document, rootNode);

		document.appendChild(rootNode);
	}

	/**
	 * Vytvoří kořenový uzel, dle názvu a s verzí dle implementace
	 * 
	 * @param document
	 * @return
	 */
	private Element createRootNode(Document document) {
		Element rootElem = document.createElement(getRootElemName());

		rootElem.setAttribute(VERSION_ATTR_NAME, getVersion());

		return rootElem;
	}

	/**
	 * Zapíše dokument do souboru.
	 * 
	 * @param document
	 * @param file
	 * @throws XMLFileException
	 */
	private void writeDocumentTo(Document document, OutputStream data)
			throws XMLFileException {
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");

			StreamResult result = new StreamResult(data);
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);

			result.getOutputStream().flush();

		} catch (ClassCastException | DOMException | LSException | IOException
				| TransformerException e) {
			throw new XMLFileException(
					"Chyba při ukládání dokumentu do souboru.", e);
		}
	}

	/*************************************************************************/

	/**
	 * Vytvoří uzel komentáře s textem value.
	 * 
	 * @param document
	 * @param value
	 * @return
	 */
	protected Node comment(Document document, String value) {
		Node comment = document.createComment(value);
		return comment;
	}

}