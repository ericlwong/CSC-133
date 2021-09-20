/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideEnergyCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public CollideEnergyCommand(GameWorld gw) {
		super("Collide with Energy Station");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Collide with Energy Station command is invoked...");
		//gw.energyCollision();			// Invoke GameWorld's energyCollision() method		DEPRECATED For A3
	}
}
