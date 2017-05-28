package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Dissemble;
import starwars.actions.Move;
import starwars.actions.VaderPower;
import starwars.entities.Blaster;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

public class Vader extends SWActor {

	private String name;

	/**
	 * Create Darth Vader.  He is the 'boss' actor of the game, the game is won if this actor
	 * is defeated.  
	 * 
	 * @param hitpoints
	 *            the number of hit points of Vader. If this
	 *            decreases to below zero, Vader will die.
	 * @param name
	 *            this actors's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Vader</code> belongs to
	 * @param forceAbility 
	 * @param trainingpoints 
	 * 
	 */
	public Vader(int hitpoints, String name, MessageRenderer m, SWWorld world, int trainingpoints, int forceAbility) {
		super(Team.EVIL, hitpoints, m, world, trainingpoints, forceAbility);
		// Sets Vader's lightsaber
		this.name = name;
		LightSaber saber =  new LightSaber(m);
		this.name = name;
		this.setItemCarried(saber);
		this.addAffordance(new VaderPower(this, m));
	}
	
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());

		AttackInformation attack = AttackNeighbours.attackLocals(this, this.world, false, false);
		
		if (attack != null) {
			say(getShortDescription() + " is looking at Luke!!! " + attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, this, 1);
		}
		//50 percent chance of moving
		else if (Math.random() > 0.5){
			
			ArrayList<Direction> possibledirections = new ArrayList<Direction>();

			// build a list of available directions
			for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
				if (SWWorld.getEntitymanager().seesExit(this, d)) {
					possibledirections.add(d);
				}
			}

			Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
			//Not show enemies movements
			say(getShortDescription() + " is heading " + heading + " next.");
			Move myMove = new Move(heading, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

	@Override
	public String getShortDescription() {
		return name + " the Sith Lord";
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
