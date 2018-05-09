	package cz.upol.fapapp.cfa.impls.droplets;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.cfa.conf.ConfigGenerator;
import cz.upol.fapapp.cfa.misc.TwoDimArray;

/**
 * Configuration containing three types of information to perform droplets automata.
 * @author martin
 *
 */
public class DropletsConfiguration extends CFAConfiguration {

	private final TwoDimArray<CellState> isDroplet;
	private final TwoDimArray<CellState> prevs;
	private final TwoDimArray<CellState> nexts;

	// public WaterDropsConfiguration(int m, TwoDimArray<CellState> cells) {
	// super(m, cells);
	// //TODO constructor
	// this.isDroplet = new TwoDimArray<>(0, m);
	// this.prevs = new TwoDimArray<>(0, m);
	// this.nexts = new TwoDimArray<>(0, m);
	// }

	

	public DropletsConfiguration(int m, TwoDimArray<CellState> cells, TwoDimArray<CellState> isDroplet,
			TwoDimArray<CellState> prevs, TwoDimArray<CellState> nexts) {
		super(m, cells);
		this.isDroplet = isDroplet;
		this.prevs = prevs;
		this.nexts = nexts;
	}
	
	public DropletsConfiguration(int m) {
		super(m);
		// TODO constructor

		ConfigGenerator gen = new ConfigGenerator();
		// TODO FIXME HACK

		this.isDroplet = gen.generateFilled(m, new CellState(0)).toArray();
		this.prevs = gen.generateFilled(m, new CellState(0)).toArray();
		this.nexts = gen.generateFilled(m, new CellState(0)).toArray();

		gen.generateFilled(m, new CellState(0)).toArray().forEach( //
				(i, j, c) -> this.setCell(i, j, c));
	}


	@Override
	public CFAConfiguration cloneItself() {
		return new DropletsConfiguration(getSize(), toArray(), isDroplet, prevs, nexts);
	}

	@Override
	public CellState getCell(int i, int j) {
		return super.getCell(i, j);
	}

	@Override
	public void setCell(int i, int j, CellState cell) {
		super.setCell(i, j, cell);
	}

	/**************************************************************************/

	
	public CellState getPrev(int i, int j) {
		return prevs.get(i, j);
	}

	public void setPrev(int i, int j, CellState cell) {
		prevs.set(i, j, cell);
	}

	public boolean isDroplet(int i, int j) {
		return (this.isDroplet.get(i, j).getValue() == 1.0);
		// TODO FIXME HACK
	}

	public void setDroplet(int i, int j, boolean value) {
		this.isDroplet.set(i, j, value ? new CellState(1.0) : new CellState(0.0));
		// TODO FIXME HACK

	}

	public CellState getNext(int i, int j) {
		return nexts.get(i, j);
	}

	public void setNext(int i, int j, CellState cell) {
		nexts.set(i, j, cell);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((isDroplet == null) ? 0 : isDroplet.hashCode());
		result = prime * result + ((nexts == null) ? 0 : nexts.hashCode());
		result = prime * result + ((prevs == null) ? 0 : prevs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DropletsConfiguration other = (DropletsConfiguration) obj;
		if (isDroplet == null) {
			if (other.isDroplet != null)
				return false;
		} else if (!isDroplet.equals(other.isDroplet))
			return false;
		if (nexts == null) {
			if (other.nexts != null)
				return false;
		} else if (!nexts.equals(other.nexts))
			return false;
		if (prevs == null) {
			if (other.prevs != null)
				return false;
		} else if (!prevs.equals(other.prevs))
			return false;
		return true;
	}
	
	/**************************************************************************/

	
	
}