package cz.upol.fapapp.fa.automata;

import java.util.Set;

import cz.upol.fapapp.core.automata.FuzzyState;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.fuzzy.sets.FuzzyTernaryRelation;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.sets.TernaryRelation.Triple;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectComposer} of {@link BaseFuzzyAutomaton}.
 * @author martin
 *
 */
public class FATIMComposer extends TIMObjectComposer<BaseFuzzyAutomaton> {

	public FATIMComposer() {
		super(FATIMParser.TYPE);
	}

	@Override
	protected void process(BaseFuzzyAutomaton object, TIMFileData data) {
		processAlphabet(object.getAlphabet(), data);
		processStates(object.getStates(), data);
		processTransitions(object.getTransitionFunction(), data);
		processInitials(object.getInitialStates(), data);
		processFinals(object.getFinalStates(), data);
	}

	private void processAlphabet(Alphabet alphabet, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.alphabetToLine(alphabet);
		data.add("alphabet", line);
	}

	private void processStates(Set<State> states, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.statesToLine(states);
		data.add("states", line);
	}

	private void processTransitions(FuzzyTernaryRelation<State, Symbol, State> transitionFunction, TIMFileData data) {
		
		for (Triple<State, Symbol, State> triple: transitionFunction.domain()) {
			State from = triple.getFirst();
			Symbol over = triple.getSecond();
			State to = triple.getThird();
			Degree degree = transitionFunction.get(triple);
			
			LineElements line = new LineElements(from.getLabel(), over.getValue(), to.getLabel(), Double.toString(degree.getValue()));
			data.add("transition function", line);	
		}
		
	}

	private void processInitials(FuzzySet<State> initialStates, TIMFileData data) {
		FuzzyState fuzzyState = new FuzzyState(initialStates);
		LineElements line = TIMObjectParserComposerTools.fuzzyStateToLine(fuzzyState);
		data.add("initial states", line);
	}

	private void processFinals(FuzzySet<State> finalStates, TIMFileData data) {
		FuzzyState fuzzyState = new FuzzyState(finalStates);
		LineElements line = TIMObjectParserComposerTools.fuzzyStateToLine(fuzzyState);
		data.add("final states", line);
	}

}
