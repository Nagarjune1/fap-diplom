package cz.upol.jfa.io;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.sets.Externisator;
import cz.upol.automaton.sets.HugeSet;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;
import cz.upol.jfa.utils.xml.XMLFileException;
import cz.upol.jfa.viewer.Position;

public class FATGXMLTools {

	public FATGXMLTools() {
	}

	/**
	 * V elementu elem vyhledá atribut attrName a jeho hodnotu použije pro
	 * vyhledání stavu v automatu automaton.
	 * 
	 * @param automaton
	 * @param elem
	 * @param attrName
	 * @return
	 * @throws XMLFileException
	 */
	public State parseStateFind(NondetFuzzyAutomatonToGUI automaton,
			Element elem, String attrName) throws XMLFileException {

		String label = elem.getAttribute(attrName);
		if (label == null) {
			throw new XMLFileException("Missing state's " + attrName
					+ " attribute");
		}

		State state = automaton.findStateByLabel(label);
		if (state == null) {
			throw new XMLFileException("Unknown state " + label);
		}

		return state;
	}

	/**
	 * Vrátí instanci pozice, která je uložena v elementu elem o souřadnicích
	 * daných atributy xPosAttrName a yPosAttrName.
	 * 
	 * @param elem
	 * @param xPosAttrName
	 * @param yPosAttrName
	 * @return
	 * @throws XMLFileException
	 */
	public Position parsePosition(Element elem, String xPosAttrName,
			String yPosAttrName) throws XMLFileException {
		String xPosStr = elem.getAttribute(xPosAttrName);
		double x;

		try {
			x = Double.parseDouble(xPosStr);
		} catch (Exception e) {
			throw new XMLFileException("Invalid x position " + xPosStr, e);
		}

		String yPosStr = elem.getAttribute(yPosAttrName);
		double y;

		try {
			y = Double.parseDouble(yPosStr);
		} catch (Exception e) {
			throw new XMLFileException("Invalid y position " + yPosStr, e);
		}

		return new Position(x, y);
	}

	/**
	 * V elementu elem vyhledá hodnotu atributu attrName a pomocí externizátoru
	 * externisator z něj zkusí vytvořit stupeň. Pokud je required false, nevolá
	 * výjimku, ale vrací null v případě, že atribut element nemá. Vytvořený
	 * stupeň se přidá do elements.
	 * 
	 * @param externisator
	 * @param required
	 * @param elem
	 * @param attrName
	 * @return
	 * @throws XMLFileException
	 */
	public Degree parseDegree(Externisator<Degree> externisator,
			Set<Degree> elements, boolean required, Element elem,
			String attrName) throws XMLFileException {

		String str = elem.getAttribute(attrName);

		if (str == null && required) {
			throw new XMLFileException("Missing degree attribute " + attrName);
		}

		Degree degree = externisator.parseKnown(str);

		if (degree == null) {
			throw new XMLFileException("Invalid degree " + str);
		}

		elements.add(degree);

		return degree;
	}

	/**
	 * V elementu elem vyhledá atribut attrName a z jeho hodnoty pomocí
	 * externizátoru externisator vytvoří symbol. Pokud je alphabet velká (
	 * {@link HugeSet}), vytvořený symbol do ní přidá.
	 * 
	 * @param externisator
	 * @param elem
	 * @param attrName
	 * @return
	 * @throws XMLFileException
	 */
	public Symbol parseSymbol(Externisator<Symbol> externisator,
			Alphabet alphabet, Element elem, String attrName)
			throws XMLFileException {

		String value = elem.getAttribute(attrName);

		if (value == null) {
			throw new XMLFileException("Missing attribute " + attrName);
		}

		Symbol symbol = externisator.parseKnown(value);

		if (symbol == null) {
			throw new XMLFileException("Unknown symbol " + value);
		}

		if (HugeSet.isSetHuge(alphabet)) {
			alphabet.add(symbol);
		}

		return symbol;
	}

	/**
	 * V elementu elem vyhledá atribut attrName a z jeho hodnoty vytvoří symbol.
	 * 
	 * @param elem
	 * @param attrName
	 * @return
	 * @throws XMLFileException
	 */
	public State parseStateCreate(Element elem, String attrName)
			throws XMLFileException {

		String label = elem.getAttribute(attrName);
		if (label == null) {
			throw new XMLFileException("Missing state's " + attrName
					+ " attribute");
		}

		Externisator<State> externisator = State.EXTERNISATOR;
		State state = externisator.parseKnown(label);

		if (state == null) {
			throw new XMLFileException("Unknown 'from' state " + label);
		}
		return state;
	}

	/**
	 * Ze stavu state vytvoří nový element.
	 * 
	 * @param automaton
	 * @param document
	 * @param state
	 * @return
	 */
	public Element createStateElem(BaseAutomatonToGUI automaton,
			Document document, State state) {
		Element stateElem = document.createElement("state");

		stateElem.setAttribute("label", state.getLabel());
		if (AutomatonDesc.hasFiniteDegree(automaton)) {
			stateElem.setAttribute("finite-in",
					AutomatonDesc.finiteDegree(automaton, state).toPrint());
		}

		if (AutomatonDesc.hasInitialDegree(automaton)) {
			stateElem.setAttribute("initial-in",
					AutomatonDesc.initialDegree(automaton, state).toPrint());
		} else {
			stateElem
					.setAttribute("is-initial", Boolean.toString(AutomatonDesc
							.isInitial(automaton, state)));
		}

		stateElem.setAttribute(
				"position-x",
				Double.toString(automaton.getProvider().findPosition(state)
						.getX()));
		stateElem.setAttribute(
				"position-y",
				Double.toString(automaton.getProvider().findPosition(state)
						.getY()));
		return stateElem;
	}

	/**
	 * Z přechodu transition vytvoří nový element.
	 * 
	 * @param automaton
	 * @param document
	 * @param transition
	 * @return
	 */
	public Element createTransitionElem(BaseAutomatonToGUI automaton,
			Document document, Transition transition) {
		Element transitionElem = document.createElement("transition");

		transitionElem.setAttribute("from", transition.getFrom().getLabel());
		transitionElem.setAttribute("over", transition.getOver().getValue());
		transitionElem.setAttribute("to", transition.getTo().getLabel());

		if (AutomatonDesc.hasDegreeOfTransition(automaton)) {
			Degree degree = AutomatonDesc.getDegreeOfTransition(automaton,
					transition);
			transitionElem.setAttribute("degree", degree.toPrint());
		}

		return transitionElem;
	}
}
