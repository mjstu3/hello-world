package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

/**
 * Command to attack entities.
 * 
 * This affordance is attached to all attackable entities
 * 
 * @author David.Squire@monash.edu (dsquire)
 */
/*
 * Change log
 * 2017/02/03	Fixed the bug where the an actor could attack another actor in the same team (asel)
 * 2017/02/08	Attack given a priority of 1 in constructor (asel)
 */
public class Fix extends SWAffordance implements SWActionInterface {

	
	/**
	 * Constructor for the <code>Attack</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Attack</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being attacked
	 * @param m message renderer to display messages
	 */
	public Fix(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}


	/**
	 * Returns the time is takes to perform this <code>Attack</code> action.
	 * 
	 * @return The duration of the Attack action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	
	/**
	 * A String describing what this <code>Attack</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "attack " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		
		return " repair " + this.target.getShortDescription();
	}
	




	/**
	 * Determine whether a particular <code>SWActor a</code> can attack the target.
	 * 
	 * @author 	dsquire
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true any <code>SWActor</code> can always try an attack, it just won't do much 
	 * 			good unless this <code>SWActor a</code> has a suitable weapon.
	 */
	@Override
	// Statements to show that Only Tusken Raiders, Ben Kenobi and Luke can attack
	public boolean canDo(SWActor a) {
		if (a.getSymbol() == "T" || a.getSymbol() == "B" || a.getSymbol() == "@" ){
			return true;
		}
		else{
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
	 * @author 	dsquire -  adapted from the equivalent class in the old Eiffel version
	 * @author 	Asel - bug fixes.
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
				
		if (target.getSymbol() == "D" || a.getSymbol() == "R2" && target.getSymbol() == "D") {
			// When Luke or a Droid encounters a stationary droid, he can fix the droid if it has already have spare droid parts.
			if (target.getSymbol() == "D" && a.getItemCarried().getSymbol() == "D" || a.getSymbol() == "R2" && target.getSymbol() == "D"){
				target.setHitpoints(200);
				a.say(a.getShortDescription() + " repaired " + target.getShortDescription());
				//Delete item carried.
				a.setItemCarried(null);
			}
			
					
		}
		else if (target.getSymbol() == "D" && a.getSymbol() == "R2"){
			//If R2 goes over a droid part
			SWEntityInterface theItem = (SWEntityInterface) target;
			a.setItemCarried(theItem);
			SWAction.getEntitymanager().remove(target);//remove the target from the entity manager since it's now held by the SWActor
		
			//remove the take affordance
			target.removeAffordance(this);
			// add a leave affordance
			target.addAffordance(new Leave(theItem, messageRenderer));
		}
			
			
		
		else {
			a.say("Nothing can be repaired..................................................................................");
		}
	}	
		
					
}	
		
		
