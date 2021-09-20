/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Display;
import com.codename1.util.MathUtil;

import java.util.Random;
import java.util.ArrayList;
import java.util.Observable;

public class GameWorld extends Observable {
	
	//private Vector<GameObject> theWorldVector;
	private GameObjectCollection gameObjects;
	private int livesRemaining = 3;
	private int clockTimer = 0;
	private int numOfBases = 0;
	private int baseReached = 0;
	private int energyLevel = 0;
	private int damageLevel = 0;
	private static int height = 0, width = 0;
	private boolean pressedX;
	private boolean sound = false;
	Random rand = new Random();
	private Sound collisionSound = new Sound("crash.wav");
	private Sound energySound = new Sound("energy.wav");
	private Sound deathSound = new Sound("explosion.wav");
	private BGSound bgSound = new BGSound("funbg.wav"); 
	private boolean paused = false;
	private boolean pressedPosition = false;
	
	// Constructor
	public GameWorld() {
		this.height = 1000;
		this.width = 1000;
		gameObjects = new GameObjectCollection();;
	}
	
	// Initialize the game world
	public void init() {
		System.out.println("Initializing the game world...\n");
		pressedX = false; // As world initializes, player has not pressed the x key yet
		createPlayerCyborg();		// Create a player cyborg
		createNonPlayerCyborgs(3);		// Create 3 NPC Cyborgs
		createBases(4);				// Create 4 bases
		createEnergyStations(2);		// Create 2 energy stations
		createDrones(2);			// Create 2 drones

		// Notify GameWorld's observers
		setChanged();
		notifyObservers();	
	}
	
