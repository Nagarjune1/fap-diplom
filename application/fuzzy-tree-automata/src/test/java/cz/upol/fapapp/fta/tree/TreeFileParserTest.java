package cz.upol.fapapp.fta.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cz.upol.fapapp.core.ling.Symbol;

public class TreeFileParserTest {

	private final TreeFileParser parser = new TreeFileParser();

	public TreeFileParserTest() {
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testTreeFile() {
		String file = "" //
				+ "type:\n" //
				+ "	tree\n" //
				+ "nonterminals:\n" //
				+ "	X, Y\n" //
				+ "terminals:\n" //
				+ "	a, b\n" //
				+ "tree:\n" //
				+ "	X {\n" //
				+ "		Y ( a )\n" //
				+ "		b\n" //
				+ "	}\n"; //

		BaseTree tree = parser.parse(file);

		CompositeTree nodeX = assertComposite(tree, "X", 2);

		BaseTree firstChild = nodeX.child(0);
		CompositeTree nodeY = assertComposite(firstChild, "Y", 1);

		BaseTree firstSubChild = nodeY.child(0);
		assertAtomic(firstSubChild, "a");

		BaseTree secondChild = nodeX.child(1);
		assertAtomic(secondChild, "b");
	}

	@Test
	public void testTreeFileWithNotTerminal() {
		String file = "" //
				+ "type:\n" //
				+ "	tree\n" //
				+ "nonterminals:\n" //
				+ "	X, Y\n" //
				+ "terminals:\n" //
				+ "	b\n" //
				+ "tree:\n" //
				+ "	X {\n" //
				+ "		Y ( xxx )\n" //
				+ "		b\n" //
				+ "	}\n"; //

		try {
			parser.parse(file);
			Assert.fail("xxx is not terminal");
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	@Test
	public void testTreeFileWithNotNonterminal() {
		String file = "" //
				+ "type:\n" //
				+ "	tree\n" //
				+ "nonterminals:\n" //
				+ "	Y\n" //
				+ "terminals:\n" //
				+ "	a, b\n" //
				+ "tree:\n" //
				+ "	XXX {\n" //
				+ "		Y ( a )\n" //
				+ "		b\n" //
				+ "	}\n"; //

		try {
			parser.parse(file);
			Assert.fail("XXX is not nonterminal");
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	@Test
	public void testTreeFileWithNullary() {
		String file = "" //
				+ "type:\n" //
				+ "	tree\n" //
				+ "nonterminals:\n" //
				+ "	X, Y, ZZZ\n" //
				+ "terminals:\n" //
				+ "	a, b\n" //
				+ "tree:\n" //
				+ "	X {\n" //
				+ "		Y ( a )\n" //
				+ "		ZZZ \n" //
				+ "	}\n"; //

		try {
			parser.parse(file);
			Assert.fail("ZZZ is terminal, not nonterminal");
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testAtomic() {
		List<String> queue = Arrays.asList("foo");
		BaseTree tree = parser.processTokens(queue);

		assertAtomic(tree, "foo");
	}

	@Test
	public void testUnaryComposite() {
		List<String> queue = Arrays.asList("foo", "(", "bar", ")");
		BaseTree tree = parser.processTokens(queue);

		CompositeTree root = assertComposite(tree, "foo", 1);

		BaseTree child = root.child(0);
		assertAtomic(child, "bar");
	}

	@Test
	public void testBinaryComposite() {
		List<String> queue = Arrays.asList("foo", "(", "bar", "baz", ")");
		BaseTree tree = parser.processTokens(queue);

		CompositeTree root = assertComposite(tree, "foo", 2);

		BaseTree firstChild = root.child(0);
		assertAtomic(firstChild, "bar");

		BaseTree secondChild = root.child(1);
		assertAtomic(secondChild, "baz");
	}

	@Test
	public void testBinaryCompositeWithCompositeSubchild() {
		List<String> queue = Arrays.asList("foo", "(", "bar", "(", "baz", ")", "aux", ")");
		BaseTree tree = parser.processTokens(queue);

		CompositeTree root = assertComposite(tree, "foo", 2);

		BaseTree firstChild = root.child(0);
		CompositeTree firstChildComposite = assertComposite(firstChild, "bar", 1);

		BaseTree firstSubChild = firstChildComposite.child(0);
		assertAtomic(firstSubChild, "baz");

		BaseTree secondChild = root.child(1);
		assertAtomic(secondChild, "aux");
	}

	@Test
	public void testSomeCompositeWithVariousParenthesis() {
		List<String> queue = Arrays.asList(//
				"foo", "(", //
				"bar", "{", "baz", "}", //
				"aux", "[", "qux", "]", //
				"foo", "<", "aux", "aux", "aux", ">", //
				")"); //

		parser.processTokens(queue);
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testInvalidInputs() {
		// unclosed
		assertException("foo", "(");
		assertException("foo", "(", "bar");
		assertException("foo", "(", "bar", "baz");
		assertException("foo", "(", "bar", "(");

		// closed more
		assertException("foo", ")");
		assertException("foo", "(", "bar", ")", "baz", ")");
		assertException("foo", ")", ")");

		// mixed parenthesis
		assertException("foo", "(", "bar", "}");
		assertException("foo", "(", "bar", "{", "baz", ")", "}");

		// special cases
		assertException();
		assertException("(");
		assertException(")");
		assertException("foo", "(", ")");
		assertException("foo", "(", ")", "(", ")");
		assertException("foo", "(", "(", "bar", ")", ")");
		
		//multiplies
		assertException("foo", "bar");
		assertException("foo", "(", "bar", ")", "baz", "(", "aux", ")");

	}
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

	private void assertException(String... tokens) {
		List<String> queue = Arrays.asList(tokens);

		try {
			BaseTree tree = parser.processTokens(queue);
			System.err.println(tree);

			Assert.fail("Tokens list " + Arrays.toString(tokens) + " is invalid");
		} catch (IllegalArgumentException e) {
			System.err.println(e);
			// ok
		}
	}

}
