package cz.upol.fas.frames;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.FuzzyLogic;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.fas.config.AutomataType;
import cz.upol.fas.config.Avaibles;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.utils.RandomFATGCreator;
import cz.upol.jfa.utils.swing.JSaveableAndCloseableDial;

public class JNewAutomataFrame extends JSaveableAndCloseableDial {

	private static final long serialVersionUID = -4330306037369091381L;

	private final RandomFATGCreator creator = new RandomFATGCreator();
	private static final String COMA_REGEX = ", ?";

	private final AutomataType type;

	private JComboBox<FuzzyLogic> cmbLogics;
	private JComboBox<Alphabet> cmbAlphabets;
	private JSpinner txtStates;
	private JSpinner txtTransitions;

	private JTextField txtSymbols;

	private JTextField txtDegrees;

	public JNewAutomataFrame(Frame owner, AutomataType type) {
		super(owner, "Nový " + type.externalize(), true);

		this.type = type;

		initializeComponents();

		pack();
	}

	@Override
	public JPanel createContentPane() {
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

		JPanel basics = createBasicsPane();
		content.add(basics);

		JPanel random = createRanomPane();
		content.add(random);

		// JLabel lblDesc = new JLabel("Symboly a stupně oddělujte čárkami");
		// content.add(lblDesc);

		return content;
	}

	public JPanel createBasicsPane() {
		JPanel pane = new JPanel(new GridLayout(2, 2, 5, 5));

		JLabel lblLogic = new JLabel("Fuzzy logika");
		pane.add(lblLogic);

		cmbLogics = new JComboBox<>(new Vector<>(Avaibles.get()
				.avaibleFuzzyLogics()));
		pane.add(cmbLogics);

		JLabel lblAlphabet = new JLabel("Abeceda");
		pane.add(lblAlphabet);

		cmbAlphabets = new JComboBox<>(new Vector<>(Avaibles.get()
				.avaibleAlphabets()));
		pane.add(cmbAlphabets);
		return pane;
	}

	private JPanel createRanomPane() {
		JPanel pane = new JPanel(new GridLayout(4, 2, 5, 5));
		pane.setBorder(new TitledBorder("Vygenerovat"));

		JLabel lblStates = new JLabel("Vygenerovat stavů");
		pane.add(lblStates);

		SpinnerModel statesModel = new SpinnerNumberModel(0, 0,
				Integer.MAX_VALUE, 1);
		txtStates = new JSpinner(statesModel);
		pane.add(txtStates);

		JLabel lblTransitions = new JLabel("Vygenerovat přechodů");
		pane.add(lblTransitions);

		SpinnerModel transitionsModel = new SpinnerNumberModel(0, 0,
				Integer.MAX_VALUE, 1);
		txtTransitions = new JSpinner(transitionsModel);
		pane.add(txtTransitions);

		JLabel lblSymbols = new JLabel("Použít symboly (oddělujte čárkami)");
		pane.add(lblSymbols);

		txtSymbols = new JTextField();
		pane.add(txtSymbols);

		JLabel lblDegrees = new JLabel("Použít stupně (oddělujte čárkami)");
		pane.add(lblDegrees);

		txtDegrees = new JTextField();
		pane.add(txtDegrees);

		return pane;
	}

	@Override
	public List<String> tryToSave() {
		List<String> errors = new ArrayList<>();

		int statesCount = (int) txtStates.getValue();
		int transitionsCount = (int) txtTransitions.getValue();
		if (statesCount == 0 && transitionsCount > 0) {
			errors.add("Cannot create automata with transitions but no states");
		}

		FuzzyLogic logic = (FuzzyLogic) cmbLogics.getSelectedItem();
		String degreesList = txtDegrees.getText();
		try {
			Set<Degree> degrees = creator.tryToParseDegrees(logic, degreesList,
					COMA_REGEX);
			if (statesCount > 0 && degrees.isEmpty()) {
				errors.add("While creating non-empty automata must be some degrees provided");
			}
		} catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}

		Alphabet alphabet = (Alphabet) cmbAlphabets.getSelectedItem();
		String symbolsList = txtSymbols.getText();
		try {
			Set<Symbol> symbols = creator.tryToParseSymbols(alphabet,
					symbolsList, COMA_REGEX);
			if (transitionsCount > 0 && symbols.isEmpty()) {
				errors.add("While creating non-empty automata must be some symbols provided");
			}
		} catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}

		return errors;
	}

	public BaseAutomatonToGUI getAutomata() {
		boolean deterministic = type == AutomataType.DETERMINISTIC_FUZZY;
		FuzzyLogic logic = (FuzzyLogic) cmbLogics.getSelectedItem();

		Alphabet alphabet = (Alphabet) cmbAlphabets.getSelectedItem();

		int statesCount = (int) txtStates.getValue();
		int transitionsCount = (int) txtTransitions.getValue();

		String symbols = txtSymbols.getText();
		String degrees = txtDegrees.getText();

		return creator.createFATG(deterministic, logic, alphabet, statesCount,
				transitionsCount, symbols, degrees, COMA_REGEX);
	}

}
