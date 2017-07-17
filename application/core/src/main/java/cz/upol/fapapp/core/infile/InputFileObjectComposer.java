package cz.upol.fapapp.core.infile;

import java.io.File;
import java.io.IOException;

public abstract class InputFileObjectComposer<T> {

	private final String type;

	public InputFileObjectComposer(String type) {
		super();
		this.type = type;
	}

///////////////////////////////////////////////////////////////////////////

	
	public void compose(T object, File file) throws IOException {
		InputFileComposer composer = new InputFileComposer();
		InputFileData data = addTypeAndProcess(object);

		composer.compose(data, file);
	}

	public String compose(T object) {
		InputFileComposer composer = new InputFileComposer();
		InputFileData data = addTypeAndProcess(object);

		return composer.compose(data);
	}
	
///////////////////////////////////////////////////////////////////////////


	protected InputFileData addTypeAndProcess(T object) {
		InputFileData data = new InputFileData();

		data.start(InputFileData.TYPE_KEY);
		data.add(InputFileData.TYPE_KEY, new LineItems(type));

		process(object, data);

		return data;

	}

	protected abstract void process(T object, InputFileData data);

}
