package cz.upol.fas.components;

import java.awt.Component;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import cz.upol.fas.components.cardsPanel.JCloseableCard;

public class JCloseablesTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = -4491747688576381750L;
	private final List<JCloseableCard> cards = new LinkedList<>();

	public JCloseablesTabbedPane() {
		super();
	}

	public JCloseablesTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
	}

	public JCloseablesTabbedPane(int tabPlacement) {
		super(tabPlacement);
	}

	public List<JCloseableCard> getCards() {
		return cards;
	}

	public JCloseableCard getCard(int index) {
		return cards.get(index);
	}

	public int getCard(JCloseableCard card) {
		return cards.indexOf(card);
	}

	@Override
	public void addTab(String title, Component content) {
		addTab(title, null, content);
	}

	@Override
	public void addTab(String title, Icon icon, Component content) {
		addTab(title, icon, content, null);
	}

	@Override
	public void addTab(String title, Icon icon, Component content, String tip) {
		JCloseableCard card = new JCloseableCard(this);
		card.setTabHeader(title);
		card.setTabIcon(icon);
		card.setCardContent(content);
		card.setTabTooltip(tip);

		addTab(card);
	}

	public void addTab(JCloseableCard card) {
		cards.add(card);

		super.addTab(card.getTabHeader(), card.getTabIcon(),
				card.getCardContent(), card.getTabTooltip());

		int index = this.getTabCount() - 1;

		setTabComponentAt(index, card.getTabComponent());
	}

	@Override
	public void removeTabAt(int index) {
		cards.remove(index);
		super.removeTabAt(index);
	}

	public void removeTab(JCloseableCard card) {
		int index = getCard(card);
		removeTabAt(index);
	}

	public void setSelectedTab(JCloseableCard card) {
		int index = getCard(card);
		setSelectedIndex(index);
	}

}
