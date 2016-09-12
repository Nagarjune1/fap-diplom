package cz.upol.jfa.testingFrames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.fuzzyLogic.rationalLogics.Rational0to1Number;
import cz.upol.automaton.sets.HugeSet;
import cz.upol.automaton.sets.SetOfAll;
import cz.upol.jfa.utils.swing.cmbbxwe.JComboBoxWthExternalisable;

public class JCBwExtTestingFrame extends JFrame {

	private static final long serialVersionUID = -2903211740624710051L;

	private Set<Degree> setE;
	private HugeSet<Degree> setNE;
	private JComboBoxWthExternalisable<Degree> cbxE;
	private JComboBoxWthExternalisable<Degree> cbxNE;

	public JCBwExtTestingFrame() {
		super("JComboBoxWithEternalisable testing form");

		initializeSets();
		initializeComponents();

		setPreferredSize(new Dimension(600, 200));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	private void initializeSets() {
		setE = new TreeSet<>();
		setE.add(new Rational0to1Number(0.314));
		setE.add(new Rational0to1Number(0.271));
		setE.add(new Rational0to1Number(0.707));

		setNE = new SetOfAll<>(Degree.class);
		setNE.add(new Rational0to1Number(0.314));
		setNE.add(new Rational0to1Number(0.271));
		setNE.add(new Rational0to1Number(0.707));
	}

	private void initializeComponents() {
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		final String description = "This frame performs the demonstration of custom combobox implementation (class "
				+ JComboBoxWthExternalisable.class.getName()
				+ "). "
				+ " If this combobox accepts as its dataset instance of "
				+ HugeSet.class.getName()
				+ " it allows user to edit entered value and submitted value is automatically save into dataset. "
				+ "Otherwise it behaves as normal JComboBox.";

		JTextArea desc = new JTextArea(description);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.setEditable(false);
		desc.setOpaque(false);
		getContentPane().add(desc);

		JPanel combosPane = new JPanel();
		combosPane.setLayout(new FlowLayout());

		cbxE = initComboBox(setE, "editable");
		combosPane.add(cbxE);

		cbxNE = initComboBox(setNE, "noneditable");
		combosPane.add(cbxNE);

		JButton show = new JButton("Display values");
		show.addActionListener(new ShowValuesButtActionListener(this));
		combosPane.add(show);

		JPanel buttsPane = new JPanel();
		buttsPane.setLayout(new FlowLayout());

		JButton setTo05 = new JButton("Set to 0.5");
		setTo05.addActionListener(new SetTo05ButtActionListener(this));
		buttsPane.add(setTo05);

		JButton setTo707 = new JButton("Set to 0.707");
		setTo707.addActionListener(new SetTo07ButtActionListener(this));
		buttsPane.add(setTo707);

		JButton addRand = new JButton("Add random");
		addRand.addActionListener(new AddRandomButtActionListener(this));
		buttsPane.add(addRand);

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		getContentPane().add(combosPane);
		getContentPane().add(buttsPane);

		JRootPane rootPane = SwingUtilities.getRootPane(show);
		rootPane.setDefaultButton(show);

	}

	private JComboBoxWthExternalisable<Degree> initComboBox(Set<Degree> set,
			String title) {

		final JComboBoxWthExternalisable<Degree> cbx = new JComboBoxWthExternalisable<Degree>(
				set, Rational0to1Number.EXTERNISATOR);

		cbx.addActionListener(new ComboBoxActionListener(cbx, title));

		return cbx;
	}

	private static final class ComboBoxActionListener implements ActionListener {
		private final JComboBoxWthExternalisable<Degree> cbx;
		private final String title;

		private ComboBoxActionListener(JComboBoxWthExternalisable<Degree> cbx,
				String title) {

			this.cbx = cbx;
			this.title = title;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Changed " + title + " combobox to value "
					+ cbx.getSelectedItem());
		}
	}

	private static final class ShowValuesButtActionListener implements
			ActionListener {

		private final JCBwExtTestingFrame frame;

		public ShowValuesButtActionListener(JCBwExtTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Degree rE = (Degree) frame.cbxE.getSelectedItem();
			Degree rNE = (Degree) frame.cbxNE.getSelectedItem();

			String msg = "Values are: " + rE + " (editable) " + rNE
					+ " (noneditable).";

			System.out.println(msg);
			JOptionPane.showMessageDialog((Component) e.getSource(), msg);
		}
	}

	private static final class AddRandomButtActionListener implements
			ActionListener {

		private final JCBwExtTestingFrame frame;

		public AddRandomButtActionListener(JCBwExtTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			double num = Math.random();
			num = Math.floor(num * 10000) / 10000;
			
			Degree rand = new Rational0to1Number(num);

			frame.setE.add(rand);
			frame.setNE.add(rand);
			frame.cbxE.dataUpdated();
			frame.cbxNE.dataUpdated();

		}
	}

	private static final class SetTo07ButtActionListener implements
			ActionListener {

		private final JCBwExtTestingFrame frame;

		public SetTo07ButtActionListener(JCBwExtTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Degree num707 = new Rational0to1Number(0.707);

			frame.cbxE.setSelectedItem(num707);
			frame.cbxNE.setSelectedItem(num707);
		}
	}

	private static final class SetTo05ButtActionListener implements
			ActionListener {

		private final JCBwExtTestingFrame frame;

		public SetTo05ButtActionListener(JCBwExtTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Degree num05 = new Rational0to1Number(0.5);

			frame.cbxE.setSelectedItem(num05);
			frame.cbxNE.setSelectedItem(num05);
		}
	}
}
