/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

public abstract class Movable extends GameObject {
	
	private int heading;
	private int speed;
	
	// Constructor for movable game objects
	public Movable () {
		this.heading = 0;
		this.speed = 0;
	}
	
	public abstract void move(int tickTime);
		
	
	// Set movable game object's heading
	public void setHeading(int num) {
		this.heading = num;
	}
	
	// Get movable game object's heading
	public int getHeading() {
		return this.heading;
	}
	
	// Set movable game object's speed
	public void setSpeed(int speedVal) {
		this.speed = speedVal;
	}
	
	// Get movable game object's speed
	public int getSpeed() {
		return this.speed;
	}
}
