package cz.upol.fapapp.core.sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.fuzzy.sets.FuzzySet;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} for {@link FuzzySet}. Assumes format:
 * <pre>
 * domain:
 *  foo, bar, baz
 * mapping:
 *  0.1, 0.2, 0.3
 * @author martin
 *
 * @param <E>
 */
public class FSTIMObjectParser<E> extends TIMObjectParser<FuzzySet<E>> {

	//TODO impelement composer
	
	private final Function<String, E> elementMapper;

	public FSTIMObjectParser(String type, Function<String, E> elementMapper) {
		super(type);

		this.elementMapper = elementMapper;
	}

	@Override
	public FuzzySet<E> process(TIMFileData data) {
		List<E> universe = processUniverse(data);

		Map<E, Degree> tuples = processTuples(data, universe);

		return new FuzzySet<>(tuples);
	}

	///////////////////////////////////////////////////////////////////////////

	private List<E> processUniverse(TIMFileData data) {
		LineElements lines = TIMObjectParserComposerTools.findElementsMerged(data, //
				"set", "domain", "universe");

		return lines.getElements().stream() //
				.map(elementMapper) //
				.collect(Collectors.toList());
	}

	private Map<E, Degree> processTuples(TIMFileData data, List<E> universe) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "mapping", "degrees");

		Map<E, Degree> map = new HashMap<>(universe.size());

		for (int i = 0; i < universe.size(); i++) {
			if (lines.size() <= i) {
				Logger.get().warning("Missing line " + i);
				continue;
			}

			LineElements line = lines.get(i);

			E elem;
			Degree degree;
			if (line.count() < 1) {
				Logger.get().warning("Missing column at line " + i);
				continue;
			} else if (line.count() == 1) {
				elem = universe.get(i);
				degree = TIMObjectParserComposerTools.parseDegree(line.getIth(0));
			} else {
				elem = elementMapper.apply(line.getIth(0));
				degree = TIMObjectParserComposerTools.parseDegree(line.getIth(1));
			}

			map.put(elem, degree);
		}

		return map;
	}

}
