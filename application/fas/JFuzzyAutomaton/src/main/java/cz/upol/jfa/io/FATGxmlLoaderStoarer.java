package cz.upol.jfa.io;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.upol.automaton.automata.ingredients.HasDelta;
import cz.upol.automaton.automata.ingredients.HasStates;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.sets.Externisator;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;
import cz.upol.jfa.utils.xml.XMLChildrenIterable;
import cz.upol.jfa.utils.xml.XMLFileException;
import cz.upol.jfa.utils.xml.XMLStorerLoader;
import cz.upol.jfa.viewer.Position;

public class FATGxmlLoaderStoarer extends XMLStorerLoader<BaseAutomatonToGUI> {

	private final FATGXMLTools tools = new FATGXMLTools();

	public FATGxmlLoaderStoarer() {
	}

	@Override
	public String getRootElemName() {
		return "fuzzy-automaton";
	}

	@Override
	public String getVersion() {
		return "0.3";
	}

	@Override
	protected BaseAutomatonToGUI setFromDocument(Document document)
			throws XMLFileException {

		Element rootElem = checkAndGetRootElement(document);
		if (!rootElem.getAttribute("class-name").equals(
				NondetFuzzyAutomatonToGUI.class.getClass().getName())) {
			throw new XMLFileException("Only nondet automata supported",
					new UnsupportedOperationException());
		}

		FuzzyLogic logic = null;
		Alphabet alphabet = null;

		for (Element elem : new XMLChildrenIterable(rootElem)) {
			if (elem.getNodeName().equals("logic")) {
				logic = processLogicElement(elem);
			}

			if (elem.getNodeName().equals("alphabet")) {
				alphabet = processAlphabetElement(elem);
			}

			if (logic != null && alphabet != null) {
				break;
			}

		}

		NondetFuzzyAutomatonToGUI automaton = new NondetFuzzyAutomatonToGUI(
				logic, alphabet);
		return addStatesAndDelta(rootElem, automaton);

	}

	private NondetFuzzyAutomatonToGUI addStatesAndDelta(Element rootElem,
			NondetFuzzyAutomatonToGUI automaton) throws XMLFileException {

		for (Element elem : new XMLChildrenIterable(rootElem)) {
			if (elem.getNodeName().equals("states")) {
				addStates(elem, automaton);
			}

			if (elem.getNodeName().equals("delta")) {
				addDelta(elem, automaton);
			}
		}

		return automaton;
	}

	private void addDelta(Element deltaElem, NondetFuzzyAutomatonToGUI automaton)
			throws XMLFileException {

		Externisator<Degree> logicExt = automaton.getResiduedLattice()
				.getUniverseElementsExternisator();

		Externisator<Symbol> alphabetExt = automaton.getAlphabet()
				.getSymbolsExternisator();

		Alphabet alphabet = automaton.getAlphabet();
		Set<Degree> logicElems = automaton.getResiduedLattice().getElements();

		for (Element elem : new XMLChildrenIterable(deltaElem)) {
			if (!elem.getNodeName().equals("transition")) {
				throw new XMLFileException("Unrecognized element "
						+ elem.getNodeName());
			}

			State fromState = tools.parseStateFind(automaton, elem, "from");
			State toState = tools.parseStateFind(automaton, elem, "to");
			Symbol over = tools
					.parseSymbol(alphabetExt, alphabet, elem, "over");
			Degree degree = tools.parseDegree(logicExt, logicElems, true, elem,
					"degree");

			Transition transition = new Transition(fromState, over, toState);
			automaton.addTransition(transition, degree);
		}
	}

