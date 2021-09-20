/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

public abstract class Cyborg extends Movable implements ISteerable {
	private int steeringDirection;
	private int maximumSpeed;
	private int energyLevel;
	private int energyConsumptionRate;
	private int damageLevel;
	private int lastBaseReached;
	
	
	// Constructor for cyborgs
	public Cyborg() {
		this.steeringDirection = 0;
		this.maximumSpeed = 100;
		this.energyLevel = 100;
		this.energyConsumptionRate = 1;
		this.damageLevel = 0;
		this.lastBaseReached = 1;
	}
	
	// Move the cyborg
	public void move(int tickTime) {
		float deltaX = (float)Math.cos(Math.toRadians(90.0 - this.getHeading())) * this.getSpeed() * tickTime / 500;
		float deltaY = (float)Math.sin(Math.toRadians(90.0 - this.getHeading())) * this.getSpeed() * tickTime / 500;
		
		// Make sure Cyborg's can only move within the GameWorld's boundaries
		if (this.getXLocation() + deltaX < GameWorld.getWidth() && this.getXLocation() + deltaX > 0 && this.getYLocation() + deltaY < GameWorld.getHeight() && this.getYLocation() + deltaY > 0)
			this.setLocation(this.getXLocation() + deltaX, this.getYLocation() + deltaY);
	}
	
	// Set cyborg's steering direction
	public void setSteeringDirection(int val) {
		this.steeringDirection = val;
	}
	
	// Get cyborg's steering direction
	public int getSteeringDirection() {
		return this.steeringDirection;
	}
	
	// Set cyborg's max speed
	public void setMaxSpeed(int val) {
		this.maximumSpeed = val;
	}
	
	// Get cyborg's max speed
	public int getMaxSpeed() {
		return this.maximumSpeed;
	}
	
	// Set cyborg's energy level
	public void setEnergyLevel(int val) {
		this.energyLevel = val;
	}
	
	// Get cyborg's energy level
	public int getEnergyLevel() {
		return this.energyLevel;
	}
	
	// Set cyborg's energy consumption rate
	public void setEConsumRate(int val) {
		this.energyConsumptionRate = val;
	}
	
	// Get cyborg's energy consumption rate
	public int getEConsumRate() {
		return this.energyConsumptionRate;
	}
	
	// Set the damage level
	public void setDmgLevel(int val) {
		this.damageLevel = val;
	}
	
	// Get the damage level
	public int getDmgLevel() {
		return this.damageLevel;
	}
	
	// Set the cyborg's last base reached
	public void setLastBase(int val) {
		this.lastBaseReached = val;
	}
	
	// Get the cyborg's last base reached
	public int getLastBase() {
		return this.lastBaseReached;
	}
	
	/*
	public String toString() {
		return super.toString() + " heading=" + this.getHeading() + " speed=" + this.getSpeed() + " size=" + this.getSize() + " maxSpeed=" + this.getMaxSpeed() + " steeringDirection=" + this.getSteeringDirection()
                + " energyLevel=" + this.getEnergyLevel() + " damageLevel=" + this.getDmgLevel();
	}*/
}
