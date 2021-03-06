package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Dissemble;
import starwars.actions.Fix;
import starwars.actions.Move;
import starwars.actions.Take;
import starwars.actions.TakeDroid;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

public class Droids extends SWActor {

	private String name;
	//StepCounter for R2D2 moves 
	private int stepCount = 0;
	//Initial value East for R2D2 to move
	int num = 2;
	
	/**
	 * Create a Droid.  These are C3PO and R2D2.  They can be dissembled into parts.
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Droid. If this
	 *            decreases to below zero, the Droid will die.
	 * @param name
	 *            this droid's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Droid</code> belongs to
	 * @param forceAbility 
	 * @param trainingpoints 
	 * 
	 */
	
	public Droids(int hitpoints, String name, MessageRenderer m, SWWorld world, int trainingpoints, int forceAbility) {
		super(Team.GOOD, hitpoints, m, world, 0, 0);
		// TODO Auto-generated constructor stub
		this.name = name;
		this.addAffordance(new TakeDroid(this, m));
		this.addAffordance(new Fix(this, m));
		this.addAffordance(new Dissemble(this, m));
	}

	@Override
	public void act() {
		
		//Movement only for R2D2
		if (name == "R2D2"){
			if (isDead()) {
				return;
			}
			
			say(describeLocation());
			
			
			if (Math.random() > 0.5){
			
			
				ArrayList<Direction> droiddirections = new ArrayList<Direction>();
	
				// build a list of available directions
				for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
					if (SWWorld.getEntitymanager().seesExit(this, d )) {
						droiddirections.add(d);
					}
				}
				
				//Validation statements to walk R2 right 5 times and left 5 times.
				if (stepCount == 10){
					stepCount = 0;
				}
				
				if (stepCount > 4 && stepCount <= 9){
					
					num = 6;
					}
				if (stepCount >= 0 && stepCount < 4){
					num = 2;
				}
				
						
				//Index of East is 2 and Index of West is 6
				Direction heading = droiddirections.get(num);
				stepCount = stepCount + 1;
				
				say(getShortDescription() + " is heading " + heading + " next.");
				Move myMove = new Move(heading, messageRenderer, world);
	
				scheduler.schedule(myMove, this, 1);
				//Trying to allow R2 to dissemble and fix
		
			}
			
		}
		
		
		if (name == "C3PO"){
			if (isDead()) {
				return;
			}
			double n = Math.random();
			// 10 percent chance of C3PO speaking per turn.
			if (n < 0.1){
				say(describeLocation() + ".   I am C3PO, Please help me please :(.");
				}
			}
		
		
		if (this.getSymbol() == "DR") {
			say(describeLocation());
		}
	}

	@Override
	public String getShortDescription() {
		//Statement to pick up Droid parts
		if (this.getSymbol() == "DP"){
			return name + " Droid Parts";
		}
		
		if (this.getSymbol() == "D"){
		return name + ", a stationary Droid";
		}
		
		
		return name + ", a Droid";
			
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	
	public boolean isDroidPart(String symbol) {
		return symbol == "D";
	}
	
	public int getHealth() {
		return this.getHitpoints();
	}
	
	public void setHealth(int hp) {
		this.setHitpoints(hp);
	}
	
	
}
