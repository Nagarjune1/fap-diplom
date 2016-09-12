package cz.upol.automaton.fuzzyLogic;

import java.util.Set;

import cz.upol.automaton.sets.Externisator;
import cz.upol.automaton.sets.HasExterernalRepresentation;


/**
 * @author  martin
 */
public interface ResiduedLattice extends HasExterernalRepresentation{
	public Set<Degree> getElements();

	public Degree supremum(Degree element1, Degree element2);

	public Degree imfimum(Degree element1, Degree element2);

	public Degree residuum(Degree element1, Degree element2);

	public Degree times(Degree element1, Degree element2);

	/**
	 * @uml.property  name="zero"
	 * @uml.associationEnd  
	 */
	public Degree getZero();

	/**
	 * @uml.property  name="one"
	 * @uml.associationEnd  
	 */
	public Degree getOne();

	public String getDescription();

	public Externisator<Degree> getUniverseElementsExternisator();

}
