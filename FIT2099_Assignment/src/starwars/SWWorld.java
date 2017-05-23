package starwars;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Take;
import starwars.entities.*;
import starwars.entities.actors.*;

/**
 * Class representing a world in the Star Wars universe. 
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 */
public class SWWorld extends World {
	
	/**
	 * <code>SWGrid</code> of this <code>SWWorld</code>
	 */
	private SWGrid myGrid;
	
	private MessageRenderer messageRenderer;
	
	/**The entity manager of the world which keeps track of <code>SWEntities</code> and their <code>SWLocation</code>s*/
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();
	
	/**
	 * Constructor of <code>SWWorld</code>. This will initialize the <code>SWLocationMaker</code>
	 * and the grid.
	 */
	public SWWorld() {
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		myGrid = new SWGrid(factory);
		space = myGrid;
		
	}

	/** 
	 * Returns the height of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int height() {
		return space.getHeight();
	}
	
	/** 
	 * Returns the width of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int width() {
		return space.getWidth();
	}
	
	/**
	 * Set up the world, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeWorld(MessageRenderer iface) {
		SWLocation loc;
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		
		// BadLands
		for (int row = 5; row < 8; row++) {
			for (int col = 4; col < 7; col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Badlands (" + col + ", " + row + ")");
				loc.setShortDescription("Badlands (" + col + ", " + row + ")");
				loc.setSymbol('b');
			}
		}
		
		//Ben's Hut
		loc = myGrid.getLocationByCoordinates(5, 6);
		loc.setLongDescription("Ben's Hut");
		loc.setShortDescription("Ben's Hut");
		loc.setSymbol('H');
		
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.SOUTH,
                CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.SOUTH,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.NORTHWEST, CompassBearing.NORTHWEST};
		
		BenKenobi ben = BenKenobi.getBenKenobi(iface, this, patrolmoves);
		ben.setSymbol("B");
		loc = myGrid.getLocationByCoordinates(4,  5);
		entityManager.setLocation(ben, loc);
		
		
		loc = myGrid.getLocationByCoordinates(5,9);
		
		// Luke
		Player luke = new Player(Team.GOOD, 100, iface, this, 0, 10);
		luke.setShortDescription("Luke");
		entityManager.setLocation(luke, loc);
		luke.resetMoveCommands(loc);
		
		
		// Beggar's Canyon 
		for (int col = 3; col < 8; col++) {
			loc = myGrid.getLocationByCoordinates(col, 8);
			loc.setShortDescription("Beggar's Canyon (" + col + ", " + 8 + ")");
			loc.setLongDescription("Beggar's Canyon  (" + col + ", " + 8 + ")");
			loc.setSymbol('C');
			loc.setEmptySymbol('='); // to represent sides of the canyon
		}
		
		// Moisture Farms
		for (int row = 0; row < 10; row++) {
			for (int col = 8; col < 10; col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setShortDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setSymbol('F');
				
				// moisture farms have reservoirs
				entityManager.setLocation(new Reservoir(iface), loc);				
			}
		}
		
		// Ben Kenobi's hut
		/*
		 * Scatter some other entities and actors around
		 */
		// a canteen
		loc = myGrid.getLocationByCoordinates(3,1);
		SWEntity canteen = new Canteen(iface, 10,0);
		canteen.setSymbol("o");
		canteen.setHitpoints(500);
		entityManager.setLocation(canteen, loc);
		canteen.addAffordance(new Take(canteen, iface));

		// an oil can treasure
		loc = myGrid.getLocationByCoordinates(1,5);
		SWEntity oilcan = new SWEntity(iface);
		oilcan.setShortDescription("an oil can");
		oilcan.setLongDescription("an oil can, which would theoretically be useful for fixing robots");
		oilcan.setSymbol("x");
		oilcan.setHitpoints(100);
		// add a Take affordance to the oil can, so that an actor can take it
		entityManager.setLocation(oilcan, loc);
		oilcan.addAffordance(new Take(oilcan, iface));
		
		// a lightsaber
		LightSaber lightSaber = new LightSaber(iface);
		loc = myGrid.getLocationByCoordinates(5,5);
		entityManager.setLocation(lightSaber, loc);
		
		// A blaster 
		Blaster blaster = new Blaster(iface);
		loc = myGrid.getLocationByCoordinates(3, 4);
		entityManager.setLocation(blaster, loc);
		
		// A Tusken Raider
		TuskenRaider tim = new TuskenRaider(200, "Tim", iface, this, 0, 0);
		tim.setSymbol("T");
		loc = myGrid.getLocationByCoordinates(4,3);
		entityManager.setLocation(tim, loc);
		
		// Another Tusken Raider
		TuskenRaider tony = new TuskenRaider(200, "Tony", iface, this, 0, 0);
		tony.setSymbol("T");
		loc = myGrid.getLocationByCoordinates(7,3);
		entityManager.setLocation(tony, loc);
		
		// Another Tusken Raider
		TuskenRaider theo = new TuskenRaider(200, "Theo", iface, this, 0, 0);
		theo.setSymbol("T");
		loc = myGrid.getLocationByCoordinates(8,2);
		entityManager.setLocation(theo, loc);
		
