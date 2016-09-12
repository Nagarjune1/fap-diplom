package cz.upol.automaton.automata.tools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.upol.automaton.automata.abstracts.SimpleAutomaton;
import cz.upol.automaton.ling.Symbol;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.Transition;
import cz.upol.automaton.misc.edges.AbstractEdge;

public abstract class AbstractAutomataTools<//
A extends SimpleAutomaton<TS, E>, //
TD, ID, FD, //
SS extends Iterable<State>, //
TS extends Iterable<Transition>, //
E extends AbstractEdge<TS>> {

	protected final A automaton;

	public AbstractAutomataTools(A automaton) {
		this.automaton = automaton;
	}

	/**
	 * Zkopíruje přechod z kolekce from do kolekce to.
	 * 
	 * @param transition
	 * @param from
	 * @param to
	 */
	public abstract void copy(Transition transition, TS from, TS to);

	/**
	 * Odebere přechod z kolekce from a vrátí jemu příslušnou hodnotu.
	 * 
	 * @param transition
	 * @param from
	 * @return
	 */
	public abstract TD remove(Transition transition, TS from);

	/***
	 * Přidá přechod do kolekce to s daným parametrem.
	 * 
	 * @param transition
	 * @param with
	 * @param to
	 */
	public abstract void add(Transition transition, TD with, TS to);

	/**
	 * Vytvoří a vrátí prázdnou kolekci přechodů.
	 * 
	 * @return
	 */
	public abstract TS transitions();

	/**
	 * Vytvoří hranu. Kolekce přechodů může být prázdná, ale ne null.
	 * 
	 * @param from
	 * @param to
	 * @param transitions
	 * @return
	 */
	public abstract E edge(State from, State to, TS transitions);

	/**
	 * Vrátí true, pokud je zadaný přechod nějakým způsobem v zadané kolekci.
	 * 
	 * @param transition
	 * @param in
	 * @return
	 */
	public abstract boolean isInSet(Transition transition, TS in);

	/**
	 * Vrací true, pokud je zadaný přechod v obou kolekcích zastoupen stejně.
	 * 
	 * @param transition
	 * @param first
	 * @param second
	 * @return
	 */
	public abstract boolean isInBothSets(Transition transition, TS first,
			TS second);

	/**
	 * Nastaví počátační stav se zadanou hodnotou.
	 * 
	 * @param state
	 * @param with
	 */
	public abstract void setInitial(State state, ID with);

	/**
	 * Nastaví koncový stav se zadanou hodnotou.
	 * 
	 * @param state
	 * @param with
	 */

	public abstract void setFinite(State state, FD with);

	/**
	 * Odnastaví stav jako počáteční a vrací jemu příslušnou hodnotu.
	 * 
	 * @param state
	 * @param with
	 */

	public abstract ID unsetInitial(State state);

	/**
	 * Onastaví stav jako koncový a vrací jemu příslušnou hodnotu.
	 * 
	 * @param state
	 * @param with
	 */
	public abstract FD unsetFinite(State state);

	/**
	 * Uklidí po smazaném stavu (odstraní jeho přechody, koncovost a
	 * počátečnost).
	 * 
	 * @param automaton
	 * @param state
	 */
	public void fixAfterStateDeleted(State state) {
		TS transitions = automaton.getTransitionsWithState(state);

		for (Transition t : transitions) {
			remove(t, automaton.getDelta());
		}

		unsetInitial(state);
		unsetFinite(state);
	}

	/**
	 * Uklidí po nahrazeném stavu.
	 * 
	 * @param automaton
	 * @param currentState
	 * @param newState
	 */
	public void fixAfterStateUpdated(State currentState, State newState) {
		TS transitions = automaton.getTransitionsWithState(currentState);

		for (Transition currentTrans : transitions) {
			State newFrom = currentTrans.getFrom(), newTo = currentTrans
					.getTo();

			if (currentTrans.getFrom().equals(currentState)) {
				newFrom = newState;
			}
			if (currentTrans.getTo().equals(currentState)) {
				newTo = newState;
			}
			Symbol over = currentTrans.getOver();
			TD degree = remove(currentTrans, automaton.getDelta());

			Transition newTrans = new Transition(newFrom, over, newTo);
			add(newTrans, degree, automaton.getDelta());
		}

		ID initD = unsetInitial(currentState);
		setInitial(newState, initD);

		FD finalD = unsetFinite(currentState);
		setFinite(newState, finalD);
	}

	/**
	 * Vrátí kolekci přechodů ze zadaného stavu.
	 * 
	 * @param from
	 * @return
	 */
	public TS transitionsFrom(State from) {
		TS transitions = transitions();

		for (Transition transition : automaton.getDelta()) {
			if (transition.isFrom(from)) {
				copy(transition, automaton.getDelta(), transitions);
			}
		}

		return transitions;
	}

	/**
	 * Vrátí kolekci přechod ze zadaného stavu a přes zvolený symbol.
	 * 
	 * @param from
	 * @param symbol
	 * @return
	 */
	public TS transitionsFromAndOver(State from, Symbol symbol) {
		TS transitions = transitions();

		for (Transition transition : automaton.getDelta()) {
			if (transition.isFrom(from) && transition.isOver(symbol)) {
				copy(transition, automaton.getDelta(), transitions);
			}
		}

		return transitions;
	}

	/**
	 * Vrátí kolekci přechodů ze zadaného stavu do zadnaého stavu.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public TS transitionsFromTo(State from, State to) {
		TS transitions = transitions();

		for (Transition transition : automaton.getDelta()) {
			if (transition.isFrom(from) && transition.isTo(to)) {
				copy(transition, automaton.getDelta(), transitions);
			}
		}

		return transitions;
	}

	/**
	 * Převede zadané přechody na hrany.
	 * 
	 * @param transitions
	 * @return
	 */
	public Set<E> transitionsToEdges(TS transitions) {

		Map<E, E> edges = new HashMap<>();

		for (Transition transition : transitions) {
			State from = transition.getFrom();
			State to = transition.getTo();

			E keyEdge = edge(from, to, transitions());

			E currentEdge = edges.get(keyEdge);
			if (currentEdge == null) {
				currentEdge = keyEdge;
				edges.put(keyEdge, currentEdge);
			}

			copy(transition, automaton.getDelta(), currentEdge.getTransitions());
		}

		return new HashSet<>(edges.values());
	}

	/**
	 * Přidá do automatu zadanou hranu (popř. slije s existující).
	 * 
	 * @param edge
	 */
	public void addEdge(E edge) {
		for (Transition transition : edge.getTransitions()) {
			checkTransition(transition);
			copy(transition, edge.getTransitions(), automaton.getDelta());
		}
	}

	/**
	 * Odstraní z automatu zadanou hranu (resp. její přechody).
	 * 
	 * @param edge
	 */
	public void removeEdge(E edge) {
		for (Transition transition : edge.getTransitions()) {
			remove(transition, automaton.getDelta());
		}
	}

	/**
	 * Upraví zadanou hranu (přechody, které v automatu chybí, doplní a ty,
	 * které přebývají, odebere).
	 * 
	 * @param edge
	 */
	public void updateEdge(E edge) {
		TS currentOnEdge = automaton.transitionsFromTo(edge.getFrom(),
				edge.getTo());

		for (Transition transition : currentOnEdge) {
			boolean isNotInSet = !isInSet(transition, edge.getTransitions());

			if (isNotInSet) {
				remove(transition, automaton.getDelta());
			}
		}

		for (Transition transition : edge.getTransitions()) {
			boolean is = isInBothSets(transition, automaton.getDelta(),
					edge.getTransitions());

			if (!is) {
				checkTransition(transition);
				copy(transition, edge.getTransitions(), automaton.getDelta());
			}
		}

	}

	/**
	 * Vrátí přechody, které koincidují se zadanýmn stavem.
	 * 
	 * @param state
	 * @return
	 */
	public TS getTransitionsWithState(State state) {
		TS transitions = transitions();

		for (Transition transition : automaton.getDelta()) {
			if (transition.isFrom(state) || transition.isTo(state)) {
				copy(transition, automaton.getDelta(), transitions);
			}
		}

		return transitions;
	}

	/**
	 * Provede úvodní kontrolu konzistence automatu.
	 */
	public void doInitialCheck() {
		for (Transition transition : automaton.getDelta()) {
			checkTransition(transition);
		}
	}

	/**
	 * Ověří, že přechod může být použit (stavy jsou ze stavů a symbol z
	 * abecedy).
	 * 
	 * @param transition
	 */
	public void checkTransition(Transition transition) {
		doBaseTransitionCheck(transition);
		doCustomTransitionCheck(transition);
	}

	public abstract void doCustomTransitionCheck(Transition transition);

	/**
	 * Provede základní kontrolu přechodu (na stavy a symbol abecedy).
	 * 
	 * @param transition
	 */
	public void doBaseTransitionCheck(Transition transition) {
		State from = transition.getFrom();
		State to = transition.getTo();
		Symbol over = transition.getOver();

		if (!automaton.getStates().contains(from)) {
			throw new IllegalArgumentException("From (" + from.getLabel()
					+ ") is not a state of the automaton.");
		}

		if (!automaton.getStates().contains(to)) {
			throw new IllegalArgumentException("To (" + to.getLabel()
					+ ") is not a state of the automaton.");
		}

		if (!automaton.getAlphabet().contains(over)) {
			throw new IllegalArgumentException("Symbol (" + over.getValue()
					+ ") is not symbol of the automaton's alphabet.");
		}
	}
}
