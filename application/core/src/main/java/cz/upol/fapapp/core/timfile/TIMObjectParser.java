package cz.upol.fapapp.core.timfile;

import java.io.File;
import java.io.IOException;

import cz.upol.fapapp.core.misc.Logger;

/**
 * Performs parsing of {@link TIMFileData} into objects of type T.
 * 
 * @author martin
 *
 * @param <T>
 */
public abstract class TIMObjectParser<T> {

	private final String type;

	/**
	 * 
	 * @param type
	 *            required type specifier of inpu {@link TIMFileData} to be
	 *            checked against
	 */
	public TIMObjectParser(String type) {
		super();
		this.type = type;
	}

	/**
	 * Parses given file.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public T parse(File file) throws IOException {
		Logger.get().info("Parsing TIM file " + file.getPath() + " of type '" + type + "'");

		TIMFileParser parser = new TIMFileParser();
		TIMFileData data = parser.parse(file);
		return processTIMData(data);
	}

	/**
	 * Parses given string.
	 * 
	 * @param content
	 * @return
	 */
	public T parse(String content) {
		TIMFileParser parser = new TIMFileParser();
		TIMFileData data = parser.parse(content);
		return processTIMData(data);
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Converts given {@link TIMFileData} into object. (performs check and
	 * invokes {@link #process(TIMFileData)} method).
	 * 
	 * @param data
	 * @return
	 */
	public T processTIMData(TIMFileData data) {
		checkType(data);

		return process(data);
	}

	/**
	 * Checks type.
	 * 
	 * @param data
	 * @throws IllegalArgumentException
	 */
	private void checkType(TIMFileData data) throws IllegalArgumentException {
		String type = data.getType();

		if (!this.type.equalsIgnoreCase(type)) {
			throw new IllegalArgumentException("Required file of type " + this.type + " but found " + type);
		}
	}

	/**
	 * Processes given data into object of type T. The method should look like as follows:
	 * <pre>
	 	\@Override
		public T process(TIMFileData data) {
			Foo foo = processFoo(data);
			Bar bar = processBar(data); 
	  
	  		return new T(foo, bar);
  		}
  		
		private Foo processFoo(TIMFileData data) {
			LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "foo");
			return TIMObjectParserComposerTools.fooo(elements);
		}
	
		private Bar processBar(TIMFileData data) {
			LineElements elements = TIMObjectParserComposerTools.findElements(data, "bar");
			return new Bar(new Baz(elements.iTh(0)), new Aux(elements.iTh(1)));
		}
	 </pre>
	 * 
	 * @param data
	 * @return
	 */
	public abstract T process(TIMFileData data);
}
