package cz.upol.fapapp.cfa.comp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CellNeighborhood;

public class CellNeighborhoodTest {

	private static final double DELTA = 0.001;

	@Test
	public void testSum() {
		CellNeighborhood neigA = createA();

		assertEquals(4.5, neigA.sum(true), DELTA);
		assertEquals(4.0, neigA.sum(false), DELTA);
		
		CellNeighborhood neigB = createB();

		assertEquals(4.8, neigB.sum(true), DELTA);
		assertEquals(4.0, neigB.sum(false), DELTA);
	}

	@Test
	public void testAvg() {
		CellNeighborhood neigA = createA();

		assertEquals(0.5, neigA.avg(true), DELTA);
		assertEquals(0.5, neigA.avg(false), DELTA);

		CellNeighborhood neigB = createB();

		assertEquals(1.6/3.0, neigB.avg(true), DELTA);
		assertEquals(0.5, neigB.avg(false), DELTA);

	}

	private CellNeighborhood createA() {
		CellNeighborhood neigh = new CellNeighborhood();
		neigh.set(-1, -1, new CellState(0.5));
		neigh.set(00, -1, new CellState(0.5));
		neigh.set(+1, -1, new CellState(0.5));

		neigh.set(-1, 00, new CellState(0.5));
		neigh.set(00, 00, new CellState(0.5));
		neigh.set(+1, 00, new CellState(0.5));

		neigh.set(-1, +1, new CellState(0.5));
		neigh.set(00, +1, new CellState(0.5));
		neigh.set(+1, +1, new CellState(0.5));

		return neigh;
	}
	
	private CellNeighborhood createB() {
		CellNeighborhood neigh = new CellNeighborhood();
		neigh.set(-1, -1, new CellState(0.8));
		neigh.set(00, -1, new CellState(0.2));
		neigh.set(+1, -1, new CellState(0.8));

		neigh.set(-1, 00, new CellState(0.2));
		neigh.set(00, 00, new CellState(0.8));
		neigh.set(+1, 00, new CellState(0.2));

		neigh.set(-1, +1, new CellState(0.8));
		neigh.set(00, +1, new CellState(0.2));
		neigh.set(+1, +1, new CellState(0.8));

		return neigh;
	}

}
