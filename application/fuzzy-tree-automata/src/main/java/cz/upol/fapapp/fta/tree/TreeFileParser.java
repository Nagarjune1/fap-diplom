package cz.upol.fapapp.fta.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import cz.upol.fapapp.core.infile.InputFileData;
import cz.upol.fapapp.core.infile.InputFileObjectParser;
import cz.upol.fapapp.core.infile.LineItems;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.sets.CollectionsUtils;

public class TreeFileParser extends InputFileObjectParser<BaseTree> {

	private static final String TYPE = "tree";

	private static final Map<String, String> PARENTHESIS = initParenthesis();

	public TreeFileParser() {
		super(TYPE);
	}

	private static Map<String, String> initParenthesis() {
		Map<String, String> map = new HashMap<>();

		map.put("(", ")");
		map.put("{", "}");
		map.put("[", "]");
		map.put("<", ">");

		return map;
	}

	@Override
	public BaseTree process(InputFileData data) {
		for (String key : data.listKeys()) {
			switch (key) {
			case InputFileData.TYPE_KEY:
				break;
			// TODO terminals and nonterminals
			case TYPE:
			case "pseudoterm":
				List<LineItems> lines = data.getItemsOf(key);
				return processLines(lines);
			default:
				Logger.get().warning("Unkown key " + key);
			}
		}

		CollectionsUtils.checkNotNull(TYPE, null);

		// TODO check terminals and nonterminals
		return null;
	}

	private BaseTree processLines(List<LineItems> lines) {
		List<String> tokens = linesToTokensList(lines);
		Queue<String> queue = new LinkedList<>(tokens);
		return processTokens(queue);
	}

	///////////////////////////////////////////////////////////////////////////

	protected BaseTree processTokens(Queue<String> remaining) {
		String label = remaining.poll();
		Symbol symbol = new Symbol(label);

		if (!"(".equals(remaining.peek())) {
			return new AtomicTree(symbol);
		} else {
			remaining.poll();

			List<BaseTree> children = new LinkedList<>();
			while (!")".equals(remaining.peek())) {
				BaseTree child = processTokens(remaining);
				children.add(child);
			}
			remaining.poll();

			return new CompositeTree(symbol, children);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	private static List<String> linesToTokensList(List<LineItems> lines) {
		return lines.stream() //
				.flatMap((l) -> l.getItems().stream()) //
				.collect(Collectors.toList());
	}

}
