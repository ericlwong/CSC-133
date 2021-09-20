/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class EnergyStation extends Fixed {
	private int capacity;
	private CollisionHandler collisionHandler = new CollisionHandler(this);
	private boolean selected;
	private GameWorld gw;
	
	// Constructor for energy stations
	public EnergyStation(GameWorld gw) {
		this.capacity = this.getSize();
		this.gw = gw;
	}
	
	
	// Set the energy station's capacity
	public void setCapacity(int num) {
		this.capacity = num;
	}
	
	// Get the energy station's capacity
	public int getCapacity() {
		return this.capacity;
	}
	
	public String toString() {
		return "Energy Station: " + super.toString() + " capacity=" + this.getCapacity();
	}


	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int energyXLocation = (int)super.getXLocation();			// Retrieve the EnergyStation's X-coordinate
		int energyYLocation = (int)super.getYLocation();			// Retrieve the EnergyStation's Y-coordinate
		String energyText = "" + this.getCapacity();
		
		g.setColor(super.getColor());				// Set the color of the EnergyStation's shape to be the color of the EnergyStation
		
		// If game is in play mode, selected items need to be unselected
		 if (gw.getPaused() == false)
		 	selected = false;
		
		// If EnergyStation is selected, draw it unfilled
		if (selected) 
			g.drawArc((int)pCmpRelPrnt.getX() + energyXLocation - (this.getSize() / 2), (int)pCmpRelPrnt.getY() + energyYLocation - (this.getSize() / 2), 2 * this.getSize(), 2 * this.getSize(), 0, 360);
		// Otheriwse, draw it filled
		else
			g.fillArc((int)pCmpRelPrnt.getX() + energyXLocation - (this.getSize() / 2), (int)pCmpRelPrnt.getY() + energyYLocation - (this.getSize() / 2), 2 * this.getSize(), 2 * this.getSize(), 0, 360);
		
		// Write in the capacity of each Energy Station on its shape
		g.setColor(ColorUtil.BLACK);
		
		// Centers the font in the EnergyStation's shape
		Font f = g.getFont();
		String toWrite = "" + this.capacity;
		int width = f.stringWidth(toWrite);
		int height = f.getHeight();
		g.drawString(toWrite, (int)(pCmpRelPrnt.getX() + energyXLocation), (int)(pCmpRelPrnt.getY() + energyYLocation));
	}


	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = collisionHandler.oneCollision(otherObject);
		return result;
	}


	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		
		// Check if other object is the PlayerCyborg or if it is a NPC Cyborg
		if (otherObject instanceof PlayerCyborg || otherObject instanceof NonPlayerCyborg) 
			gw.energyCollision(this);			// Call energyCollision method in GameWorld	
	}


	@Override
	public void setSelected(boolean b) {
		if (gw.getPaused()) 
			this.selected = b;	
	}


	@Override
	public boolean isSelected() {
		return this.selected;	
	}


	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		// Pointer location relative to parent's origin
		int px = (int)pPtrRelPrnt.getX(); 
		int py = (int)pPtrRelPrnt.getY();
				
		// Base's shape location relative to parent's origin
		int xLoc = (int)(pCmpRelPrnt.getX()) + (int)(this.getXLocation());
		int yLoc = (int)(pCmpRelPrnt.getY()) + (int)(this.getYLocation());
				
		if ((px >= xLoc - this.getSize() / 2) && (px <= xLoc + this.getSize() / 2) && (py >= yLoc - this.getSize() / 2) && (py <= yLoc + this.getSize() / 2)) 
			return true;
		else 
			return false;
	}
}
