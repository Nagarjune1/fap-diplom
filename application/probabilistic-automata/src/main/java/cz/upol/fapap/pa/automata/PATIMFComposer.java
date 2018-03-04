package cz.upol.fapap.pa.automata;

import java.util.Set;

import cz.upol.fapap.core.QuadriaryRelation;
import cz.upol.fapap.core.QuadriaryRelation.Quadrituple;
import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.probability.Probability;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

public class PATIMFComposer extends TIMObjectComposer<ProbablisticAutomaton> {

	public PATIMFComposer() {
		super(PATIMFParser.TYPE);
	}

	@Override
	protected void process(ProbablisticAutomaton object, TIMFileData data) {
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

	private void processTransitions(QuadriaryRelation<State, Symbol, State, Probability> transititons, TIMFileData data) {
		
		for (Quadrituple<State, Symbol, State, Probability> quadrituple: transititons.getTuples()) {
			State from = quadrituple.getFirst();
			Symbol over = quadrituple.getSecond();
			State to = quadrituple.getThird();
			Probability prob = quadrituple.getFourth();
			
			LineElements line = new LineElements(from.getLabel(), over.getValue(), to.getLabel(), Double.toString(prob.getValue()));
			data.add("transition function", line);	
		}
		
	}

	private void processInitials(Set<State> initials, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.statesToLine(initials);
		data.add("initial states", line);
	}

	private void processFinals(Set<State> finals, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.statesToLine(finals);
		data.add("final states", line);
	}

}
