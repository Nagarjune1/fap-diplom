package cz.upol.fapapp.core.sets;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestRelations {

	public TestRelations() {
		// TODO Auto-generated constructor stub
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void test() {
		Map<Integer, String> map1 = new HashMap<>();

		map1.put(42, "foo");
		map1.put(43, "bar");

		BinaryRelation<Integer, String> r1 = new BinaryRelation<>(map1);

		System.out.println(r1);

		assertEquals("foo", r1.get(42));

		try {
			r1.get(99);
			fail("should failed");
		} catch (IllegalStateException e) {
			// ok
		}
	}

}
