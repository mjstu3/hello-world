package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLegend;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.actions.Train;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.Patrol;

/**
 * Ben (aka Obe-Wan) Kenobi.  
 * 
 * At this stage, he's an extremely strong critter with a <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * 
 * Note that you can only create ONE Ben, like all SWLegends.
 * @author rober_000
 *
 */
public class BenKenobi extends SWLegend {

	private static BenKenobi ben = null; // yes, it is OK to return the static instance!
	private Patrol path;
	private BenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.GOOD, 1000, m, world, 0, 100);
		path = new Patrol(moves);
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		LightSaber bensweapon = new LightSaber(m);
		setItemCarried(bensweapon);
		this.addAffordance(new Train(this, m));
	    
	}
	
	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		ben = new BenKenobi(m, world, moves);
		ben.activate();
		return ben;
	}
	
	@Override
	protected void legendAct() {
	
		if(isDead()) {
			return;
		}
		
		AttackInformation attack = AttackNeighbours.attackLocals(this, this.world, false, false);
		if (attack != null) {
			say(getShortDescription() + " has attacked " + attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, this, 1);
		}
		//If Ben's health has dropped, and is holding an empty canteen, he will not 
		else if (this.getHitpoints() < 1000 ) {
			if (this.getItemCarried().getSymbol() == "o"){
				//Adds 200hp to ben's health
				this.setHitpoints(200);
				}
			}
		
	
		else {
				Direction newdirection = path.getNext();
				say(getShortDescription() + " moves " + newdirection);
				Move myMove = new Move(newdirection, messageRenderer, world);
	
				scheduler.schedule(myMove, this, 1);
				}
			
		
	}
}
	

