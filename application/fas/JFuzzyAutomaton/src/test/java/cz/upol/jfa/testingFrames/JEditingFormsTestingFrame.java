package cz.upol.jfa.testingFrames;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.automaton.misc.edges.NFAedge;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;
import cz.upol.jfa.editor.forms.JEditEdgeDial;
import cz.upol.jfa.editor.forms.JEditStateDial;
import cz.upol.jfa.utils.TestingAutomataToGUI;
import cz.upol.jfa.viewer.Position;

public class JEditingFormsTestingFrame extends JFrame {
	private static final long serialVersionUID = -8088177847936322672L;

	private final JEditStateDial stateEditDial;
	private final JEditEdgeDial edgeEditDial;

	private final NondetFuzzyAutomatonToGUI automaton = TestingAutomataToGUI
			.createTriangle();

	public JEditingFormsTestingFrame() {
		super("JComboBoxWithEternalisable testing form");

		initializeComponents();

		stateEditDial = new JEditStateDial(this, automaton);
		edgeEditDial = new JEditEdgeDial(this, automaton);

		setPreferredSize(new Dimension(600, 200));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	private void initializeComponents() {
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		final String description = "This frame demonstrates use cases of by-automaton-editor-used dialogs ("
				+ JEditStateDial.class.getName()
				+ " and "
				+ JEditEdgeDial.class.getName() + ").";

		JTextArea desc = new JTextArea(description);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.setEditable(false);
		desc.setOpaque(false);
		getContentPane().add(desc);

		JPanel stateButtsPane = new JPanel();
		stateButtsPane.setLayout(new FlowLayout());

		JButton createStateButt = new JButton("Create state");
		createStateButt.addActionListener(new CreateStateButtActionListener(
				this));
		stateButtsPane.add(createStateButt);

		JButton editStateButt = new JButton("Edit state");
		editStateButt.addActionListener(new EditStateButtActionListener(this));
		stateButtsPane.add(editStateButt);

		JPanel edgeButtsPane = new JPanel();
		edgeButtsPane.setLayout(new FlowLayout());
		JButton createNewEdge = new JButton("Create new edge");
		createNewEdge.addActionListener(new CreateNewEdgeButtActionListener(
				this));
		edgeButtsPane.add(createNewEdge);

		JButton editEdgeButt = new JButton("Edit edge");
		editEdgeButt.addActionListener(new EditEdgeButtActionListener(this));
		edgeButtsPane.add(editEdgeButt);

		getContentPane().add(stateButtsPane);
		getContentPane().add(edgeButtsPane);
	}

	private static final class EditEdgeButtActionListener implements
			ActionListener {

		private final JEditingFormsTestingFrame frame;

		public EditEdgeButtActionListener(JEditingFormsTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			State from = new State("q0"), to = new State("q1");
			NFAedge oldEdge = frame.automaton.getEdge(from, to);
			System.out.println("Opening edit edge " + oldEdge.toString());

			frame.edgeEditDial.setToEdge(oldEdge);

			if (frame.edgeEditDial.getSaved() == true) {
				AbstractEdge<?> newEdge = frame.edgeEditDial.getEdge();

				System.out.println("Closed edit edge: " + newEdge.toString());

			} else if (frame.edgeEditDial.getSaved() == false) {
				System.out.println("Closed edit edge: canceled");
			}
		}
	}

	private static final class CreateNewEdgeButtActionListener implements
			ActionListener {
		private final JEditingFormsTestingFrame frame;

		public CreateNewEdgeButtActionListener(JEditingFormsTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Opening creating of whole new edge ");

			frame.edgeEditDial.setToNewEdge();

			if (frame.edgeEditDial.getSaved() == true) {
				AbstractEdge<?> newEdge = frame.edgeEditDial.getEdge();

				System.out.println("Closed creating of new edge: "
						+ newEdge.toString());

			} else if (frame.edgeEditDial.getSaved() == false) {
				System.out.println("Closed creating of new edge: canceled");
			}
		}
	}

	private static final class CreateStateButtActionListener implements
			ActionListener {
		private final JEditingFormsTestingFrame frame;

		public CreateStateButtActionListener(JEditingFormsTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Position oldPosition = new Position(42, 69);
			System.out
					.println("Opening create state. (on " + oldPosition + ")");
			frame.stateEditDial.openNewState(oldPosition);

			if (frame.stateEditDial.getSaved() == true) {
				State state = frame.stateEditDial.getState();
				Degree initialDegree = frame.stateEditDial.getInitialDegree();
				Degree finiteDegree = frame.stateEditDial.getFiniteDegree();
				Position position = frame.stateEditDial.getPosition();

				System.out.println("Closed create state: " + state + ": ->"
						+ initialDegree + ", _" + finiteDegree + ", @ "
						+ position);
			} else if (frame.stateEditDial.getSaved() == false) {
				System.out.println("Closed create state: canceled");
			}

		}
	}

	private static final class EditStateButtActionListener implements
			ActionListener {
		private final JEditingFormsTestingFrame frame;

		public EditStateButtActionListener(JEditingFormsTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			State state = new State("q1");
			System.out.println("Opening edit state: " + state + " ...");
			frame.stateEditDial.openStateEdit(state);

			if (frame.stateEditDial.getSaved() == true) {
				State newState = frame.stateEditDial.getState();
				Degree newInitialDegree = frame.stateEditDial
						.getInitialDegree();
				Degree newFiniteDegree = frame.stateEditDial.getFiniteDegree();
				Position newPosition = frame.stateEditDial.getPosition();

				System.out.println("Closed edit state: " + newState + ": ->"
						+ newInitialDegree + ", _" + newFiniteDegree + ", @ "
						+ newPosition);

			} else if (frame.stateEditDial.getSaved() == false) {
				System.out.println("Closed edit state: canceled");
			}

		}
	}
}
