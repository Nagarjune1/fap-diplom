package cz.upol.feda.event;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cz.upol.feda.lingvar.LingvisticVariable;

public class FuzzyEventsSequence {

	private final List<FuzzyEvent> events;

	public FuzzyEventsSequence(List<FuzzyEvent> events) {
		super();
		this.events = events;
	}

	public FuzzyEventsSequence(FuzzyEvent... events) {
		super();
		this.events = Arrays.asList(events);
	}

	public List<FuzzyEvent> getEvents() {
		return events;
	}

	public Set<LingvisticVariable> inferVariables() {
		return events.stream() //
				.flatMap((e) -> e.getVariables().stream()) //
				.collect(Collectors.toSet()); //
	}
	
///////////////////////////////////////////////////////////////////////////
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuzzyEventsSequence other = (FuzzyEventsSequence) obj;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FuzzyEventsSequence [events=" + events + "]";
	}
	
	
	
	

}
