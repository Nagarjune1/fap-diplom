package cz.upol.fapapp.fta.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import cz.upol.fapapp.core.ling.Symbol;

public class TreeFileParserTest {

	private final TreeFileParser parser = new TreeFileParser();

	@Test
	public void testAtomic() {
		Queue<String> queue = new LinkedList<>(Arrays.asList("foo"));
		BaseTree tree = parser.processTokens(queue);

		assertAtomic(tree, "foo");
	}

	@Test
	public void testUnaryComposite() {
		Queue<String> queue = new LinkedList<>(Arrays.asList("foo", "(", "bar", ")"));
		BaseTree tree = parser.processTokens(queue);

		CompositeTree root = assertComposite(tree, "foo", 1);

		BaseTree child = root.child(0);
		assertAtomic(child, "bar");
	}

	@Test
	public void testBinaryComposite() {
		Queue<String> queue = new LinkedList<>(Arrays.asList("foo", "(", "bar", "baz", ")"));
		BaseTree tree = parser.processTokens(queue);

		CompositeTree root = assertComposite(tree, "foo", 2);

		BaseTree firstChild = root.child(0);
		assertAtomic(firstChild, "bar");

		BaseTree secondChild = root.child(1);
		assertAtomic(secondChild, "baz");
	}

	@Test
	public void testBinaryCompositeWithCompositeSubchild() {
		Queue<String> queue = new LinkedList<>(Arrays.asList("foo", "(", "bar", "(", "baz", ")", "aux", ")"));
		BaseTree tree = parser.processTokens(queue);

		CompositeTree root = assertComposite(tree, "foo", 2);

		BaseTree firstChild = root.child(0);
		CompositeTree firstChildComposite = assertComposite(firstChild, "bar", 1);

		BaseTree firstSubChild = firstChildComposite.child(0);
		assertAtomic(firstSubChild, "baz");

		BaseTree secondChild = root.child(1);
		assertAtomic(secondChild, "aux");

	}

	///////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////

	private AtomicTree assertAtomic(BaseTree tree, String label) {
		assertTrue(tree instanceof AtomicTree);
		AtomicTree atomic = (AtomicTree) tree;

		assertEquals(new Symbol(label), atomic.getLabel());

		return atomic;
	}

	private CompositeTree assertComposite(BaseTree tree, String label, int childCount) {

		assertTrue(tree instanceof CompositeTree);
		CompositeTree composite = (CompositeTree) tree;

		assertEquals(new Symbol(label), composite.getLabel());
		assertEquals(childCount, composite.getChildCount());

		return composite;

	}

}