	private void addStates(Element statesElem,
			NondetFuzzyAutomatonToGUI automaton) throws XMLFileException {

		Externisator<Degree> logicExt = automaton.getResiduedLattice()
				.getUniverseElementsExternisator();

		Set<Degree> logicElems = automaton.getResiduedLattice().getElements();

		for (Element elem : new XMLChildrenIterable(statesElem)) {
			if (!elem.getNodeName().equals("state")) {
				throw new XMLFileException("Unrecognized element "
						+ elem.getNodeName());
			}

			State state = tools.parseStateCreate(elem, "label");
			Position position = tools.parsePosition(elem, "position-x",
					"position-y");
			state = automaton.addOrRenameState(state, position);

			Degree initial = tools.parseDegree(logicExt, logicElems, false,
					elem, "initial-in");
			if (initial != null) {
				automaton.setInitialState(state, initial);
			}

			Degree finite = tools.parseDegree(logicExt, logicElems, false,
					elem, "finite-in");
			if (finite != null) {
				automaton.setFiniteState(state, finite);
			}
		}
	}

	private Alphabet processAlphabetElement(Element elem)
			throws XMLFileException {
		String className = elem.getAttribute("class");

		if (className == null || className.isEmpty()) {
			throw new XMLFileException(
					"Attribute 'class' of element 'alphabet' is missing");
		}

		Alphabet alphabet = null;
		try {
			Class<?> clazz = getClass().getClassLoader().loadClass(className);
			Object object = clazz.newInstance();

			alphabet = (Alphabet) object;

		} catch (Exception e) {
			throw new XMLFileException("Cannot instantite alphabet", e);
		}

		return alphabet;
	}

	private FuzzyLogic processLogicElement(Element elem)
			throws XMLFileException {

		String className = elem.getAttribute("class");

		if (className == null || className.isEmpty()) {
			throw new XMLFileException(
					"Attribute 'class' of element 'logic' is missing");
		}

		FuzzyLogic logic = null;
		try {
			Class<?> clazz = getClass().getClassLoader().loadClass(className);
			Object object = clazz.newInstance();

			logic = (FuzzyLogic) object;

		} catch (Exception e) {
			throw new XMLFileException("Cannot instantite logic", e);
		}

		return logic;
	}

	@Override
	protected void putToRootNode(BaseAutomatonToGUI object, Document document,
			Element rootNode) {

		rootNode.setAttribute("class-name", object.getClass().getName());

		putLogic(AutomatonDesc.residuedLattice(object), document, rootNode);
		putAlphabet(AutomatonDesc.alphabet(object), document, rootNode);

		putStates(object, document, rootNode);
		putTransitions(object, document, rootNode);

	}

	private void putTransitions(BaseAutomatonToGUI automaton,
			Document document, Element rootNode) {

		Element deltaElem = document.createElement("delta");
		for (Transition transition : ((HasDelta<?, ?>) automaton)
				.iterateOverTransitions()) {
			Element transitionElem = tools.createTransitionElem(automaton,
					document, transition);

			deltaElem.appendChild(transitionElem);
		}
		rootNode.appendChild(deltaElem);
	}

	private void putStates(BaseAutomatonToGUI automaton, Document document,
			Element rootNode) {

		Element statesElem = document.createElement("states");
		for (State state : ((HasStates) automaton).iterateOverStates()) {
			Element stateElem = tools.createStateElem(automaton, document,
					state);

			statesElem.appendChild(stateElem);
		}

		rootNode.appendChild(statesElem);
	}

	private void putLogic(ResiduedLattice logic, Document document,
			Element rootNode) {

		Element logicElem = document.createElement("logic");
		logicElem.setAttribute("class", logic.getClass().getName());

		logicElem.appendChild(document.createComment(logic.getDescription()));

		rootNode.appendChild(logicElem);

	}

	private void putAlphabet(Alphabet alphabet, Document document,
			Element rootNode) {

		Element alphabetElem = document.createElement("alphabet");
		alphabetElem.setAttribute("class", alphabet.getClass().getName());

		alphabetElem.appendChild(document.createComment(alphabet
				.getDescription()));

		rootNode.appendChild(alphabetElem);

	}

	@Override
	protected void processRootNodeChild(Element child, BaseAutomatonToGUI object)
			throws XMLFileException {
	}

}
