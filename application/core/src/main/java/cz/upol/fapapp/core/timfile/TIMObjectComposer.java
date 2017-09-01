package cz.upol.fapapp.core.timfile;

import java.io.File;
import java.io.IOException;

import cz.upol.fapapp.core.misc.Logger;

/**
 * Performs construction of {@link TIMFileData} from objects of type T.
 * 
 * @author martin
 *
 * @param <T>
 */
public abstract class TIMObjectComposer<T> {

	private final String type;

	/**
	 * 
	 * @param type
	 *            type specifier for generated {@link TIMFileData}
	 */
	public TIMObjectComposer(String type) {
		super();
		this.type = type;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Saves given object into given file.
	 * 
	 * @param object
	 * @param file
	 * @throws IOException
	 */
	public void compose(T object, File file) throws IOException {
		Logger.get().moreinfo("Composing TIM file of type '" + type + "' into " + file.getPath());

		TIMFileComposer composer = new TIMFileComposer();
		TIMFileData data = composeTIMData(object);

		composer.compose(data, file);
	}

	/**
	 * Converts given object into given file.
	 * 
	 * @param object
	 * @return
	 */
	public String compose(T object) {
		TIMFileComposer composer = new TIMFileComposer();
		TIMFileData data = composeTIMData(object);

		return composer.compose(data);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Composes {@link TIMFileData} for given object. (Adds type and invokes
	 * {@link #process(Object, TIMFileData)} method)
	 * 
	 * @param object
	 * @return
	 */
	protected TIMFileData composeTIMData(T object) {
		TIMFileData data = new TIMFileData();

		addType(data);

		process(object, data);

		return data;
	}

	/**
	 * Adds type type specifier into given data.
	 * 
	 * @param data
	 */
	private void addType(TIMFileData data) {
		data.add(TIMFileData.TYPE_KEY, type);
	}

	/**
	 * Processes given object into given data.
	 * 
	 * @param object
	 * @param data
	 */
	protected abstract void process(T object, TIMFileData data);

}
