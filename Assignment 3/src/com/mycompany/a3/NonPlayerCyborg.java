/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public class NonPlayerCyborg extends Cyborg {
	private IStrategy currStrategy;
	private CollisionHandler collisionHandler = new CollisionHandler(this);
	
	// Constructor
	public NonPlayerCyborg() {
		this.currStrategy = null;
	}
	
	// Set the current strategy 
	public void setStrategy(IStrategy s) {
		currStrategy = s;
	}

	// Invoke the current strategy
	public void invokeStrategy() {
		currStrategy.apply();	
	}
	
	// Retrieve the current strategy
	public IStrategy getStrategy() {
		return currStrategy;
	}
	
	@Override
	public void steerLeft() { }

	@Override
	public void steerRight() { }
	
	public String toString() {
		return "NPC Cyborg: " + super.toString() + " heading=" + this.getHeading() + " speed=" + this.getSpeed() + " size=" + this.getSize() + " maxSpeed=" + this.getMaxSpeed() + " steeringDirection=" + this.getSteeringDirection()
        + " energyLevel=" + this.getEnergyLevel() + " damageLevel=" + this.getDmgLevel() + " strategy=" + this.getStrategy();
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int npcXLocation = (int)super.getXLocation();			// Retrieve the NPC cyborg's X-coordinate
		int npcYLocation = (int)super.getYLocation();			// Retrieve the NPC cyborg's Y-coordinate
		
		g.setColor(super.getColor());		// Set the color of the PlayerCyborg shape to be the color of the PlayerCyborg
		g.drawRect((int)pCmpRelPrnt.getX() + npcXLocation - (this.getSize() / 2), (int)pCmpRelPrnt.getY() + npcYLocation - (this.getSize() / 2), this.getSize(), this.getSize());		// Draw the SQUARE shape of the PlayerCyborg
		
		
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
		
		} else if (otherObject instanceof PlayerCyborg) {
			gw.cyborgCollision(this);
			
		// If none of the above, check if it is a Drone
		} else {
			gw.droneCollision(this);	// Call droneCollision method in GameWorld
		}
	}
}
