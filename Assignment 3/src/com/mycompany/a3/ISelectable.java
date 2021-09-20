/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface ISelectable {
	// Method to set an object as selected or not
	public void setSelected(boolean b);
	
	// Method to see if an object is selected
	public boolean isSelected();
	
	// Method to see if a pointer is in an object
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	
	// Method to draw the object
	public void draw(Graphics g, Point pCmpRelPrnt);
}
