package cz.upol.fapapp.core.fuzzy.sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.BinaryRelation.Couple;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * Base fuzzy relation {@link TIMObjectParser}. Expects following format:
 * <pre>
 * source:
 *   foo, bar
 *   
 * target:
 *   42, 99
 *   
 * relation:
 *   0.5 0.9
 *   1.0 0.4
 * </pre>
 * @author martin
 *
 * @param <TD>
 * @param <TT>
 */
public class FBRTIMObjectParser<TD, TT> extends TIMObjectParser<FuzzyBinaryRelation<TD, TT>> {

	//TODO implement composer
	
	private final Function<String, TD> domainMapper;
	private final Function<String, TT> targetMapper;

	public FBRTIMObjectParser(String type, Function<String, TD> domainMapper, Function<String, TT> targetMapper) {
		super(type);

		this.domainMapper = domainMapper;
		this.targetMapper = targetMapper;
	}

	@Override
	public FuzzyBinaryRelation<TD, TT> process(TIMFileData data) {
		List<TD> domain = processDomain(data);
		List<TT> target = processTarget(data);

		Map<Couple<TD, TT>, Degree> couples = processCouples(data, domain, target);

		return new FuzzyBinaryRelation<>(couples);
	}

	///////////////////////////////////////////////////////////////////////////

	private List<TD> processDomain(TIMFileData data) {
		LineElements lines = TIMObjectParserComposerTools.findElementsMerged(data, //
				"source", "domain", "from", "universe");

		return lines.getElements().stream() //
				.map(domainMapper) //
				.collect(Collectors.toList());
	}

	private List<TT> processTarget(TIMFileData data) {
		LineElements lines = TIMObjectParserComposerTools.findElementsMerged(data, //
				"destination", "target", "to", "universe");

		return lines.getElements().stream() //
				.map(targetMapper) //
				.collect(Collectors.toList());
	}

	private Map<Couple<TD, TT>, Degree> processCouples(TIMFileData data, List<TD> domain, List<TT> target) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, "relation", "degrees");

		Map<Couple<TD, TT>, Degree> couples = new HashMap<>(domain.size() * target.size());

		for (int i = 0; i < domain.size(); i++) {
			if (lines.size() <= i) {
				Logger.get().warning("Missing line " + i);
				continue;
			}

			LineElements line = lines.get(i);
			for (int j = 0; j < target.size(); j++) {
				if (line.count() <= j) {
					Logger.get().warning("Missing column " + j + " at line " + i);
					continue;
				}

				TD domainItem = domain.get(i);
				TT targetItem = target.get(j);

				String elem = line.getIth(j);
				Degree degree = TIMObjectParserComposerTools.parseDegree(elem);

				Couple<TD, TT> couple = new Couple<>(domainItem, targetItem);

				couples.put(couple, degree);
			}
		}

		return couples;
	}

}
