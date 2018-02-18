package cz.upol.fapapp.fta.tree;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import cz.upol.fapapp.core.ling.Symbol;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

/**
 * {@link TIMObjectComposer} of {@link BaseTree}.
 * @author martin
 *
 */
public class TreeTIMComposer extends TIMObjectComposer<BaseTree> {

	private static final String OPENING_PARENTHESIS = "(";
	private static final String CLOSING_PARENTHESIS = ")";

	public TreeTIMComposer() {
		super(TreeTIMParser.TYPE);
	}

	@Override
	protected void process(BaseTree tree, TIMFileData data) {
		//TODO make nonterminals and terminals optional
		processNonterminals(tree, data);	
		processTerminals(tree, data);
		processTreeNodes(tree, data);
	}

	///////////////////////////////////////////////////////////////////////////

	private void processNonterminals(BaseTree tree, TIMFileData data) {
		Set<Symbol> alphabet = new TreeSet<>();

		processRecursive(tree, data, //
				null, //
				(c) -> alphabet.add(c.getLabel()), //
				null);

		LineElements elements = TIMObjectParserComposerTools.symbolsToLine(alphabet);
		data.add("nonterminals", elements);
	}

	private void processTerminals(BaseTree tree, TIMFileData data) {
		Set<Symbol> alphabet = new TreeSet<>();

		processRecursive(tree, data, //
				(a) -> alphabet.add(a.getLabel()), //
				null, //
				null);

		LineElements elements = TIMObjectParserComposerTools.symbolsToLine(alphabet);
		data.add("terminals", elements);
	}

	///////////////////////////////////////////////////////////////////////////

	private void processTreeNodes(BaseTree tree, TIMFileData data) {
		processRecursive(tree, data, //
				(a) -> processAtomicTreeNodes(a, data), //
				(c) -> preprocessCompositeTreeNodes(c, data), //
				(c) -> postprocessCompositeTreeNodes(c, data));
	}

	private void processAtomicTreeNodes(AtomicTree tree, TIMFileData data) {
		String value = tree.getLabel().getValue();
		data.add("tree", value);
	}

	private void preprocessCompositeTreeNodes(CompositeTree tree, TIMFileData data) {
		String label = tree.getLabel().getValue();
		LineElements elems = new LineElements(label, OPENING_PARENTHESIS);
		data.add("tree", elems);
	}

	private void postprocessCompositeTreeNodes(CompositeTree tree, TIMFileData data) {
		data.add("tree", CLOSING_PARENTHESIS);
	}

	///////////////////////////////////////////////////////////////////////////

	private void processRecursive(BaseTree tree, TIMFileData data, Consumer<AtomicTree> processAtomic,
			Consumer<CompositeTree> preprocessComposite, Consumer<CompositeTree> postprocessComposite) {

		if (tree instanceof AtomicTree) {
			AtomicTree atomic = (AtomicTree) tree;
			if (processAtomic != null) {
				processAtomic.accept(atomic);
			}

		} else if (tree instanceof CompositeTree) {
			CompositeTree composite = (CompositeTree) tree;

			if (preprocessComposite != null) {
				preprocessComposite.accept(composite);
			}

			for (BaseTree child : composite.getChildren()) {
				processRecursive(child, data, processAtomic, preprocessComposite, postprocessComposite);
			}

			if (postprocessComposite != null) {
				postprocessComposite.accept(composite);
			}

		} else {
			throw new IllegalArgumentException("Unknown tree type: " + tree);
		}
	}

}
