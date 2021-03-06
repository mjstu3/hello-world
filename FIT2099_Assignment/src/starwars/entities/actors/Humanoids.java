package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.TakeDroid;
import starwars.actions.TakeHumanoid;

public class Humanoids extends SWActor {

	private String name;

	/**
	 * Create a Humanoid.  These are Luke's Uncle Owen and Aunt Beru, who are
	 * in the moisture farm.  They have no force powers and don't move.  They are 
	 * used to test Luke's mind control powers after he has been trained.
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
	public Humanoids(int hitpoints, String name, MessageRenderer m, SWWorld world, int trainingpoints, int forceAbility) {
		super(Team.GOOD, hitpoints, m, world, 0, 0);
		// TODO Auto-generated constructor stub
		this.name = name;
		//Leia can be picked up
		
		this.addAffordance(new TakeHumanoid(this, m));
		
	}

	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());
	}

	@Override
	public String getShortDescription() {
		return name ;
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
}
