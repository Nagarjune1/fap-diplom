package cz.upol.jfa.io;

import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.ling.alphabets.AllStringsAlphabet;
import cz.upol.jfa.StorerLoaderAbstractTest;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;
import cz.upol.jfa.utils.TestingAutomataToGUI;

public class FATGStorerLoaderTest extends StorerLoaderAbstractTest<BaseAutomatonToGUI> {

	public FATGStorerLoaderTest() {
		super(new FATGxmlLoaderStoarer());

	}

	@Override
	public NondetFuzzyAutomatonToGUI createNewObject() {
		return new NondetFuzzyAutomatonToGUI(new LukasiewiczLogic(),
				new AllStringsAlphabet());
	}

	@Override
	public NondetFuzzyAutomatonToGUI createTestingObject() {
		return TestingAutomataToGUI.createLikeYpsilonAutomaton();
	}

}
