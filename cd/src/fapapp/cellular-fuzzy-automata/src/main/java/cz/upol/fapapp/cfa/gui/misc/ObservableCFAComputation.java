package cz.upol.fapapp.cfa.gui.misc;

import java.util.ArrayList;
import java.util.List;

import cz.upol.fapapp.cfa.conf.CFAComputation;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * An Fx extension of {@link CFAComputation} but {@link Observable}.
 * 
 * @author martin
 *
 */
public class ObservableCFAComputation extends CFAComputation implements Observable {

	private final List<InvalidationListener> listeners;

	public ObservableCFAComputation(CFAComputation computation) {
		super(computation.getAutomaton(), computation.getConfig(), computation.getGeneration());

		this.listeners = new ArrayList<>();
	}

	/**************************************************************************/

	@Override
	public void reset() {
		super.reset();
		notifyListeners();
	}

	@Override
	public void toNextGeneration() {
		super.toNextGeneration();
		notifyListeners();
	}

	/**************************************************************************/

	private void notifyListeners() {
		listeners.forEach((l) -> l.invalidated(this));
	}

	@Override
	public void addListener(InvalidationListener listener) {
		listeners.add(listener);
		notifyListeners();
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		listeners.remove(listener);
		notifyListeners();
	}

}
