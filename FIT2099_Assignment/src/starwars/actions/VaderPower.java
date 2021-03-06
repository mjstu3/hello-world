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
public class VaderPower extends SWAffordance implements SWActionInterface {
	

	
	/**
	 * Constructor for the <code>Attack</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Attack</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being attacked
	 * @param m message renderer to display messages
	 */
	public VaderPower(SWEntityInterface theTarget, MessageRenderer m) {
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
		
		return "Control " + target.getShortDescription();
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
	// Statements to show that Only Vader can do this
	public boolean canDo(SWActor a) {
		if (a.getSymbol() == "!V!"){
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
		
		//Exception for null error
		if (target == null) {
			return;
		}
		
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		//Interaction only for Luke		
		if (target.getSymbol() == "@") {
			
			if (target.getTrainingpoints() > 100){
				//25% chance of forcing luke to the dark side
				if (Math.random() > 0.75){
					//Lose the game, set luke to 0hp
					target.setHitpoints(0);
					System.out.println("Luke is seduced by the Dark side. You lose");
					
				}
			}
			
			else {
				//50% chance of forcing Luke to the dark side
				if (Math.random() > 0.5){
					//Lose the game, set Luke to 0hp
					target.setHitpoints(0);
					a.say("Luke is seduced by the Dark side. You lose");
					
					 
				 }
			}
			
		}	
		
					
	}
}
		
		

