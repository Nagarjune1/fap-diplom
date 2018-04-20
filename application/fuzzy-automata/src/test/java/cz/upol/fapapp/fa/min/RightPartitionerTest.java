package cz.upol.fapapp.fa.min;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.automata.TestAutomataCreator;

public class RightPartitionerTest {

	@Test
	public void testInitialParition() {
		System.err.println("Warning, test ignored");
		// elementary automaton
		FuzzyAutomaton automaton1 = TestAutomataCreator.createAutomaton1();
		assertEquals(2, automaton1.getStates().size());

		testInitialPartition(automaton1, 1.0, 1);
		testInitialPartition(automaton1, 0.0, 2);
		testInitialPartition(automaton1, 0.3, 2);
		testInitialPartition(automaton1, 0.5, 1);

		// automaton of lang
		FuzzyAutomaton automaton2 = TestAutomataCreator.createAutomatonOfLang();
		assertEquals(8, automaton2.getStates().size());

		testInitialPartition(automaton2, 1.0, 1);
		testInitialPartition(automaton2, 0.0, 2);
		testInitialPartition(automaton2, 0.5, 2);
	}

	@Test
	@Ignore
	public void testMatch() {
		FuzzyAutomaton automaton1 = TestAutomataCreator.createAutomatonOfLang();
		Degree delta = new Degree(0.5);
		RightPartitioner partitioner = new RightPartitioner(automaton1, delta);
		SetsPartition<State> partition = partitioner.createInitialPartition();

		// full match
		State first1 = new State("q_q_3_7");
		State second1 = new State("q_q_3_3");

		Degree match1 = partitioner.matchOfPart(first1, second1, partition);
		assertEquals(Degree.ONE, match1);

		// definetelly no match
		State first2 = new State("q_q_0_4");
		State second2 = new State("q_q_3_7");

		Degree match2 = partitioner.matchOfPart(first2, second2, partition);
		assertEquals(Degree.ZERO, match2);

		// some another wierd
		State first3 = new State("q_q_0_4");
		State second3 = new State("q_q_1_5");

		Degree match3 = partitioner.matchOfPart(first3, second3, partition);
		assertEquals(Degree.ZERO, match3);
	}

	@Test
	// @Ignore
	public void testPartition() {
		System.err.println("Warning, test ignored");
		// elementary automaton (too scruffy, no partition possible)
		FuzzyAutomaton automaton1 = TestAutomataCreator.createAutomaton1();
		assertEquals(2, automaton1.getStates().size());

		testPartitioner(automaton1, 1.0, 2);
		testPartitioner(automaton1, 0.0, 2);

		// automaton of lang
		FuzzyAutomaton automaton2 = TestAutomataCreator.createAutomatonOfLang();
		assertEquals(8, automaton2.getStates().size());

		testPartitioner(automaton2, 0.0, 2);
		testPartitioner(automaton2, 0.5, 5);
		testPartitioner(automaton2, 1.0, 5);

		// detemrinised
		FuzzyAutomaton automaton3 = (FuzzyAutomaton) automaton2.determinise();
		testPartitioner(automaton3, 0.0, 2);
		testPartitioner(automaton3, 0.5, 5);
		testPartitioner(automaton3, 1.0, 1);
		
	}

	///////////////////////////////////////////////////////////////////////////
	private void testInitialPartition(FuzzyAutomaton automaton, double deltaVal, int expectedPartSize) {
		Degree delta = new Degree(deltaVal);
		RightPartitioner partitioner = new RightPartitioner(automaton, delta);
		SetsPartition<State> part = partitioner.createInitialPartition();

		System.out.println("Init part with " + delta + ": " + part);
		assertEquals(expectedPartSize, part.getPartitions().size());
	}

	private void testPartitioner(FuzzyAutomaton automaton, double deltaVal, int expectedPartSize) {
		Degree delta = new Degree(deltaVal);
		RightPartitioner partitioner = new RightPartitioner(automaton, delta);
		SetsPartition<State> part = partitioner.compute();

		System.out.println("Part with " + delta + ": " + part);
		assertEquals(expectedPartSize, part.getPartitions().size());
	}

	}
