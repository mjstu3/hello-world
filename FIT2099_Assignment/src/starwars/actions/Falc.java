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
public class Falc extends SWAffordance implements SWActionInterface {

	
	/**
	 * Constructor for the <code>Attack</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Attack</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being attacked
	 * @param m message renderer to display messages
	 */
	public Falc(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 0;
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
		
		return "Are you going to the Mil Falcon?";
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
	// Statements to show that only Luke can enter the Mil Falcon
	public boolean canDo(SWActor a) {
		if (a.getSymbol() == "@" ){
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
				
		if (target.getSymbol() == "XIT") {
			// When Luke goes to the Falcon
			System.out.println("\n\n\n\n");
			System.out.println(
					"| .:M... | .:A... | \n"
					+ "| .:@... | .:.... |"
					);
			
			System.out.println("You arrive at the Rebel's Base.");
			System.out.println("\n\n");
			
			//Print Win condition when leia and R2 all arrive at the falc safely
			if (a.getCharaCarried().getSymbol() == "L" && a.getDroidCarried().getSymbol() == "R2"){
				System.out.println("\n\nAdmiral Ackbar: Well done fellas! We have all the tools needed to destroy the death star. Leave the rest to us!");
				System.exit(0);
			}
			else {
				System.out.println("\n\nMothma: What are you doing here, farmboy? Bring us General Organa and the plans!\n\n ~You return to the deathstar....~");
			}
					
			}
		
			}
			
					
		
		
					
}	
		
		
