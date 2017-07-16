package cz.upol.fapapp.core.sets;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestRelations {

	@Test
	public void test() {
		Map<Integer, String> map1 = new HashMap<>();
		
		map1.put(42, "foo");
		map1.put(43, "bar");
		
		BinaryRelation<Integer, String> r1 = new BinaryRelation<>(map1);
		
		System.out.println(r1);
	}

}

