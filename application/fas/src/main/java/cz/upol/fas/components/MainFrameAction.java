package cz.upol.fas.components;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import cz.upol.fas.JFASMainFrame;
import cz.upol.fas.config.AutomataType;
import cz.upol.fas.config.ExportFormat;

public abstract class MainFrameAction extends AbstractAction {

	private static final long serialVersionUID = -4096048838169275335L;

	protected final JFASMainFrame mainFrame;
	protected final boolean requiresAutomaton;

	public MainFrameAction(JFASMainFrame mainFrame, boolean requiresAutomaton,
			String name) {
		super(name);

		this.mainFrame = mainFrame;
		this.requiresAutomaton = requiresAutomaton;
	}

	public boolean isRequiresAutomaton() {
		return requiresAutomaton;
	}

	public static class ExitAction extends MainFrameAction implements Action {

		private static final long serialVersionUID = -7482814248375624035L;

		public ExitAction(JFASMainFrame mainFrame, String name) {
			super(mainFrame, false, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.close(); 
		}
	}

	public static class CloseAction extends MainFrameAction {
		private static final long serialVersionUID = 7704082836361104949L;

		public CloseAction(JFASMainFrame mainFrame, String name) {
			super(mainFrame, true, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.closeCurrentAutomaton();
		}

	}

	public static class SaveAsAction extends MainFrameAction implements Action {

		private static final long serialVersionUID = -4741249613157851905L;

		public SaveAsAction(JFASMainFrame mainFrame, String name) {
			super(mainFrame, true, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.getCurrentCard().saveAs();
		}

	}

	public static class SaveAction extends MainFrameAction implements Action {

		private static final long serialVersionUID = -6661051060199485457L;

		public SaveAction(JFASMainFrame mainFrame, String name) {
			super(mainFrame, true, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.getCurrentCard().save();
		}

	}

	public static class OpenAction extends MainFrameAction implements Action {

		private static final long serialVersionUID = 3395129438391915592L;

		public OpenAction(JFASMainFrame mainFrame, String name) {
			super(mainFrame, false, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.openAutomaton();
		}

	}

	public static class NewAutomataAction extends MainFrameAction {

		private static final long serialVersionUID = 5632686753089958497L;

		private final AutomataType type;

		public NewAutomataAction(JFASMainFrame mainFrame, AutomataType type) {
			super(mainFrame, false, type.externalize());
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.newAutomaton(type);
		}
	}

	public static class ExportAction extends MainFrameAction {

		private static final long serialVersionUID = -5966825815067617527L;

		private final ExportFormat format;

		public ExportAction(JFASMainFrame mainFrame, ExportFormat format) {
			super(mainFrame, true, format.externalize());
			this.format = format;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.getCurrentCard().export(format);
		}

	}

}
