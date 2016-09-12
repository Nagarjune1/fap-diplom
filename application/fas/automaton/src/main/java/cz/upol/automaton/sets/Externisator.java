package cz.upol.automaton.sets;

public abstract class Externisator<T> {

	/**
	 * Pokusí se zparsovat zadanou textovou reprezentaci objektu. Vrací null,
	 * pokud se zparsovat nepodařilo, nebo tento typ objektu není podporován.
	 * 
	 * @param string textová reprezentace objektu
	 * @return objekt
	 */
	public Object parse(String string) {
		return parseKnown(string);
	}

	/**
	 * Pokusí se zparsovat zadanou textovou reprezentaci objektu jako objekt
	 * typu T. Pokud se tak nepodaří (string není textovou reprezentací žádného
	 * objektu typu T), vrací null.
	 * 
	 * @param string textová reprezentace objektu
	 * @return objekt
	 */
	public abstract T parseKnown(String string);

	/**
	 * Převede zadaný objekt na textovou reprezentaci. Pokud je podporován (je
	 * typu T) tak metodou {@link #externalizeKnown(Object)}, pokud není
	 * podporován (není typu T), tak metodou {@link #externalizeUnknown(Object)}
	 * .
	 * 
	 * @param object objekt
	 * @return textová reprezantace objektu
	 */
	@SuppressWarnings("unchecked")
	public String externalize(Object object) {
		T externasiable;

		if (object == null) {
			return null;
		}

		try {
			externasiable = (T) object;
		} catch (ClassCastException e) {
			return externalizeUnknown(object);
		}

		return externalizeKnown(externasiable);
	}

	/**
	 * Převede na textovou reprezentaci objekt, který není typu T. Metoda může
	 * např vracet {@link Object#toString()}, prázdný řetězec nebo null.
	 * 
	 * @param object objekt
	 * @return textová reprezantace objektu
	 */
	public String externalizeUnknown(Object object) {
		return "";
	}

	/**
	 * Převede na textovou reprezentaci objekt typu T.
	 * 
	 * @param externasiable objekt
	 * @return textová reprezantace objektu
	 */
	public abstract String externalizeKnown(T externasiable);

}
