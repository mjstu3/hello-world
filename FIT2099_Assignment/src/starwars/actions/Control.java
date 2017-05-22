package starwars.actions;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.swinterfaces.SWGridController;

/**
 * Command to control entities using the force
 * 
 * This affordance is attached to all entities with the force
 * 
 */

public class Control extends SWAffordance implements SWActionInterface {

	protected SWWorld world;
	
	/**
	 * Constructor for the <code>Attack</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Attack</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being attacked
	 * @param m message renderer to display messages
	 */
	public Control(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}

	/**
	 * A String describing what this <code>Control</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "control " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "control " + this.target.getShortDescription();
	}


	/**
	 * Determine whether a particular <code>SWActor a</code> can control the target.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true any <code>SWActor</code> can always try an attack, it just won't do much 
	 * 			good unless this <code>SWActor a</code> has a suitable weapon.
	 */

	@Override
    public boolean canDo(SWActor actor) {
		return actor.hasCapability(Capability.MIND_CONTROLLER) && !getTarget().isDead();
    }

    @Override
    public void act(SWActor actor) {
    	ArrayList<ActionInterface> cmds = new ArrayList<ActionInterface>();
    	for (CompassBearing dir : CompassBearing.values()) {
    		if (world.canMove((SWActor)target, dir) )
    			cmds.add(new Move(dir, messageRenderer, world));
	}

	messageRenderer.render(String.format("Force %s to move in which direction?", target.getShortDescription()));
	SWActionInterface move = SWGridController.getSelection(cmds);
	target.say(String.format("%s looks confused, but does it anyway.", target.getShortDescription()));
	((SWActor)target).schedule(move);
    }

    
	
}
