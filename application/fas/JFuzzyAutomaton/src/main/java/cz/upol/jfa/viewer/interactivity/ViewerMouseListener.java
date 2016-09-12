package cz.upol.jfa.viewer.interactivity;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import cz.upol.jfa.viewer.JFuzzyAutomatonViewer;
import cz.upol.jfa.viewer.Position;

public class ViewerMouseListener implements MouseListener {

	private static final int DOUBLECLICK_COEF = 100000;
	private static final Integer timerInterval = ((int) Toolkit
			.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval"))
			/ DOUBLECLICK_COEF;

	private final JFuzzyAutomatonViewer viewer;
	private boolean wasDoubleClick;
	private Timer timer;

	public ViewerMouseListener(JFuzzyAutomatonViewer viewer) {
		this.viewer = viewer;
	}

	public boolean isWasDoubleClick() {
		return wasDoubleClick;
	}

	public void setWasDoubleClick(boolean wasDoubleClick) {
		this.wasDoubleClick = wasDoubleClick;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!viewer.isEnabled()) {
			return;
		}

		if (e.getClickCount() == 2) {
			setWasDoubleClick(true);
			fireEvent(e, true);
		} else {
			timer = new Timer(timerInterval.intValue(),
					new JFAMLTimerActionListener(this, e));

			timer.setRepeats(false);
			timer.start();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void fireEvent(MouseEvent e, boolean isDoubleclick) {
		ViewerEventType type = typeFromEvent(e, isDoubleclick);
		Position where = new Position(e.getPoint());
		ViewerSelection on = viewer.clickedOn(where);
		long when = e.getWhen();
		int id = e.getID();

		ViewerActionEvent event = new ViewerActionEvent(viewer, type, on,
				where, id, when);

		viewer.fireActions(event);
	}

	private ViewerEventType typeFromEvent(MouseEvent e, boolean isDoubleclick) {
		boolean isLeft = SwingUtilities.isLeftMouseButton(e);
		boolean isRight = SwingUtilities.isRightMouseButton(e);

		return ViewerEventType.get(isLeft, isRight, isDoubleclick);
	}

	private static final class JFAMLTimerActionListener implements
			ActionListener {

		private final ViewerMouseListener sendingListener;
		private final MouseEvent sendingEvent;

		public JFAMLTimerActionListener(ViewerMouseListener seidingListener,
				MouseEvent sendingEvent) {

			this.sendingListener = seidingListener;
			this.sendingEvent = sendingEvent;
		}

		public void actionPerformed(ActionEvent evt) {
			if (sendingListener.isWasDoubleClick()) {
				sendingListener.setWasDoubleClick(false);
			} else {
				sendingListener.fireEvent(sendingEvent, false);
			}
		}

	}

}
