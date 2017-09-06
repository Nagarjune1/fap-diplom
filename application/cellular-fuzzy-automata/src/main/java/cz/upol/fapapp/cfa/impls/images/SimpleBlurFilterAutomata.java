package cz.upol.fapapp.cfa.impls.images;

public class SimpleBlurFilterAutomata extends ConvolutionalCFA {

	public SimpleBlurFilterAutomata(int size) {
		super(size, createCore());
	}

	private static ConvolutionalCore createCore() {
		ConvolutionalCore core = new ConvolutionalCore(0.0);

		core.set(0, 0, 1.0);
		core.set(1, 0, 2.0);
		core.set(2, 0, 1.0);

		core.set(0, 1, 2.0);
		core.set(1, 1, 4.0);
		core.set(2, 1, 2.0);

		core.set(0, 2, 1.0);
		core.set(1, 2, 2.0);
		core.set(2, 2, 1.0);

		return core;
	}

}
