/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideDroneCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public CollideDroneCommand(GameWorld gw) {
		super("Collide with Drone");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Collide with Drone command is invoked...");
		//gw.droneCollision();			// Invoke GameWorld's droneCollision() method		DEPRECATED For A3
	}

}
