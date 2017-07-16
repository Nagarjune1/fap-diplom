package cz.upol.fapapp.core.fuzzy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestFuzzy {

	@Test
	public void test() {
		Map<String, Degree> map1 = new HashMap<>();

		map1.put("foo", Degree.ZERO);
		map1.put("bar", Degree.ONE);
		map1.put("baz", new Degree(0.4));
		map1.put("aux", new Degree(0.1));

		FuzzySet<String> s1 = new FuzzySet<>(map1);

		System.out.println(s1);
	}

}
