package cz.upol.fas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cz.upol.fas.components.JCloseablesTabbedPane;
import cz.upol.fas.components.cardsPanel.JCloseableCard;

public class JCTPTestFrame extends JFrame {
	public class AddButtActionListener implements ActionListener {

		private final JCloseablesTabbedPane tabs;

		public AddButtActionListener(JCloseablesTabbedPane tabs) {
			this.tabs = tabs;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			tabs.addTab("Next tab", new JLabel("I am next tab"));
			JCloseableCard card = tabs.getCard(tabs.getTabCount() - 1);
			card.setTabHeader("The " + tabs.getCard(card) + "th card");
		}

	}

	private static final long serialVersionUID = -3753515806302544817L;

	public JCTPTestFrame() {
		super("JCloseablesTabbedPane test frame");

		initializeComponents();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(400, 400));
		pack();
	}

	private void initializeComponents() {
		JCloseablesTabbedPane tabs = new JCloseablesTabbedPane();

		JButton addButt = new JButton("Add next");
		addButt.addActionListener(new AddButtActionListener(tabs));
		tabs.addTab("First tab", null, addButt, "This is first tab");

		getContentPane().add(tabs, BorderLayout.CENTER);
	}

}
