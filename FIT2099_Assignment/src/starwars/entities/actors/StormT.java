package starwars.entities.actors;

import java.util.ArrayList;


import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.actions.SpawnStorm;
import starwars.actions.Take;
import starwars.entities.Blaster;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

public class StormT extends SWActor {
	
	private String name;

	public StormT(int hitpoints, String name, MessageRenderer m, SWWorld world, int trainingpoints, int forceAbility) {
		super(Team.EVIL, hitpoints, m, world, trainingpoints, forceAbility);
		
		// Sets a blaster to the storm troopers initially
		Blaster gun =  new Blaster(m);
		this.name = name;
		this.setItemCarried(gun);
		
		
		
	}
	





	@Override
	public void act() {
		if (isDead()) {
			return;
		}

		AttackInformation attack = AttackNeighbours.attackLocals(this, this.world, true, true);
		//By setting 3rd and 4th param to true, allows storm troopers to not attack friendlys and ignore non actors
		
			if (attack != null) {
				//25 % chance of stormtroopers hitting, 75% chance missing!
				if (Math.random() < 0.25){
					scheduler.schedule(attack.affordance, this, 1);
				
					say(getShortDescription() + " has attacked " + attack.entity.getShortDescription());
			}
				else {
					this.say("Stormtrooper shoots wildly!");
			}
			
			
		}
		
		else if (Math.random() < 1){
			
					
		}
		
		else {	
			ArrayList<Direction> possibledirections = new ArrayList<Direction>();

			// build a list of available directions
			for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
				if (SWWorld.getEntitymanager().seesExit(this, d)) {
					possibledirections.add(d);
				}
			}

			Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
			//Not show enemies movements
			//say(getShortDescription() + " is heading " + heading + " next.");
			Move myMove = new Move(heading, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}
	
	
	public void spawn(StormT S) {

	}

	@Override
	public String getShortDescription() {
		return name + " the Storm Trooper";
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
