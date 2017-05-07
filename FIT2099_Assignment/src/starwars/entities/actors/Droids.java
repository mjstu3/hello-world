package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;

public class Droids extends SWActor {
	private String name;
	
	
	public Droids(int hitpoints, String name,String Owner, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);
		// TODO Auto-generated constructor stub
		this.name = name;
	}
}
