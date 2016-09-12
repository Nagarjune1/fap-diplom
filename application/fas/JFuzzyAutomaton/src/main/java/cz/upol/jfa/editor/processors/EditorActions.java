package cz.upol.jfa.editor.processors;

import javax.swing.JOptionPane;

import cz.upol.automaton.automata.ingredients.HasDelta;
import cz.upol.automaton.automata.ingredients.HasStates;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.editor.JFAEditor;
import cz.upol.jfa.editor.forms.JEditEdgeDial;
import cz.upol.jfa.editor.forms.JEditStateDial;
import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.interactivity.ViewerSelection;

public class EditorActions {

	private final EditorActionsLogic logic;

	public EditorActions(EditorActionsLogic logic) {
		this.logic = logic;
	}

	public void createState(Position position) {
		BaseAutomatonToGUI automaton = logic.getEditor().getAutomaton();
		JEditStateDial dial = logic.getEditStateDial();

		dial.openNewState(position);

		if (dial.getSaved() == true) {
			State newState = dial.getState();
			Position newPosition = dial.getPosition();
			newState = automaton.getProvider().addOrRenameState(newState,
					newPosition);

			if (AutomatonDesc.hasInitialDegree(automaton)) {
				Degree initial = dial.getInitialDegree();
				AutomatonDesc.setInitialDegree(automaton, newState, initial);
			} else {
				boolean initial = dial.getIsInitial();
				AutomatonDesc.setInitial(automaton, newState, initial);
			}

			Degree finite = dial.getFiniteDegree();
			AutomatonDesc.setFiniteDegree(automaton, newState, finite);
		}

	}

	public void changeState(State state) {
		BaseAutomatonToGUI automaton = logic.getEditor().getAutomaton();
		JEditStateDial dial = logic.getEditStateDial();
		JFAEditor editor = logic.getEditor();

		State oldState = state;
		dial.openStateEdit(state);

		if (dial.getSaved() == true) {
			State newState = dial.getState();
			Position newPosition = dial.getPosition();

			((HasStates) automaton).updateState(oldState, newState);
			automaton.getProvider().moveStateTo(newState, newPosition);

			if (AutomatonDesc.hasInitialDegree(automaton)) {
				Degree initial = dial.getInitialDegree();
				AutomatonDesc.setInitialDegree(automaton, newState, initial);
			} else {
				boolean initial = dial.getIsInitial();
				AutomatonDesc.setInitial(automaton, newState, initial);
			}

			Degree finite = dial.getFiniteDegree();
			AutomatonDesc.setFiniteDegree(automaton, newState, finite);
			editor.automatonChanged();
		}
	}

	public void deleteState(State state) {
		JFAEditor editor = logic.getEditor();

		int result = JOptionPane
				.showConfirmDialog(
						editor.getOwner(),
						"Opravdu si přejete odstranit tento stav? Odstraníte tím i všechny přechody z něj a do něj",
						"Opravdu?", JOptionPane.YES_NO_OPTION);

		editor.changeSelection((ViewerSelection) null);

		if (result == JOptionPane.YES_OPTION) {
			BaseAutomatonToGUI automaton = logic.getEditor().getAutomaton();

			((HasStates) automaton).removeState(state);
			editor.automatonChanged();
		}

	}

	public void createEdge() {
		BaseAutomatonToGUI automaton = logic.getEditor().getAutomaton();
		JEditEdgeDial dial = logic.getEditEdgeDial();
		JFAEditor editor = logic.getEditor();

		dial.setToNewEdge();

		if (dial.getSaved() == true) {
			AbstractEdge<?> newEdge = dial.getEdge();
			((HasDelta<?, AbstractEdge<?>>) automaton).updateEdge(newEdge);
			editor.automatonChanged();
		}
	}

	public void changeEdge(AbstractEdge<?> edge) {
		BaseAutomatonToGUI automaton = logic.getEditor().getAutomaton();
		JEditEdgeDial dial = logic.getEditEdgeDial();
		JFAEditor editor = logic.getEditor();

		dial.setToEdge(edge);

		if (dial.getSaved() == true) {
			AbstractEdge<?> newEdge = dial.getEdge();
			((HasDelta<?, AbstractEdge<?>>) automaton).updateEdge(newEdge);
			editor.automatonChanged();
		}
	}

	public void deleteEdge(AbstractEdge<?> edge) {
		JFAEditor editor = logic.getEditor();

		int result = JOptionPane.showConfirmDialog(editor.getOwner(),
				"Opravdu si přejete odstranit všechny tyto přechody?",
				"Opravdu?", JOptionPane.YES_NO_OPTION);

		editor.changeSelection((ViewerSelection) null);

		if (result == JOptionPane.YES_OPTION) {
			BaseAutomatonToGUI automaton = logic.getEditor().getAutomaton();

			((HasDelta<?, AbstractEdge<?>>) automaton).removeEdge(edge);
			editor.automatonChanged();
		}
	}

}
