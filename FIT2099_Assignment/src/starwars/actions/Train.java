package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class Train extends SWAffordance implements SWActionInterface {
	/**
	 * Constructor for the <code>Train</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Train</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being trained
	 * @param m message renderer to display messages
	 */
	public Train(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}
	
	/**
	 * Returns the time is takes to perform this <code>Train</code> action.
	 * 
	 * @return The duration of the Train action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	
	/**
	 * A String describing what this <code>Train</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "train " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "train " + this.target.getShortDescription();
	}


	/**
	 * Determine whether a particular <code>SWActor a</code> can be trained.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if <code>SWActor</code> is human controlled (aka Luke), false if not.
	 */
	@Override
	public boolean canDo(SWActor a) {
		if (a.isHumanControlled()) { 
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Perform the <code>Attack</code> command on an entity.
	 * <p>
	 * This method does not perform any damage (an attack) if,
	 * <ul>
	 * 	<li>The target of the <code>Attack</code> and the <code>SWActor a</code> are in the same <code>Team</code></li>
	 * 	<li>The <code>SWActor a</code> is holding an item without the <code>WEAPON Affordance</code></li>
	 * </ul>
	 * <p>
	 * else it would damage the entity attacked, tires the attacker, and blunts any weapon used for the attack.
	 * 
	 * TODO : check if the weapon has enough hitpoints and the attacker has enough energy before an attack.
	 * 
	 * @param 	a the <code>SWActor</code> who is attacking
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		an <code>Attack</code> must not be performed on a dead <code>SWActor</code>
	 * @post	if a <code>SWActor</code>dies in an <code>Attack</code> their <code>Attack</code> affordance would be removed
	 * @see		starwars.SWActor#isDead()
	 * @see 	starwars.Team
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		// to be continued
	}
}
