package cz.upol.jfa.io;

import java.io.File;

import cz.upol.automaton.automata.impls.DeterministicFuzzyAutomata;
import cz.upol.automaton.automata.impls.NondetermisticFuzzyAutomata;
import cz.upol.automaton.io.ExportException;
import cz.upol.automaton.io.PlainTextExporter;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.automata.DetermFuzzyAutomatonToGUI;
import cz.upol.jfa.utils.xml.XMLFileException;

public class IOManager {

	public IOManager() {
	}

	public void save(BaseAutomatonToGUI automaton, File file)
			throws XMLFileException {

		FATGxmlLoaderStoarer storer = new FATGxmlLoaderStoarer();
		storer.save(automaton, file);
	}

	public BaseAutomatonToGUI load(File file) throws XMLFileException {

		FATGxmlLoaderStoarer loader = new FATGxmlLoaderStoarer();
		return loader.load(file);
	}

	public void exportPNG(BaseAutomatonToGUI automaton, boolean colorful,
			File file) throws ExportException {

		ImageExporter exporter = new ImageExporter();
		exporter.exportBitmap(automaton, file, colorful);
	}

	public void exportPlain(BaseAutomatonToGUI automaton, File file)
			throws ExportException {
		PlainTextExporter exporter = new PlainTextExporter();

		if (automaton instanceof NondetermisticFuzzyAutomata) {
			exporter.exportToFile(file, (NondetermisticFuzzyAutomata) automaton);
		} else if (automaton instanceof DetermFuzzyAutomatonToGUI) {
			exporter.exportToFile(file, (DeterministicFuzzyAutomata) automaton);
		} else {
			throw new IllegalArgumentException("Unkown automata");
		}

	}

}
