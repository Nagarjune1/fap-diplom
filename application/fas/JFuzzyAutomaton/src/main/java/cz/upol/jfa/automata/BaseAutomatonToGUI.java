package cz.upol.jfa.automata;

import cz.upol.automaton.automata.abstracts.BaseAutomaton;

public interface BaseAutomatonToGUI extends BaseAutomaton, IsToGUI {
	public AutomataGUIProvider getProvider();
}
