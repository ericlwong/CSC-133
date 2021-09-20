/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public class PlayerCyborg extends Cyborg {
	private static PlayerCyborg playerCyborg;
	private CollisionHandler collisionHandler = new CollisionHandler(this);
	
	private PlayerCyborg() { }
	
	
	// Create a single instance of a player cyborg (Singleton Pattern)
	public static PlayerCyborg getCyborg() {
		if (playerCyborg == null) 
			playerCyborg = new PlayerCyborg();
		return playerCyborg;
	}
	
	@Override
	public void steerLeft() {
		if (playerCyborg.getSteeringDirection() > -40) {
			playerCyborg.setSteeringDirection(playerCyborg.getSteeringDirection() - 5);
			System.out.println("Steering the cyborg left 5 degrees.\n");
		} else {
			System.out.println("The left steering direction of the cyborg has reached its limit.\n");
		}
		
	}

	@Override
	public void steerRight() {
		if (playerCyborg.getSteeringDirection() < 40) {
			playerCyborg.setSteeringDirection(playerCyborg.getSteeringDirection() + 5);
			System.out.println("Steering the cyborg right 5 degrees.\n");
		} else {
			System.out.println("The right steering direction of the cyborg has reached its limit.\n");
		}
		
		
	}
	
	public String toString() {
		return "Cyborg: " + super.toString() + " heading=" + this.getHeading() + " speed=" + this.getSpeed() + " size=" + this.getSize() + " maxSpeed=" + this.getMaxSpeed() + " steeringDirection=" + this.getSteeringDirection()
        + " energyLevel=" + this.getEnergyLevel() + " damageLevel=" + this.getDmgLevel();
	}


	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int playerXLocation = (int)super.getXLocation();			// Retrieve the player cyborg's X-coordinate
		int playerYLocation = (int)super.getYLocation();			// Retrieve the player cyborg's Y-coordinate
		
		g.setColor(super.getColor());		// Set the color of the PlayerCyborg shape to be the color of the PlayerCyborg
		g.fillRect((int)pCmpRelPrnt.getX() + playerXLocation - (this.getSize() / 2), (int)pCmpRelPrnt.getY() + playerYLocation - (this.getSize() / 2), this.getSize(), this.getSize());		// Draw the SQUARE shape of the PlayerCyborg
	}


	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = collisionHandler.oneCollision(otherObject);
		return result;
	}


	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		
		// Check if the other GameObject is a Base
		if (otherObject instanceof Base) {
			int baseNum = ((Base) otherObject).getSequenceNum();
			gw.baseCollision(baseNum, this);	// Call baseCollision method in GameWorld
		
		// If not a base, check if it is an EnergyStation
		} else if (otherObject instanceof EnergyStation) {
			gw.energyCollision(otherObject);	// Call energyCollision method in GameWorld
			
		// If not an EnergyStation, check if it is a NonPlayerCyborg
		} else if (otherObject instanceof NonPlayerCyborg) {
			gw.cyborgCollision(this);	// Call cyborgCollision method in GameWorld
		
		// If none of the above, check if it is a Drone
		} else {
			gw.droneCollision(this);	// Call droneCollision method in GameWorld
		}
	}
}
