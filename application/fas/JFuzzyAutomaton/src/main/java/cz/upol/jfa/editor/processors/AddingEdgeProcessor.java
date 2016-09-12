package cz.upol.jfa.editor.processors;

import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.automaton.misc.edges.DFAedge;
import cz.upol.automaton.misc.edges.NFAedge;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.viewer.interactivity.ViewerActionEvent;
import cz.upol.jfa.viewer.interactivity.ViewerEventType;
import cz.upol.jfa.viewer.interactivity.ViewerSelection;

public class AddingEdgeProcessor implements EditorEventProcessor {

	private final EditorActionsLogic logic;

	private State fromState;

	public AddingEdgeProcessor(EditorActionsLogic logic) {
		this.logic = logic;
	}

	@Override
	public void process(ViewerActionEvent event) {
		if (logic.checkAndProcessLMB(event)) {
			return;
		}

		ViewerSelection on = event.getOn();
		if (event.getType() == ViewerEventType.LMB_DOUBLECLICK) {
			createWholeNewEdge();
			return;
		}
		if (!on.isSelectedState()) {
			resetChoosenState();
			return;
		}

		if (fromState == null) {
			storeClickedFromState(on);
		} else {
			bringStoredAndCreateEdge(on);
		}

	}

	private void createWholeNewEdge() {
		logic.getActions().createEdge();
	}

	public void resetChoosenState() {
		fromState = null;
		logic.getEditor().changeSelection((ViewerSelection) null);
	}

	public void storeClickedFromState(ViewerSelection on) {
		fromState = on.getSelectedState();
		logic.getEditor().changeSelection(on);
	}

	private void bringStoredAndCreateEdge(ViewerSelection on) {
		State toState = on.getSelectedState();
		logic.getEditor().changeSelection(on);

		BaseAutomatonToGUI automaton = logic.getEditor().getAutomaton();

		AbstractEdge<?> edge;
		if (AutomatonDesc.hasDegreeOfTransition(automaton)) {
			edge = new NFAedge(fromState, toState,
					(NondetermisticFuzzyAutomata) automaton);
		} else {
			edge = new DFAedge(fromState, toState);
		}

		logic.getActions().changeEdge(edge);

		resetChoosenState();
	}

}
