package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Falc;

public class Mil extends SWActor {

	private String name;

	/**
	 * Create a Ship.  Used for the exit
	 * Starts on 0 health
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Humanoid. If this
	 *            decreases to below zero, the Humanoid will die.
	 * @param name
	 *            this humanoids's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Humanoid</code> belongs to
	 * @param forceAbility 
	 * @param trainingpoints 
	 * 
	 */
	public Mil(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world, 0, 0);
		// TODO Auto-generated constructor stub
		this.name = name;
		this.addAffordance(new Falc(this, m));
	}

	@Override
	public void act() {
		
		}
		
		
	

	@Override
	public String getShortDescription() {
		return name + " The Ship";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}


}
