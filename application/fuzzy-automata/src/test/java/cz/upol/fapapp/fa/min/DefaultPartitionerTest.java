package cz.upol.fapapp.fa.min;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import cz.upol.fapapp.core.automata.State;
import cz.upol.fapapp.core.fuzzy.Degree;
import cz.upol.fapapp.core.misc.CollectionsUtils;
import cz.upol.fapapp.fa.automata.FuzzyAutomaton;
import cz.upol.fapapp.fa.automata.TestAutomataCreator;

public class DefaultPartitionerTest {

	@Test
	public void testStrict() {
		Degree delta = Degree.ZERO;
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();
		DefaultPartitioner partitioner = new DefaultPartitioner(automaton, delta);

		Set<Set<State>> parts = partitioner.compute();
		System.out.println(parts);
		assertEquals(2, parts.size());

		@SuppressWarnings("unchecked")
		Set<State> part1 = (Set<State>) parts.stream().toArray()[0];
		assertEquals(1, part1.size());		
		assertTrue(part1.contains(new State("q_0")));

		@SuppressWarnings("unchecked")
		Set<State> part2 = (Set<State>) parts.stream().toArray()[1];
		assertEquals(1, part2.size());
		assertTrue(part2.contains(new State("q_1")));
	}

	@Test
	public void testVague() {
		Degree delta = Degree.ONE;
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();
		DefaultPartitioner partitioner = new DefaultPartitioner(automaton, delta);

		Set<Set<State>> parts = partitioner.compute();
		System.out.println(parts);
		assertEquals(1, parts.size());

		Set<State> part1 = parts.stream().findAny().get();
		assertEquals(2, part1.size());
		assertTrue(part1.contains(new State("q_0")));
		assertTrue(part1.contains(new State("q_1")));
	}

	@Test
	public void testSome() {
		Degree delta = new Degree(0.4);
		FuzzyAutomaton automaton = TestAutomataCreator.createAutomaton1();
		DefaultPartitioner partitioner = new DefaultPartitioner(automaton, delta);

		assertEquals(2, partitioner.compute().size());
	}
	
	
	@Test
	public void setsMashup() {
		Set<Set<String>> set = CollectionsUtils.toSet(CollectionsUtils.toSet("foo", "bar"));
		System.out.println(set);
		
		set.iterator().next().add("baz");
		System.out.println(set);
		
		set.iterator().next().remove("foo");
		System.out.println(set);
		
		
	}

}
