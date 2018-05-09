package cz.upol.fapapp.cfa.impls.images;

import java.util.Map;

import cz.upol.fapapp.cfa.misc.TwoDimArray;

/**
 * Convolutionar core, in fact {@link TwoDimArray} of size -1 to +1 filled with doubles.
 * @author martin
 *
 */
public class ConvolutionalCore extends TwoDimArray<Double> {

	public ConvolutionalCore(Map<Integer, Map<Integer, Double>> items) {
		super(0, 3, items);
	}

	public ConvolutionalCore(double dflt) {
		super(0, 3, dflt);
	}

}
