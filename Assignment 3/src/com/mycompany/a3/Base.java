/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class Base extends Fixed {
	private int sequenceNumber;
	private CollisionHandler collisionHandler = new CollisionHandler(this);
	private boolean selected;
	private GameWorld gw;
	
	// Constructor for bases
	public Base(int seqNum, GameWorld gw) {
		this.sequenceNumber = seqNum;
		this.gw = gw;
	}
	
	// Set the base's sequence number
	public void setSequenceNum(int num) {
		this.sequenceNumber = num;
	}
	
	// Get the base's sequence number
	public int getSequenceNum() {
		return this.sequenceNumber;
	}
	
	public String toString() {
		return "Base: " + super.toString() + " seqNum=" + this.getSequenceNum();
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
 		int baseXLocation = (int)super.getXLocation();				// Retrieve the Base's X-coordinate
 		int baseYLocation = (int)super.getYLocation(); 				// Retrieve the Base's Y-coordinate
 		
 		// Create points of each corner of the isosceles triangle
 		Point topCorner = new Point(pCmpRelPrnt.getX() + baseXLocation, pCmpRelPrnt.getY() + baseYLocation + (this.getSize() / 2));
 		Point botLeftCorner = new Point(pCmpRelPrnt.getX() + baseXLocation - (this.getSize() / 2), pCmpRelPrnt.getY() + baseYLocation - (this.getSize() / 2));
 		Point botRightCorner = new Point(pCmpRelPrnt.getX() + baseXLocation + (this.getSize() / 2), pCmpRelPrnt.getY() + baseYLocation - (this.getSize() /2));
 		
 		// Store the X and Y coordinates of the triangle's corners into arrays
 		int[] xPoints = {(int)topCorner.getX(), (int)botLeftCorner.getX(), (int)botRightCorner.getX()};
 		int[] yPoints = {(int)topCorner.getY(), (int)botLeftCorner.getY(), (int)botRightCorner.getY()};
 		
 		g.setColor(super.getColor());				// Set color of the Base's shape to be the color of the Base
		
 		// If game is in play mode, selected items need to be unselected
 		if (gw.getPaused() == false)
 			selected = false;
 		
 		if (selected) 
 			g.drawPolygon(xPoints, yPoints, 3);			// Draw the Base's isosceles triangle unfilled if selected
 		else
 			g.fillPolygon(xPoints, yPoints, 3);			// Fill the Base's triangle if unselected
 		
 		// Write each Base's sequence number on the shape
 		g.setColor(ColorUtil.BLACK);		// Set the font to be BLACK
 		
 		// Centers the font in the Base's shape
 		Font f = g.getFont();
		String toWrite = "" + this.sequenceNumber;
		int width = f.stringWidth(toWrite);
		int height = f.getHeight();
		g.drawString(toWrite, (int)(pCmpRelPrnt.getX() + baseXLocation - width / 2), (int)(pCmpRelPrnt.getY() + baseYLocation - height / 2));
		
	
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = collisionHandler.oneCollision(otherObject);
		return result;
	}

	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		
		// Check if other object is the PlayerCyborg or if it is a NPC Cyborg
		if (otherObject instanceof PlayerCyborg) { 
			int baseNum = this.sequenceNumber;		// Retrieve this base's sequence number
			gw.baseCollision(baseNum, otherObject);					// Call GameWorld's baseCollision method
		} else if (otherObject instanceof NonPlayerCyborg) {
			int baseNum = this.sequenceNumber;		// Retrieve this base's sequence number
			gw.baseCollision(baseNum, otherObject);					// Call GameWorld's baseCollision method
		}
			
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
