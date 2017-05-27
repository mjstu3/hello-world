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

    public SpawnStorm(SWEntityInterface theTarget, MessageRenderer m) {
	super(theTarget, m);
    }

    @Override
    public boolean canDo(SWActor actor) {
    	if (actor.getSymbol() == "S") {
    		return true; 
    	}
    	return false;
    }

    @Override
    public void act(SWActor actor) {
	EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
	//entityManager.setLocation(new StormT(100, "Clone", messageRenderer, 0, 0), entityManager.whereIs(actor));
	
	
	actor.say(String.format("%s has spawned another storm clone!", target.getShortDescription()));
    }

    @Override
    public String getDescription() {
	return ("CloneTrooper ready ");
    }
}
