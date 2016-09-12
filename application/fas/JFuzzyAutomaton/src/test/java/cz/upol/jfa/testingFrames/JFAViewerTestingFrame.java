package cz.upol.jfa.testingFrames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cz.upol.automaton.fuzzyLogic.rationalLogics.LukasiewiczLogic;
import cz.upol.automaton.io.ExportException;
import cz.upol.automaton.ling.alphabets.Alphabet;
import cz.upol.automaton.ling.alphabets.CharactersAlphabet;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.io.IOManager;
import cz.upol.jfa.utils.RandomFATGCreator;
import cz.upol.jfa.utils.TestingAutomataToGUI;
import cz.upol.jfa.utils.xml.XMLFileException;
import cz.upol.jfa.viewer.JFuzzyAutomatonViewer;

public class JFAViewerTestingFrame extends JFrame {

	private static final long serialVersionUID = -3566066128086123603L;
	private JComponent content;

	public JFAViewerTestingFrame() {
		super("JFuzzyAutomatonViewer testing form");

		initializeComponents();

		setPreferredSize(new Dimension(800, 500));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	private void initializeComponents() {
		JPanel headers = new JPanel();
		headers.setLayout(new BoxLayout(headers, BoxLayout.PAGE_AXIS));

		final String description = "This frame demonstrates "
				+ JFuzzyAutomatonViewer.class.getName()
				+ " component and loading from file."
				+ " The component prints into console captured events (clicking).";

		JTextArea desc = new JTextArea(description);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.setEditable(false);
		desc.setOpaque(false);
		headers.add(desc, BorderLayout.NORTH);

		JPanel butts = new JPanel(new FlowLayout());

		JButton openButt = new JButton("Display simple");
		openButt.addActionListener(new DisplayButtActionListener(this, 1));
		butts.add(openButt);

		JButton open2Butt = new JButton("Display \"triangle\"");
		open2Butt.addActionListener(new DisplayButtActionListener(this, 2));
		butts.add(open2Butt);

		JButton open3Butt = new JButton("Display \"y\"");
		open3Butt.addActionListener(new DisplayButtActionListener(this, 3));
		butts.add(open3Butt);

		JButton open4Butt = new JButton("Small random");
		open4Butt.addActionListener(new DisplayButtActionListener(this, 4));
		butts.add(open4Butt);

		JButton loadButt = new JButton("Load from file");
		loadButt.addActionListener(new LoadButtActionListener(this));
		butts.add(loadButt);

		JButton exButt = new JButton("Export");
		exButt.addActionListener(new ExportButtActionListener(this));
		butts.add(exButt);

		headers.add(butts);
		getContentPane().add(headers, BorderLayout.NORTH);

		content = new JLabel("Firstly open an automaton");
		getContentPane().add(content, BorderLayout.CENTER);
	}

	public void openAutomaton(BaseAutomatonToGUI automaton) {
		getContentPane().remove(content);

		JFuzzyAutomatonViewer viewer = new JFuzzyAutomatonViewer(automaton);

		viewer.addActionListener(new ViewerActionListener());
		content = viewer;

		getContentPane().add(content, BorderLayout.CENTER);
		pack();
		invalidate();
	}

	public static class DisplayButtActionListener implements ActionListener {

		private final JFAViewerTestingFrame frame;
		private final int number;

		public DisplayButtActionListener(JFAViewerTestingFrame frame, int number) {
			this.frame = frame;
			this.number = number;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			BaseAutomatonToGUI automaton = null;
			switch (number) {
			case 1:
				automaton = TestingAutomataToGUI.createOneStateAutomaton();
				break;
			case 2:
				automaton = TestingAutomataToGUI.createAnotherTriangle();
				break;
			case 3:
				automaton = TestingAutomataToGUI.createLikeYpsilonAutomaton();
				break;
			case 4:
				Alphabet alphabet = new CharactersAlphabet(true, false, false);
				automaton = new RandomFATGCreator().createFATG(null,
						new LukasiewiczLogic(), alphabet, 5, 5, 2, 4, "a", "b",
						"c");
				break;
			default:
				return;
			}

			frame.openAutomaton(automaton);
		}

	}

	public static class LoadButtActionListener implements ActionListener {

		private final JFAViewerTestingFrame frame;

		public LoadButtActionListener(JFAViewerTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			File file = new File("automaton.xml");
			BaseAutomatonToGUI automaton;

			try {
				automaton = new IOManager().load(file);
			} catch (XMLFileException ex) {
				JOptionPane.showMessageDialog(frame, ex.getMessage());
				return;
			}

			frame.openAutomaton(automaton);
		}

	}

	public class ViewerActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Occured event: " + e);
		}

	}

	public static class ExportButtActionListener implements ActionListener {

		private static final File FILE = new File("output.png");
		private final JFAViewerTestingFrame frame;

		public ExportButtActionListener(JFAViewerTestingFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (frame.content instanceof JFuzzyAutomatonViewer) {
				JFuzzyAutomatonViewer viewer = (JFuzzyAutomatonViewer) frame.content;
				BaseAutomatonToGUI automaton = viewer.getAutomaton();
				try {
					new IOManager().exportPNG(automaton, true, FILE);
					System.out.println("export complete");
				} catch (ExportException ex) {
					ex.printStackTrace();
				}

			}
		}

	}
}