		// Another Tusken Raider
		TuskenRaider troy = new TuskenRaider(200, "Troy", iface, this, 0, 0);
		troy.setSymbol("T");
		loc = myGrid.getLocationByCoordinates(2,5);
		entityManager.setLocation(troy, loc);
	
		
		//Droid R2D2
		Droids R2D2 = new Droids(200, "R2D2", iface, this, 0, 0);
		R2D2.setSymbol("R2");
		loc = myGrid.getLocationByCoordinates(3,3);
		entityManager.setLocation(R2D2, loc);
		
		
		
		
		//Droid C3PO
		Droids C3PO = new Droids(200, "C3PO", iface, this, 0, 0);
		C3PO.setSymbol("C3");
		loc = myGrid.getLocationByCoordinates(1,1);
		entityManager.setLocation(C3PO, loc);
				
		//Droid 1
		Droids d1 = new Droids(200, "White Droid", iface, this, 0, 0);
		d1.setSymbol("D");
		loc = myGrid.getLocationByCoordinates(6,3);
		entityManager.setLocation(d1, loc);
		
		//Droid 2
		Droids d2 = new Droids(200, "Black Droid", iface, this, 0, 0);
		d2.setSymbol("D");
		loc = myGrid.getLocationByCoordinates(2,3);
		entityManager.setLocation(d2, loc);
		
		//Humanoid Uncle Owen
		Humanoids owen = new Humanoids(100, "Uncle Owen", iface, this, 0, 0);
		owen.setSymbol("U");
		loc = myGrid.getLocationByCoordinates(8,7);
		entityManager.setLocation(owen, loc);
				
		//Humanoid Aunt Beru
		Humanoids beru = new Humanoids(100, "Aunt Beru", iface, this, 0, 0);
		beru.setSymbol("A");
		loc = myGrid.getLocationByCoordinates(9,6);
		entityManager.setLocation(beru, loc);
		
		
		//Canteen on Ben's Route
		loc = myGrid.getLocationByCoordinates(5,5);
		SWEntity canteen2 = new Canteen(iface, 10,0);
		canteen2.setSymbol("o");
		canteen2.setHitpoints(500);
		entityManager.setLocation(canteen2, loc);
		canteen.addAffordance(new Take(canteen2, iface));
		
		// Darth Vader
		Vader vader = new Vader(10000, "Darth Vader", iface, this, 0, 0);
		vader.setSymbol("!V!");
		loc = myGrid.getLocationByCoordinates(4,4);
		entityManager.setLocation(vader, loc);
		
		//Humanoid Princess Leia
		Humanoids leia = new Humanoids(100, "Princess Leia", iface, this, 0, 0);
		leia.setSymbol("L");
		loc = myGrid.getLocationByCoordinates(0,0);
		entityManager.setLocation(leia, loc);
		
		//Storm Trooper 1
		StormT s1 = new StormT(100, "1", iface, this, 0, 0);
		s1.setSymbol("S");
		loc = myGrid.getLocationByCoordinates(2,0);
		entityManager.setLocation(s1, loc);
		
		//Storm Trooper 2
		StormT s2 = new StormT(100, "2", iface, this, 0, 0);
		s2.setSymbol("S");
		loc = myGrid.getLocationByCoordinates(3,0);
		entityManager.setLocation(s2, loc);
		
		//Storm Trooper 3
		StormT s3 = new StormT(100, "3", iface, this, 0, 0);
		s3.setSymbol("S");
		loc = myGrid.getLocationByCoordinates(4,0);
		entityManager.setLocation(s3, loc);
		
		//Storm Trooper 4
		StormT s4 = new StormT(100, "4", iface, this, 0, 0);
		s4.setSymbol("S");
		loc = myGrid.getLocationByCoordinates(5,0);
		entityManager.setLocation(s4, loc);
		
		//Storm Trooper 5
		StormT s5 = new StormT(100, "5", iface, this, 0, 0);
		s5.setSymbol("S");
		loc = myGrid.getLocationByCoordinates(6,0);
		entityManager.setLocation(s5, loc);
	}

	/*
	 * Render method was removed from here
	 */
	
	/**
	 * Determine whether a given <code>SWActor a</code> can move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canMove(SWActor a, Direction whichDirection) {
		SWLocation where = (SWLocation)entityManager.whereIs(a); // requires a cast for no reason I can discern
		return where.hasExit(whichDirection);
	}
	
	/**
	 * Accessor for the grid.
	 * 
	 * @author ram
	 * @return the grid
	 */
	public SWGrid getGrid() {
		return myGrid;
	}

	/**
	 * Move an actor in a direction.
	 * 
	 * @author ram
	 * @param a the actor to move
	 * @param whichDirection the direction in which to move the actor
	 */
	public void moveEntity(SWActor a, Direction whichDirection) {
		
		//get the neighboring location in whichDirection
		Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
		
		// Base class unavoidably stores superclass references, so do a checked downcast here
		if (loc instanceof SWLocation)
			//perform the move action by setting the new location to the the neighboring location
			entityManager.setLocation(a, (SWLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
	}

	/**
	 * Returns the <code>Location</code> of a <code>SWEntity</code> in this grid, null if not found.
	 * Wrapper for <code>entityManager.whereIs()</code>.
	 * 
	 * @author 	ram
	 * @param 	e the entity to find
	 * @return 	the <code>Location</code> of that entity, or null if it's not in this grid
	 */
	public Location find(SWEntityInterface e) {
		return entityManager.whereIs(e); //cast and return a SWLocation?
	}

	/**
	 * This is only here for compliance with the abstract base class's interface and is not supposed to be
	 * called.
	 */

	@SuppressWarnings("unchecked")
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return SWWorld.getEntitymanager();
	}

	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>SWEntities</code> and
	 * <code>SWLocations</code> in <code>SWWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>SWWorld</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}

	public MessageRenderer getMessageRenderer() {
		return messageRenderer;
	}
}
