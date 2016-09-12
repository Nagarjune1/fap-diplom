package cz.upol.fas;

import java.awt.Frame;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cz.upol.automaton.io.ExportException;
import cz.upol.fas.config.ExportFormat;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.automata.NondetFuzzyAutomatonToGUI;
import cz.upol.jfa.io.IOManager;
import cz.upol.jfa.utils.xml.XMLFileException;

public class DialsUtils {
	private static final String PNG_SUFFIX = "PNG";
	private static final String TXT_SUFFIX = "TXT";
	private static final String SVG_SUFFIX = "SVG";
	private static final String AUTOMATON_SUFFIX = "XML";

	private final IOManager manager = new IOManager();

	private final Frame parent;

	public DialsUtils(Frame parent) {
		this.parent = parent;
	}

	public void openFileAndExport(BaseAutomatonToGUI automaton,
			ExportFormat format) {

		try {
			switch (format) {
			case BLACK_PNG: {
				File file = openFileToWrite("PNG", PNG_SUFFIX);
				manager.exportPNG(automaton, false, file);
				break;
			}
			case BLACK_SVG:

				break;
			case COLOR_PNG: {
				File file = openFileToWrite("PNG", PNG_SUFFIX);
				manager.exportPNG(automaton, true, file);
				break;
			}
			case COLOR_SVG:

				break;
			case PLAINTEXT: {
				File file = openFileToWrite("Textový soubor", TXT_SUFFIX);
				manager.exportPlain(automaton, file);
				break;
			}
			default:
				break;

			}
		} catch (ExportException e) {
			JOptionPane.showMessageDialog(parent, "Chyba při exportování",
					"Chyba", JOptionPane.ERROR_MESSAGE);
		}
	}

	public File openAutomatonFile() {
		File file = openFileToRead("XML soubor automatu", AUTOMATON_SUFFIX);
		return file;
	}

	public BaseAutomatonToGUI loadAutomaton(File file) {
		if (file != null) {
			try {
				return manager.load(file);
			} catch (XMLFileException e) {
				JOptionPane.showMessageDialog(parent,
						"Chyba při otevírání automatu", "Chyba",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		return null;
	}

	public File openFileAndSaveAutomaton(BaseAutomatonToGUI automaton) {
		File file = openFileToWrite("XML soubor automatu", AUTOMATON_SUFFIX);

		if (file != null) {
			saveAutomaton(automaton, file);
		}

		return file;
	}

	public void saveAutomaton(BaseAutomatonToGUI automaton, File file) {
		try {
			manager.save(automaton, file);
		} catch (XMLFileException e) {
			JOptionPane.showMessageDialog(parent,
					"Chyba při ukládání automatu", "Chyba",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private File openFileToWrite(String description, String suffix) {
		JFileChooser chooser = new JFileChooser();

		FileFilter filter = new FileNameExtensionFilter(description, suffix);
		chooser.setFileFilter(filter);

		chooser.showOpenDialog(parent);
		return chooser.getSelectedFile();
	}

	private File openFileToRead(String description, String suffix) {
		JFileChooser chooser = new JFileChooser();

		FileFilter filter = new FileNameExtensionFilter(description, suffix);
		chooser.setFileFilter(filter);

		chooser.showSaveDialog(parent);
		return chooser.getSelectedFile();
	}

	public boolean confirmCloseAutomaton() {
		int result = JOptionPane.showConfirmDialog(parent,
				"Opravu si přejete zavřít tento automat?", "Zavřít automat?",
				JOptionPane.WARNING_MESSAGE);

		return result == JOptionPane.YES_OPTION;
	}

	public boolean confirmClose() {
		int result = JOptionPane.showConfirmDialog(parent,
				"Opravu si přejete ukončit aplikaci?", "Ukončit?",
				JOptionPane.WARNING_MESSAGE);

		return result == JOptionPane.YES_OPTION;
	}
}
