/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public abstract class GameObject implements IDrawable, ICollider {
	
	private int size;
	private Point location;
	private int color;
	
	//public GameObject() { }
	
	// Constructor to create game objects
	public GameObject() {
		// Default values of each game object
		this.size = 0;
		this.location = new Point(0, 0);
		this.color = ColorUtil.rgb(0, 0, 0);
	}
	
	// Set game object's size
	public void setSize(int newSize) {
		this.size = newSize;
	}
	
	// Obtain game object's size
	public int getSize() {
		return size;
	}
	
	// Set game object's location
	public void setLocation(float x, float y) {
		this.location.setX(x); // Set object's x-coordinate
		this.location.setY(y); // Set object's y-coordinate
		
	}
	
	// Obtain game object's location
	public float getXLocation() {
		return this.location.getX();
	}
	
	public float getYLocation() {
		return this.location.getY();
	}
	
	// Set game object's color
	public void setColor(int r, int g, int b) {
		this.color = ColorUtil.rgb(r, g, b); 
	}
	
	// Obtain game object's color
	public int getColor() {
		return this.color;
	}
	
	public String toString() {
		return "loc=" + Math.round(this.getXLocation() * 10.0)/10.0 + "," + Math.round(this.getYLocation() * 10.0)/10.0 + " color=[" + ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + "," + ColorUtil.blue(this.getColor()) + "] size=" + this.getSize();
	}
	

}

