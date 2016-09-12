package cz.upol.fas.components;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import cz.upol.fas.JFASMainFrame;
import cz.upol.fas.config.AutomataType;
import cz.upol.fas.config.Avaibles;
import cz.upol.fas.config.ExportFormat;

public class JMainMenuBar extends JMenuBar {

	private static final long serialVersionUID = 8905118550855443513L;

	private final JFASMainFrame mainFrame;
	private final Set<MainFrameAction> actions = new HashSet<>();

	private JMenu menuFile;
	private JMenu menuHelp;

	public JMainMenuBar(JFASMainFrame mainFrame) {
		this.mainFrame = mainFrame;

		initializeComponents();

		setEnabledToActionsWRA(false);
	}

	private void initializeComponents() {
		menuFile = initializeMenuFile();
		this.add(menuFile);

		menuHelp = initializeMenuHelp();
		this.add(menuHelp);
	}

	private JMenu initializeMenuFile() {
		JMenu menu = new JMenu("Soubor");

		JMenu smNew = initializeSubmenuNew();
		menu.add(smNew);

		JMenuItem miOpen = new JMenuItem();
		miOpen.setAction(action(new MainFrameAction.OpenAction(mainFrame,
				"Otevřít")));
		menu.add(miOpen);

		menu.add(new JSeparator());

		JMenuItem miSave = new JMenuItem();
		miSave.setAction(action(new MainFrameAction.SaveAction(mainFrame,
				"Uložit")));
		menu.add(miSave);

		JMenuItem miSaveAs = new JMenuItem();
		miSaveAs.setAction(action(new MainFrameAction.SaveAsAction(mainFrame,
				"Uložit jako")));
		menu.add(miSaveAs);

		JMenu smExport = initializeSubmenuExport();
		menu.add(smExport);

		menu.add(new JSeparator());

		JMenuItem miClose = new JMenuItem();
		miClose.setAction(action(new MainFrameAction.CloseAction(mainFrame,
				"Zavřít automat")));
		menu.add(miClose);
		
		JMenuItem miExit = new JMenuItem();
		miExit.setAction(action(new MainFrameAction.ExitAction(mainFrame,
				"Ukončit")));
		menu.add(miExit);

		return menu;
	}

	private JMenu initializeMenuHelp() {
		JMenu menu = new JMenu("Nápověda");

		JMenuItem miHelp = new JMenuItem("Nápověda");
		menu.add(miHelp);

		JMenuItem miAbout = new JMenuItem("O Aplikaci");
		menu.add(miAbout);

		return menu;
	}

	private JMenu initializeSubmenuNew() {
		JMenu smNew = new JMenu("Nový ...");

		List<AutomataType> types = Avaibles.get().avaibleAutomaton();

		for (AutomataType type : types) {
			JMenuItem miNewAutomata = new JMenuItem();
			miNewAutomata
					.setAction(action(new MainFrameAction.NewAutomataAction(
							mainFrame, type)));
			smNew.add(miNewAutomata);
		}

		return smNew;
	}

	private JMenu initializeSubmenuExport() {
		JMenu smExport = new JMenu("Exportovat");

		List<ExportFormat> formats = Avaibles.get().avaibleExportFormats();

		for (ExportFormat format : formats) {
			JMenuItem miExport = new JMenuItem();
			miExport.setAction(action(new MainFrameAction.ExportAction(
					mainFrame, format)));
			smExport.add(miExport);
		}

		return smExport;
	}

	private MainFrameAction action(MainFrameAction action) {
		actions.add(action);
		return action;
	}

	public void setEnabledToActionsWRA(boolean enable) {
		for (MainFrameAction action : actions) {
			if (action.isRequiresAutomaton()) {
				action.setEnabled(enable);
			}
		}

	}
}
