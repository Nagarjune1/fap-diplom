package cz.upol.fapapp.cfa.gui.misc;

import java.util.concurrent.TimeUnit;

import cz.upol.fapapp.cfa.conf.CFAComputation;
import cz.upol.fapapp.core.misc.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * JavaFx service performing background computation, {@link CFAComputation}.
 * After each step waits given time interval.
 * 
 * @author martin
 *
 */
public class CFaComputationService extends Service<CFAComputation> {

	private static final int DEFAULT_SPEED = 10;

	private final CFAComputation computation;
	private final IntegerProperty speedProperty;

	public CFaComputationService(CFAComputation computation) {
		super();
		this.computation = computation;
		this.speedProperty = new SimpleIntegerProperty(DEFAULT_SPEED);
	}

	@Override
	protected Task<CFAComputation> createTask() {
		return new CFAComputationTask(computation, speedProperty);
	}

	public IntegerProperty speedProperty() {
		return speedProperty;
	}

	public static class CFAComputationTask extends Task<CFAComputation> {

		private final CFAComputation computation;
		private final IntegerProperty speedProperty;

		public CFAComputationTask(CFAComputation computation, IntegerProperty speedProperty) {
			super();
			this.computation = computation;
			this.speedProperty = speedProperty;

		}

		/**************************************************************************/

		@Override
		protected CFAComputation call() throws Exception {
			Logger.get().info("Running computation task since generation " + computation.getGeneration());
			while (!this.isCancelled()) {
				doOneStep();

				sleepItself();
			}

			Logger.get().info("Finished computation at generation " + computation.getGeneration());
			return computation;
		}

		private void sleepItself() {
			int speed = speedProperty.get();
			int timeout = 1000 / speed;

			try {
				TimeUnit.MILLISECONDS.sleep(timeout);
			} catch (InterruptedException eIgnore) {
			}
		}

		public void doOneStep() {
			computation.toNextGeneration();
		}

	}

}
