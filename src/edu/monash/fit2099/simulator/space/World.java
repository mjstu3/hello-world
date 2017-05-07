package edu.monash.fit2099.simulator.space;
import java.util.Observable;

import edu.monash.fit2099.simulator.matter.EntityInterface;
import edu.monash.fit2099.simulator.matter.EntityManager;

/**
 * World: base class for simulated worlds.
 * 
 * A World comprises a collection of Location (space) and a collection of associations between Entities and 
 * Locations (matter located in space).  The time aspect is handled by Scheduler, which processes Commands.
 * Entities can be Actors, which have the ability to perform Commands.
 *  
 * Generated by UML Lab from class diagram.
 * 
 * TODO: This class stores data that the user will want to see, so should take the role of
 * Subject in the Observer pattern.  UI components should display it.
 * 
 * TODO: figure out how to integrate display of objects and locations.
 * 
 * @author ram
 * @date 17 Feb 2013
 * 
 */
/* Changelog
 * 
 * 2013-02-17: initial version
 * 2013-02-18: created abstract LocationContainer -- Worlds can have different 
 * 		dimensionality, etc. (ram)
 * 2013-02-19: removed association with UserInterface -- we now have a 
 * 		SimulationController, and the association will run the other way. (ram)
 * 		made World observable (ram)
 * 2013-03-10: made the entity manager static and added an accessor (ram)
 * 2013-04-09: removed the entity manager altogether, since it really needs to live in the subclass.
 * 		It needs type parameters which only the client will know, and if I make World generic so that
 * 		it can pass those parameters along, then I can't make the entity manager static.  So I've put
 * 		in a protected abstract accessor to force concrete subclasses to at least have access to an
 * 		entity manager.  (ram)
 * 
 */



public abstract class World extends Observable {
	/**
	 * <pre>
	 *           1..1     1..1
	 * World ------------------------- Grid
	 *           world        &gt;       grid
	 * </pre>
	 */
	@SuppressWarnings("rawtypes")		// space will contain instances of a Location subtype specified in client code
	protected LocationContainer space;	// instantiate in subclass constructor
	
	
	/**
	 * Part of the Observer pattern.
	 * 
	 * TODO: make this useful -- determining what exactly to pass back would be a good start.
	 * May need to defer this to client code.
	 * 
	 * @author ram
	 */
	public void refresh() {
		notifyObservers();
	}
	
	/**
	 * Get the <code>EntityManager</code> of this <code>World</code> which stores the assoications between the 
	 * <code>Locations</code> and <code>Entities</code> in this <code>World</code>.
	 * 
	 * @param <E> <code>Entity</code> subclass
	 * @param <L> <code>Location</code> subclass
	 * @return The <code>EntityManager</code> of this <code>World</code>
	 */
	protected abstract <E extends EntityInterface, L extends Location> EntityManager<E, L> getEntityManager();


	/**
	 * Ask all <code>Entities</code> in this <code>World</code> if they need to do something.
	 * 
	 * <code>Actors</code> have a chance to select an action command; non-Actor entities can passively change over time.
	 * 
	 * @author 	ram
	 * @date 	19 February 2013
	 */
	public void tick() {
		getEntityManager().tick();
	}
}