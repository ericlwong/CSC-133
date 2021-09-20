/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideNPCCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public CollideNPCCommand(GameWorld gw) {
		super("Collide with NPC");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Collide with NPC command is invoked...");
		//gw.cyborgCollision();			// Invoke GameWorld's cyborgCollision() method		DEPRECATED for A3
	}

}
