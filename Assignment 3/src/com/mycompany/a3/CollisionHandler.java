/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import java.util.Vector;

public class CollisionHandler {
	private Vector<GameObject> alreadyCollided = new Vector<GameObject>();
	private GameObject gameObj;
	
	// Constructor
	public CollisionHandler(GameObject gameObj) {
		this.gameObj = gameObj;
	}
	
	// Method to handle one instance of a collision 
	public boolean oneCollision(GameObject otherObj) {
		boolean result = false;
			
		// Find the centers of the current game object and the other GameObject
		int thisCenterX = (int)(gameObj.getXLocation() + (gameObj.getSize() / 2));
		int thisCenterY = (int)(gameObj.getYLocation() + (gameObj.getSize() / 2));
		int otherCenterX = (int)(otherObj.getXLocation() + (otherObj.getSize() / 2));
		int otherCenterY = (int)(otherObj.getYLocation() + (otherObj.getSize() / 2));
			
		// Find the distance between the centers of the current game object and the other GameObject
		int dx = thisCenterX - otherCenterX;
		int dy = thisCenterY - otherCenterY;
		int distBetweenCentersSqr = (dx * dx + dy * dy);
			
		// Find the square of sum of radii
		int thisRadius = gameObj.getSize() / 2;
		int otherRadius = otherObj.getSize() / 2;
		int radiiSqr = (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
			
		// Check if the two GameObjects collide
		if (distBetweenCentersSqr <= radiiSqr) {
				
			// If the alreadyCollided Vector does contain the other GameObject
			if (alreadyCollided.contains(otherObj)) {
				result = false;			// Set the collision boolean value to be FALSE
				
			// If the Vector doesn't contain the other GameObject
			} else {
				alreadyCollided.add(otherObj);	// Add the other GameObject to the Vector to keep track of it
				result = true;		// Set the collision boolean value to be TRUE
			}
				
		// If there is no longer a collision between the two GameObjects, remove the other GameObject from the Vector
		} else {
			alreadyCollided.remove(otherObj);
			result = false;
		}
		return result;
	}
}
