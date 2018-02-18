package cz.upol.fapapp.fta.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cz.upol.fapapp.core.ling.Alphabet;
import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectParser;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectParser} of {@link BaseTree}.
 * 
 * @author martin
 *
 */
public class TreeTIMParser extends TIMObjectParser<BaseTree> {

	public static final String TYPE = "tree";

	private static final Map<String, String> PARENTHESIS = initParenthesis();

	public TreeTIMParser() {
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
	public BaseTree process(TIMFileData data) {
		// TODO make nonterminals and terminals optional
		Alphabet nonterminals = processNonterminals(data);
		Alphabet terminals = processTerminals(data);
		BaseTree tree = processTree(data);

		tree.validate(nonterminals, terminals);
		return tree;
	}

	private Alphabet processNonterminals(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "nonterminals", "N");
		return TIMObjectParserComposerTools.parseAlphabet(elements);
	}

	private Alphabet processTerminals(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "terminals", "T");
		return TIMObjectParserComposerTools.parseAlphabet(elements);
	}

	private BaseTree processTree(TIMFileData data) {
		LineElements elements = TIMObjectParserComposerTools.findElementsMerged(data, "tree", "pseudoterm", "term");
		List<String> tokens = elements.getElements();
		return processTokens(tokens);
	}

	///////////////////////////////////////////////////////////////////////////

	protected BaseTree processTokens(List<String> tokens) {
		Queue<String> queue = new LinkedList<>(tokens);
		BaseTree tree = processTokens(queue);

		if (!queue.isEmpty()) {
			throw new IllegalArgumentException("Unexpected " + queue + " at the end");
		}

		return tree;
	}

	///////////////////////////////////////////////////////////////////////////

	protected BaseTree processTokens(Queue<String> remaining) {
		if (remaining.isEmpty() || isOpeningParenthesis(remaining.peek()) || isClosingParenthesis(remaining.peek())) {
			throw new IllegalArgumentException(
					"Excepted symbol" + (remaining.isEmpty() ? "" : ", found: " + remaining.peek()));
		}

		String label = remaining.poll();
		Symbol symbol = new Symbol(label);

		if (remaining.isEmpty()) {
			return new AtomicTree(symbol);
		}

		String next = remaining.peek();
		if (!isOpeningParenthesis(next)) {
			return processWithNextNotParenthesis(symbol);
		}

		if (isOpeningParenthesis(next)) {
			return processWithNextOpeningParenthesis(remaining, label, symbol);
		}

		return null;
	}

	private BaseTree processWithNextNotParenthesis(Symbol symbol) {
		return new AtomicTree(symbol);
	}

	private BaseTree processWithNextOpeningParenthesis(Queue<String> remaining, String label, Symbol symbol) {
		String openingParenthesis = remaining.poll();
		String closingParenthesis = PARENTHESIS.get(openingParenthesis);

		List<BaseTree> children = new LinkedList<>();

		while (true) {
			BaseTree child = processTokens(remaining);
			children.add(child);

			if (remaining.isEmpty()) {
				throw new IllegalArgumentException("Missing " + closingParenthesis);
			}

			String willBeNext = remaining.peek();
			if (isClosingParenthesis(willBeNext)) {
				String realClosingParenthesis = willBeNext;
				if (closingParenthesis.equals(realClosingParenthesis)) {
					remaining.poll();
					break;
				} else {
					throw new IllegalArgumentException(
							"Unexpected " + realClosingParenthesis + " (expected " + closingParenthesis + ") at: "
									+ label + " " + openingParenthesis + " ... " + realClosingParenthesis);
				}
			}
		}

		return new CompositeTree(symbol, children);
	}

	///////////////////////////////////////////////////////////////////////////

	private boolean isOpeningParenthesis(String token) {
		return PARENTHESIS.containsKey(token);
	}

	private boolean isClosingParenthesis(String token) {
		return PARENTHESIS.containsValue(token);
	}

	///////////////////////////////////////////////////////////////////////////

}
