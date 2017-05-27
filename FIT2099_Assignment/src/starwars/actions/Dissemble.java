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
public class Dissemble extends SWAffordance implements SWActionInterface {

	
	/**
	 * Constructor for the <code>Attack</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Attack</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being attacked
	 * @param m message renderer to display messages
	 */
	public Dissemble(SWEntityInterface theTarget, MessageRenderer m) {
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
		//Ensure Luke can dissemble all droids but not droid parts, due to clarity reasons in console. (Too much spam)
		if (this.getTarget().getSymbol() != "DP"){
			return "dissemble " +  this.target.getShortDescription();
		}
		return "Nothing to see here";
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
	// Statements to show that R2 and yourself can dissemble the droids.
	public boolean canDo(SWActor a) {
		if (a.getSymbol() == "R2" ||  a.getSymbol() == "@" ){
			return true;
		}
		else{
			return false;
		}
	}

	
	
	@Override
	public void act(SWActor a) {
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
	
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		//Exception for null error
		if (target == null) {
			return;
		}
				
		if ((a.getSymbol() == "R2"  || a.getSymbol() == "@") && (target.getSymbol() == "D" || target.getSymbol() == "DR" )) {
			
			
			// When Luke or a Droid encounters a stationary droid, he can dissemble the droid if it has already have spare droid parts.
		
		target.setSymbol("DP");
		a.say(a.getShortDescription() + " dissembled " + target.getShortDescription());
		//Allows R2 to carry droid parts if it is not carrying any parts
		if (a.getSymbol() == "R2") {
			a.setItemCarried(target);
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
			a.say("Nothing happened..................................................................................");
		}
	}	
		
					
}	
		
		

