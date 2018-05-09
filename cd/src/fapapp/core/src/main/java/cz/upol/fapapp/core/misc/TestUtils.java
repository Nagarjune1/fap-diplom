package cz.upol.fapapp.core.misc;

import java.util.HashMap;
import java.util.Map;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;

/**
 * Some utilities for testing.
 * 
 * @author martin
 *
 */
public class TestUtils {

	/**
	 * Creates "binaryton" fuzzy state.
	 * 
	 * @param state1
	 * @param degree1
	 * @param state2
	 * @param degree2
	 * @return
	 */
	public static FuzzyState createFuzzyState(State state1, Degree degree1, State state2, Degree degree2) {
		Map<State, Degree> map = new HashMap<>();

		map.put(state1, degree1);
		map.put(state2, degree2);

		return new FuzzyState(map);
	}

}
