/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {
	private GameWorld gw;
	private Point mapOrigin;
	
	// Constructor
	public MapView(GameWorld gw) {
		this.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.rgb(255, 0, 0)));
		this.gw = gw;	
		mapOrigin = new Point();
	}

	@Override
	public void update(Observable observable, Object data) {
		((GameWorld)observable).map();		// Display the state values of all the game objects in the GameWorld
		mapOrigin.setX(this.getX());		// Set the map's X-coordinate origin relative to the parent container
		mapOrigin.setY(this.getY());		// Set the map's Y-coordinate origin relative to the parent contianer
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		IIterator<GameObject> iterator = gw.getIterator();
		
		while (iterator.hasNext()) {
			GameObject currElement = iterator.getNext();
			
			if (currElement instanceof IDrawable) {
				currElement.draw(g, mapOrigin);
			}
			

		}
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		
		// Check if Position button is pressed
		if (gw.getPositionPressed() == false) {
			// Make pointer location relative to parent's origin
			Point pPtrRelPrnt = new Point(x, y);
			Point pCmpRelPrnt = new Point(getX(), getY());
				
			IIterator<GameObject> iterator = gw.getIterator();	
			while (iterator.hasNext()) {
				GameObject currElement = iterator.getNext();
				if (currElement instanceof Base) {
					if (((Base) currElement).contains(pPtrRelPrnt, pCmpRelPrnt)) {
						((Base) currElement).setSelected(true);
					} else {
						((Base) currElement).setSelected(false);
					}
				} else if (currElement instanceof EnergyStation) {
					if (((EnergyStation)currElement).contains(pPtrRelPrnt, pCmpRelPrnt)) {
						((EnergyStation)currElement).setSelected(true);
					} else {
						((EnergyStation)currElement).setSelected(false);
					}
				}
			}
		
		// Position button is pressed
		} else {
			IIterator<GameObject> iterator = gw.getIterator();
			while (iterator.hasNext()) {
				GameObject currElement = iterator.getNext();
				
				// If GameObject is a selected Base, set its new location
				if (currElement instanceof Base && ((Base)currElement).isSelected()) {
					((Base)currElement).setLocation(x - getX(), y - getY());
					((Base)currElement).setSelected(false);			// Deselect the GameObject after setting it in its new location
					gw.pressedPositionButton();						// Treat the Position button as if it was not pressed 
				
				// If GameObject is a selected EnergyStation, set its new location
				} else if (currElement instanceof EnergyStation && ((EnergyStation)currElement).isSelected()) {
					((EnergyStation)currElement).setLocation(x - getX(), y - getY());		
					((EnergyStation)currElement).setSelected(false);	// Deselect the GameObject after choosing its new location
					gw.pressedPositionButton();							// Treat the position button as if it was not pressed
				}
			}
		}
		repaint();
	}
}
