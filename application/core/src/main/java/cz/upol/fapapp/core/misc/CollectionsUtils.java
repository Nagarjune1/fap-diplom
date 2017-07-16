
package cz.upol.fapapp.core.misc;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionsUtils {

	@SafeVarargs
	public static <E> Set<E> toSet(E... elements) {
		return Arrays.stream(elements).collect(Collectors.toSet());
	}
}
