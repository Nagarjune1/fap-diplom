package cz.upol.jfa.utils.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public abstract class JSaveableAndCloseableDial extends JDialog {

	private static final long serialVersionUID = 6454626867403227331L;

	private JButton submitButt;
	private JButton cancelButt;

	private Boolean saved = null;

	public JSaveableAndCloseableDial(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public JSaveableAndCloseableDial(Frame owner, String title) {
		super(owner, title);
	}

	public Boolean getSaved() {
		return saved;
	}

	public void initializeComponents() {
		JPanel formFieldsPane = createContentPane();
		JPanel buttonsPane = createButtonsPane();

		getContentPane().add(formFieldsPane, BorderLayout.CENTER);
		getContentPane().add(buttonsPane, BorderLayout.SOUTH);

		setResizable(false);

		setEnterEscapeKeys();
		addWindowListener(new JSACDWindowListener(this));
		pack();

	}

	public abstract JPanel createContentPane();

	private JPanel createButtonsPane() {
		JPanel buttonsPane = new JPanel(new FlowLayout(FlowLayout.CENTER));

		submitButt = new JButton("Potvrdit");
		submitButt.addActionListener(new SubmitButtListener(this));
		buttonsPane.add(submitButt);

		cancelButt = new JButton("Zrušit");
		cancelButt.addActionListener(new CancelButtListener(this));
		buttonsPane.add(cancelButt);

		return buttonsPane;
	}

	private void setEnterEscapeKeys() {
		ActionListener escapeListener = new JSACDEscapeListener(this);
		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

		getRootPane().registerKeyboardAction(escapeListener, keyStroke,
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		getRootPane().setDefaultButton(submitButt);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			saved = null;
		}

		super.setVisible(b);
	}

	public static boolean tryToShowErrors(Window parent, List<String> errors) {
		String error = errorsToErrorMsg(errors);

		if (error != null) {
			JOptionPane.showMessageDialog(parent, error, "Invalid content",
					JOptionPane.ERROR_MESSAGE);
			return true;
		} else {
			return false;
		}
	}

	public void saveInvoked() {
		List<String> errors = tryToSave();

		boolean shown = tryToShowErrors(this, errors);
		if (!shown) {
			setVisible(false);
			saved = true;
		}
	}

	public void cancelInvoked() {
		setVisible(false);
		saved = false;
	}

	public abstract List<String> tryToSave();

	public static String errorsToErrorMsg(List<String> errors) {
		if (errors == null || errors.isEmpty()) {
			return null;
		}

		StringBuilder result = new StringBuilder(
				"<html><p>Cannot continue. Firstly fix following problems:</p><ul>");

		for (String error : errors) {
			result.append("<li>");
			result.append(error);
			result.append("</li>");
		}

		result.append("</ul></html>");

		return result.toString();
	}

	public static class JSACDEscapeListener implements ActionListener {

		final JSaveableAndCloseableDial dial;

		public JSACDEscapeListener(JSaveableAndCloseableDial dial) {

			this.dial = dial;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dial.cancelInvoked();
		}

	}

	public static class SubmitButtListener implements ActionListener {

		private final JSaveableAndCloseableDial dial;

		public SubmitButtListener(JSaveableAndCloseableDial dial) {
			this.dial = dial;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dial.saveInvoked();
		}

	}

	public class CancelButtListener implements ActionListener {

		private final JSaveableAndCloseableDial dial;

		public CancelButtListener(JSaveableAndCloseableDial dial) {
			super();
			this.dial = dial;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dial.cancelInvoked();
		}

	}

	public class JSACDWindowListener extends WindowAdapter {

		private final JSaveableAndCloseableDial dial;

		public JSACDWindowListener(JSaveableAndCloseableDial dial) {

			this.dial = dial;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			dial.cancelInvoked();
		}

	}
}
