package cz.upol.fapapp.cfa.outers;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;

public class CloningOuterSupplierTest {

	private final CloningOuterSupplier supplier = new CloningOuterSupplier();

	@Test
	public void test() {
		CFAConfiguration config = createA();

		assertEquals(new CellState(0.11), supplier.computeOuterCell(-1, -1, config));
		assertEquals(new CellState(0.11), supplier.computeOuterCell(+0, -1, config));
		assertEquals(new CellState(0.12), supplier.computeOuterCell(+1, -1, config));
		assertEquals(new CellState(0.13), supplier.computeOuterCell(+2, -1, config));
		assertEquals(new CellState(0.14), supplier.computeOuterCell(+3, -1, config));
		assertEquals(new CellState(0.14), supplier.computeOuterCell(+4, -1, config));

		assertEquals(new CellState(0.2), supplier.computeOuterCell(-1, 1, config));
		assertEquals(new CellState(0.8), supplier.computeOuterCell(-1, 2, config));
		// center not needed
		assertEquals(new CellState(0.2), supplier.computeOuterCell(+4, 1, config));
		assertEquals(new CellState(0.8), supplier.computeOuterCell(+4, 2, config));

		assertEquals(new CellState(0.9), supplier.computeOuterCell(-1, 4, config));
		assertEquals(new CellState(0.9), supplier.computeOuterCell(+2, 4, config));
		assertEquals(new CellState(0.9), supplier.computeOuterCell(+4, 4, config));

	}

	private CFAConfiguration createA() {
		CFAConfiguration config = new CFAConfiguration(4);

		config.setCell(0, 0, new CellState(0.11));
		config.setCell(1, 0, new CellState(0.12));
		config.setCell(2, 0, new CellState(0.13));
		config.setCell(3, 0, new CellState(0.14));

		config.setCell(0, 1, new CellState(0.2));
		config.setCell(1, 1, new CellState(0.2));
		config.setCell(2, 1, new CellState(0.2));
		config.setCell(3, 1, new CellState(0.2));

		config.setCell(0, 2, new CellState(0.8));
		config.setCell(1, 2, new CellState(0.8));
		config.setCell(2, 2, new CellState(0.8));
		config.setCell(3, 2, new CellState(0.8));

		config.setCell(0, 3, new CellState(0.9));
		config.setCell(1, 3, new CellState(0.9));
		config.setCell(2, 3, new CellState(0.9));
		config.setCell(3, 3, new CellState(0.9));

		return config;
	}

}
