package cz.upol.fas.components.cardsPanel;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cz.upol.fas.components.JCloseablesTabbedPane;

public class JCloseableCard extends JPanel {

	private static final long serialVersionUID = -1279845203244269735L;

	private final JCloseablesTabbedPane owner;

	private JPanel tabPane;
	private JLabel tabLabel;
	private JButton closeButt;

	private Component content;

	public JCloseableCard(JCloseablesTabbedPane owner) {
		super();
		this.owner = owner;

		initializeComponents();
	}

	public JCloseableCard(JCloseablesTabbedPane owner, LayoutManager layout) {
		super(layout);
		this.owner = owner;

		initializeComponents();
	}

	public JCloseablesTabbedPane getOwner() {
		return owner;
	}

	public String getTabHeader() {
		return tabLabel.getText();
	}

	public void setTabHeader(String header) {
		tabLabel.setText(header);
	}

	public String getTabTooltip() {
		return tabLabel.getToolTipText();
	}

	public void setTabTooltip(String tip) {
		tabLabel.setToolTipText(tip);
	}

	public Icon getTabIcon() {
		return tabLabel.getIcon();
	}

	public void setTabIcon(Icon icon) {
		tabLabel.setIcon(icon);
	}

	public Component getCardContent() {
		return content;
	}

	public void setCardContent(Component content) {
		this.content = content;
	}

	public JComponent getTabComponent() {
		return tabPane;
	}

	private void initializeComponents() {
		tabPane = new JPanel();
		tabPane.addMouseListener(new JCCTabMouseListener(this));
		tabPane.setOpaque(false);

		tabLabel = new JLabel("tab");
		tabLabel.addMouseListener(new JCCTabMouseListener(this));
		tabLabel.setOpaque(false);
		tabPane.add(tabLabel);

		closeButt = new JButton("X");
		closeButt.addActionListener(new JCCCloseButActionListener(this));
		closeButt.setOpaque(false);
		closeButt.setContentAreaFilled(false);
		tabPane.add(closeButt);
	}

	public class JCCCloseButActionListener implements ActionListener {

		private final JCloseableCard card;

		public JCCCloseButActionListener(JCloseableCard card) {
			this.card = card;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			card.getOwner().removeTab(card);
			// TODO before close listener
		}

	}

	public class JCCTabMouseListener extends MouseAdapter implements
			MouseListener {

		private final JCloseableCard card;

		public JCCTabMouseListener(JCloseableCard card) {
			this.card = card;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			card.getOwner().setSelectedTab(card);
		}

	}

}