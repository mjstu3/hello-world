package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.actors.StormT;
import starwars.SWWorld;

public class SpawnStorm extends SWAffordance {
	
	/**
	 * Constructor for the <code>SpawnStorm</code> class. 
	 * 
	 * @param theTarget the target being trained
	 * @param m message renderer to display messages
	 */
    public SpawnStorm(SWEntityInterface theTarget, MessageRenderer m) {
    	super(theTarget, m);
    }
    
    /**
	 * Determine whether a particular <code>SWActor a</code> can clone.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if <code>SWActor</code> has symbol S (StormTrooper), false if not.
	 */
    @Override
    public boolean canDo(SWActor actor) {
    	if (actor.getSymbol() == "S") {
    		return true; 
    	}
    	return false;
    }
    
    /**
	 * Perform the <code>SpawnStorm</code> command on an entity.
	 * @param 	a the <code>SWActor</code> who is being cloned
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		cloning must not be performed on a dead <code>SWActor</code>
	 * @see		starwars.SWActor#isDead()
	 * @see 	starwars.Team
	 */
    @Override
    public void act(SWActor actor) {
    	if (Math.random() < 0.05){
	    	EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
	    	entityManager.setLocation(new StormT(100, "Clone", messageRenderer, actor.getSWWorld(), 0, 0), entityManager.whereIs(actor));
    	}
    	
    	actor.say(String.format("%s has spawned another storm clone!", target.getShortDescription()));
    }
    
    /**
	 * A String describing what this <code>SpawnStorm</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "CloneTrooper ready " and the short description of the target of this <code>Affordance</code>
	 */
    @Override
    public String getDescription() {
    	return ("CloneTrooper ready ");
    }
}
