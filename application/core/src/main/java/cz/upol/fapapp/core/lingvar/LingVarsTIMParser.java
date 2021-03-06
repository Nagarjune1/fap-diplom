package cz.upol.fapapp.core.lingvar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} for {@link Set} of {@link LingvisticVariable}s. Uses
 * following format:
 * 
 * <pre>
 * itemName:
 *   age is low if linear 0, 0, 15, 30
 *   age is old if linear 40, 60, 150, 150
 *   crash is happend if unary
 *   temperature is hight if gaussian 30, 80
 * </pre>
 * 
 * 
 * @author martin
 *
 */
public class LingVarsTIMParser extends TIMObjectParser<Set<LingvisticVariable>> {

	private final String[] itemNames;

	public LingVarsTIMParser(String type, String itemName) {
		super(type);
		this.itemNames = new String[] { itemName };
	}

	public LingVarsTIMParser(String type, String... itemNames) {
		super(type);
		this.itemNames = itemNames;
	}

	@Override
	public Set<LingvisticVariable> process(TIMFileData data) {
		List<LineElements> lines = TIMObjectParserComposerTools.findElements(data, itemNames);

		Map<String, Set<BaseLingVarLabel>> map = parse(lines);

		return convert(map);

	}

	private Map<String, Set<BaseLingVarLabel>> parse(List<LineElements> lines) {
		Map<String, Set<BaseLingVarLabel>> map = new HashMap<>();

		for (LineElements line : lines) {
			TIMObjectParserComposerTools.lenthAtLeast(line, 5);

			String varName = line.getIth(0);
			TIMObjectParserComposerTools.is(line, 1, "is");
			String labelName = line.getIth(2);
			TIMObjectParserComposerTools.is(line, 3, "if");
			String labelType = line.getIth(4);

			BaseLingVarLabel label;

			switch (labelType) {
			case "linear":
				label = parseLinear(labelName, line);
				break;
			case "unary":
				label = parseUnary(labelName, line);
				break;
			case "gauss":
			case "gaussian":
				label = parseGaussian(labelName, line);
				break;
			default:
				Logger.get().warning("Unknown type " + labelType);
				continue;
			}

			Set<BaseLingVarLabel> labels = map.get(varName);

			if (labels == null) {
				labels = new HashSet<>();
				map.put(varName, labels);
			}

			labels.add(label);
		}

		return map;
	}

	private BaseLingVarLabel parseGaussian(String labelName, LineElements line) {
		LingVarValue leftSigma = TIMObjectParserComposerTools.parseLingVarValue(line.getIth(5));
		LingVarValue center = TIMObjectParserComposerTools.parseLingVarValue(line.getIth(6));
		LingVarValue rightSigma = TIMObjectParserComposerTools.parseLingVarValue(line.getIth(7));

		return new GaussianLingVarLabel(labelName, center, leftSigma, rightSigma);
	}

	private BaseLingVarLabel parseLinear(String labelName, LineElements line) {
		LingVarValue start = TIMObjectParserComposerTools.parseLingVarValue(line.getIth(5));
		LingVarValue top = TIMObjectParserComposerTools.parseLingVarValue(line.getIth(6));
		LingVarValue decrease = TIMObjectParserComposerTools.parseLingVarValue(line.getIth(7));
		LingVarValue finish = TIMObjectParserComposerTools.parseLingVarValue(line.getIth(8));

		return new LinearLingVarLabel(labelName, start, top, decrease, finish);
	}

	private BaseLingVarLabel parseUnary(String labelName, LineElements line) {
		return new UnaryVarLabel(labelName);
	}

	private Set<LingvisticVariable> convert(Map<String, Set<BaseLingVarLabel>> map) {
		Set<LingvisticVariable> set = new HashSet<>();

		for (String varName : map.keySet()) {
			Set<BaseLingVarLabel> labels = map.get(varName);

			LingvisticVariable var = new LingvisticVariable(varName, labels);
			set.add(var);
		}

		return set;
	}

}