	// Create a player cyborg
	public void createPlayerCyborg() {
		PlayerCyborg playerCyborg = PlayerCyborg.getCyborg();
		playerCyborg.setEnergyLevel(100);
		playerCyborg.setSpeed(30);  // Set initial speed of cyborg to 30
		playerCyborg.setSize(50);	// Set size of cyborg to 40
		playerCyborg.setColor(0, 0, 255);	// Color the cyborg BLUE
		playerCyborg.setLocation(200, 300);		// Set the cyborg's starting location
		playerCyborg.setDmgLevel(0);		// Ensure that player cyborg's damage level is 0
		playerCyborg.setMaxSpeed(100);		// Ensure that player cyborg's max speed is 100
		energyLevel = playerCyborg.getEnergyLevel();
		gameObjects.add(playerCyborg);		// Add the player cyborg to the gameObjects Collection
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	// Create NPC cyborgs
	public void createNonPlayerCyborgs(int num) {
		// for loop to create num instances of NPC cyborgs
		for (int i = 0; i < num; i++) {
			NonPlayerCyborg cyborgNPC = new NonPlayerCyborg();
			if (i == 0) {
				cyborgNPC.setStrategy(new BaseStrategy(cyborgNPC, this));
				cyborgNPC.setLocation(275 + rand.nextInt(201), 375 + rand.nextInt(201));
			} else if (i == 2){
				cyborgNPC.setStrategy(new BaseStrategy(cyborgNPC, this));
				cyborgNPC.setLocation(300 + rand.nextInt(251), 400 + rand.nextInt(251));
			} else {
				cyborgNPC.setStrategy(new AttackStrategy(cyborgNPC, this));
				cyborgNPC.setLocation(300 + rand.nextInt(151), 400 + rand.nextInt(151));
			}
			cyborgNPC.setSize(50);
			// Set NPC cyborgs' locations to be around the first base
			//cyborgNPC.setLocation(300 + rand.nextInt(151), 400 + rand.nextInt(151));
			cyborgNPC.setSpeed(30);
			cyborgNPC.setColor(255, 0, 255);		// Set NPC Cyborgs to have PURPLE color
			cyborgNPC.setDmgLevel(0);		// Ensure that the NPC cyborg's damage level is 0
			cyborgNPC.setMaxSpeed(100);		// Ensure that the NPC cyborg's max speed is 100
			gameObjects.add(cyborgNPC);		// Add NPC cyborg's to the gameObjects Collection
			
			// Notify GameWorld's observers
			setChanged();
			notifyObservers();
		}
	}
	
	// Create bases
	public void createBases(int num) {
		// For loop to create num instances of bases
		for (int i = 0; i < num; i++) {
			Base base = new Base(i + 1, this);
			base.setSize(50);
			base.setColor(255, 0, 0);		// Set bases to be RED
			
			// Set each base's fixed location
			if (i == 0) {
				base.setLocation(200, 300);
			} else if (i == 1) {
				base.setLocation(400, 750);
			} else if (i == 2) {
				base.setLocation(850, 250);
			} else {
				base.setLocation(1300, 450);
			}
			
			gameObjects.add(base);		// Add each base to the GameObjectCollection
			numOfBases++;			// Keep track of number of bases in the GameWorld
			
			// Notify GameWorld's observers
			setChanged();
			notifyObservers();
		}
		baseReached = 1;	// Player cyborg should start at base 1
	}
	
	// Create energy stations
	public void createEnergyStations(int num) {
		// For loop to create num instances of Energy Stations
		for (int i = 0; i < num; i++) {
			EnergyStation energyStation = new EnergyStation(this);
			// Set the energy stations' locations to be randomly generated with x and y values between 0 and 1000
			energyStation.setLocation(rand.nextFloat() * width, rand.nextFloat() * height);
			energyStation.setSize(20 + rand.nextInt(21));	// Set size of energy stations to be of a random size between 20 and 40
 			energyStation.setColor(0, 255, 0);				// Set color of energy station to be GREEN
			energyStation.setCapacity(energyStation.getSize());		// Set capacity of energy stations to equal their respective sizes
			gameObjects.add(energyStation);		// Add Energy Station to gameObjects Collection
			
			// Notify GameWorld's observers
			setChanged();
			notifyObservers();
		}	
	}
	
	// Create drones
	public void createDrones(int num) {
		// For loop to create num instances of Drones
		for (int i = 0; i < num; i++) {
			Drone drone = new Drone();
			drone.setSize(50 + rand.nextInt(21));	// Set size of drones to be of a random size b/w 50 and 70
			// Set the drones' locations to be randomly generated with x and y values between 0 and 1000
			drone.setLocation(rand.nextFloat() * width, rand.nextFloat() * height);
			drone.setHeading(rand.nextInt(360));		// Set drone's heading to be randomized between 0 and 359 degrees
			drone.setSpeed(5 + rand.nextInt(6));		// Set drones' speeds to be randomized between 5 and 10
			drone.setColor(0, 255, 255);		// Color the drones YELLOW
			gameObjects.add(drone);		// Add drones to the gamesObjects Collection
			
			// Notify GameWorld's observers
			setChanged();
			notifyObservers();
		}	
	}
	
	// Accelerates the cyborg
	public void accelerate() {
		int accel = 10; 		// Amount to increase speed by when accelerating
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		// Iterate through gameObjects Collection
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			// If current element in iterator is the Player cyborg, accelerate
			if (currElement instanceof PlayerCyborg) {
				
				// Check if Player cyborg's speed is less than its maximum capable speed
				if (((PlayerCyborg)currElement).getSpeed() < ((PlayerCyborg)currElement).getMaxSpeed()) {
				((PlayerCyborg) currElement).setSpeed(((PlayerCyborg)currElement).getSpeed() + accel);
				System.out.println("The cyborg is accelerating.\n");
				
				// Otherwise, indicate to user that the cyborg has reached its maximum capable speed
				} else {
					System.out.println("The cyborg is already going at its maximum speed.\n");
				}
			}
		}
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	// Applies brakes to the cyborg
	public void brake() {
		int brakeSpeed = 5;			// Amount to decrease speed by when braking
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		// Iterate through gameObjects Collection
		while(iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			// If currentElement in iterator is the Player cyborg, apply the brakes
			if (currElement instanceof PlayerCyborg) {
				
				// Check if Player cyborg's speed is greater than 0
				if (((PlayerCyborg)currElement).getSpeed() > 0) {
					((PlayerCyborg)currElement).setSpeed(((PlayerCyborg)currElement).getSpeed() - brakeSpeed);
					System.out.println("The cyborg is braking.\n");
				
				// If the player cyborg's speed is 0, tell the user the cyborg has stopped
				} else {
					System.out.println("The cyborg has come to a stop.\n");
				}
			}
		}
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	// Steers the cyborg left
	public void steerLeft() {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		// Iterate through gameObjects Collection
		while(iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof PlayerCyborg) {
				((PlayerCyborg)currElement).steerLeft();
			}
		}
		
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	// Steers the cyborg right
	public void steerRight() {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		// Iterate through gameObjects Collection
		while(iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof PlayerCyborg) {
				((PlayerCyborg)currElement).steerRight();
			}
		}
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	// Notifies game world of a collision with another cyborg
	public void cyborgCollision(GameObject gameObj) {
		int playerCyborgDmg = 10;		// Player cyborg sustains 10 damage
		int nonPlayerCyborgDmg = 5;		// NPC cyborg sustains only 5 damage to allow them to play longer (Consider this as them being armored)
		
		IIterator<GameObject> iterator = gameObjects.getIterator();
		//ArrayList<NonPlayerCyborg> allNPCCyborg = new ArrayList<NonPlayerCyborg>();		// Create an array list to to hold all the NPC cyborgs
		
		//int onlyOneCollision = 1;		// Ensure only one cyborg collision happens at an instance
		
		if (sound == true) {
			collisionSound.play();
		}
		
		// Iterate through gameObjects Collection
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof PlayerCyborg) {
				if (currElement == gameObj) {
					System.out.println("Your cyborg has collided with another cyborg.\n");
					PlayerCyborg playerElement = PlayerCyborg.getCyborg(); 		// Retrieve the player cyborg
					// Damage the player cyborg during collision
					playerElement.setDmgLevel(playerElement.getDmgLevel() + playerCyborgDmg);
					damageLevel = playerElement.getDmgLevel();
					playerElement.setMaxSpeed(playerElement.getMaxSpeed() - playerCyborgDmg);		// Decrease cyborg's maximum capable speed after taking damage
					playerElement.setColor(0, 0, 255 - playerElement.getDmgLevel());		// Fade the color of the cyborg each time it takes damage
				
					// Check if cyborg lost a life
					if (playerElement.getDmgLevel() >= 100 || playerElement.getMaxSpeed() == 0) {
						livesRemaining -= 1;
						
						if (sound == true) {
							deathSound.play();
						}
						
						System.out.println("Your cyborg has lost a life. Now reinitializing the game world. You have " + livesRemaining + " remaining.\n");
						// After decreasing a life, remove all game objects from the GameWorld, reinitialize with init(), and update the damage level for the ScoreView to 0
						iterator.removeAll();
						init();
						playerElement.setLastBase(1);
						damageLevel = 0;
						
						// Print GAME OVER and exit the game if no lives are left
						if (livesRemaining == 0) {
							System.out.println("You have no lives remaining. GAME OVER.\n");
							Display.getInstance().exitApplication();
						}
					}
				}
				
			} else if (currElement instanceof NonPlayerCyborg) {
				if (currElement == gameObj) {
					// Damage the NPC cyborg during collision
					//allNPCCyborg.add((NonPlayerCyborg)currElement);		// Add each NPC cyborg to the array list				
					((NonPlayerCyborg)currElement).setDmgLevel(((NonPlayerCyborg)currElement).getDmgLevel() + nonPlayerCyborgDmg);		// Increase NPC Cyborg's damage sustained
					((NonPlayerCyborg)currElement).setMaxSpeed(((NonPlayerCyborg)currElement).getMaxSpeed() - nonPlayerCyborgDmg);		// Decrease NPC cyborg's maximum capable speed after taking damage
					((NonPlayerCyborg)currElement).setColor(255 - ((NonPlayerCyborg)currElement).getDmgLevel(), 0, 255 - ((NonPlayerCyborg)currElement).getDmgLevel());  	// Fade the color of the NPC cyborg each time it takes damage
				}
			}
		}
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	// Notifies game world of reaching a base
	public void baseCollision(int baseNum, GameObject gameObj) {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
		
			// Base Collision for PlayerCyborg
			if (currElement instanceof PlayerCyborg) {
				if (currElement == gameObj) {
					// Check if user's base input exists in the game world
					if (baseNum > numOfBases) {
						System.out.println("Base " + baseNum + " doesn't exist. There are only a total of " + numOfBases + " bases.\n");
					
					// Check if user's base input is equal to the next base the cyborg must reach
					} else if (baseNum == ((PlayerCyborg)currElement).getLastBase() + 1) {
						((PlayerCyborg)currElement).setLastBase(baseNum);
						System.out.println("Your cyborg has reached base " + ((PlayerCyborg)currElement).getLastBase() + ".\n");
						baseReached = baseNum;
						
						// Check if the final base has been reached
						if (baseNum == 4) {
							System.out.println("You have reached the final base. Congratulations! YOU WIN! Total Time: " + clockTimer  + "\n");
							Display.getInstance().exitApplication(); 	// Exit the application and prompt to the player that he/she has won 
						} else {
							System.out.println("Now proceed to base " + (((PlayerCyborg)currElement).getLastBase() + 1) + ".\n");
						}
					
					// Check if user's base input is a base that the cyborg is currently on or has already reached
					} else if (baseNum <= ((PlayerCyborg)currElement).getLastBase()) {
						System.out.println("Your cyborg has already visited base " + baseNum + ".\n");
					
					// Check if user is having cyborg reach the bases in sequential order
					} else if (baseNum > ((PlayerCyborg)currElement).getLastBase() + 1) {
						System.out.println("Your cyborg is currently at base " + ((PlayerCyborg)currElement).getLastBase() + ". "
								+ "Please reach base " + (((PlayerCyborg)currElement).getLastBase() + 1) + " before visiting base " + baseNum + ".\n");
					}
				}
			
			// Base Collision for NPC Cyborg
			} else if (currElement instanceof NonPlayerCyborg) {
				if (currElement == gameObj) {
					// Check if user's base input exists in the game world
					if (baseNum > numOfBases) {
						System.out.println("Base " + baseNum + " doesn't exist. There are only a total of " + numOfBases + " bases.\n");
					
					// Check if NPC Cyborg's base reached is equal to the next base the cyborg must reach
					} else if (baseNum == ((NonPlayerCyborg)currElement).getLastBase() + 1) {
						((NonPlayerCyborg)currElement).setLastBase(baseNum);
						System.out.println("An NPC cyborg has reached base " + ((NonPlayerCyborg)currElement).getLastBase() + ".\n");
						
						// Check if the final base has been reached
						if (baseNum == 4) {
							System.out.println("An NPC has reached the final base. YOU LOSE.\n");
							Display.getInstance().exitApplication(); 	// Exit the application and prompt to the player that the NPC has won 
						} else {
							System.out.println("Now proceed to base " + (((NonPlayerCyborg)currElement).getLastBase() + 1) + ".\n");
						}
					
					// Check if it is a base that the NPC cyborg is currently on or has already reached
					} else if (baseNum <= ((NonPlayerCyborg)currElement).getLastBase()) {
						System.out.println("The NPC cyborg has already visited base " + baseNum + ".\n");
					
					// Check if NPC cyborg is reaching the bases in sequential order
					} else if (baseNum > ((NonPlayerCyborg)currElement).getLastBase() + 1) {
						System.out.println("The NPC cyborg is currently at base " + ((NonPlayerCyborg)currElement).getLastBase() + ". "
								+ "Please reach base " + (((NonPlayerCyborg)currElement).getLastBase() + 1) + " before visiting base " + baseNum + ".\n");
					}
				}
			}
		}
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();	
	}
	
