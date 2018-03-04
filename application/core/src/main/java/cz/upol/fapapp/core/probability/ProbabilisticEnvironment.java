package cz.upol.fapapp.core.probability;

import java.util.Random;

public class ProbabilisticEnvironment {

	private static ProbabilisticEnvironment instance = new ProbabilisticEnvironment();
	
	private final Random random;
	
	protected ProbabilisticEnvironment() {
		this.random = new Random();
	}
	
	protected ProbabilisticEnvironment(int seed) {
		this.random = new Random(seed);
	}
	
	public int next(int range) {
		return random.nextInt(range);
	}
	
	//TODO like getIthOfArray or getRandWithProbability ...
	
	public boolean isProbable(Probability probability) {
		double rand = random.nextDouble();
		
		return rand < probability.getValue();
	}

	public static ProbabilisticEnvironment get() {
		return instance;
	}

	public static void set(ProbabilisticEnvironment instance) {
		ProbabilisticEnvironment.instance = instance;
	}
	
	


}
