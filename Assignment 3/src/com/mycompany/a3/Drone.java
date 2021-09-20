/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public class Drone extends Movable {
	private CollisionHandler collisionHandler = new CollisionHandler(this);
	
	// Constructor for drones
	public Drone() {
			
	}
		
	// Move the drone
	public void move(int tickTime) {
		float deltaX = (float)Math.cos(Math.toRadians(90.0 - this.getHeading())) * this.getSpeed() * tickTime / 100;
		float deltaY = (float)Math.sin(Math.toRadians(90.0 - this.getHeading())) * this.getSpeed() * tickTime / 100;
		
		// Ensure that the drones stone within the GameWorld's boundaries
		if (this.getXLocation() + deltaX < GameWorld.getWidth() && this.getXLocation() + deltaX > 0 && this.getYLocation() + deltaY < GameWorld.getHeight() && this.getYLocation() + deltaY > 0)
			this.setLocation(this.getXLocation() + deltaX, this.getYLocation() + deltaY);
		else
			this.setHeading(this.getHeading() + 180);		// When drone hit's boundaries of MapView, bounce off the walls
	}
		
	public String toString() {
		return "Drone: " + super.toString() + " heading=" + this.getHeading() + " speed=" + this.getSpeed() + " size=" + this.getSize();
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int droneXLocation = (int)super.getXLocation();				// Retrieve the Drone's X-coordinate
	 	int droneYLocation = (int)super.getYLocation(); 				// Retrieve the Drone's Y-coordinate
	 		
	 	// Create points of each corner of the isosceles triangle
	 	Point topCorner = new Point(pCmpRelPrnt.getX() + droneXLocation, pCmpRelPrnt.getY() + droneYLocation + (this.getSize() / 2));
	 	Point botLeftCorner = new Point(pCmpRelPrnt.getX() + droneXLocation - (this.getSize() / 2), pCmpRelPrnt.getY() + droneYLocation - (this.getSize() / 2));
	 	Point botRightCorner = new Point(pCmpRelPrnt.getX() + droneXLocation + (this.getSize() / 2), pCmpRelPrnt.getY() + droneYLocation - (this.getSize() /2));
	 		
	 	// Store the X and Y coordinates of the triangle's corners into arrays
	 	int[] xPoints = {(int)topCorner.getX(), (int)botLeftCorner.getX(), (int)botRightCorner.getX()};
	 	int[] yPoints = {(int)topCorner.getY(), (int)botLeftCorner.getY(), (int)botRightCorner.getY()};
	 		
	 	g.setColor(super.getColor());				// Set color of the Drone's shape to be the color of the Base
	 	g.drawPolygon(xPoints, yPoints, 3);			// Draw the Drone's isosceles triangle
			
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = collisionHandler.oneCollision(otherObject);
		return result;
	}

	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		
		// Check if Drone collides with PlayerCyborg or NPC Cyborg
		if (otherObject instanceof PlayerCyborg) { 
			gw.droneCollision(this); 		// Call droneCollision method in GameWorld
		} else if (otherObject instanceof NonPlayerCyborg) {
			gw.droneCollision(this);
		}
	}

}