	// Notifies game world of reaching an energy station
	public void energyCollision(GameObject gameObj) {
		System.out.println("Your cyborg has reached an energy station.\n");
		
		if (sound == true) {
			energySound.play();
		}
		
		IIterator<GameObject> iterator = gameObjects.getIterator();
		PlayerCyborg playerElement = PlayerCyborg.getCyborg(); 		// Retrieve player cyborg
		int onlyOneEnergyStation = 1;		// Set condition for if statement to ensure only one energy station is absorbed by player cyborg
		
		// Iterate through gameObjects Collection
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof EnergyStation) {
				if (currElement == gameObj) {
					EnergyStation energyStationElement = (EnergyStation)currElement;
					
					// Ensures that only one energy station is used
					if (onlyOneEnergyStation > 0) {
						playerElement.setEnergyLevel(playerElement.getEnergyLevel() + energyStationElement.getCapacity());		// Add energy station's capacity to player cyborg
						energyLevel = playerElement.getEnergyLevel();
						energyStationElement.setCapacity(0);			// Set used energy station capacity to 0
						energyStationElement.setColor(0, 0, 0);
						iterator.remove();			// Remove used energy station
						createEnergyStations(1);	// Create a new energy station and add to game world
						onlyOneEnergyStation--;		// Decrement to end if statement
					}
				}
			}
		}
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();		
	}
	
	// Notifies game world of collision with a drone
	public void droneCollision(GameObject gameObj) {
		int playerDroneDmg = 10;					// Damage value for Player-Drone collision
		int npcDroneDmg = 5;						// Damage value for NPC-Drone collision
		
		if (sound == true) {
			collisionSound.play();
		}
		
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof PlayerCyborg) {
				if (currElement == gameObj) {
					System.out.println("Your cyborg has collided with a drone.\n");
					((PlayerCyborg)currElement).setDmgLevel(((PlayerCyborg)currElement).getDmgLevel() + playerDroneDmg);
					damageLevel = ((PlayerCyborg)currElement).getDmgLevel();
					((PlayerCyborg)currElement).setMaxSpeed(((PlayerCyborg)currElement).getMaxSpeed() - playerDroneDmg);
					((PlayerCyborg)currElement).setColor(0, 0, 255 - ((PlayerCyborg)currElement).getDmgLevel());
					
					// Check if player cyborg's speed is greater than it's max speed. If true, limit its current speed to the max speed
					if (((PlayerCyborg)currElement).getSpeed() > ((PlayerCyborg)currElement).getMaxSpeed()) {
						int maxSpeed = ((PlayerCyborg)currElement).getMaxSpeed();
						((PlayerCyborg)currElement).setSpeed(maxSpeed);
					}			
					
					if (((PlayerCyborg)currElement).getDmgLevel() >= 100 || ((PlayerCyborg)currElement).getMaxSpeed() == 0) {
						livesRemaining -= 1;
						
						if (sound == true) {
							deathSound.play();
						}
						
						System.out.println("Your cyborg has lost a life. Now reinitializing the world. You have " + livesRemaining + " remaining.\n");
						
						// After decreasing a life, remove all game objects from the GameWorld, reinitialize with init(), and update the damage level for the ScoreView to 0
						iterator.removeAll();
						init();
						((PlayerCyborg)currElement).setLastBase(1);
						damageLevel = 0;
						
						// Print GAME OVER and exit the game if no lives are left
						if (livesRemaining == 0) {
							System.out.println("You have ran out of lives. GAME OVER.\n");
							Display.getInstance().exitApplication();		// Exit application
						}
					}
				}
				
			} else if (currElement instanceof NonPlayerCyborg) {
				if (currElement == gameObj) {
					((NonPlayerCyborg)currElement).setDmgLevel(((NonPlayerCyborg)currElement).getDmgLevel() + npcDroneDmg);
					((NonPlayerCyborg)currElement).setMaxSpeed(((NonPlayerCyborg)currElement).getMaxSpeed() - npcDroneDmg);
					((NonPlayerCyborg)currElement).setColor(255 - ((NonPlayerCyborg)currElement).getDmgLevel(), 0, 255 - ((NonPlayerCyborg)currElement).getDmgLevel());
				}
			}
		}
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	// Advance the clock timer
	public void clockTick(int tickTime) {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		// Iterate through the gameObjects Collection
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof PlayerCyborg) {
				
				if(((PlayerCyborg)currElement).getEnergyLevel() != 0 && ((PlayerCyborg)currElement).getDmgLevel() < 100 && ((PlayerCyborg)currElement).getSpeed() > 0) {
					
					// Check if the current heading is facing straight ahead and if the cyborg's steering direction is left
					if(((PlayerCyborg)currElement).getHeading() == 0 && ((PlayerCyborg)currElement).getSteeringDirection() < 0) {
						((PlayerCyborg)currElement).setHeading(360 + ((PlayerCyborg)currElement).getSteeringDirection());   // Add the negative steering direction to set the heading to the left
				
					// Check if heading is facing straight ahead and if cyborg's steering direction is to the right
					} else if (((PlayerCyborg)currElement).getHeading() > 360) {
						((PlayerCyborg)currElement).setHeading(((PlayerCyborg)currElement).getHeading() - 360);
						
					// Check if cyborg's heading is towards the left and adjust heading appropriately
					} else if (((PlayerCyborg)currElement).getHeading() < 0) {
						((PlayerCyborg)currElement).setHeading(((PlayerCyborg)currElement).getHeading() + 360);
					} else {
						((PlayerCyborg)currElement).setHeading(((PlayerCyborg)currElement).getHeading() + ((PlayerCyborg)currElement).getSteeringDirection());
					}
				
					// Make the energy level decrease at a reasonable rate in comparison to the UITimer's time
					if (clockTimer % 15 == 0)
						// Decrease energy level
						((PlayerCyborg)currElement).setEnergyLevel(((PlayerCyborg)currElement).getEnergyLevel() - ((PlayerCyborg)currElement).getEConsumRate());
		
					// Check if player cyborg's energy level is greater than 100. If true, update the ScoreView accordingly. 
					if (((PlayerCyborg)currElement).getEnergyLevel() > 0) {
						energyLevel = ((PlayerCyborg)currElement).getEnergyLevel();
					// If the player cyborg runs out of energy, decrease life count, reinitialize game world and set energy back to 100
					} else {
						livesRemaining -= 1;
						iterator.removeAll();		// Removes game objects from GameObjectCollection
						init();				// Reinitialize game world and add game objects back to GameObjectCollection
						((PlayerCyborg)currElement).setLastBase(1);
						((PlayerCyborg)currElement).setEnergyLevel(100);
						energyLevel = ((PlayerCyborg)currElement).getEnergyLevel();
						
						// Print GAME OVER and exit the game if no lives are left
						if (livesRemaining == 0) {
							System.out.println("You have ran out of lives. GAME OVER.\n");
							Display.getInstance().exitApplication();
						}
					}
				
					/*
					// Check if the cyborg no longer has energy, has sustained the maximum amount of damage, or it's max speed has been reduced to zero
					if (((PlayerCyborg)currElement).getDmgLevel() >= 100 || ((PlayerCyborg)currElement).getMaxSpeed() == 0) {
						livesRemaining -= 1;
						System.out.println("Your cyborg has lost a life. Now reinitializing the world. You have " + livesRemaining + " remaining.\n");
						init();
						
						// Print GAME OVER and exit the game if no lives are left
						if (livesRemaining == 0) {
							System.out.println("You have ran out of lives. GAME OVER.\n");
							Display.getInstance().exitApplication();			// Exit application
						}
					}*/
				}
			}
			
			// Each tick will change the drone's heading to the right or left
			if (currElement instanceof Drone) {
				if (((Drone)currElement).getHeading() >= 0 && ((Drone)currElement).getHeading() < 360) {
					((Drone)currElement).setHeading(((Drone)currElement).getHeading() + (rand.nextInt(20) - 10));
				} else {
					((Drone)currElement).setHeading(((Drone)currElement).getHeading() + (rand.nextInt(20) - 10) - 360);  // Reset the drone's heading if it goes past 360
				}
			} // end if for Drone
			
			// Move the objects
			if (currElement instanceof Movable) {
				((Movable)currElement).move(tickTime);
			}
			
			// Invoke each NPC cyborg's strategy
			if (currElement instanceof NonPlayerCyborg) {
				((NonPlayerCyborg)currElement).invokeStrategy();
			}
				
		}
		clockTimer++;
		checkCollisions();
		
		// Notify GameWorld's Observers
		setChanged();
		notifyObservers();	
	}
	
	// Display the current game's state values
	public void display() {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof PlayerCyborg) {
				System.out.println("****** CURRENT GAME STATS ******");
				System.out.println("Number of lives remaining: " + livesRemaining);
				System.out.println("Current elapsed time : " + clockTimer);
				System.out.println("Highest base number reached so far: " + ((PlayerCyborg)currElement).getLastBase());
				System.out.println("Cyborg's current energy level: " + ((PlayerCyborg)currElement).getEnergyLevel());
				System.out.println("Cyborg's current damage level: " + ((PlayerCyborg)currElement).getDmgLevel());
				System.out.println();
			}
		}
	}
	
	// Display the map of the game world
	public void map() {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
		
			if (currElement instanceof Base) {
				System.out.println((Base)currElement);
			} else if (currElement instanceof PlayerCyborg) {
				System.out.println((PlayerCyborg)currElement);
			} else if (currElement instanceof Drone) {
				System.out.println((Drone)currElement);
			} else if (currElement instanceof EnergyStation){
				System.out.println((EnergyStation)currElement);
			} else {
				System.out.println((NonPlayerCyborg)currElement);
			}
		}
		System.out.println();
	}
	
	// Prompt user if he/she would like to exit game
	public void exit() {
		System.out.println("Would you like to exit the game? (y/n): ");
		pressedX = true;
	}
	
	// Confirm if user wants to exit
	public void confirmExit() {
		if (pressedX)
			System.exit(0);
		else 
			System.out.println("Invalid option. You can only press y after you've pressed x.");
	}
	
	// Confirms user doesn't want to exit
	public void unconfirmExit() {
		if(pressedX) 
			pressedX = false;
		else
			System.out.println("Invalid option. You can only press n after you've pressed x.");
	}
	
	// Prompt user to enter a valid character
	public void invalidInput() {
		System.out.println("You have entered an invalid character. Please try again.");
	}
	
	// Return the timer on the clock
	public int getClockTimer() {
		return clockTimer;
	}
	
	// Return the number of lives remaining for the player
	public int getLivesRemaining() {
		return livesRemaining;
	}
	
	// Return the last base reached by the player cyborg
	public int getLastBaseReached() {
		return baseReached; 
	}
	
	// Return the player cyborg's energy level
	public int getEnergyLevel() {
		return energyLevel;
	}
	
	// Return the player cyborg's damage level
	public int getDamageLevel() {
		return damageLevel;
	}
	
	// Return the sound status
	public boolean getSound() {
		return sound;
	}
	
	// Set the sound to ON or OFF
	public void setSound() {
		this.sound = !sound;
		
		// If sound is ON, play background music, else pause it
		if (sound == true && paused == false) {
			bgSound.play();
		} else {
			bgSound.pause();
		}
		
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	// Set the height of the GameWorld
	public void setHeight(int value) {
		GameWorld.height = value;
	}
	
	// Get the height of the GameWorld
	public static int getHeight() {
		return GameWorld.height;
	}
	
	// Set the width of the GameWorld
	public void setWidth(int value) {
		GameWorld.width = value;
	}
	
	// Get the width of the GameWorld
	public static int getWidth() {
		return GameWorld.width;
	}
	
	// Strategy method for a NPC to move to the next base. Currently only moving to base 2 works because I have yet to implement the logic to keep track of the bases reached by NPC's
	public void headToNextBase(NonPlayerCyborg npc) {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		int distanceX = 0, distanceY = 0;
		int idealHeading = 0;
		
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof Base) {
				Base tmpBase = (Base)currElement;
				
				// If the NPC cyborg's last based reached is 1, grab the horizontal (X) and vertical (Y) distances of base 2
				if (npc.getLastBase() == 1) {
					if (tmpBase.getSequenceNum() == 2) {
						distanceX = (int) (tmpBase.getXLocation() - npc.getXLocation());
						distanceY = (int) (tmpBase.getYLocation() - npc.getYLocation());
						break;
					} 
				// If the NPC cyborg's last based reached is 2, grab the horizontal (X) and vertical (Y) distances of base 3
				} else if (npc.getLastBase() == 2) {
					if (tmpBase.getSequenceNum() == 3) {
						distanceX = (int) (tmpBase.getXLocation() - npc.getXLocation());
						distanceY = (int) (tmpBase.getYLocation() - npc.getYLocation());
						break;
					}
				// If the NPC cyborg's last based reached is 3, grab the horizontal (X) and vertical (Y) distances of base 4
				} else if (npc.getLastBase() == 3) {
					if (tmpBase.getSequenceNum() == 4) {
						distanceX = (int) (tmpBase.getXLocation() - npc.getXLocation());
						distanceY = (int) (tmpBase.getYLocation() - npc.getYLocation());
						break;
					}
				// If the NPC cyborg's last base reached is 4, end the game and print GAME OVER
				} else {
					System.out.println("The NPC has reached the final base before you. GAME OVER.\n");
					Display.getInstance().exitApplication(); 	// Exit the application
				}
			}
		}
		
		// Calculate the idealHeading = arctan(x, y)
		idealHeading = (int) Math.toDegrees(MathUtil.atan2(distanceX, distanceY));
		// Set the NPC Cyborg's heading to the ideal heading
		npc.setHeading(idealHeading);
		// Set the steering direction of the NPC Cyborg relative to the ideal heading (90 - idealHeading)
		npc.setSteeringDirection(90 - idealHeading);
	}
	
	// Strategy method for a NPC to move towards player cyborg's location to attack the player
	public void attackPlayer(NonPlayerCyborg npc) {
		PlayerCyborg player = PlayerCyborg.getCyborg();		// Retrieve the player cyborg
		int distanceX = 0, distanceY = 0;
		int idealHeading = 0;
		
		
		distanceX = (int) (player.getXLocation() - npc.getXLocation());		// Calculate the horizontal distance between the player cyborg and the NPC cyborg
		distanceY = (int) (player.getYLocation() - npc.getYLocation());		// Calculate the horizontal distance between the player cyborg and the NPC cyborg
		idealHeading = (int) Math.toDegrees(MathUtil.atan2(distanceX, distanceY));		// Calculate the ideal heading required for the NPC cyborg to reach the player cyborg
		npc.setHeading(idealHeading);		// Set the NPC cybor's heading to the ideal heading
		npc.setSteeringDirection(90 - idealHeading);		// Set the NPC cyborg's steering direction relative to the ideal heading (90 - idealHeading)
		
	}
	
	// Method to switch strategies when invoking the switch strategies command
	public void switchStrategies() {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		NonPlayerCyborg npcCyborg;
		
		// Iterate through the gameObjects Collection
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof NonPlayerCyborg) {
				npcCyborg = (NonPlayerCyborg)currElement;
				
				// Check if the current NPC cyborg has the base strategy. If so, change it's strategy to the attack player strategy
				if (npcCyborg.getStrategy() instanceof BaseStrategy) {
					npcCyborg.setStrategy(new AttackStrategy(npcCyborg, this));
				// If the NPC cyborg has the attack player strategy, change it to the base strategy
				} else {
					npcCyborg.setStrategy(new BaseStrategy(npcCyborg, this));
				}
				
				//npcCyborg.setLastBase(npcCyborg.getLastBase() + 1);		// Ensure that NPC cyborg gets the next last based reached value (in order to move beyond base 2)
			}
		}
		// Notify GameWorld's observers
		setChanged();
		notifyObservers();
	}
	
	//Retrieve the iterator for the game objects
	public IIterator<GameObject> getIterator() {
		return gameObjects.getIterator();
	}
	
	// Method to check collisions of GameObjects
	public void checkCollisions() {
		IIterator<GameObject> iterator = gameObjects.getIterator();
		
		while (iterator.hasNext()) {
			ICollider currObj = (ICollider)iterator.getNext();  // Get a collidable game object
			
			// Check if this object collides with any other object
			IIterator<GameObject> iterator2 = gameObjects.getIterator();
			while (iterator2.hasNext()) {
				ICollider otherObj = (ICollider)iterator2.getNext();  		// Get another collidable object
				
				// Check if there is a collision
				if (otherObj != currObj) {
					if (currObj.collidesWith((GameObject)otherObj)) {
						currObj.handleCollision((GameObject)otherObj, this);
					}
				}
			}
		}
	}
	
	// Method to pause game objects
	public void pause() {
		paused = true;
		
		// If game is paused and sound is ON, pause the BG music
		if (paused == true && sound == true) 
			bgSound.pause();
		
		setChanged();
		notifyObservers();
	}
	
	// Method to pause game objects
	public void unpause() {
		paused = false;
		
		// If game is in play mode and sound is ON, play the BG music
		if (paused == false && sound == true)
			bgSound.play();

		setChanged();
		notifyObservers();
	}
	
	// Method to see if the GameWorld is paused
	public boolean getPaused() {
		return this.paused;
	}
		
	// Method to see if the Position button is pressed
	public void pressedPositionButton() {
		this.pressedPosition = !pressedPosition;
		setChanged();
		notifyObservers();
	}
	
	// Method to get the boolean value if the Position button is pressed or not
	public boolean getPositionPressed() {
		return this.pressedPosition;
	}
	
	// Method to create sounds for the game (DOES NOT WORK -- Results in NullPointer errors for me)
	public void createSounds() {
		Sound collisionSound = new Sound("crash.wav");
		Sound energySound = new Sound("energy.wav");
		Sound deathSound = new Sound("explosion.wav");
		BGSound bgSound = new BGSound("funbg.wav");
	}
}
