package cz.upol.jfa.editor.forms;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.ResiduedLattice;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.sets.Externisator;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.utils.swing.JSaveableAndCloseableDial;
import cz.upol.jfa.utils.swing.cmbbxwe.JComboBoxWthExternalisable;
import cz.upol.jfa.viewer.Position;

public class JEditStateDial extends JSaveableAndCloseableDial {

	private static final long serialVersionUID = 5963061942275023795L;

	private final BaseAutomatonToGUI automaton;

	private State state;
	private Degree initialDegree;
	private Degree finiteDegree;
	private Position position;

	private JTextField stateLabel;
	private JComboBoxWthExternalisable<Degree> initialDegreeCbx;
	private JComboBoxWthExternalisable<Degree> finiteDegreeCbx;
	private JTextField positionXLabel;
	private JTextField positionYLabel;

	private JCheckBox initialCkbx;

	private Boolean initial;

	public JEditStateDial(Frame owner, BaseAutomatonToGUI automaton) {
		super(owner, "Stav", true);

		this.automaton = automaton;

		initializeComponents();
		setLocation(40, 40);
	}

	public State getState() {
		return state;
	}

	public Degree getInitialDegree() {
		return initialDegree;
	}

	public Boolean getIsInitial() {
		return initial;
	}

	public Degree getFiniteDegree() {
		return finiteDegree;
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public JPanel createContentPane() {

		JPanel formFieldsPane = new JPanel(new GridLayout(5, 2));

		initializeLabelComponents(formFieldsPane);

		initializeDegreesComponents(formFieldsPane);

		initializePositionComponents(formFieldsPane);

		return formFieldsPane;
	}

	public void initializeLabelComponents(JPanel formFieldsPane) {
		JLabel stateLabellbl = new JLabel("Označení stavu");
		formFieldsPane.add(stateLabellbl);

		stateLabel = new JTextField("q");
		formFieldsPane.add(stateLabel);
	}

	public void initializeDegreesComponents(JPanel formFieldsPane) {
		ResiduedLattice residuedLattice = AutomatonDesc
				.residuedLattice(automaton);
		Set<Degree> elements = residuedLattice.getElements();
		Externisator<Degree> externisator = residuedLattice
				.getUniverseElementsExternisator();

		if (AutomatonDesc.hasInitialDegree(automaton)) {
			JLabel initialDegreelbl = new JLabel("Vstupní ve stupni");
			formFieldsPane.add(initialDegreelbl);

			initialDegreeCbx = new JComboBoxWthExternalisable<>(elements,
					externisator);
			formFieldsPane.add(initialDegreeCbx);
		} else {
			JLabel initialDegreelbl = new JLabel("Počáteční?");
			formFieldsPane.add(initialDegreelbl);

			initialCkbx = new JCheckBox();
			formFieldsPane.add(initialCkbx);
		}

		JLabel finiteDegreelbl = new JLabel("Koncový ve stupni");
		formFieldsPane.add(finiteDegreelbl);

		finiteDegreeCbx = new JComboBoxWthExternalisable<>(elements,
				externisator);

		formFieldsPane.add(finiteDegreeCbx);
	}

	public void initializePositionComponents(JPanel formFieldsPane) {
		JLabel positionXlbl = new JLabel("Pozice X");
		formFieldsPane.add(positionXlbl);

		positionXLabel = new JTextField("0");
		formFieldsPane.add(positionXLabel);

		JLabel positionYlbl = new JLabel("Pozice Y");
		formFieldsPane.add(positionYlbl);

		positionYLabel = new JTextField("0");
		formFieldsPane.add(positionYLabel);
	}

	/**
	 * Otevře k editaci zadaný stav.
	 * 
	 * @param state
	 * @param automaton
	 */
	public void openStateEdit(State state) {
		this.state = state;
		if (AutomatonDesc.hasInitialDegree(automaton)) {
			this.initialDegree = AutomatonDesc.initialDegree(automaton, state);
		} else {
			this.initial = AutomatonDesc.isInitial(automaton, state);
		}

		this.finiteDegree = AutomatonDesc.finiteDegree(automaton, state);
		this.position = automaton.getProvider().findPosition(state);

		setToState();
		setVisible(true);
	}

	/**
	 * Otevře vytvoření nového stavu na zadané pozici (která je nepovinná, může
	 * být null).
	 * 
	 * @param position
	 */
	public void openNewState(Position position) {
		this.state = null;
		if (AutomatonDesc.hasInitialDegree(automaton)) {
			this.initialDegree = AutomatonDesc.residuedLattice(automaton)
					.getZero();
		} else {
			this.initial = false;
		}
		this.finiteDegree = AutomatonDesc.residuedLattice(automaton).getZero();

		if (position != null) {
			this.position = position;
		} else {
			this.position = new Position(0, 0);
		}

		setToState();
		setVisible(true);
	}

	private void setToState() {
		if (state == null) {
			this.stateLabel.setText(automaton.getProvider().nextStateLabel());
		} else {
			this.stateLabel.setText(state.getLabel());
		}

		if (initialDegreeCbx != null) {
			this.initialDegreeCbx.setSelectedItem(initialDegree);
		} else {
			this.initialCkbx.setSelected(initial);
		}
		this.finiteDegreeCbx.setSelectedItem(finiteDegree);
		this.positionXLabel.setText(Integer.toString(position.getIntX()));
		this.positionYLabel.setText(Integer.toString(position.getIntY()));
	}

	@Override
	public List<String> tryToSave() {
		List<String> errors = new LinkedList<>();

		tryToSaveLabel(errors);

		tryToSaveDegrees(errors);

		tryToSavePosition(errors);

		return errors;
	}

	public void tryToSaveLabel(List<String> errors) {
		if (stateLabel.getText().isEmpty()) {
			errors.add("Enter state label");
		}

		state = new State(stateLabel.getText());
	}

	public void tryToSaveDegrees(List<String> errors) {
		if (AutomatonDesc.hasInitialDegree(automaton)) {
			initialDegree = (Degree) initialDegreeCbx.getSelectedItem();
			if (initialDegree == null) {
				errors.add("Provide value of initial degree");
			}
		} else {
			initial = initialCkbx.isSelected();
		}

		finiteDegree = (Degree) finiteDegreeCbx.getSelectedItem();
		if (finiteDegree == null) {
			errors.add("Provide value of finite degree");
		}
	}

	public void tryToSavePosition(List<String> errors) {
		int positionX = position.getIntX(), positionY = position.getIntY();
		try {
			positionX = Integer.parseUnsignedInt(positionXLabel.getText());
		} catch (Exception e) {
			errors.add("Cannot parse x position");
		}

		try {
			positionY = Integer.parseUnsignedInt(positionYLabel.getText());
		} catch (Exception e) {
			errors.add("Cannot parse y position");
		}

		position = new Position(positionX, positionY);
	}
}
