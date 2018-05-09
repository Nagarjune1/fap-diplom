package cz.upol.fapapp.fa.min;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import cz.upol.fapapp.core.misc.CollectionsUtils;

public class SetsPartitionTest {

	@Test
	public void testFind() {
		SetsPartition<String> partB = createTestPart(true);
		SetsPartition<String> partS = createTestPart(false);

		Set<String> part1 = partB.findPartContaining("foo");
		assertEquals(6, part1.size());
		assertTrue(part1.contains("foo"));

		Set<String> part2 = partS.findPartContaining("foo");
		assertEquals(1, part2.size());
		assertTrue(part2.contains("foo"));

	}

	@Test
	public void testSplit() {
		SetsPartition<String> part = createTestPart(true);

		assertEquals(1, part.getPartitions().size());
		System.out.println(part);

		// split corect
		Set<String> first1 = CollectionsUtils.toSet("foo", "bar", "baz");
		Set<String> second1 = CollectionsUtils.toSet("aux", "qux", "zux");
		part.split(first1, second1);

		assertEquals(2, part.getPartitions().size());
		System.out.println(part);

		// merge uncorrect
		Set<String> first2 = CollectionsUtils.toSet("foo", "bar");
		Set<String> second2 = CollectionsUtils.toSet("bar");
		try {
			part.split(first2, second2);
			fail("should throw exception");
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testMerge() {
		SetsPartition<String> part = createTestPart(false);

		assertEquals(6, part.getPartitions().size());
		System.out.println(part);

		// merge corect
		Set<String> first1 = CollectionsUtils.toSet("foo");
		Set<String> second1 = CollectionsUtils.toSet("bar");
		part.merge(first1, second1);

		assertEquals(5, part.getPartitions().size());
		System.out.println(part);

		// merge uncorrect
		Set<String> first2 = CollectionsUtils.toSet("baz", "aux");
		Set<String> second2 = CollectionsUtils.toSet("zux");
		try {
			part.merge(first2, second2);
			fail("should throw exception");
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testMove() {
		SetsPartition<String> part = createTestTwoPart();

		Set<String> to1 = CollectionsUtils.toSet("aux", "qux", "zux");
		part.move("foo", to1);
		System.out.println(part);

		assertEquals(2, part.getPartitions().size());

		assertTrue(part.findPartContaining("foo").contains("foo"));
		assertFalse(part.findPartContaining("bar").contains("foo"));
	}

	@Test
	public void testSeparate() {
		SetsPartition<String> part = createTestTwoPart();

		part.separate("foo");
		System.out.println(part);

		assertEquals(3, part.getPartitions().size());
		assertEquals(1, part.findPartContaining("foo").size());

		assertTrue(part.findPartContaining("foo").contains("foo"));
		assertFalse(part.findPartContaining("bar").contains("foo"));
		assertFalse(part.findPartContaining("aux").contains("foo"));

	}

	private SetsPartition<String> createTestTwoPart() {
		SetsPartition<String> part = createTestPart(true);

		Set<String> first1 = CollectionsUtils.toSet("foo", "bar", "baz");
		Set<String> second1 = CollectionsUtils.toSet("aux", "qux", "zux");
		part.split(first1, second1);

		System.out.println(part);

		return part;
	}

	///////////////////////////////////////////////////////////////////////////

	private SetsPartition<String> createTestPart(boolean isBulk) {
		Set<String> set = CollectionsUtils.toSet("foo", "bar", "baz", "aux", "qux", "zux");

		SetsPartition<String> part;
		if (isBulk) {
			part = SetsPartition.createBulk(set);
		} else {
			part = SetsPartition.createSinglies(set);
		}

		System.out.println();
		System.out.println(part);

		return part;
	}

}
