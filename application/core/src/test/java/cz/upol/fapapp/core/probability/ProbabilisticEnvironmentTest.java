package cz.upol.fapapp.core.probability;

import static org.junit.Assert.*;

import java.util.function.Predicate;

import org.junit.Test;

import cz.upol.fapapp.core.probability.ProbabilisticEnvironment;
import cz.upol.fapapp.core.probability.Probability;

public class ProbabilisticEnvironmentTest {

	@Test
	public void testIsProbable() {
		ProbabilisticEnvironment env = new ProbabilisticEnvironment(42);

		Probability prob1 = new Probability(0.25);
		check(1000, (c) -> {
			return env.isProbable(prob1);
		}, 200, 300);

		Probability prob2 = new Probability(0.75);
		check(1000, (c) -> {
			return env.isProbable(prob2);
		}, 700, 800);

		
	}
	
	
	
	

	private void check(int totalCount, Predicate<Void> body, int expectedMin,
			int expectedMax) {
		int currentCount = 0;

		for (int i = 0; i < totalCount; i++) {
			if (body.test(null)) {
				currentCount++;
			}
		}

		assertTrue("Count should be < " + expectedMin + ", but is "
				+ currentCount, expectedMin <= currentCount);

		assertTrue("Count should be > " + expectedMax + ", but is "
				+ currentCount, expectedMax >= currentCount);
	}

}
