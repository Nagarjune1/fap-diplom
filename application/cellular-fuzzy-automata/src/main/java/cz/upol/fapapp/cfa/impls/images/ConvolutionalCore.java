package cz.upol.fapapp.cfa.impls.images;

import java.util.Map;

import cz.upol.fapapp.cfa.misc.TwoDimArray;

public class ConvolutionalCore extends TwoDimArray<Double> {

	public ConvolutionalCore(Map<Integer, Map<Integer, Double>> items) {
		super(0, 3, items);
	}

	public ConvolutionalCore(double dflt) {
		super(0, 3, dflt);
	}

}
